package com.abc12366.gateway.component;

import com.abc12366.gateway.model.BodyStatus;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.PropertiesUtil;
import com.abc12366.gateway.util.Utils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * IO拦截器
 *
 * @create 2017-02-23 9:31 AM
 * @since 1.0.0
 */
public class AdminUserInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminUserInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 用户验证
        String adminToken = request.getHeader(Constant.ADMIN_TOKEN_HEAD);
        response.setContentType("application/json;charset=UTF-8");
        if (StringUtils.isEmpty(adminToken)) {
            BodyStatus bodyStatus = Utils.bodyStatus(4197);
            response.setStatus(200);
            response.getWriter().write(JSON.toJSONString(bodyStatus));
            response.getWriter().flush();
            response.getWriter().close();
            LOGGER.warn("URI:{}, IP:{}, {}", request.getRequestURI(), request.getRemoteAddr(), bodyStatus);
            return false;
        }
        //发送 POST 请求
        String abc12366_admin = PropertiesUtil.getValue("abc12366.admin.url");
        /*String check_url = "/admintoken/check/" + adminToken;
        String result = HttpRequestUtil.sendPost(abc12366_admin + check_url, "");
        if (!result.equals("true")){
            BodyStatus bodyStatus = Utils.bodyStatus(4196);
            response.setStatus(200);
            response.getWriter().write(JSON.toJSONString(bodyStatus));
            response.getWriter().flush();
            response.getWriter().close();
            LOGGER.warn("URI:{}, IP:{}, {}", request.getRequestURI(), request.getRemoteAddr(), bodyStatus);
            return false;
        }
        String refresh_url = "/user/token/" + adminToken;
        String result = HttpRequestUtil.sendPost(abc12366_admin + refresh_url, "");
        System.out.println(result);
        if (!result.equals("true")){
            BodyStatus bodyStatus = Utils.bodyStatus(4129);
            response.setStatus(200);
            response.getWriter().write(JSON.toJSONString(bodyStatus));
            response.getWriter().flush();
            response.getWriter().close();
            LOGGER.warn("URI:{}, IP:{}, {}", request.getRequestURI(), request.getRemoteAddr(), bodyStatus);
            return false;
        }*/

        return true;
    }
}
