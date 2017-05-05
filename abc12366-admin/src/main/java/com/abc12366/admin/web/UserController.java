package com.abc12366.admin.web;

import com.abc12366.admin.model.User;
import com.abc12366.admin.model.bo.UserBO;
import com.abc12366.admin.service.UserService;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 *
 * @author lizhongwei
 * @create 2017-05-02 10:08 AM
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize) {
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<User> dataList = userService.selectList();
        LOGGER.info("{}", dataList);
        if(dataList != null && dataList.size()!=0){
            return ResponseEntity.ok(Utils.kv("dataList", (Page) dataList, "total", ((Page) dataList).getTotal()));
        }
        return ResponseEntity.ok(dataList);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity selectOne(@PathVariable("id")String id) {
        User temp = userService.selectOne(id);
        LOGGER.info("{}", temp);
        return ResponseEntity.ok(temp);
    }

    @PostMapping(path = "/register")
    public ResponseEntity register(@Valid @RequestBody UserBO userBO) {
        int registerNum = userService.register(userBO);
        LOGGER.info("{}", registerNum);
        return ResponseEntity.ok(registerNum);
    }

    @PostMapping(path = "/login")
    public ResponseEntity login(@Valid @RequestBody UserBO userBO) {
        UserBO user = userService.login(userBO);
        LOGGER.info("{}", user);
        return ResponseEntity.ok(user);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity updateUser(@Valid @RequestBody UserBO userBO,@PathVariable("id")String id) {
        int upd = userService.updateUser(userBO);
        LOGGER.info("{}", upd);
        return ResponseEntity.ok(upd);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteUserById(@PathVariable("id")String id) {
        int del = userService.deleteUserById(id);
        LOGGER.info("{}", del);
        return ResponseEntity.ok(del);
    }
}
