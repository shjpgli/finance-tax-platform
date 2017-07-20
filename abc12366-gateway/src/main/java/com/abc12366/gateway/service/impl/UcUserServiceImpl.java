package com.abc12366.gateway.service.impl;

import com.abc12366.common.util.Constant;
import com.abc12366.gateway.model.bo.AdminResponseBO;
import com.abc12366.gateway.model.bo.UserResponseBO;
import com.abc12366.gateway.util.HttpRequestUtil;
import com.abc12366.gateway.util.PropertiesUtil;
import com.abc12366.gateway.util.RestTemplateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.abc12366.gateway.service.UcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-06-21
 * Time: 9:50
 */
@Service
public class UcUserServiceImpl implements UcUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UcUserServiceImpl.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplateUtil restTemplateUtil;

    @Override
    public boolean isAuthentication(String adminToken, String userToken, HttpServletRequest request) {
        LOGGER.info("{}:{}:{}", adminToken, userToken, request);
        //1.调用admin的token校验接口，如果校验通过直接返回true
        if (adminTokenAuthen(adminToken, request)) {
            return true;
        }
        //2.调用uc的token校验接口，如果校验通过刷新token并返回true
        return userTokenAuthen(userToken, request);
    }

    private boolean adminTokenAuthen(String adminToken, HttpServletRequest request) {
        LOGGER.info("{}:{}", adminToken, request);
        try {
            //1.调用admin的token校验接口，如果校验通过直接返回true
            String adminTokenVerifyResult = HttpRequestUtil.sendPost(PropertiesUtil.getValue("admin.token.check.url") + adminToken, "");
            if (!StringUtils.isEmpty(adminTokenVerifyResult) && adminTokenVerifyResult.equals("true")) {
                //刷新token时间
                HttpRequestUtil.sendPost(PropertiesUtil.getValue("admin.token.refresh.url") + adminToken, "");
                //根据token获取admin的userId，并将userId设置到request中
                String url = PropertiesUtil.getValue("admin.token.userid.url") + adminToken;
                ResponseEntity userByTokenResponse = restTemplateUtil.send(url, HttpMethod.GET, request);
                if (userByTokenResponse != null && userByTokenResponse.hasBody()) {
                    AdminResponseBO adminResponseBO = objectMapper.readValue(((String) userByTokenResponse.getBody()).getBytes(), AdminResponseBO.class);
                    LOGGER.info("{}", adminResponseBO);
                    if (!StringUtils.isEmpty(adminResponseBO.getData().getUserId())) {
                        request.setAttribute(Constant.USER_ID, adminResponseBO.getData().getUserId());
                    }
                }
                return true;
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }

    private boolean userTokenAuthen(String userToken, HttpServletRequest request) {
        LOGGER.info("{}:{}", userToken, request);
        try {
            //调用uc的token校验接口，如果校验通过刷新token并返回true
            //String url = "http://localhost:9100/uc/auth/" + userToken;
            String userTokenVerifyResult = HttpRequestUtil.sendPost(PropertiesUtil.getValue("user.token.check.url") + userToken, "");
            if (!StringUtils.isEmpty(userTokenVerifyResult) && userTokenVerifyResult.equals("true")) {
                //根据token获取admin的userId，并将userId设置到request中
                String url = PropertiesUtil.getValue("user.token.userid.url") + userToken;
                ResponseEntity userByTokenResponse = restTemplateUtil.send(url, HttpMethod.GET, request);
                if (userByTokenResponse != null && userByTokenResponse.hasBody()) {
                    UserResponseBO userResponseBO = objectMapper.readValue(((String) userByTokenResponse.getBody()).getBytes(), UserResponseBO.class);
                    LOGGER.info("{}", userResponseBO);
                    if (userResponseBO != null && userResponseBO.getData() != null && !StringUtils.isEmpty(userResponseBO.getData().getId())) {
                        request.setAttribute(Constant.USER_ID, userResponseBO.getData().getId());
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
                return true;
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }

}
