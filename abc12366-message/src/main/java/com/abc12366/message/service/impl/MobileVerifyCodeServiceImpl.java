package com.abc12366.message.service.impl;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Properties;
import com.abc12366.gateway.util.Utils;
import com.abc12366.message.mapper.db1.PhoneCodeMapper;
import com.abc12366.message.mapper.db2.PhoneCodeRoMapper;
import com.abc12366.message.model.PhoneCode;
import com.abc12366.message.model.bo.NeteaseQueryStatusResponseBO;
import com.abc12366.message.model.bo.NeteaseTemplateResponseBO;
import com.abc12366.message.model.bo.PhoneCodeBO;
import com.abc12366.message.model.bo.SmsOpsLog;
import com.abc12366.message.service.MobileVerifyCodeService;
import com.abc12366.message.util.CheckSumBuilder;
import com.abc12366.message.util.MessageConstant;
import com.abc12366.message.util.RandomNumber;
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

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PhoneCodeMapper phoneCodeMapper;

    @Autowired
    private PhoneCodeRoMapper phoneCodeRoMapper;

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

    //获取验证码
    @Transactional("db1TxManager")
    @Override
    public void getCode(String phone) throws IOException {
        LOGGER.info("{}", phone);
        //手机号码不是11为抛出异常
        if (!(phone.trim().length() == 11)) {
            throw new ServiceException(4801);
        }

        //五分钟之内重复获取验证码，就发送之前的那个
        String code = getPriviousCode(phone);
        if (code == null || code.trim().equals("")) {
            code = RandomNumber.getRandomNumber(MessageConstant.VERIFY_CODE_LENGTH);
            //将验证码信息写入表
            phoneCodeMapper.delete(phone);

            PhoneCode phoneCode = new PhoneCode();
            phoneCode.setId(Utils.uuid());
            phoneCode.setPhone(phone);
            phoneCode.setCode(code);
            phoneCode.setExpireDate(new Date(System.currentTimeMillis() + 1000 * MessageConstant.VERIFY_CODE_VALID_SECONDS));
            phoneCodeMapper.insert(phoneCode);
        }

        boolean sendCodeThroghNetease = sendNeteaseTemplate(phone, MessageConstant.VERIFY_CODE_FILL_CONTENT, code);
        //调用网易短信接口不成功，则换调用阿里云短信接口
//        if (!sendCodeThroghNetease) {
//            sendAliyunTemplate(phone, code);
//        }

    }

    @Override
    public void verify(String phone, String code) {
        LOGGER.info("{}:{}:{}", phone, code);
        PhoneCode phoneCodeParam = new PhoneCode();
        phoneCodeParam.setPhone(phone);
        phoneCodeParam.setCode(code);
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
        ResponseEntity neteaseResponse = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        if (neteaseResponse != null && neteaseResponse.getStatusCode().is2xxSuccessful() && neteaseResponse.hasBody()) {
            NeteaseTemplateResponseBO neteaseTemplateResponseBO = objectMapper.readValue(((String) neteaseResponse.getBody()).getBytes(), NeteaseTemplateResponseBO.class);
            if (neteaseTemplateResponseBO != null && neteaseTemplateResponseBO.getCode().equals("200")) {
                return queryNeteaseStatus(neteaseTemplateResponseBO.getObj());
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
        ResponseEntity queryStatusResponse = restTemplate.exchange(neteaseQueryUrl, HttpMethod.POST, requestQueryEntity, String.class);
        if (queryStatusResponse != null && queryStatusResponse.getStatusCode().is2xxSuccessful() && queryStatusResponse.hasBody()) {
            NeteaseQueryStatusResponseBO neteaseQueryStatusResponseBO = objectMapper.readValue(((String) queryStatusResponse.getBody()).getBytes(), NeteaseQueryStatusResponseBO.class);
            if (neteaseQueryStatusResponseBO.getCode().equals("200") && (neteaseQueryStatusResponseBO.getObj().get(0)).getStatus().equals("1")) {
                //记日志
                SmsOpsLog smsOpsLog = new SmsOpsLog();
                return true;
            }
        }
        return false;
    }

    private String getPriviousCode(String phone) {
        //五分钟之内重复获取验证码，就发送之前的那个
        List<PhoneCodeBO> phoneCodeBOList = phoneCodeRoMapper.selectListByPhone(phone);
        String privCode = "";
        if (phoneCodeBOList != null && phoneCodeBOList.size() > 0) {
            PhoneCodeBO phoneCodeBO = phoneCodeBOList.get(0);
            if (phoneCodeBO.getExpireDate().getTime() > System.currentTimeMillis()) {
                privCode = phoneCodeBOList.get(0).getCode();
            }
        }
        return privCode;
    }

    private boolean sendYoupaiTemplate(String phone, String code){
        //发送通知类短信接口地址
        String url = "https://sms-api.upyun.com/api/messages";
        //调用网易接口请求头设置
        HttpHeaders httpHeaders = getHeader();
        //调用网易接口请求体设置
        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("mobile", phone);
        requestBody.add("template_id", "789");
        requestBody.add("vars", code);
        HttpEntity entity = new HttpEntity(requestBody, httpHeaders);
        ResponseEntity responseEntity = new RestTemplate().exchange(url, HttpMethod.POST, entity, String.class);
        return true;
    }

    public static void main(String[] args) {
        new MobileVerifyCodeServiceImpl().sendYoupaiTemplate("13278849423", "123546");
    }

//    private boolean sendAliyunTemplate(String phone, String code) throws IOException {
//        /**
//         * Step 1. 获取主题引用
//         */
//        String accessId = properties.getValue("message.aliyun.accesskey.id");
//        String accessKey = properties.getValue("message.aliyun.accesskey.secret");
//        String mnsEndpoint = properties.getValue("message.aliyun.endpoint");
//        String myTopic = properties.getValue("message.aliyun.topic");
//        String mySignature = properties.getValue("message.aliyun.signature");
//        String templateCode = properties.getValue("message.aliyun.template.code");
//        String messageBody = properties.getValue("message.aliyun.messagebody");
//
//        CloudAccount account = new CloudAccount(accessId, accessKey, mnsEndpoint);
//        MNSClient client = account.getMNSClient();
//        CloudTopic topic = client.getTopicRef(myTopic);
//        /**
//         * Step 2. 设置SMS消息体（必须）
//         *
//         * 注：目前暂时不支持消息内容为空，需要指定消息内容，不为空即可。
//         */
//        RawTopicMessage msg = new RawTopicMessage();
//        msg.setMessageBody(messageBody);
//        /**
//         * Step 3. 生成SMS消息属性
//         */
//        MessageAttributes messageAttributes = new MessageAttributes();
//        BatchSmsAttributes batchSmsAttributes = new BatchSmsAttributes();
//        // 3.1 设置发送短信的签名（SMSSignName）
//        batchSmsAttributes.setFreeSignName(mySignature);
//        // 3.2 设置发送短信使用的模板（SMSTempateCode）
//        batchSmsAttributes.setTemplateCode(templateCode);
//        // 3.3 设置发送短信所使用的模板中参数对应的值（在短信模板中定义的，没有可以不用设置）
//        BatchSmsAttributes.SmsReceiverParams smsReceiverParams = new BatchSmsAttributes.SmsReceiverParams();
//        smsReceiverParams.setParam("code", code);
//        // 3.4 增加接收短信的号码
//        batchSmsAttributes.addSmsReceiver(phone, smsReceiverParams);
//        messageAttributes.setBatchSmsAttributes(batchSmsAttributes);
//        try {
//            /**
//             * Step 4. 发布SMS消息
//             */
//            TopicMessage ret = topic.publishMessage(msg, messageAttributes);
//            System.out.println("MessageId: " + ret.getMessageId());
//            System.out.println("MessageMD5: " + ret.getMessageBodyMD5());
//        } catch (com.aliyun.mns.common.ServiceException se) {
//            System.out.println(se.getErrorCode() + se.getRequestId());
//            System.out.println(se.getMessage());
//            se.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        client.close();
//        return true;
//    }
}
