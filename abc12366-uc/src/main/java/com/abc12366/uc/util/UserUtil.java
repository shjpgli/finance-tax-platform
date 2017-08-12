package com.abc12366.uc.util;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Constant;
import com.abc12366.uc.model.admin.Admin;
import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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

    public static Admin getAdminInfo() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String adminJson = (String) request.getAttribute(Constant.ADMIN_USER);
        if (StringUtils.isEmpty(adminJson)) {
            throw new ServiceException(4130);
        }
        return JSON.parseObject(adminJson, Admin.class);
    }
}
