package com.abc12366.uc.web;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.gateway.web.BaseController;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.service.AuthService;
import com.abc12366.uc.service.IpService;
import com.abc12366.uc.wsbssoa.utils.RSA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
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
    private AuthService authService;

    @Autowired
    private IpService ipService;

    public AuthController(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refresh(@RequestHeader(Constant.USER_TOKEN_HEAD) String token) {
        String refreshedToken = authService.refresh(token);
        return ResponseEntity.ok(Utils.kv("data", refreshedToken));
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
    public ResponseEntity register(@Valid @RequestBody RegisterBO registerBO, HttpServletRequest request) throws
            IOException {
        LOGGER.info("{}", registerBO);
        // 记录用户IP归属
        if (!StringUtils.isEmpty(request.getHeader(Constant.CLIENT_IP))) {
            ipService.merge(request.getHeader(Constant.CLIENT_IP));
        }

        //进行手机验证码验证
        VerifyingCodeBO verifyBO = new VerifyingCodeBO();
        verifyBO.setPhone(registerBO.getPhone());
        verifyBO.setType(registerBO.getVerifyingType());
        verifyBO.setCode(registerBO.getVerifyingCode());
        if (authService.verifyCode(verifyBO, request)) {
            //注册
            UserReturnBO userReturnBO = authService.register(registerBO);
            return ResponseEntity.ok(Utils.kv("data", userReturnBO));
        } else {
            throw new ServiceException(4201);
        }


    }

    /*
    用户登录方法：用于java做接口调用进行登录
        1.请求访问时获取token，token为空则需要用户名和密码登录
     */
    @PostMapping(path = "/login")
    public ResponseEntity login(@Valid @RequestBody LoginBO loginBO, HttpServletRequest request) throws Exception {
        LOGGER.info("{}", loginBO);
        // 记录用户IP归属
        if (!StringUtils.isEmpty(request.getHeader(Constant.CLIENT_IP))) {
            ipService.merge(request.getHeader(Constant.CLIENT_IP));
        }
        Map token = authService.login(loginBO, request.getHeader(Constant.APP_TOKEN_HEAD));
        LOGGER.info("{}", token);
        return ResponseEntity.ok(Utils.kv("data", token));
    }

    /*
    用户登录方法：用于js做接口调用进行登录(此场景多用于移动客户端登录)
        1.请求访问时获取token，token为空则需要用户名和密码登录
     */
    @PostMapping(path = "/login/js")
    public ResponseEntity loginJs(@Valid @RequestBody LoginBO loginBO, HttpServletRequest request) throws Exception {
        LOGGER.info("{}", loginBO);
        // 记录用户IP归属
        if (!StringUtils.isEmpty(request.getHeader(Constant.CLIENT_IP))) {
            ipService.merge(request.getHeader(Constant.CLIENT_IP));
        }
        Map token = authService.loginJs(loginBO, request.getHeader(Constant.APP_TOKEN_HEAD));
        LOGGER.info("{}", token);
        return ResponseEntity.ok(Utils.kv("data", token));
    }

    /*
    用户通过手机验证码进行登录
     */
    @PostMapping(path = "/verifylogin")
    public ResponseEntity loginByVerifyingCode(@Valid @RequestBody VerifyingCodeBO loginBO, HttpServletRequest
            request) throws Exception {
        LOGGER.info("{}", loginBO);
        // 记录用户IP归属
        if (!StringUtils.isEmpty(request.getHeader(Constant.CLIENT_IP))) {
            ipService.merge(request.getHeader(Constant.CLIENT_IP));
        }
        //进行手机验证码验证
        if (authService.verifyCode(loginBO, request)) {
            Map token = authService.loginByVerifyingCode(loginBO, request.getHeader(Constant.APP_TOKEN_HEAD));
            LOGGER.info("{}", token);
            return ResponseEntity.ok(Utils.kv("data", token));
        } else {
            throw new ServiceException(4201);
        }
    }

    /**
     * 用户登出
     */
    @DeleteMapping(path = "/logout/{token}")
    public ResponseEntity logout(@PathVariable String token, HttpServletRequest request) throws Exception {
        LOGGER.info("{}", token);
        authService.logout(token);
        return ResponseEntity.ok(Utils.kv());
    }

    /**
     *
     * @param loginBO
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping(path = "/rsa/login")
    public ResponseEntity rsaLogin(@Valid @RequestBody LoginBO loginBO, HttpServletRequest request) throws Exception {
        LOGGER.info("{}", loginBO);
        // 记录用户IP归属
        if (!StringUtils.isEmpty(request.getHeader(Constant.CLIENT_IP))) {
            ipService.merge(request.getHeader(Constant.CLIENT_IP));
        }
        //将密码解密
        loginBO.setPassword(decode(loginBO.getPassword()));

        Map token = authService.login(loginBO, request.getHeader(Constant.APP_TOKEN_HEAD));
        LOGGER.info("{}", token);
        return ResponseEntity.ok(Utils.kv("data", token));
    }

    public String decode(String str) throws Exception {
        RSAPublicKey publicKey = RSA.getDefaultPublicKey();
        RSAPrivateKey privateKey = RSA.getDefaultPrivateKey();

//        byte[] bytes = RSA.encrypt(publicKey, str.getBytes());
//        String s = new BASE64Encoder().encode(bytes);
//        byte[] bytes2 = new BASE64Decoder().decodeBuffer(s);
//        byte[] bytes2 = new BASE64Decoder().decodeBuffer(str);
//        String response2 = new String(RSA.decrypt(privateKey, bytes2));
//        System.out.println(response2);
//        return response2;

        byte[] bytes2 = new BASE64Decoder().decodeBuffer(str);
        String newStr = new String(bytes2);
        String []test=newStr.split(" ");
        String json="";
        for(String s : test){
            json+= RSA.decryptStringByJs(s);
        }
        return json;
    }

}
