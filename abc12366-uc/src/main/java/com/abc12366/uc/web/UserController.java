package com.abc12366.uc.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User测试控制器类，包含CRUD接口；以常规JSON形式返回数据
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-20 3:18 PM
 * @since 2.0.0
 */
@RestController
@RequestMapping(path = "/user", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity selectList(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String nickname,
            @RequestParam(required = false) Boolean status,
            @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
            @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        LOGGER.info("{}:{}:{}:{}:{}:{}", username, phone, nickname, status, page, size);
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("phone", phone);
        map.put("nickname", nickname);
        map.put("status", status);
        List<UserBO> userList = userService.selectList(map);
        LOGGER.info("{}", userList);
        return (userList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("userList", (Page) userList, "total", ((Page) userList).getTotal()));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> selectOne(@PathVariable String id) {
        LOGGER.info("{}", id);
        Map map = userService.selectOne(id);
        LOGGER.info("{}", map);
        return ResponseEntity.ok(map);
    }

    @GetMapping(path = "/u/{usernameOrPhone}")
    public ResponseEntity selectByUsernameOrPhone(@PathVariable String usernameOrPhone) {
        LOGGER.info("{}", usernameOrPhone);
        UserBO user = userService.selectByUsernameOrPhone(usernameOrPhone);
        LOGGER.info("{}", user);
        return (user != null) ? ResponseEntity.ok(user) : new ResponseEntity(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity update(@Valid @RequestBody UserUpdateBO userUpdateDTO, @PathVariable String id) {
        LOGGER.info("{}", userUpdateDTO);
        userUpdateDTO.setId(id);
        UserBO user = userService.update(userUpdateDTO);
        LOGGER.info("{}", user);
        return (user != null) ? ResponseEntity.ok(user) : new ResponseEntity(Utils.bodyStatus(4102), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseEntity delete(@PathVariable String userId) {
        LOGGER.info("{}", userId);
        UserBO user = userService.delete(userId);
        LOGGER.info("{}", user);
        return (user != null) ? ResponseEntity.ok(user) : new ResponseEntity(Utils.bodyStatus(4103), HttpStatus.BAD_REQUEST);
    }
}
