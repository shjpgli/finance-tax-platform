package com.abc12366.gateway.service;

import com.abc12366.common.util.Constant;
import com.abc12366.gateway.model.bo.AdminResponseBO;
import com.abc12366.gateway.model.bo.UserResponseBO;
import com.abc12366.gateway.util.PropertiesUtil;
import com.abc12366.gateway.util.RestTemplateUtil;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
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
public class TokenServiceImpl implements TokenService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenServiceImpl.class);

    @Autowired
    private RestTemplateUtil restTemplateUtil;

    @Override
    public boolean isAuthentication(String adminToken, String userToken, HttpServletRequest request) {
        LOGGER.info("{}:{}:{}", adminToken, userToken, request);
        if (!StringUtils.isEmpty(request.getHeader(Constant.USER_TOKEN_HEAD))) {
            return userTokenAuth(userToken, request);
        }
        if (!StringUtils.isEmpty(request.getHeader(Constant.ADMIN_TOKEN_HEAD))) {
            return adminTokenAuth(adminToken, request);
        }
        return false;
    }

    /**
     * 调用admin的token校验接口，如果校验通过刷新token
     * @param adminToken 用户token
     * @param request HttpServletRequest
     * @return true: 通过校验
     */
    private boolean adminTokenAuth(String adminToken, HttpServletRequest request) {
        LOGGER.info("{}:{}", adminToken, request);
        boolean isAuth = false;
        try {
            String abc12366_admin = PropertiesUtil.getValue("abc12366.admin.url");
            String check_url = "/admintoken/check/" + adminToken;
            // 1.调用admin的token校验接口，如果校验通过直接返回true
            String result = restTemplateUtil.send(abc12366_admin + check_url, HttpMethod.POST, request);
            if ("true".equals(result)) {
                isAuth = true;
                // 刷新token时间
                refreshAdminToken(adminToken, request, abc12366_admin);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        LOGGER.info("校验admin的token状态为: {}", isAuth);
        return isAuth;
    }

    private void refreshAdminToken(String adminToken, HttpServletRequest request, String abc12366_admin) {
        // 刷新token时间
        String refresh_url = "/admintoken/refresh/" + adminToken;
        // 根据token获取admin的userId
        String userid_url = "/user/token/" + adminToken;
        restTemplateUtil.send(abc12366_admin + refresh_url, HttpMethod.POST, request);
        // 将userId设置到request中
        String body = restTemplateUtil.send(abc12366_admin + userid_url, HttpMethod.GET, request);
        if (body != null) {
            AdminResponseBO adminResponseBO = JSON.parseObject(body, AdminResponseBO.class);
            LOGGER.info("{}", adminResponseBO);
            if (adminResponseBO.getData() != null && !StringUtils.isEmpty(adminResponseBO.getData().getUserId())) {
                request.setAttribute(Constant.USER_ID, adminResponseBO.getData().getUserId());
            }
        }
    }

    /**
     * 调用uc的token校验接口，如果校验通过刷新token
     * @param userToken 用户token
     * @param request HttpServletRequest
     * @return true: 通过校验
     */
    private boolean userTokenAuth(String userToken, HttpServletRequest request) {
        LOGGER.info("{}:{}", userToken, request);
        boolean isAuth = false;
        try {
            //调用uc的token校验接口，如果校验通过刷新token并返回true
            String abc12366_uc = PropertiesUtil.getValue("abc12366.uc.url");
            String check_url = "/auth/" + userToken;
            String result = restTemplateUtil.send(abc12366_uc + check_url, HttpMethod.POST, request);
            if ("true".equals(result)) {
                isAuth = true;
                //根据token获取admin的userId，并将userId设置到request中
                refreshUserToken(userToken, request, abc12366_uc);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        LOGGER.info("校验admin的token状态为: {}", isAuth);
        return isAuth;
    }

    private void refreshUserToken(String userToken, HttpServletRequest request, String abc12366_uc) {
        String url = "/user/token/" + userToken;
        String body = restTemplateUtil.send(abc12366_uc + url, HttpMethod.GET, request);
        if (body != null) {
            UserResponseBO userResponseBO = JSON.parseObject(body, UserResponseBO.class);
            LOGGER.info("{}", userResponseBO);
            if (userResponseBO.getData() != null && !StringUtils.isEmpty(userResponseBO.getData().getId())) {
                request.setAttribute(Constant.USER_ID, userResponseBO.getData().getId());
            }
        }
    }

}
