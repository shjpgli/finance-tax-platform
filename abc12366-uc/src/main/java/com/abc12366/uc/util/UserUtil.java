package com.abc12366.uc.util;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Constant;

import javax.servlet.http.HttpServletRequest;

/**
 * Admin: liuguiyao<435720953@qq.com>
 * Date: 2017-07-26
 * Time: 17:19
 */
public class UserUtil {
    public static String getUserId(HttpServletRequest request) {
        String userId = (String) request.getAttribute(Constant.USER_ID);
        if (userId == null || userId.equals("")) {
            throw new ServiceException(4193);
        }
        return userId;
    }
}
