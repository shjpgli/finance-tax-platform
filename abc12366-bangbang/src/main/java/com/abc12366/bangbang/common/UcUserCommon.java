package com.abc12366.bangbang.common;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Constant;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-11
 * Time: 10:04
 */
public class UcUserCommon {
    public static String getUserId(HttpServletRequest request) {
        String userId = (String) request.getAttribute(Constant.USER_ID);
        if (userId == null || userId.equals("")) {
            throw new ServiceException(4193);
        }
        return userId;
    }

    public static String getUserId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String userId = (String) request.getAttribute(Constant.USER_ID);
        userId = "e132ea76-a820-4649-82db-ba6753bf6dfc";
//        if (StringUtils.isEmpty(userId)) {
//            throw new ServiceException(4193);
//        }
        return userId;
    }
}
