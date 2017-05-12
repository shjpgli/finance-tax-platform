package com.abc12366.admin.config;

import com.abc12366.admin.service.UserService;
import com.abc12366.common.model.BodyStatus;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * IO拦截器
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-23 9:31 AM
 * @since 1.0.0
 */
public class UserInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserInterceptor.class);

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 用户验证
        String userToken = request.getHeader(Constant.USER_TOKEN_HEAD);
        response.setContentType("application/json;charset=UTF-8");
        if (StringUtils.isEmpty(userToken)) {
            BodyStatus bodyStatus = Utils.bodyStatus(4199);
            response.setStatus(400);
            response.getWriter().write(JSON.toJSONString(bodyStatus));
            response.getWriter().flush();
            response.getWriter().close();
            LOGGER.warn("URI:{}, IP:{}, {}", request.getRequestURI(), request.getRemoteAddr(), bodyStatus);
            return false;
        }
        if (!userService.isAuthentication(userToken)) {
            BodyStatus bodyStatus = Utils.bodyStatus(4198);
            response.setStatus(401);
            response.getWriter().write(JSON.toJSONString(bodyStatus));
            response.getWriter().flush();
            response.getWriter().close();
            LOGGER.warn("URI:{}, IP:{}, {}", request.getRequestURI(), request.getRemoteAddr(), bodyStatus);
            return false;
        }

        return true;
    }
}
