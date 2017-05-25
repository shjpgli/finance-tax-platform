package com.abc12366.admin.web;

import com.abc12366.admin.model.User;
import com.abc12366.admin.model.UserExtend;
import com.abc12366.admin.model.bo.UserBO;
import com.abc12366.admin.model.bo.UserExtendBO;
import com.abc12366.admin.model.bo.UserPasswordBO;
import com.abc12366.admin.model.bo.UserUpdateBO;
import com.abc12366.admin.service.UserService;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
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

/**
 * @author lizhongwei
 * @create 2017-05-02 10:08 AM
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/user", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                     @RequestParam(value = "username", required = false) String username,
                                     @RequestParam(value = "nickname", required = false) String nickname,
                                     @RequestParam(value = "status", required = false) Boolean status) {
        User user = new User();
        user.setUsername(username);
        user.setNickname(nickname);
        user.setStatus(status);
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<UserBO> userList = userService.selectList(user);
        LOGGER.info("{}", userList);
        return (userList == null) && userList.size() != 0 ?
                new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("userList", (Page) userList, "total", ((Page) userList).getTotal()));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity selectOne(@PathVariable("id") String id) {
        UserBO temp = userService.selectOne(id);
        LOGGER.info("{}", temp);
        return ResponseEntity.ok(temp);
    }


    @PostMapping
    public ResponseEntity addUser(@Valid @RequestBody UserBO userBO) {
        int upd = userService.addUser(userBO);
        LOGGER.info("{}", upd);
        return ResponseEntity.ok(upd);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity updateUser(@Valid @RequestBody UserUpdateBO userUpdateBO, @PathVariable("id") String id) {
        userUpdateBO.setId(id);
        int upd = userService.updateUser(userUpdateBO);
        LOGGER.info("{}", upd);
        return ResponseEntity.ok(upd);
    }

    /**
     * 修改用户密码
     * @param userPasswordBO
     * @return
     */
    @PutMapping(path = "/password")
    public ResponseEntity updateUserPwd(@Valid @RequestBody UserPasswordBO userPasswordBO) {
        int upd = userService.updateUserPwd(userPasswordBO);
        LOGGER.info("{}", upd);
        return ResponseEntity.ok(upd);
    }

    /**
     * 重置用户密码
     * @param id
     * @return
     */
    @PutMapping(path = "/password/{id}")
    public ResponseEntity resetUserPwd( @PathVariable("id") String id) {
        int upd = userService.resetUserPwd(id);
        LOGGER.info("{}", upd);
        return ResponseEntity.ok(upd);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteUserById(@PathVariable("id") String id) {
        int del = userService.deleteUserById(id);
        LOGGER.info("{}", del);
        return ResponseEntity.ok(del);
    }


    /**
     * 查看User详情
     * @param id
     * @return
     */
    @GetMapping(path = "/extend/{id}")
    public ResponseEntity selectUserExtend(@PathVariable("id") String id) {
        UserExtend temp = userService.selectUserExtendByUserId(id);
        LOGGER.info("{}", temp);
        return ResponseEntity.ok(temp);
    }

    /**
     * 更新User详情
     * @param id
     * @return
     */
    @PutMapping(path = "/extend/{id}")
    public ResponseEntity updateUserExtend(@Valid @RequestBody UserExtendBO userExtendBO,@PathVariable("id") String id) {
        userExtendBO.setUserId(id);
        UserExtend userExtend = userService.updateUserExtend(userExtendBO);
        LOGGER.info("{}", userExtend);
        return ResponseEntity.ok(userExtend);
    }

}
