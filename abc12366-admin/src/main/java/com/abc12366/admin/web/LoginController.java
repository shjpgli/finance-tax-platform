package com.abc12366.admin.web;

import com.abc12366.admin.model.bo.UserBO;
import com.abc12366.admin.service.UserService;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public ResponseEntity loginPost(@Valid @RequestBody UserBO userBO, HttpServletRequest request) {
        String appId = (String) request.getAttribute(Constant.APP_ID);
        UserBO user = userService.login(userBO, appId);
        LOGGER.info("{}", user);
        return ResponseEntity.ok(Utils.kv("data", user));
    }

    /**
     * 退出
     * @param token
     * @param request
     * @return
     */
    @PostMapping(value = "/logout/{token}")
    public ResponseEntity logout(@PathVariable("token") String token,HttpServletRequest request) {
        LOGGER.info("登出");
        userService.logout(token);
        return ResponseEntity.ok(Utils.kv());
    }

    /*@PostMapping(path = "/register")
    public ResponseEntity register(@Valid @RequestBody UserBO userBO) {
        int registerNum = userService.register(userBO);
        LOGGER.info("{}", registerNum);
        return ResponseEntity.ok(registerNum);
    }*/
}
