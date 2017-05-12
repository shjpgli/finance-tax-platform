package com.abc12366.admin.web;

import com.abc12366.admin.model.bo.UserBO;
import com.abc12366.admin.service.UserService;
import com.abc12366.common.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @description：登录退出
 */
@Controller
@RequestMapping(headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
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
    public ResponseEntity loginPost(@Valid @RequestBody UserBO userBO, @RequestHeader(Constant.APP_TOKEN_HEAD) String appToken) {
        UserBO user = userService.login(userBO, appToken);
        LOGGER.info("{}", user);
        return ResponseEntity.ok(user);
    }


    @PostMapping(path = "/register")
    public ResponseEntity register(@Valid @RequestBody UserBO userBO) {
        int registerNum = userService.register(userBO);
        LOGGER.info("{}", registerNum);
        return ResponseEntity.ok(registerNum);
    }
}
