package com.abc12366.uc.util;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.model.User;
import com.abc12366.gateway.util.Constant;
import com.abc12366.uc.model.admin.Admin;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeanUtils;
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

    public static String getAdminId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String adminId = (String) request.getAttribute(Constant.ADMIN_ID);
        if (adminId == null || adminId.equals("")) {
            throw new ServiceException(4193);
        }
        return adminId;
    }

    public static Admin getAdminInfo() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        User user = (User) request.getAttribute(Constant.ADMIN_USER);
        if (StringUtils.isEmpty(user)) {
            throw new ServiceException(4130);
        }
        Admin admin = new Admin();
        BeanUtils.copyProperties(user,admin);
        return admin;
    }

    public static com.abc12366.uc.model.User getUserInfo() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        com.abc12366.uc.model.User user = (com.abc12366.uc.model.User) request.getAttribute(Constant.USER_INFO);
        if (StringUtils.isEmpty(user)) {
            throw new ServiceException(4130);
        }
        return user;
    }
}
