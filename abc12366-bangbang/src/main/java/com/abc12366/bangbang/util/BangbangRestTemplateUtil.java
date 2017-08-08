package com.abc12366.bangbang.util;

import com.abc12366.gateway.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-03
 * Time: 14:49
 */
@Component
public class BangbangRestTemplateUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(BangbangRestTemplateUtil.class);

    @Autowired
    private RestTemplate restTemplate;

    public String send(String url, HttpMethod method, HttpServletRequest request) {
        //请求头设置
        HttpHeaders httpHeaders = new HttpHeaders();
        if (!StringUtils.isEmpty(request.getHeader(Constant.APP_TOKEN_HEAD))) {
            httpHeaders.add(Constant.APP_TOKEN_HEAD, request.getHeader(Constant.APP_TOKEN_HEAD));
        }
        if (!StringUtils.isEmpty(request.getHeader(Constant.ADMIN_TOKEN_HEAD))) {
            httpHeaders.add(Constant.ADMIN_TOKEN_HEAD, request.getHeader(Constant.ADMIN_TOKEN_HEAD));
        }
        if (!StringUtils.isEmpty(request.getHeader(Constant.USER_TOKEN_HEAD))) {
            httpHeaders.add(Constant.USER_TOKEN_HEAD, request.getHeader(Constant.USER_TOKEN_HEAD));
        }
        if (!StringUtils.isEmpty(request.getHeader(Constant.VERSION_HEAD))) {
            httpHeaders.add(Constant.VERSION_HEAD, request.getHeader(Constant.VERSION_HEAD));
        }

        HttpEntity requestEntity = new HttpEntity(null, httpHeaders);
        LOGGER.info("Request: {}, {}", url, requestEntity);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(url, method, requestEntity, String.class);
        } catch (RestClientException e) {
            LOGGER.error("RestClient调用服务出现异常: " + e.getMessage(), e);
        }
        LOGGER.info("Response: {}, {}", url, responseEntity);
        return responseEntity != null ? responseEntity.getBody() : null;
    }


    public ResponseEntity exchange(String url, HttpMethod method, HttpServletRequest request) {
        //请求头设置
        HttpHeaders httpHeaders = new HttpHeaders();
        if (!StringUtils.isEmpty(request.getHeader(Constant.APP_TOKEN_HEAD))) {
            httpHeaders.add(Constant.APP_TOKEN_HEAD, request.getHeader(Constant.APP_TOKEN_HEAD));
        }
        if (!StringUtils.isEmpty(request.getHeader(Constant.ADMIN_TOKEN_HEAD))) {
            httpHeaders.add(Constant.ADMIN_TOKEN_HEAD, request.getHeader(Constant.ADMIN_TOKEN_HEAD));
        }
        if (!StringUtils.isEmpty(request.getHeader(Constant.USER_TOKEN_HEAD))) {
            httpHeaders.add(Constant.USER_TOKEN_HEAD, request.getHeader(Constant.USER_TOKEN_HEAD));
        }
        if (!StringUtils.isEmpty(request.getHeader(Constant.VERSION_HEAD))) {
            httpHeaders.add(Constant.VERSION_HEAD, request.getHeader(Constant.VERSION_HEAD));
        }

        HttpEntity requestEntity = new HttpEntity(null, httpHeaders);
        LOGGER.info("Request: {}, {}", url, requestEntity);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(url, method, requestEntity, String.class);
        } catch (RestClientException e) {
            LOGGER.error("RestClient调用服务出现异常: " + e.getMessage(), e);
        }
        LOGGER.info("Response: {}, {}", url, responseEntity);
        return responseEntity;
    }

    public String send(String url, HttpMethod method, Map<String, Object> map, HttpServletRequest request) {
        //请求头设置
        HttpHeaders httpHeaders = new HttpHeaders();
        if (!StringUtils.isEmpty(request.getHeader(Constant.APP_TOKEN_HEAD))) {
            httpHeaders.add(Constant.APP_TOKEN_HEAD, request.getHeader(Constant.APP_TOKEN_HEAD));
        }
        if (!StringUtils.isEmpty(request.getHeader(Constant.ADMIN_TOKEN_HEAD))) {
            httpHeaders.add(Constant.ADMIN_TOKEN_HEAD, request.getHeader(Constant.ADMIN_TOKEN_HEAD));
        }
        if (!StringUtils.isEmpty(request.getHeader(Constant.USER_TOKEN_HEAD))) {
            httpHeaders.add(Constant.USER_TOKEN_HEAD, request.getHeader(Constant.USER_TOKEN_HEAD));
        }
        if (!StringUtils.isEmpty(request.getHeader(Constant.VERSION_HEAD))) {
            httpHeaders.add(Constant.VERSION_HEAD, request.getHeader(Constant.VERSION_HEAD));
        }

        HttpEntity requestEntity = new HttpEntity(map, httpHeaders);
        LOGGER.info("Request: {}, {}", url, requestEntity);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(url, method, requestEntity, String.class);
        } catch (RestClientException e) {
            LOGGER.error("RestClient调用服务出现异常: " + e.getMessage(), e);
        }
        LOGGER.info("Response: {}, {}", url, responseEntity);
        return responseEntity != null ? responseEntity.getBody() : null;
    }


}
