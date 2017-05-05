package com.abc12366.admin.web;

import com.abc12366.admin.model.bo.UserBO;
import com.abc12366.admin.service.UserService;
import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @description：登录退出
 */
@Controller
@RequestMapping(path = "/login")
public class LoginController {

    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;


    /**
     * @param userBO 用户信息
     * @return
     */
    @PostMapping(value = "/login")
    @ResponseBody
    public ResponseEntity loginPost(@Valid @RequestBody UserBO userBO,@RequestHeader(Constant.APP_TOKEN_HEAD) String appToken) {

        /*LOGGER.info("POST请求登录");
        if (StringUtils.isBlank(username)) {
            LOGGER.info("用户名不能为空：{}", username);
            throw new ServiceException(4121);
        }
        if (StringUtils.isBlank(password)) {
            LOGGER.info("用户名不能为空：{}", password);
            throw new ServiceException(4122);
        }*/

        UserBO user = userService.login(userBO,appToken);
        LOGGER.info("{}", user);
        return ResponseEntity.ok(user);
    }


    @PostMapping(path = "/register")
    public ResponseEntity register(@Valid @RequestBody UserBO userBO) {
        int registerNum = userService.register(userBO);
        LOGGER.info("{}", registerNum);
        return ResponseEntity.ok(registerNum);
    }
    /**
     * 退出
     *
     * @param request
     * @return
     *//*
    @RequestMapping(value = "/logout")
    @ResponseBody
    public Result logout(HttpServletRequest request) {
        LOGGER.info("登出");
        Subject subject = SecurityUtils.getSubject();
        Result result = new Result();
        subject.logout();
        result.setSuccess(true);
        return result;
    }*/
}
