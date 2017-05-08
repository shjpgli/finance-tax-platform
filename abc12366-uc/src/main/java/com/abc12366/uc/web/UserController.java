package com.abc12366.uc.web;

import com.abc12366.common.util.Constant;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * User测试控制器类，包含CRUD接口；以常规JSON形式返回数据
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-20 3:18 PM
 * @since 2.0.0
 */
@RestController
@RequestMapping(path = "/user", headers = Constant.VERSION_HEAD + "=1")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity selectList() {
        List<UserBO> users = userService.selectList();
        LOGGER.info("{}", users);
        return ResponseEntity.ok(users);
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<?> selectOne(@PathVariable String userId) {
        LOGGER.info("{}", userId);
        UserBO user = userService.selectOne(userId);
        LOGGER.info("{}", user);
        return ResponseEntity.ok(user);
    }

    @GetMapping(path = "/u/{usernameOrPhone}")
    public ResponseEntity selectByUsernameOrPhone(@PathVariable String usernameOrPhone) {
        LOGGER.info("{}", usernameOrPhone);
        UserBO user = userService.selectByUsernameOrPhone(usernameOrPhone);
        LOGGER.info("{}", user);
        return ResponseEntity.ok(user);
    }

    @PutMapping()
    public ResponseEntity update(@Valid @RequestBody UserUpdateBO userUpdateDTO) {
        LOGGER.info("{}", userUpdateDTO);
        UserBO user = userService.update(userUpdateDTO);
        LOGGER.info("{}", user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(path = "/register")
    public ResponseEntity register(@Valid @RequestBody RegisterBO registerBO) {
        LOGGER.info("{}", registerBO);
        UserBO userBO1 = userService.register(registerBO);
        LOGGER.info("{}", userBO1);
        return ResponseEntity.ok(userBO1);
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseEntity delete(@PathVariable String userId) {
        LOGGER.info("{}", userId);
        UserBO userBO = userService.delete(userId);
        LOGGER.info("{}", userBO);
        return ResponseEntity.ok(userBO);
    }

    /*
    用户登录方法：
        1.请求访问时获取token，token为空则需要用户名和密码登录
     */
    @PostMapping(path = "/login")
    public ResponseEntity login(@Valid @RequestBody LoginBO loginBO, HttpServletRequest request) {
        LOGGER.info("{}", loginBO);
        String userToken = userService.login(loginBO, request.getHeader(Constant.APP_TOKEN_HEAD));
        LOGGER.info("{}", userToken);
        return userToken != null ? ResponseEntity.ok(userToken) : new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
