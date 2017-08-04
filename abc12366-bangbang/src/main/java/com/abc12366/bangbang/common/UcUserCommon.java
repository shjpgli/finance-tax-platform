package com.abc12366.bangbang.common;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Constant;

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
}
