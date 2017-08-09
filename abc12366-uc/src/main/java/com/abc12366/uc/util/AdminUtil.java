package com.abc12366.uc.util;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Constant;

import javax.servlet.http.HttpServletRequest;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-08-09
 * Time: 17:13
 */
public class AdminUtil {
    public static String getAdminId(HttpServletRequest request) {
        String adminId = (String) request.getAttribute(Constant.ADMIN_ID);
        if (adminId == null || adminId.equals("")) {
            throw new ServiceException(4130);
        }
        return adminId;
    }
}
