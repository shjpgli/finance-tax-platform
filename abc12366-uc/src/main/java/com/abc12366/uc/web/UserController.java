package com.abc12366.uc.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping(path = "/user", headers = "version=1")
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

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> selectOne(@PathVariable Long id) {
        LOGGER.info("{}", id);
        UserBO user = userService.selectOne(id);
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
}
