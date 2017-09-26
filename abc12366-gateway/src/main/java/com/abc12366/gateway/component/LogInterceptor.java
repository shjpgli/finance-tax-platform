package com.abc12366.gateway.component;

import com.abc12366.gateway.model.ApiLog;
import com.abc12366.gateway.model.BodyStatus;
import com.abc12366.gateway.service.ApiLogService;
import com.abc12366.gateway.service.BlacklistService;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.DateUtils;
import com.abc12366.gateway.util.Utils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * IO拦截器
 *
 * @author lizhongwei
 * @create 2017-07-18
 * @since 2.0.0
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
        String addr = Utils.getAddr(request);
        String userAgent = Utils.getUserAgent(request);
        String uri = request.getRequestURI();
        String version = request.getHeader(Constant.VERSION_HEAD);

//        try {
//            request.setCharacterEncoding("UTF-8");
//            int size = request.getContentLength();
//            System.out.println(size);
//
//            InputStream is = request.getInputStream();
//
//            byte[] reqBodyBytes = readBytes(is, size);
//
//            String res = new String(reqBodyBytes);
//
//            System.out.println(res);
//            response.setContentType("text/html;charset=UTF-8");
//            response.setCharacterEncoding("UTF-8");
//            response.getOutputStream().write(res.getBytes("utf-8"));
//            response.flushBuffer();
//        } catch (Exception e) {
//        }
        LOGGER.info("URI:{}, Version:{}, IP:{}, User-Agent:{}", uri, version, addr, userAgent);

        // 版本头检查
        if (StringUtils.isEmpty(version)) {
            BodyStatus bodyStatus = Utils.bodyStatus(4009);
            response.setStatus(200);
            response.getWriter().write(JSON.toJSONString(bodyStatus));
            response.getWriter().flush();
            response.getWriter().close();
            LOGGER.info("URI:{}, Version:{}, IP:{}, {}", uri, version, addr, bodyStatus);
            return false;
        }
        if (!(Constant.VERSION_1.equals(version) || Constant.VERSION_2.equals(version))) {
            BodyStatus bodyStatus = Utils.bodyStatus(4010);
            response.setStatus(200);
            response.getWriter().write(JSON.toJSONString(bodyStatus));
            response.getWriter().flush();
            response.getWriter().close();
            LOGGER.info("URI:{}, Version:{}, IP:{}, {}", uri, version, addr, bodyStatus);
            return false;
        }

        // 2.黑名单服务
        if (blacklistService.isBlacklist(addr)) {
            BodyStatus bodyStatus = Utils.bodyStatus(4003);
            response.setStatus(200);
            response.getWriter().write(JSON.toJSONString(bodyStatus));
            response.getWriter().flush();
            response.getWriter().close();
            LOGGER.info("URI:{}, Version:{}, IP:{}, {}", uri, version, addr, bodyStatus);
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
        String addr = Utils.getAddr(request);
        String userAgent = Utils.getUserAgent(request);
        String uri = (String) request.getAttribute("org.springframework.web.servlet.HandlerMapping" +
                ".bestMatchingPattern");
        long inTime = (long) request.getAttribute("inTime");
        request.removeAttribute("inTime");
        String method = request.getMethod();
        long outTime = System.currentTimeMillis();
        int status = response.getStatus();
        String appId = (String) request.getAttribute(Constant.APP_ID);
        request.removeAttribute(Constant.APP_ID);
        request.removeAttribute(Constant.USER_ID);
        String userId = request.getHeader(Constant.USER_TOKEN_HEAD);
        String version = request.getHeader(Constant.VERSION_HEAD);


        ApiLog log = new ApiLog();
        log.setId(Utils.uuid());
        log.setUri(uri);
        log.setUserAgent(userAgent);
        log.setIp(addr);
        log.setInTime(inTime);
        log.setOutTime(outTime);
        log.setStatus(String.valueOf(response.getStatus()));
        log.setAppId(appId);
        log.setUserId(userId);
        log.setVersion(version);
        log.setMethod(method);
        log.setYyyyMMdd(DateUtils.getDateFormat(new Date(), "yyyyMMdd"));

        // 5.访问计数
        // 6.后置日志和日志表
        LOGGER.info("{}", log);
        apiLogService.insert(log);
    }

    public static final byte[] readBytes(InputStream is, int contentLen) {
        if (contentLen > 0) {
            int readLen = 0;

            int readLengthThisTime = 0;

            byte[] message = new byte[contentLen];

            try {

                while (readLen != contentLen) {

                    readLengthThisTime = is.read(message, readLen, contentLen - readLen);

                    if (readLengthThisTime == -1) {// Should not happen.
                        break;
                    }

                    readLen += readLengthThisTime;
                }

                return message;
            } catch (IOException e) {
                // Ignore
                // e.printStackTrace();
            }
        }

        return new byte[] {};
    }
}
