package com.abc12366.uc.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.web.BaseController;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-27 4:13 PM
 * @since 1.0.0
 */
@RestController
@RequestMapping(headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class AuthController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthService authService;

    public AuthController(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refresh(@RequestHeader(Constant.USER_TOKEN_HEAD) String token) {
        String refreshedToken = authService.refresh(token);
        if (refreshedToken == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(new TokenBO(refreshedToken));
        }
    }

    //老的注册接口暂时注释保留
    /*@PostMapping(path = "/register")
    public ResponseEntity register(@Valid @RequestBody RegisterBO registerBO) {
        LOGGER.info("{}", registerBO);
        UserReturnBO userReturnBO = authService.register(registerBO);
        if (userReturnBO == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(userReturnBO);
    }*/

    @PostMapping(path = "/register")
    public ResponseEntity register(@Valid @RequestBody RegisterBO registerBO) throws IOException {
        LOGGER.info("{}", registerBO);
        //进行手机验证码验证
        ResponseEntity response = authService.verifyCode(registerBO.getPhone(), registerBO.getVerifyingCode());
        if (response == null) {
            return ResponseEntity.badRequest().body(null);
        }
        VerifyCodeResponse verifyCodeResponse = objectMapper.readValue(((String) response.getBody()).getBytes(), VerifyCodeResponse.class);
        if (!verifyCodeResponse.getCode().equals("200")) {
            return ResponseEntity.badRequest().body(response);
        }

        //注册
        UserReturnBO userReturnBO = authService.register(registerBO);
        if (userReturnBO == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(userReturnBO);
    }

    /*
    用户登录方法：
        1.请求访问时获取token，token为空则需要用户名和密码登录
     */
    @PostMapping(path = "/login")
    public ResponseEntity login(@Valid @RequestBody LoginBO loginBO, HttpServletRequest request) throws Exception {
        LOGGER.info("{}", loginBO);
        Map token = authService.login(loginBO, request.getHeader(Constant.APP_TOKEN_HEAD));
        LOGGER.info("{}", token);
        return token != null ? ResponseEntity.ok(token) : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    /*
    用户通过手机验证码进行登录
     */
    @PostMapping(path = "/verifylogin")
    public ResponseEntity loginByVerifyingCode(@Valid @RequestBody LoginVerifyingCodeBO loginBO, HttpServletRequest request) throws Exception {
        LOGGER.info("{}", loginBO);
        //进行手机验证码验证
        ResponseEntity response = authService.verifyCode(loginBO.getPhone(), loginBO.getCode());
        if (response == null) {
            return ResponseEntity.badRequest().body(null);
        }
        VerifyCodeResponse verifyCodeResponse = objectMapper.readValue(((String) response.getBody()).getBytes(), VerifyCodeResponse.class);
        if (!verifyCodeResponse.getCode().equals("200")) {
            return ResponseEntity.badRequest().body(response);
        }
        Map token = authService.loginByVerifyingCode(loginBO, request.getHeader(Constant.APP_TOKEN_HEAD));
        LOGGER.info("{}", token);
        return token != null ? ResponseEntity.ok(token) : new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
