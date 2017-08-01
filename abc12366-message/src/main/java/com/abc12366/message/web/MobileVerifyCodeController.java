package com.abc12366.message.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.message.model.bo.VerifyParam;
import com.abc12366.message.service.MobileVerifyCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-20
 * Time: 0:08
 */
@RestController
@RequestMapping(headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class MobileVerifyCodeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MobileVerifyCodeController.class);

    @Autowired
    private MobileVerifyCodeService moboleVerifyCodeService;

    //获取验证码接口
//    @PostMapping(path = "/getcode/{phone}/{codeType}")
//    public ResponseEntity getCode(@PathVariable String phone, @PathVariable String codeType, HttpSession session) throws IOException {
//        LOGGER.info("{}:{}:{}", phone, codeType, session);
//        moboleVerifyCodeService.getCode(phone, codeType, session);
//        return ResponseEntity.ok(Utils.kv());
//    }

    //获取注册验证码接口
//    @PostMapping(path = "/getcode/register/{phone}")
//    public ResponseEntity getRegisterCode(@PathVariable String phone, HttpSession session) throws IOException {
//        LOGGER.info("{}:{}", phone, session);
//        moboleVerifyCodeService.getCode(phone, "注册", session);
//        return ResponseEntity.ok(Utils.kv());
//    }

    //获取更新密码验证码接口
//    @PostMapping(path = "/getcode/updatepassword/{phone}")
//    public ResponseEntity getUpdatePasswordCode(@PathVariable String phone, HttpSession session) throws IOException {
//        LOGGER.info("{}:{}", phone, session);
//        moboleVerifyCodeService.getCode(phone, "更新密码", session);
//        return ResponseEntity.ok(Utils.kv());
//    }

    //获取登录验证码接口
//    @PostMapping(path = "/getcode/login/{phone}")
//    public ResponseEntity getLoginCode(@PathVariable String phone, HttpSession session) throws IOException {
//        LOGGER.info("{}:{}", phone, session);
//        moboleVerifyCodeService.getCode(phone, "登录", session);
//        return ResponseEntity.ok(Utils.kv());
//    }

    //获取绑定企业验证码接口
//    @PostMapping(path = "/getcode/bind/{phone}")
//    public ResponseEntity getBindCode(@PathVariable String phone, HttpSession session) throws IOException {
//        LOGGER.info("{}:{}", phone, session);
//        moboleVerifyCodeService.getCode(phone, "绑定企业", session);
//        return ResponseEntity.ok(Utils.kv());
//    }

    //获取验证码接口
    @PostMapping(path = "/getcode/{type}/{phone}")
    public ResponseEntity getCode(@PathVariable String type, @PathVariable String phone) throws IOException {
        LOGGER.info("{}:{}", type, phone);
        moboleVerifyCodeService.getCode(type, phone);
        return ResponseEntity.ok(Utils.kv());
    }

    //验证码校验接口
    @PostMapping(path = "/verify")
    public ResponseEntity verify(@Valid @RequestBody VerifyParam verifyParam) throws IOException {
        LOGGER.info("{}", verifyParam);
        moboleVerifyCodeService.verify(verifyParam);
        return ResponseEntity.ok(Utils.kv());
    }
}
