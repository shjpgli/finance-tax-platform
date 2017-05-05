package com.abc12366.gateway.service;


import com.abc12366.gateway.model.bo.AppBO;
import com.abc12366.gateway.model.bo.AppRespBO;
import com.abc12366.gateway.model.bo.TokenBO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-05 1:11 PM
 * @since 1.0.0
 */
public interface AppService {

    /**
     * 注册App
     *
     * @param appBO AppBO
     * @return AppRespBO
     */
    AppRespBO register(AppBO appBO);

    /**
     * App登录获取token
     *
     * @param appBO AppBO
     * @return String token
     */
    String login(AppBO appBO) throws Exception;

    /**
     * App验证
     *
     * @param accessToken Access-Token
     * @return true:已验证,false:未验证
     */
    boolean isAuthentication(String accessToken);

    /**
     * App授权
     *
     * @param request
     * @return true:已授权,false:未授权
     */
    boolean isAuthentization(HttpServletRequest request);
}
