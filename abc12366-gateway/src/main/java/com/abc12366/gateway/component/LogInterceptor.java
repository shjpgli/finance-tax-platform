package com.abc12366.gateway.component;

import com.abc12366.common.model.BodyStatus;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.gateway.model.ApiLog;
import com.abc12366.gateway.service.BlacklistService;
import com.abc12366.gateway.service.ApiLogService;
import com.alibaba.fastjson.JSON;
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
    private ApiLogService apiLogService;

    @Autowired
    private BlacklistService blacklistService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        // 1.前置日志
        String addr = request.getRemoteAddr();
        String uri = request.getRequestURI();
        LOGGER.info("URI:{}, IP:{}, User-Agent:{}", uri, addr, request.getHeader("User-Agent"));

        // 2.黑名单服务
        if (blacklistService.isBlacklist(addr)) {
            BodyStatus bodyStatus = Utils.bodyStatus(4003);
            response.setStatus(403);
            response.getWriter().write(JSON.toJSONString(bodyStatus));
            response.getWriter().flush();
            response.getWriter().close();
            LOGGER.info("URI:{}, IP:{}, {}", uri, addr, bodyStatus);
            return false;
        }

        // 记录开始访问时间
        long inTime = System.currentTimeMillis();
        request.setAttribute("inTime", inTime);
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
        long inTime = (long) request.getAttribute("inTime");
        request.removeAttribute("inTime");

        long outTime = System.currentTimeMillis();
        int status = response.getStatus();
        String appId = request.getHeader(Constant.APP_TOKEN_HEAD);
        String userId = request.getHeader(Constant.USER_TOKEN_HEAD);

        ApiLog log = new ApiLog.Builder()
                .id(Utils.uuid())
                .uri(uri)
                .userAgent(userAgent)
                .ip(ip)
                .inTime(inTime)
                .outTime(outTime)
                .status(status)
                .appId(appId)
                .userId(userId)
                .build();

        // 5.访问计数
        // 6.后置日志和日志表
        LOGGER.info("{}", log);
        apiLogService.insert(log);
    }
}
