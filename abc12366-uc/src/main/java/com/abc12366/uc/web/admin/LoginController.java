package com.abc12366.uc.web.admin;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.admin.bo.AdminBO;
import com.abc12366.uc.model.admin.bo.LoginInfoBO;
import com.abc12366.uc.service.admin.AdminService;
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
@RequestMapping(path = "/admin", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class LoginController {

    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AdminService adminService;


    /**
     * @param adminBO 用户信息
     * @return
     */
    @PostMapping(value = "/login")
    @ResponseBody
    public ResponseEntity loginPost(@Valid @RequestBody AdminBO adminBO, HttpServletRequest request) {
        String appId = (String) request.getAttribute(Constant.APP_ID);
        AdminBO user = adminService.login(adminBO, appId);
        LOGGER.info("{}", user);
        return ResponseEntity.ok(Utils.kv("data", user));
    }

    /**
     * 退出
     *
     * @param token
     * @param request
     * @return
     */
    @PostMapping(value = "/logout/{token}")
    public ResponseEntity logout(@PathVariable("token") String token, HttpServletRequest request) {
        LOGGER.info("登出");
        adminService.logout(token);
        return ResponseEntity.ok(Utils.kv());
    }

    /**
     * 查看LoginInfo信息
     *
     * @param token
     * @return
     */
    @GetMapping(path = "/token/{token}")
    public ResponseEntity selectUser(@PathVariable("token") String token) {
        long start = System.currentTimeMillis();
        LOGGER.info("token:{}", token);
        LoginInfoBO loginInfo = adminService.selectLoginInfoByToken(token);
        LOGGER.info("loginInfo:{}", loginInfo);
        long end = System.currentTimeMillis();
        long res = end - start;
        LOGGER.info("响应用时:{}毫秒", res);
        return ResponseEntity.ok(Utils.kv("data", loginInfo));
    }

    /*@PostMapping(path = "/register")
    public ResponseEntity register(@Valid @RequestBody AdminBO userBO) {
        int registerNum = adminService.register(userBO);
        LOGGER.info("{}", registerNum);
        return ResponseEntity.ok(registerNum);
    }*/
}
