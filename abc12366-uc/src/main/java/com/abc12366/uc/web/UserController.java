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
@RequestMapping(path = "/user", headers = Constant.VERSION_HEAD + "=" +Constant.VERSION_1)
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize) {
        LOGGER.info("{}:{}", pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<UserBO> userList = userService.selectList();
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
        return ResponseEntity.ok(user);
    }

    @PutMapping()
    public ResponseEntity update(@Valid @RequestBody UserUpdateBO userUpdateDTO) {
        LOGGER.info("{}", userUpdateDTO);
        UserBO user = userService.update(userUpdateDTO);
        LOGGER.info("{}", user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseEntity delete(@PathVariable String userId) {
        LOGGER.info("{}", userId);
        UserBO userBO = userService.delete(userId);
        LOGGER.info("{}", userBO);
        return ResponseEntity.ok(userBO);
    }
}
