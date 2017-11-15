package com.abc12366.uc.service.impl;

import com.abc12366.gateway.component.SpringCtxHolder;
import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.MessageConstant;
import com.abc12366.gateway.util.RestTemplateUtil;
import com.abc12366.uc.model.Message;
import com.abc12366.uc.model.bo.MessageBO;
import com.abc12366.uc.service.MessageSendUtil;
import com.abc12366.gateway.util.RemindConstant;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/18.
 */
@Service("messageSendUtil")
public class MessageSendUtilImpl implements MessageSendUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageSendUtilImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RestTemplateUtil restTemplateUtil;

    /**
     * 需要验证token的发送
     */
    @Override
    public MessageBO sendMessage(Message message, HttpServletRequest request) {
        LOGGER.info("{}:{}", message, request);
        String url = SpringCtxHolder.getProperty("abc12366.message.url") + "/business";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", message.getUserId());
        map.put("businessId", message.getBusinessId());
        map.put("content", message.getContent());
        map.put("status", "1");
        map.put("type", message.getType());
        map.put("url", message.getUrl());

        String responseStr;
        try {
            responseStr = restTemplateUtil.exchange(url, HttpMethod.POST, map, request);
        } catch (Exception e) {
            throw new ServiceException(4821);
        }
        MessageBO response = null;
        if (!StringUtils.isEmpty(responseStr)) {
            response = JSON.parseObject(responseStr, MessageBO.class);
        }
        return response;
    }

    /**
     * 不需要验证token的发送
     */
    @Override
    public MessageBO sendMessage(Message message) {
        LOGGER.info("{}:{}", message);
        String url = SpringCtxHolder.getProperty("abc12366.message.url") + "/business";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", message.getUserId());
        map.put("businessId", message.getBusinessId());
        map.put("content", message.getContent());
        map.put("status", "1");
        map.put("type", message.getType());
        map.put("url", message.getUrl());

        String responseStr;
        try {
            responseStr = sendMsg(url, HttpMethod.POST, map);
        } catch (Exception e) {
            throw new ServiceException(4821);
        }
        MessageBO response = null;
        if (!StringUtils.isEmpty(responseStr)) {
            response = JSON.parseObject(responseStr, MessageBO.class);
        }
        return response;
    }

    @Override
    public String sendMsg(String url, HttpMethod method, Map<String, Object> map) {
        //请求头设置
        HttpHeaders httpHeaders = new HttpHeaders();

        HttpEntity requestEntity = new HttpEntity(map, httpHeaders);
        LOGGER.info("Request: {}, {}", url, requestEntity);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(url, method, requestEntity, String.class);
        } catch (RestClientException e) {
            throw new ServiceException("0000", "调用接口异常，地址：" + url);
        }
        LOGGER.info("Response: {}, {}", url, responseEntity);
        return responseEntity != null ? responseEntity.getBody() : null;
    }

    public static void main(String[] args) {
        Message message = new Message();
        message.setBusinessId("cs");
        message.setBusiType(MessageConstant.SPDD);
        message.setType(MessageConstant.SYS_MESSAGE);
        message.setContent(RemindConstant.BUYING_MEMBERS_PREFIX + RemindConstant.BUYING_MEMBERS_SUFFIX + "。<a " +
                "href=\"/member/member_rights.html\">会员权益详情查看</a>");
        message.setUserId("cs");
        new MessageSendUtilImpl().sendMessage(message);
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public MessageBO sendMessage(Message message, String accessToken) {
		String url = SpringCtxHolder.getProperty("abc12366.message.url") + "/business/system";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", message.getUserId());
        map.put("businessId", message.getBusinessId());
        map.put("content", message.getContent());
        map.put("status", "1");
        map.put("type", message.getType());
        map.put("url", message.getUrl());
        map.put("busiType", message.getBusiType());
        
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Access-Token", accessToken);
        httpHeaders.add("Version", "1");
        HttpEntity requestEntity = new HttpEntity(map, httpHeaders);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        } catch (RestClientException e) {
            throw new ServiceException("0000", "调用接口异常，地址：" + url);
        }
        LOGGER.info("Response: {}, {}", url, responseEntity);
        String responseStr=( responseEntity != null ? responseEntity.getBody() : null);
        MessageBO response = null;
        if (!StringUtils.isEmpty(responseStr)) {
            response = JSON.parseObject(responseStr, MessageBO.class);
        }
        return response;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void sendPhoneMessage(String phone,String templateId,  List<Map<String,String>> list, String accessToken) {
		
		String url = SpringCtxHolder.getProperty("abc12366.message.url") + "/mobile/msg";
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("templateId", templateId);
        map.put("vars", list);
        
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Access-Token", accessToken);
        httpHeaders.add("Version", "1");
        HttpEntity requestEntity = new HttpEntity(map,httpHeaders);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            LOGGER.info("Response: {}, {}", url, responseEntity);
        } catch (RestClientException e) {
            throw new ServiceException("0000", "调用接口异常，地址：" + url);
        }
		
	}
}
