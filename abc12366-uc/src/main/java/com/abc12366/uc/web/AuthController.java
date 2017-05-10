package com.abc12366.uc.web;

import com.abc12366.common.util.Constant;
import com.abc12366.uc.model.bo.LoginBO;
import com.abc12366.uc.model.bo.RegisterBO;
import com.abc12366.uc.model.bo.TokenBO;
import com.abc12366.uc.model.bo.UserBO;
import com.abc12366.uc.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-27 4:13 PM
 * @since 1.0.0
 */
@RestController
@RequestMapping(headers = Constant.VERSION_HEAD + "=" +Constant.VERSION_1)
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    /*
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@Valid @RequestBody LoginBO loginDTO) {
        LOGGER.info("{}", loginDTO);

        final String token = authService.login(loginDTO);

        // Return the token
        return ResponseEntity.ok(new TokenBO(token));
    }*/

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refresh(@RequestHeader(Constant.USER_TOKEN_HEAD) String token) {
        String refreshedToken = authService.refresh(token);
        if (refreshedToken == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(new TokenBO(refreshedToken));
        }
    }

    /*
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity register(@Valid @RequestBody RegisterBO registerBO) {
        LOGGER.info("{}", registerBO);
        UserBO user = authService.register(registerBO);
        return user != null ? ResponseEntity.ok(user) : new ResponseEntity<>(HttpStatus.CONFLICT);
    }*/

    @PostMapping(path = "/register")
    public ResponseEntity register(@Valid @RequestBody RegisterBO registerBO) {
        LOGGER.info("{}", registerBO);
        UserBO userBO = authService.register(registerBO);
        LOGGER.info("{}", userBO);
        return ResponseEntity.ok(userBO);
    }

    /*
用户登录方法：
    1.请求访问时获取token，token为空则需要用户名和密码登录
 */
    @PostMapping(path = "/login")
    public ResponseEntity login(@Valid @RequestBody LoginBO loginBO, HttpServletRequest request) throws Exception {
        LOGGER.info("{}", loginBO);
        String userToken = authService.login(loginBO, request.getHeader(Constant.APP_TOKEN_HEAD));
        LOGGER.info("{}", userToken);
        return userToken != null ? ResponseEntity.ok(userToken) : new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
