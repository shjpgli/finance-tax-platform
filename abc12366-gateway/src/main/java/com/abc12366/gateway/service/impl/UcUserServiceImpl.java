package com.abc12366.gateway.service.impl;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.abc12366.gateway.service.UcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-06-21
 * Time: 9:50
 */
@Service
public class UcUserServiceImpl implements UcUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UcUserServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public boolean isAuthentication(String userToken, HttpServletRequest request) {
        LOGGER.info("{}:{}", userToken, request);
        //1.调用admin的token校验接口，如果校验通过直接返回true
        //TODO
        //2.调用uc的token校验接口，如果校验通过刷新token并返回true
        String url = "http://api.abc12366.com/uc";
        //请求头设置
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(Constant.VERSION_HEAD, Constant.VERSION_1);
        httpHeaders.add("Content-Type", "application/json");
        ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        if (responseEntity == null || !responseEntity.getStatusCode().is2xxSuccessful() || !responseEntity.hasBody()) {
            throw new ServiceException(4104);
        }
        //UCUserBO ucUserBO = objectMapper.readValue(((String) responseEntity.getBody()).getBytes(), UCUserBO.class);

        return true;
    }
}
