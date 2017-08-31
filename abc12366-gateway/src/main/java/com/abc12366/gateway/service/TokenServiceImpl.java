package com.abc12366.gateway.service;

import com.abc12366.gateway.model.bo.LoginInfoBO;
import com.abc12366.gateway.model.bo.ResultLoginInfo;
import com.abc12366.gateway.model.bo.UCUserBO;
import com.abc12366.gateway.model.bo.UserResponseBO;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.PropertiesUtil;
import com.abc12366.gateway.util.RestTemplateUtil;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOperations;

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
     *
     * @param adminToken 用户token
     * @param request    HttpServletRequest
     * @return true: 通过校验
     */
    private boolean adminTokenAuth(String adminToken, HttpServletRequest request) {
        LOGGER.info("{}:{}", adminToken, request);
        boolean isAuth = false;
        if (redisTemplate.hasKey(adminToken)) {
            String adminInfo = valueOperations.get(adminToken);
            LoginInfoBO user = JSON.parseObject(adminInfo, LoginInfoBO.class);
            if (user != null) {
                isAuth = true;
                request.setAttribute(Constant.ADMIN_ID, user.getUserId());
                request.setAttribute(Constant.ADMIN_USER, user.getAdmin());
            }
        } else {
            try {
                String abcAdmin = PropertiesUtil.getValue("abc12366.uc.url");
                String checkUrl = "/admin/token/" + adminToken;
                // 1.调用admin的token校验接口，如果校验通过直接返回true
                String result = restTemplateUtil.send(abcAdmin + checkUrl, HttpMethod.GET, request);
                if (result == null) {
                    isAuth = false;
                }

                ResultLoginInfo resultLoginInfo = JSON.parseObject(result, ResultLoginInfo.class);
                if (resultLoginInfo == null || resultLoginInfo.getData() == null) {
                    isAuth = false;
                } else {
                    LoginInfoBO bo = resultLoginInfo.getData();
                    request.setAttribute(Constant.ADMIN_ID, bo.getUserId());
                    request.setAttribute(Constant.ADMIN_USER, bo.getAdmin());
                    valueOperations.set(bo.getToken(), JSON.toJSONString(bo), Constant.USER_TOKEN_VALID_SECONDS / 2,
                            TimeUnit.SECONDS);
                    isAuth = true;
                }
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        LOGGER.info("校验admin的token状态为: {}", isAuth);
        return isAuth;
    }

    /**
     * 调用uc的token校验接口，如果校验通过刷新token
     *
     * @param userToken 用户token
     * @param request   HttpServletRequest
     * @return true: 通过校验
     */
    private boolean userTokenAuth(String userToken, HttpServletRequest request) {
        LOGGER.info("{}:{}", userToken, request);
        boolean isAuth = false;

        if (redisTemplate.hasKey(userToken)) {
            String userInfo = valueOperations.get(userToken);
            UCUserBO user = JSON.parseObject(userInfo, UCUserBO.class);
            if (user != null) {
                isAuth = true;
                // 设置USER_ID，USER_INFO
                request.setAttribute(Constant.USER_ID, user.getId());
                request.setAttribute(Constant.USER_INFO, user);
            }
        } else {
            try {
                //调用uc的token校验接口，如果校验通过刷新token并返回true
                String abc12366_uc = PropertiesUtil.getValue("abc12366.uc.url");
                String check_url = "/user/token/" + userToken;
                String body = restTemplateUtil.send(abc12366_uc + check_url, HttpMethod.GET, request);
                if (body != null) {
                    UserResponseBO userResponseBO = JSON.parseObject(body, UserResponseBO.class);
                    LOGGER.info("{}", userResponseBO);
                    if (userResponseBO.getData() != null) {
                        isAuth = true;
                        // 设置USER_ID，USER_INFO
                        request.setAttribute(Constant.USER_ID, userResponseBO.getData().getId());
                        request.setAttribute(Constant.USER_INFO, userResponseBO.getData());
                    }
                }
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        LOGGER.info("校验uc的token状态为: {}", isAuth);
        return isAuth;
    }
}
