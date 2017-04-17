package com.abc12366.gateway.component;

import com.abc12366.common.model.BodyStatus;
import com.abc12366.common.util.Utils;
import com.abc12366.gateway.service.AppService;
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
public class AppInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppInterceptor.class);

    @Autowired
    private AppService appService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 3.App验证
        String accessToken = request.getHeader("Access-Token");
        response.setContentType("text/plain;charset=UTF-8");
        if (StringUtils.isEmpty(accessToken)) {
            BodyStatus bodyStatus = Utils.code(4000);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, bodyStatus.getMessage());
            LOGGER.warn("URI:{}, IP:{}, BodyStatus:{}", request.getRequestURI(), request.getRemoteAddr(), bodyStatus);
            return false;
        }
        if (appService.isAuthentication(accessToken)) {
            BodyStatus bodyStatus = Utils.code(4001);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, bodyStatus.getMessage());
            LOGGER.warn("URI:{}, IP:{}, BodyStatus:{}", request.getRequestURI(), request.getRemoteAddr(), bodyStatus);
            return false;
        }
        // 4.App授权
        if (appService.isAuthentization(accessToken, request.getRequestURI())) {
            BodyStatus bodyStatus = Utils.code(4002);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, bodyStatus.getMessage());
            LOGGER.warn("URI:{}, IP:{}, BodyStatus:{}", request.getRequestURI(), request.getRemoteAddr(), bodyStatus);
            return false;
        }

        return true;
    }
}
