package com.abc12366.gateway.component;

import com.abc12366.common.util.Message;
import com.abc12366.common.util.Utils;
import com.abc12366.gateway.model.Log;
import com.abc12366.gateway.service.BlacklistService;
import com.abc12366.gateway.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
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
public class LogInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogInterceptor.class);

    @Autowired
    private LogService logService;

    @Autowired
    private BlacklistService blacklistService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        response.setContentType("text/plain;charset=UTF-8");
        // 1.前置日志
        String addr = request.getRemoteAddr();
        String uri = request.getRequestURI();
        LOGGER.info("URI:{}, IP:{}, User-Agent:{}", uri, addr, request.getHeader("User-Agent"));

        // 2.黑名单服务
        if (blacklistService.isBlacklist(addr)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, Message.C4003);
            LOGGER.info("URI:{}, IP:{}, Message:{}", uri, addr, Message.C4003);
            return false;
        }

        // 记录开始访问时间
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        String uri = request.getRequestURI();
        String userAgent = request.getHeader("User-Agent");
        String ip = request.getRemoteAddr();
        long startTime = (long) request.getAttribute("startTime");
        request.removeAttribute("startTime");

        long endTime = System.currentTimeMillis();
        int status = response.getStatus();
        String appId = request.getHeader("Access-Token");

        Log log = new Log.Builder()
                .id(Utils.uuid())
                .uri(uri)
                .userAgent(userAgent)
                .ip(ip)
                .startTime(startTime)
                .endTime(endTime)
                .status(status)
                .appId(appId)
                .build();

        // 5.访问计数
        // 6.后置日志和日志表
        LOGGER.info("{}", log);
        logService.insert(log);
    }
}
