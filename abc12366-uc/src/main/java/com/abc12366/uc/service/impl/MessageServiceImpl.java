package com.abc12366.uc.service.impl;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Properties;
import com.abc12366.uc.model.Message;
import com.abc12366.uc.model.bo.MessageBO;
import com.abc12366.uc.model.bo.MessageListBO;
import com.abc12366.uc.service.MessageService;
import com.abc12366.uc.util.UcRestTemplateUtil;
import com.abc12366.uc.util.UserUtil;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-08-09
 * Time: 11:41
 */
@Service
public class MessageServiceImpl implements MessageService {
    private static Properties properties = new Properties("application.properties");

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private UcRestTemplateUtil ucRestTemplateUtil;

    @Override
    public MessageListBO selectList(String type, int page, int size, HttpServletRequest request) throws IOException {
        LOGGER.info("{},{}:{}:{}", type, page, size, request);
        String url = properties.getValue("chabc.soa.message.url") + "/business?";
        if (type != null && !type.equals("")) {
            url = url + "type=" + type;
        }
        if (!StringUtils.isEmpty(request.getAttribute("page"))) {
            url += "&page=" + request.getAttribute("page");
            if (!StringUtils.isEmpty(request.getAttribute("size"))) {
                url += "&size=" + request.getAttribute("size");
            }
        }
        String responseStr;
        try {
            responseStr = ucRestTemplateUtil.send(url, HttpMethod.GET, request);
        } catch (Exception e) {
            throw new ServiceException(4821);
        }
        MessageListBO response = null;
        if (!StringUtils.isEmpty(responseStr)) {
            response = JSON.parseObject(responseStr, MessageListBO.class);
        }
        return response;
    }

    @Override
    public MessageBO insert(Message message, HttpServletRequest request) throws IOException {
        LOGGER.info("{}:{}", message, request);

        String url = properties.getValue("chabc.soa.message.url") + "/business";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", message.getUserId());
        map.put("businessId", message.getBusinessId());
        map.put("content", message.getContent());
        map.put("status", "1");
        map.put("type", message.getType());

        String responseStr;
        try {
            responseStr = ucRestTemplateUtil.send(url, HttpMethod.POST, map, request);
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
    public MessageBO selectOne(String id, HttpServletRequest request) throws IOException {
        String url = properties.getValue("chabc.soa.message.url") + "/business/" + id;

        String responseStr;
        try {
            responseStr = ucRestTemplateUtil.send(url, HttpMethod.GET, request);
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
    public MessageBO update(String id, HttpServletRequest request) throws IOException {
        String url = properties.getValue("chabc.soa.message.url") + "/business/" + id;

        String responseStr;
        try {
            responseStr = ucRestTemplateUtil.send(url, HttpMethod.PUT, request);
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
    public MessageBO delete(String id, HttpServletRequest request) throws IOException {
        String url = properties.getValue("chabc.soa.message.url") + "/business/" + id;

        String responseStr;
        try {
            responseStr = ucRestTemplateUtil.send(url, HttpMethod.DELETE, request);
        } catch (Exception e) {
            throw new ServiceException(4821);
        }
        MessageBO response = null;
        if (!StringUtils.isEmpty(responseStr)) {
            response = JSON.parseObject(responseStr, MessageBO.class);
        }
        return response;
    }
}
