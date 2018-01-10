package com.abc12366.uc.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.bo.LoginBO;
import com.abc12366.uc.model.bo.PasswordUpdateBO;
import com.abc12366.uc.model.bo.RegisterBO;
import com.abc12366.uc.model.bo.ResetPasswordBO;
import com.abc12366.uc.model.bo.UserReturnBO;
import com.abc12366.uc.model.bo.VerifyingCodeBO;
import com.abc12366.uc.service.AuthService;
import com.abc12366.uc.service.AuthServiceNew;

@RestController
@RequestMapping(headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class AuthControllerNew {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthControllerNew.class);

	@Autowired
    private AuthService authService;
	
    @Autowired
    private AuthServiceNew authServiceNew;
    
    /**
     * 用户使用手机号码进行注册
     *
     * @param registerBO RegisterBO
     * @param request    HttpServletRequest
     * @return 注册后生成的用户信息
     */
    @SuppressWarnings("rawtypes")
	@PostMapping(path = "/registernew")
    public ResponseEntity register(@Valid @RequestBody RegisterBO registerBO, HttpServletRequest request) {
        LOGGER.info("{}", registerBO);

        //进行手机验证码验证
        VerifyingCodeBO verifyBO = new VerifyingCodeBO();
        verifyBO.setPhone(registerBO.getPhone());
        verifyBO.setType(registerBO.getVerifyingType());
        verifyBO.setCode(registerBO.getVerifyingCode());
        if (authService.verifyCode(verifyBO, request)) {
            //注册
            UserReturnBO userReturnBO = authServiceNew.register(registerBO, request);
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
    @SuppressWarnings("rawtypes")
	@PostMapping(path = "/loginnew")
    public ResponseEntity login(@Valid @RequestBody LoginBO loginBO){
        LOGGER.info("{}", loginBO);
        Map token = authServiceNew.login(loginBO, "1");
        LOGGER.info("{}", token);
        return ResponseEntity.ok(Utils.kv("data", token));
    }
    
    /**
     * 用户登录方法：用于js做接口调用进行登录(此场景多用于移动客户端登录)
     *
     * @param loginBO LoginBO
     * @return 用户基本信息、用户token、token有效时间
     */
    @SuppressWarnings("rawtypes")
	@PostMapping(path = "/login/jsnew")
    public ResponseEntity loginJs(@Valid @RequestBody LoginBO loginBO) {
        LOGGER.info("{}", loginBO);
        Map token = authServiceNew.login(loginBO, "2");
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
    @SuppressWarnings("rawtypes")
	@PostMapping(path = "/verifyloginnew")
    public ResponseEntity verifylogin(@Valid @RequestBody VerifyingCodeBO loginBO, HttpServletRequest request) {
        LOGGER.info("{}", loginBO);

        //进行手机验证码验证
        if (authService.verifyCode(loginBO, request)) {
            //如果用户当天定时任务没有完成，就在登录的时候生成
            LoginBO login = new LoginBO();
            login.setUsernameOrPhone(loginBO.getPhone());
            Map token = authServiceNew.login(login, "3");
            LOGGER.info("{}", token);
            return ResponseEntity.ok(Utils.kv("data", token));
        } else {
            authService.loginByVerifyFail(loginBO);
            return null;
        }
    }
    
    /**
     * 通过手机号修改密码
     *
     * @param bo ResetPasswordBO
     * @return true:成功, false:失败
     * @throws Exception md5加密异常
     */
    @SuppressWarnings("rawtypes")
	@PostMapping(path = "/resetpasswordnew")
    public ResponseEntity resetPasswordByPhone(@Valid @RequestBody ResetPasswordBO bo) throws Exception {
        LOGGER.info("{}", bo);
        boolean result = authServiceNew.resetPasswordByPhone(bo);
        LOGGER.info("{}", result);
        return ResponseEntity.ok(Utils.kv("data", result));
    }
    
    //用户修改密码
    @SuppressWarnings("rawtypes")
	@PutMapping(path = "/passwordnew")
    public ResponseEntity updatePassword(@Valid @RequestBody PasswordUpdateBO passwordUpdateBO, HttpServletRequest
            request) {
        LOGGER.info("用户修改密码：{}", passwordUpdateBO);
        Boolean message = authServiceNew.updatePassword(passwordUpdateBO, request);
        LOGGER.info("{}", message);
        LOGGER.info("用户修改密码结果：{}", message);
        return ResponseEntity.ok(Utils.kv("data", message));
    }
    
}
