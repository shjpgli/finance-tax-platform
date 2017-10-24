package com.abc12366.message.service.impl;

import com.abc12366.gateway.component.SpringCtxHolder;
import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.message.config.ApplicationConfig;
import com.abc12366.message.mapper.db2.MessageSendLogRoMapper;
import com.abc12366.message.model.MessageSendLog;
import com.abc12366.message.model.bo.MobileMsgBO;
import com.abc12366.message.model.bo.UpyunErrorBO;
import com.abc12366.message.model.bo.UpyunMessageResponse;
import com.abc12366.message.service.SendMobileMsgService;
import com.abc12366.message.service.SendMsgLogService;
import com.abc12366.message.util.MessageConstant;
import com.abc12366.message.util.soaUtil;
import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudTopic;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.BatchSmsAttributes;
import com.aliyun.mns.model.MessageAttributes;
import com.aliyun.mns.model.RawTopicMessage;
import com.aliyun.mns.model.TopicMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-10-24
 * Time: 9:59
 */
@Service
public class SendMobileMsgServiceImpl implements SendMobileMsgService{

    private static final Logger LOGGER = LoggerFactory.getLogger(SendMobileMsgServiceImpl.class);


    @Autowired
    private ApplicationConfig config;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MessageSendLogRoMapper messageSendLogRoMapper;

    @Autowired
    private SendMsgLogService sendMsgLogService;


    @Override
    public void sendMsg(MobileMsgBO mobileMsgBO) {
        LOGGER.info("发送业务通知短信：{}", mobileMsgBO.toString());
        //版本4.0阿里和友拍轮流发
        List<MessageSendLog> sendLogList = messageSendLogRoMapper.selectLast();
        if (sendLogList == null || sendLogList.size() < 1) {
            sendMsgByAliyun(mobileMsgBO);
        } else {
            MessageSendLog messageSendLog = sendLogList.get(0);
            if (messageSendLog.getSendchanel().equals(MessageConstant.MSG_CHANNEL_ALI)) {
                sendMsgByUppyun(mobileMsgBO);
            } else {
                sendMsgByAliyun(mobileMsgBO);
            }
        }
    }

    @Override
    public void sendMsgByUppyun(MobileMsgBO mobileMsgBO){
            //发送通知类短信接口地址
            String url = SpringCtxHolder.getProperty("message.upyun.send.url");
            //调用又拍接口请求头设置
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Type", config.getContentType());
            httpHeaders.add("Authorization", SpringCtxHolder.getProperty("message.upyun.auth"));
            //调用又拍接口请求体设置
            LinkedMultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("mobile", mobileMsgBO.getPhone());
            requestBody.add("template_id", SpringCtxHolder.getProperty("message.upyun.inform.templateid"));
            requestBody.add("vars", mobileMsgBO.getContent());
            HttpEntity entity = new HttpEntity(requestBody, httpHeaders);
            ResponseEntity responseEntity;
            try {
                responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            } catch (Exception e) {
                //记日志
                MessageSendLog sendLog = new MessageSendLog(MessageConstant.MSG_CHANNEL_YOUPAI,mobileMsgBO.getPhone(), MessageConstant.MOBILE_MSG_BUSI_TYPE,
                        mobileMsgBO.getContent(), MessageConstant.SEND_MSG_STATUS_FAIL, MessageConstant.SEND_MSG_CHANNEL_ERROR_CODE,MessageConstant.SEND_MSG_CHANNEL_ERROR_YOUPAI);
                sendMsgLogService.insert(sendLog);
                LOGGER.info("发送业务通知短信失败，号码：{}，通道：又拍", mobileMsgBO.getContent());
                throw new ServiceException(4204);
            }
            if (soaUtil.isExchangeSuccessful(responseEntity)) {
                try {
                    UpyunMessageResponse essageResponse = JSON.parseObject(String.valueOf(responseEntity.getBody()), UpyunMessageResponse.class);
                    //记日志
                    MessageSendLog sendLog = new MessageSendLog(MessageConstant.MSG_CHANNEL_YOUPAI,mobileMsgBO.getPhone(), MessageConstant.MOBILE_MSG_BUSI_TYPE,
                    mobileMsgBO.getContent(), MessageConstant.SEND_MSG_STATUS_SUCCESS, MessageConstant.SEND_MSG_SUCCESS_CODE,MessageConstant.SEND_MSG_SUCCESS_CONTENT);
                    sendMsgLogService.insert(sendLog);
                } catch (Exception e) {
                    UpyunErrorBO response = JSON.parseObject(String.valueOf(responseEntity.getBody()), UpyunErrorBO.class);
                    //记日志
                    MessageSendLog sendLog = new MessageSendLog(MessageConstant.MSG_CHANNEL_YOUPAI,mobileMsgBO.getPhone(), MessageConstant.MOBILE_MSG_BUSI_TYPE,
                            mobileMsgBO.getContent(), MessageConstant.SEND_MSG_STATUS_FAIL, response.getError_code(),response.getMessage());
                    sendMsgLogService.insert(sendLog);
                }
            }
    }

    @Override
    public void sendMsgByAliyun(MobileMsgBO mobileMsgBO){
        String accessId = SpringCtxHolder.getProperty("message.aliyun.accessid");
        String accessKey = SpringCtxHolder.getProperty("message.aliyun.accesskey");
        String endPoint = SpringCtxHolder.getProperty("message.aliyun.endpoint");
        String topicRef = SpringCtxHolder.getProperty("message.aliyun.topic");
        String signName = SpringCtxHolder.getProperty("message.aliyun.signname");
        String templateCode = SpringCtxHolder.getProperty("message.aliyun.inform.templatecode");

        /**
         * Step 1. 获取主题引用
         */
        CloudAccount account = new CloudAccount(accessId, accessKey, endPoint);
        MNSClient client = account.getMNSClient();
        CloudTopic topic = client.getTopicRef(topicRef);
        /**
         * Step 2. 设置SMS消息体（必须）
         *
         * 注：目前暂时不支持消息内容为空，需要指定消息内容，不为空即可。
         */
        RawTopicMessage msg = new RawTopicMessage();
        msg.setMessageBody("sms-message");
        /**
         * Step 3. 生成SMS消息属性
         */
        MessageAttributes messageAttributes = new MessageAttributes();
        BatchSmsAttributes batchSmsAttributes = new BatchSmsAttributes();
        // 3.1 设置发送短信的签名（SMSSignName）
        batchSmsAttributes.setFreeSignName(signName);
        // 3.2 设置发送短信使用的模板（SMSTempateCode）
        batchSmsAttributes.setTemplateCode(templateCode);
        // 3.3 设置发送短信所使用的模板中参数对应的值（在短信模板中定义的，没有可以不用设置）
        BatchSmsAttributes.SmsReceiverParams smsReceiverParams = new BatchSmsAttributes.SmsReceiverParams();
        smsReceiverParams.setParam("name", mobileMsgBO.getContent());
        // 3.4 增加接收短信的号码
        batchSmsAttributes.addSmsReceiver(mobileMsgBO.getPhone(), smsReceiverParams);
        messageAttributes.setBatchSmsAttributes(batchSmsAttributes);
        try {
            /**
             * Step 4. 发布SMS消息
             */
            TopicMessage ret = topic.publishMessage(msg, messageAttributes);
            //记日志
            MessageSendLog sendLog = new MessageSendLog(MessageConstant.MSG_CHANNEL_ALI,mobileMsgBO.getPhone(), MessageConstant.MOBILE_MSG_BUSI_TYPE,
                    mobileMsgBO.getContent(), MessageConstant.SEND_MSG_STATUS_SUCCESS, MessageConstant.SEND_MSG_SUCCESS_CODE,
                    MessageConstant.SEND_MSG_SUCCESS_CONTENT);
            sendMsgLogService.insert(sendLog);
            System.out.println("MessageId: " + ret.getMessageId());
            System.out.println("MessageMD5: " + ret.getMessageBodyMD5());
        } catch (com.aliyun.mns.common.ServiceException se) {
            System.out.println(se.getErrorCode() + se.getRequestId());
            System.out.println(se.getMessage());
            //记日志
            MessageSendLog sendLog = new MessageSendLog(MessageConstant.MSG_CHANNEL_ALI,mobileMsgBO.getPhone(), MessageConstant.MOBILE_MSG_BUSI_TYPE,
                    mobileMsgBO.getContent(), MessageConstant.SEND_MSG_STATUS_FAIL, se.getErrorCode(),se.getMessage());
            sendMsgLogService.insert(sendLog);
            se.printStackTrace();
            LOGGER.info("发送业务通知短信失败，号码：{}，通道：阿里云", mobileMsgBO.getContent());
            throw new ServiceException(4204);
        } catch (Exception e) {
            e.printStackTrace();
            //记日志
            MessageSendLog sendLog = new MessageSendLog(MessageConstant.MSG_CHANNEL_ALI,mobileMsgBO.getPhone(), MessageConstant.MOBILE_MSG_BUSI_TYPE,
                    mobileMsgBO.getContent(), MessageConstant.SEND_MSG_STATUS_FAIL, MessageConstant.SEND_MSG_CHANNEL_ERROR_CODE,e.getMessage());
            sendMsgLogService.insert(sendLog);
            LOGGER.info("发送业务通知短信失败，号码：{}，通道：阿里云", mobileMsgBO.getContent());
            throw new ServiceException(4204);
        }
        client.close();
    }
}
