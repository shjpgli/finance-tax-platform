package com.abc12366.message.service.impl;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Properties;
import com.abc12366.gateway.util.Utils;
import com.abc12366.message.mapper.db1.PhoneCodeMapper;
import com.abc12366.message.mapper.db2.PhoneCodeRoMapper;
import com.abc12366.message.model.PhoneCode;
import com.abc12366.message.model.bo.*;
import com.abc12366.message.service.MobileVerifyCodeService;
import com.abc12366.message.util.CheckSumBuilder;
import com.abc12366.message.util.MessageConstant;
import com.abc12366.message.util.RandomNumber;
import com.abc12366.message.util.soaUtil;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-20
 * Time: 0:10
 */
@Service
public class MobileVerifyCodeServiceImpl implements MobileVerifyCodeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MobileVerifyCodeServiceImpl.class);
    private static Properties properties = new Properties("application.properties");
    private static String appKey;
    private static String appSecret;
    private static String contentType;
    private static String charset;

    static {
        try {
            appKey = properties.getValue("message.netease.appKey");//"2dea65aed55012fd8e4686177392412e";
            appSecret = properties.getValue("message.netease.appSecret");//"cf03fe4b439f";
            contentType = properties.getValue("message.netease.contentType");//"application/x-www-form-urlencoded";
            charset = properties.getValue("message.netease.charset");//"utf-8";
        } catch (IOException e) {
            LOGGER.warn("网易短信服务接口参数加载异常！");
            throw new ServiceException(4802);
        }
    }

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PhoneCodeMapper phoneCodeMapper;
    @Autowired
    private PhoneCodeRoMapper phoneCodeRoMapper;

    public static void main(String[] args) throws IOException {
        new MobileVerifyCodeServiceImpl().sendYoupaiTemplate("13278849423", "9876987");
    }

    //获取验证码
    @Transactional("db1TxManager")
    @Override
    public void getCode(String type, String phone) throws IOException {
        LOGGER.info("{}:{}", type, phone);
        //手机号码不是11为抛出异常
        if (!(phone.trim().length() == 11)) {
            throw new ServiceException(4801);
        }

        //五分钟之内重复获取验证码，就发送之前的那个
        String code = getPriviousCode(type, phone);
        if (code == null || code.trim().equals("")) {
            code = RandomNumber.getRandomNumber(MessageConstant.VERIFY_CODE_LENGTH);
            //将验证码信息写入表
            PhoneCode phoneCodeDel = new PhoneCode();
            phoneCodeDel.setType(type);
            phoneCodeDel.setPhone(phone);
            phoneCodeMapper.delete(phoneCodeDel);

            PhoneCode phoneCode = new PhoneCode();
            phoneCode.setId(Utils.uuid());
            phoneCode.setPhone(phone);
            phoneCode.setCode(code);
            phoneCode.setExpireDate(new Date(System.currentTimeMillis() + 1000 * MessageConstant
                    .VERIFY_CODE_VALID_SECONDS));
            phoneCode.setType(type);
            phoneCodeMapper.insert(phoneCode);
        }
        boolean sendCodeThroghNetease = sendNeteaseTemplate(phone, MessageConstant.VERIFY_CODE_FILL_CONTENT, code);
        //调用网易短信接口不成功，则换调用又拍云短信接口
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!sendCodeThroghNetease) {
            boolean sendCodeThroghUpyun = sendYoupaiTemplate(phone, code);
            if (!sendCodeThroghUpyun) {
                throw new ServiceException(4204);
            }
        }

    }

    @Override
    public void verify(VerifyParam verifyParam) {
        LOGGER.info("{}", verifyParam);
        PhoneCode phoneCodeParam = new PhoneCode();
        phoneCodeParam.setType(verifyParam.getType());
        phoneCodeParam.setPhone(verifyParam.getPhone());
        phoneCodeParam.setCode(verifyParam.getCode());
        List<PhoneCodeBO> phoneCodeBOList = phoneCodeRoMapper.selectList(phoneCodeParam);
        if (phoneCodeBOList == null) {
            throw new ServiceException(4202);
        }

        if (phoneCodeBOList.size() != 1) {
            throw new ServiceException(4201);
        }
        PhoneCodeBO phoneCodeBO = phoneCodeBOList.get(0);

        long now = System.currentTimeMillis();
        long exp = phoneCodeBO.getExpireDate().getTime();
        boolean isValid = exp > now;

        if (!isValid) {
            throw new ServiceException(4203);
        }

    }

    //调用网易短信服务接口请求头设置
    private HttpHeaders getHeader() {
        String nonce = Utils.uuid();
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);
        //请求头设置
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("appKey", appKey);
        httpHeaders.add("appSecret", appSecret);
        httpHeaders.add("Content-Type", contentType);
        httpHeaders.add("charset", charset);
        httpHeaders.add("Nonce", nonce);
        httpHeaders.add("CurTime", curTime);
        httpHeaders.add("CheckSum", checkSum);
        return httpHeaders;
    }

    private boolean sendNeteaseTemplate(String phone, String codeType, String code) throws IOException {
        //发送通知类短信接口地址
        String url = properties.getValue("message.netease.url.sendtemplate");
        //调用网易接口请求头设置
        HttpHeaders httpHeaders = getHeader();
        //调用网易接口请求体设置
        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("templateid", MessageConstant.NEREASE_TEMPLATE_ID);
        requestBody.add("mobiles", "['" + phone + "']");
        requestBody.add("params", "['" + codeType + "','" + code + "']");

        HttpEntity requestEntity = new HttpEntity(requestBody, httpHeaders);
        ResponseEntity neteaseResponse;
        try {
            neteaseResponse = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        } catch (Exception e) {
            throw new ServiceException(4204);
        }
        if (neteaseResponse != null && neteaseResponse.getStatusCode().is2xxSuccessful() && neteaseResponse.hasBody()) {
            NeteaseTemplateResponseBO neteaseTemplateResponseBO = JSON.parseObject(String.valueOf(neteaseResponse.getBody()), NeteaseTemplateResponseBO.class);
            if (neteaseTemplateResponseBO != null && neteaseTemplateResponseBO.getCode().equals("200")) {
                return true;
                //return queryNeteaseStatus(neteaseTemplateResponseBO.getObj());
            }
        }
        return false;
    }

    private boolean queryNeteaseStatus(String obj) throws IOException {
        //调用网易接口查询发送信息状态
        String neteaseQueryUrl = properties.getValue("message.netease.url.querystatus");
        MultiValueMap<String, Object> requestQueryBody = new LinkedMultiValueMap<>();
        requestQueryBody.add("sendid", obj);

        HttpHeaders httpHeaders = getHeader();
        HttpEntity requestQueryEntity = new HttpEntity(requestQueryBody, httpHeaders);
        ResponseEntity queryStatusResponse = restTemplate.exchange(neteaseQueryUrl, HttpMethod.POST,
                requestQueryEntity, String.class);
        if (queryStatusResponse != null && queryStatusResponse.getStatusCode().is2xxSuccessful() &&
                queryStatusResponse.hasBody()) {
            NeteaseQueryStatusResponseBO neteaseQueryStatusResponseBO = objectMapper.readValue(((String)
                    queryStatusResponse.getBody()).getBytes(), NeteaseQueryStatusResponseBO.class);
            if (neteaseQueryStatusResponseBO.getCode().equals("200") && (neteaseQueryStatusResponseBO.getObj().get(0)
            ).getStatus().equals("1")) {
                //记日志
                SmsOpsLog smsOpsLog = new SmsOpsLog();
                return true;
            }
        }
        return false;
    }

    private String getPriviousCode(String type, String phone) {
        //五分钟之内重复获取验证码，就发送之前的那个
        PhoneCode phoneCode = new PhoneCode();
        phoneCode.setType(type);
        phoneCode.setPhone(phone);
        List<PhoneCodeBO> phoneCodeBOList = phoneCodeRoMapper.selectListByPhone(phoneCode);
        String privCode = "";
        if (phoneCodeBOList != null && phoneCodeBOList.size() > 0) {
            PhoneCodeBO phoneCodeBO = phoneCodeBOList.get(0);
            if (phoneCodeBO.getExpireDate().getTime() > System.currentTimeMillis()) {
                privCode = phoneCodeBOList.get(0).getCode();
            }
        }
        return privCode;
    }


    private boolean sendYoupaiTemplate(String phone, String code) throws IOException {
        //发送通知类短信接口地址
        String url = properties.getValue("message.upyun.send.url");
        //调用网易接口请求头设置
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", contentType);
        httpHeaders.add("Authorization", properties.getValue("message.upyun.auth"));
        //调用网易接口请求体设置
        LinkedMultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("mobile", phone);
        requestBody.add("template_id", properties.getValue("message.upyun.templateid"));
        requestBody.add("vars", code);
        HttpEntity entity = new HttpEntity(requestBody, httpHeaders);
        ResponseEntity responseEntity;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        } catch (Exception e) {
            throw new ServiceException(4204);
        }
        if (soaUtil.isExchangeSuccessful(responseEntity)) {
            //UpyunMessageResponse response = JSON.parseObject(String.valueOf(responseEntity.getBody()),
            // UpyunMessageResponse.class);
            return true;
        }
        return false;
    }

}
