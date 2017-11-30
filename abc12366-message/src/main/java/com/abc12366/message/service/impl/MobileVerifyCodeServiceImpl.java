package com.abc12366.message.service.impl;

import com.abc12366.gateway.component.SpringCtxHolder;
import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.MessageConstant;
import com.abc12366.gateway.util.RestTemplateUtil;
import com.abc12366.gateway.util.Utils;
import com.abc12366.message.config.ApplicationConfig;
import com.abc12366.message.mapper.db1.MessageSendLogMapper;
import com.abc12366.message.mapper.db1.PhoneCodeMapper;
import com.abc12366.message.mapper.db2.MsgUcUserRoMapper;
import com.abc12366.message.mapper.db2.PhoneCodeRoMapper;
import com.abc12366.message.model.MessageSendLog;
import com.abc12366.message.model.PhoneCode;
import com.abc12366.message.model.PhoneExist;
import com.abc12366.message.model.bo.*;
import com.abc12366.message.service.MobileVerifyCodeService;
import com.abc12366.message.service.SendMsgLogService;
import com.abc12366.message.util.CheckSumBuilder;
import com.abc12366.message.util.RandomNumber;
import com.abc12366.message.util.WeightFactorProduceStrategy;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
    private ApplicationConfig config;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PhoneCodeMapper phoneCodeMapper;

    @Autowired
    private PhoneCodeRoMapper phoneCodeRoMapper;

    @Autowired
    private MessageSendLogMapper sendLogMapper;

    @Autowired
    private MsgUcUserRoMapper msgUcUserRoMapper;

    @Autowired
    private SendMsgLogService sendMsgLogService;

    @Override
    public void getCode(String type, String phone) throws IOException {
        //五分钟之内重复获取验证码，就发送之前的那个
        String code = getPriviousCode(type, phone);
        if (code == null || "".equals(code.trim())) {
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

        //根据轮询发送短信消息
        String channel = WeightFactorProduceStrategy.getInstance().getPartitionIdForTopic();
        LOGGER.info("短信发送通道[" + channel + "],内容:" + (type + code));
        if (MessageConstant.MSG_CHANNEL_ALI.equals(channel)) {
            sendAliYunMsg(phone, type, code, MessageConstant.ALIYUNTEMP_YZM);
        } else if (MessageConstant.MSG_CHANNEL_YOUPAI.equals(channel)) {
            sendYoupaiTemplate(phone, type, code);
        } else {
            sendNeteaseTemplate(phone, type, code);
        }

    }

    @Override
    public void verify(VerifyParam verifyParam) {
        PhoneCode phoneCodeParam = new PhoneCode();
        phoneCodeParam.setType(verifyParam.getType().trim());
        phoneCodeParam.setPhone(verifyParam.getPhone().trim());
        phoneCodeParam.setCode(verifyParam.getCode().trim());
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
        PhoneCode del = new PhoneCode();
        del.setPhone(phoneCodeBO.getPhone());
        del.setType(phoneCodeBO.getType());
        phoneCodeMapper.delete(del);

    }

    /**
     * 阿里云信息发送
     *
     * @param phone   手机号码
     * @param type    短信类型
     * @param msg     消息内容
     * @param temCode 模板ID
     * @return 是否发送成功
     */
    @Override
    public boolean sendAliYunMsg(String phone, String type, String msg, String temCode) {
        try {
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");

            // 短信API产品名称（短信产品名固定，无需修改）
            final String product = "Dysmsapi";
            // 短信API产品域名（接口地址固定，无需修改）
            final String domain = "dysmsapi.aliyuncs.com";
            // 你的accessKeyId "LTAItz4dVk9FX02a"
            final String accessKeyId = SpringCtxHolder.getProperty("message.aliyun.accessid");
            // 你的accessKeySecret "m9FiMcm2nmh7gj9uXQIx0mzNcZQzN5"
            final String accessKeySecret = SpringCtxHolder.getProperty("message.aliyun.accesskey");
            // 初始化ascClient,暂时不支持多region（请勿修改）
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                    accessKeySecret);

            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);

            SendSmsRequest request = new SendSmsRequest();
            // 使用post提交
            request.setMethod(MethodType.POST);
            // 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,
            // 批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
            request.setPhoneNumbers(phone);

            request.setSignName(SpringCtxHolder.getProperty("message.aliyun.signname"));

            request.setTemplateCode(temCode);

            String sendMsg;

            if (MessageConstant.ALIYUNTEMP_YZM.equals(temCode)) {
                sendMsg = "{\"code\":\"" + msg + "\"}";
            } else {
                sendMsg = "{\"name\":\"" + msg + "\"}";
            }
            request.setTemplateParam(sendMsg);

            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            // 请求成功
            if (sendSmsResponse.getCode() != null && "OK".equals(sendSmsResponse.getCode())) {
                messageLog(MessageConstant.MSG_CHANNEL_ALI, phone, type, type + ":" + msg, MessageConstant
                        .SEND_MSG_STATUS_SUCCESS, MessageConstant.SEND_MSG_SUCCESS_CODE, MessageConstant
                        .SEND_MSG_SUCCESS_CONTENT);
                return true;
            } else {
                messageLog(MessageConstant.MSG_CHANNEL_ALI, phone, type, type + ":" + msg, MessageConstant
                        .SEND_MSG_STATUS_FAIL, sendSmsResponse.getCode(), sendSmsResponse.getMessage());
                return false;
            }
        } catch (ClientException e) {
            messageLog(MessageConstant.MSG_CHANNEL_ALI, phone, type, type + ":" + msg, MessageConstant
                    .SEND_MSG_STATUS_FAIL, "SYSTEM.EXCEPTION", e.getMessage());
            return false;
        }
    }

    @Override
    public void getRegisCode(String type, String phone) {
        PhoneExist user = msgUcUserRoMapper.selectPhoneExist(phone);
        if (user != null) {
            throw new ServiceException(4117);
        }
        try {
            getCode(type, phone);
        } catch (Exception e) {
            LOGGER.info("发送短信失败：{}", e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 调用网易短信服务接口请求头设置
     *
     * @return HttpHeaders请求头
     */
    private HttpHeaders getHeader() {
        String nonce = Utils.uuid();
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(config.getAppSecret(), nonce, curTime);
        //请求头设置
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("appKey", config.getAppKey());
        httpHeaders.add("appSecret", config.getAppSecret());
        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");
        httpHeaders.add("charset", "utf-8");
        httpHeaders.add("Nonce", nonce);
        httpHeaders.add("CurTime", curTime);
        httpHeaders.add("CheckSum", checkSum);
        return httpHeaders;
    }

    /**
     * 网易发送短信
     *
     * @param phone 手机号
     * @param type  验证码类型
     * @param code  验证码
     * @return 是否发送成功
     * @throws IOException
     */
    private boolean sendNeteaseTemplate(String phone, String type, String code) throws IOException {
        //发送通知类短信接口地址
        String url = SpringCtxHolder.getProperty("message.netease.api.url") + "/sendtemplate.action";
        String templateId = SpringCtxHolder.getProperty("message.netease.templateid");
        //调用网易接口请求头设置
        HttpHeaders httpHeaders = getHeader();
        //调用网易接口请求体设置
        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("templateid", templateId);
        requestBody.add("mobiles", "['" + phone + "']");
        requestBody.add("params", "['" + type + "','" + code + "']");

        HttpEntity requestEntity = new HttpEntity(requestBody, httpHeaders);
        ResponseEntity neteaseResponse;
        try {
            neteaseResponse = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        } catch (Exception e) {
            return false;
        }
        if (neteaseResponse != null && neteaseResponse.getStatusCode().is2xxSuccessful() && neteaseResponse.hasBody()) {
            NeteaseTemplateResponseBO neteaseTemplateResponseBO = JSON.parseObject(String.valueOf(neteaseResponse
                    .getBody()), NeteaseTemplateResponseBO.class);
            String respCode = neteaseTemplateResponseBO.getCode();
            String msg = neteaseTemplateResponseBO.getMsg();
            if ("200".equals(respCode)) {
                //记日志
                messageLog("wy", phone, type, type + ":" + code, "1", respCode, "发送成功");
                return true;
            } else if ("416".equals(respCode) || "417".equals(respCode) || "419".equals(respCode)) {
                //如果发送失败状态码是416、417、419中的一个，就将异常信息抛出给用户
                //记日志
                messageLog("wy", phone, type, type + ":" + code, "4", respCode, msg);
                throw new ServiceException(respCode, msg);
            } else {
                //记日志
                messageLog("wy", phone, type, type + ":" + code, "4", "4204", "网易短信发送通道异常");
                return false;
            }
        }
        //记日志
        messageLog("wy", phone, type, type + ":" + code, "4", "4204", "网易短信发送通道异常");
        return false;
    }

    /**
     * 五分钟之内重复获取验证码，就发送之前的那个
     *
     * @param type  类型
     * @param phone 手机号
     * @return 验证码
     */
    private String getPriviousCode(String type, String phone) {
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


    /**
     * 发送又拍云短信
     *
     * @param phone 手机号
     * @param type  验证码类型
     * @param code  验证码
     * @return 发送是否成功
     * @throws IOException
     */
    private boolean sendYoupaiTemplate(String phone, String type, String code) throws IOException {
        //发送通知类短信接口地址
        String url = SpringCtxHolder.getProperty("message.upyun.send.url") + "/messages";
        //调用又拍接口请求头设置
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");
        httpHeaders.add("Authorization", SpringCtxHolder.getProperty("message.upyun.auth"));
        //调用又拍接口请求体设置
        LinkedMultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("mobile", phone);
        requestBody.add("template_id", MessageConstant.MESSAGE_UPYUN_TEMPLATE_296);
        requestBody.add("vars", type + "|" + code);
        HttpEntity entity = new HttpEntity(requestBody, httpHeaders);
        ResponseEntity responseEntity;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        } catch (Exception e) {
            //记日志
            MessageSendLog sendLog = new MessageSendLog(
                    MessageConstant.MSG_CHANNEL_YOUPAI,
                    phone, MessageConstant
                    .MOBILE_MSG_BUSI_TYPE,
                    (type + ":" + code),
                    MessageConstant.SEND_MSG_STATUS_FAIL,
                    MessageConstant.SEND_MSG_CHANNEL_ERROR_CODE,
                    MessageConstant.SEND_MSG_CHANNEL_ERROR_YOUPAI);
            sendMsgLogService.insert(sendLog);
            throw new ServiceException(4204);
        }
        if (RestTemplateUtil.isExchangeSuccessful(responseEntity)) {
            try {
                UpyunMessageResponse umr = JSON.parseObject(String.valueOf(responseEntity.getBody()),
                        UpyunMessageResponse.class);
                LOGGER.info("{}", umr);
                //记日志
                MessageSendLog sendLog = new MessageSendLog(
                        MessageConstant.MSG_CHANNEL_YOUPAI,
                        phone,
                        MessageConstant.MOBILE_MSG_BUSI_TYPE,
                        (type + ":" + code),
                        MessageConstant.SEND_MSG_STATUS_SUCCESS,
                        MessageConstant.SEND_MSG_SUCCESS_CODE,
                        MessageConstant.SEND_MSG_SUCCESS_CONTENT);
                sendMsgLogService.insert(sendLog);
            } catch (Exception e) {
                UpyunErrorBO response = JSON.parseObject(String.valueOf(responseEntity.getBody()), UpyunErrorBO.class);
                //记日志
                MessageSendLog sendLog = new MessageSendLog(
                        MessageConstant.MSG_CHANNEL_YOUPAI,
                        phone,
                        MessageConstant.MOBILE_MSG_BUSI_TYPE,
                        (type + ":" + code),
                        MessageConstant.SEND_MSG_STATUS_FAIL,
                        response.getError_code(),
                        response.getMessage());
                sendMsgLogService.insert(sendLog);
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 发送短信消息日志
     *
     * @param sendchanel 通道类型
     * @param biztype    短信类型
     * @param sendinfo   内容
     * @param sendstatus 发送状态
     * @param failcode   失败code
     * @param failcause  失败原因
     */
    private void messageLog(String sendchanel, String phone, String biztype, String sendinfo, String sendstatus,
                            String failcode, String failcause) {
        MessageSendLog sendLog = new MessageSendLog();
        Date time = new Date();
        sendLog.setId(Utils.uuid());
        sendLog.setSendchanel(sendchanel);
        sendLog.setBiztype(biztype);
        sendLog.setSendinfo(sendinfo);
        sendLog.setSendtime(time);
        sendLog.setSendstatus(sendstatus);
        sendLog.setFailcode(failcode);
        sendLog.setFailcause(failcause);
        sendLog.setLogtime(time);
        sendLog.setPhone(phone);
        sendLogMapper.insert(sendLog);
    }
}
