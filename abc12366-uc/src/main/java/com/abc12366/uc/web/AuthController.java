package com.abc12366.uc.web;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.gateway.web.BaseController;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.service.AuthService;
import com.abc12366.uc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 用户注册、登录等行为控制器
 *
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
    private UserService userService;

    public AuthController(RestTemplate restTemplate) {
        super(restTemplate);
    }

    /**
     * 用户使用手机号码进行注册
     *
     * @param registerBO RegisterBO
     * @param request    HttpServletRequest
     * @return 注册后生成的用户信息
     */
    @PostMapping(path = "/register")
    public ResponseEntity register(@Valid @RequestBody RegisterBO registerBO, HttpServletRequest request) {
        LOGGER.info("{}", registerBO);

        //进行手机验证码验证
        VerifyingCodeBO verifyBO = new VerifyingCodeBO();
        verifyBO.setPhone(registerBO.getPhone());
        verifyBO.setType(registerBO.getVerifyingType());
        verifyBO.setCode(registerBO.getVerifyingCode());
        if (authService.verifyCode(verifyBO, request)) {
            //注册
            UserReturnBO userReturnBO = authService.register(registerBO, request);
            return ResponseEntity.ok(Utils.kv("data", userReturnBO));
        } else {
            throw new ServiceException(4201);
        }
    }

    /**
     * 用户登录方法：用于java做接口调用进行登录
     *
     * @param loginBO LoginBO
     * @return 用户基本信息、用户token、token有效时间
     */
    @PostMapping(path = "/login")
    public ResponseEntity login(@Valid @RequestBody LoginBO loginBO){
        LOGGER.info("{}", loginBO);
        Map token = authService.login(loginBO, "1");
        LOGGER.info("{}", token);
        return ResponseEntity.ok(Utils.kv("data", token));
    }

    /**
     * 用户登录方法：用于js做接口调用进行登录(此场景多用于移动客户端登录)
     *
     * @param loginBO LoginBO
     * @return 用户基本信息、用户token、token有效时间
     */
    @PostMapping(path = "/login/js")
    public ResponseEntity loginJs(@Valid @RequestBody LoginBO loginBO) {
        LOGGER.info("{}", loginBO);
        Map token = authService.login(loginBO, "2");
        LOGGER.info("{}", token);
        return ResponseEntity.ok(Utils.kv("data", token));
    }

    /**
     * 用户通过手机号码+验证码的方式进行登录
     *
     * @param loginBO VerifyingCodeBO
     * @param request HttpServletRequest
     * @return 用户基本信息、用户token、token有效时间
     */
    @PostMapping(path = "/verifylogin")
    public ResponseEntity verifylogin(@Valid @RequestBody VerifyingCodeBO loginBO, HttpServletRequest request) {
        LOGGER.info("{}", loginBO);

        //进行手机验证码验证
        if (authService.verifyCode(loginBO, request)) {
            //如果用户当天定时任务没有完成，就在登录的时候生成
            LoginBO login = new LoginBO();
            login.setUsernameOrPhone(loginBO.getPhone());
            Map token = authService.login(login, "3");
            LOGGER.info("{}", token);
            return ResponseEntity.ok(Utils.kv("data", token));
        } else {
            authService.loginByVerifyFail(loginBO);
            return null;
        }
    }

    @GetMapping(path = "/user/u/openid/{openid}")
    public ResponseEntity loginByOpenId(@PathVariable String openid) {
        LOGGER.info("{}", openid);
        UserBO user = userService.selectByopenid(openid);
        if (user == null) {
            throw new ServiceException(4018);
        }
        LoginBO bo = new LoginBO();
        bo.setUsernameOrPhone(user.getUsername());
        Map token = authService.login(bo, "4");
        LOGGER.info("{}", user);
        return ResponseEntity.ok(Utils.kv("data", token));
    }

    /**
     * 登陆成功之后需要处理的业务
     *
     * @param request HttpServletRequest
     * @return 无
     */
    @GetMapping(path = "/login/todo")
    public ResponseEntity todoAfterLogin(HttpServletRequest request) {
        CompletableFuture cf = authService.todoAfterLogin(request);
        CompletableFuture.allOf(cf);
        LOGGER.info("todoAfterLogin");
        return ResponseEntity.ok(Utils.kv());
    }

    /**
     * 用户通过手机号码+验证码的方式身份验证
     *
     * @param loginBO 手机号、验证码、验证码类型
     * @param request HttpServletRequest
     * @return token 重置密码时需要带上的token
     * @throws Exception md5加密异常
     */
    @PostMapping(path = "/verifyphone")
    public ResponseEntity verifyPhone(@Valid @RequestBody VerifyingCodeBO loginBO,
                                      HttpServletRequest request) throws Exception {
        LOGGER.info("{}", loginBO);

        //进行手机验证码验证
        if (authService.verifyCode(loginBO, request)) {
            String token = authService.verifyPhone(loginBO.getPhone());
            LOGGER.info("{}", token);
            return ResponseEntity.ok(Utils.kv("data", token));
        } else {
            throw new ServiceException(4201);
        }
    }

    /**
     * 通过手机号修改密码
     *
     * @param bo ResetPasswordBO
     * @return true:成功, false:失败
     * @throws Exception md5加密异常
     */
    @PostMapping(path = "/resetpassword")
    public ResponseEntity resetPasswordByPhone(@Valid @RequestBody ResetPasswordBO bo) throws Exception {
        LOGGER.info("{}", bo);
        boolean result = authService.resetPasswordByPhone(bo);
        LOGGER.info("{}", result);
        return ResponseEntity.ok(Utils.kv("data", result));
    }

    /**
     * 用户退出登录接口
     *
     * @param token String
     * @return ResponseEntity
     */
    @DeleteMapping(path = "/logout/{token}")
    public ResponseEntity logout(@PathVariable String token) {
        LOGGER.info("{}", token);
        authService.logout(token);
        return ResponseEntity.ok(Utils.kv());
    }
}
