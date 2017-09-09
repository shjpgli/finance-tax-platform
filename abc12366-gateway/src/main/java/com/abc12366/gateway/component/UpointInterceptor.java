package com.abc12366.gateway.component;

import com.abc12366.gateway.service.UexpService;
import com.abc12366.gateway.service.UpointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-08-26
 * Time: 10:49
 */
public class UpointInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpointInterceptor.class);

    @Autowired
    private UpointService upointService;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        upointService.compute(request);
    }

}
