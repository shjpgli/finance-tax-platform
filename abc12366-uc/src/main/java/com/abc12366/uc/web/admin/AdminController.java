package com.abc12366.uc.web.admin;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.admin.Admin;
import com.abc12366.uc.model.admin.AdminExtend;
import com.abc12366.uc.model.admin.bo.*;
import com.abc12366.uc.service.admin.AdminService;
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
 * @author lizhongwei
 * @create 2017-05-02 10:08 AM
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/admin/user", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class AdminController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                     @RequestParam(value = "username", required = false) String username,
                                     @RequestParam(value = "nickname", required = false) String nickname,
                                     @RequestParam(value = "orgId", required = false) String orgId,
                                     @RequestParam(value = "status", required = false) Boolean status) {
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setNickname(nickname);
        admin.setStatus(status);
        admin.setOrganizationId(orgId);
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<AdminBO> userList = adminService.selectList(admin);
        LOGGER.info("userList:{}", userList);
        return userList == null ?
                ResponseEntity.ok(Utils.kv()):
                ResponseEntity.ok(Utils.kv("dataList", (Page) userList, "total", ((Page) userList).getTotal()));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity selectOne(@PathVariable("id") String id) {
        AdminBO adminBO = adminService.selectOne(id);
        LOGGER.info("adminBO:{}", adminBO);
        return ResponseEntity.ok(Utils.kv("data", adminBO));
    }


    /**
     * 新增用户
     * @param adminBO
     * @return
     */
    @PostMapping
    public ResponseEntity addUser(@Valid @RequestBody AdminBO adminBO) {
        AdminBO bo = adminService.addUser(adminBO);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    /**
     * 修改用户信息
     * @param adminUpdateBO
     * @param id
     * @return
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity updateUser(@Valid @RequestBody AdminUpdateBO adminUpdateBO, @PathVariable("id") String id) {
        LOGGER.info("id:{}", id);
        adminUpdateBO.setId(id);
        AdminUpdateBO bo = adminService.updateUser(adminUpdateBO);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    /**
     * 启用、禁用
     * @return
     */
    @PutMapping(path = "/enable")
    public ResponseEntity enable(@Valid @RequestBody AdminUpdateBO adminUpdateBO) {
        LOGGER.info("{}", adminUpdateBO);
        adminService.enable(adminUpdateBO);
        return ResponseEntity.ok(Utils.kv());
    }


    /**
     * 修改用户密码
     * @param userPasswordBO
     * @return
     */
    @PutMapping(path = "/password")
    public ResponseEntity updateUserPwd(@Valid @RequestBody UserPasswordBO userPasswordBO) {
        LOGGER.info("userPasswordBO:{}", userPasswordBO);
        int upd = adminService.updateUserPwd(userPasswordBO);
        LOGGER.info("upd:{}", upd);
        return ResponseEntity.ok(Utils.kv("data", upd));
    }

    /**
     * 重置用户密码
     * @param id
     * @return
     */
    @PutMapping(path = "/password/{id}")
    public ResponseEntity resetUserPwd( @PathVariable("id") String id) {
        LOGGER.info("id:{}", id);
        int upd = adminService.resetUserPwd(id);
        LOGGER.info("upd:{}", upd);
        return ResponseEntity.ok(Utils.kv("data", upd));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteUserById(@PathVariable("id") String id) {
        LOGGER.info("id{}", id);
        int del = adminService.deleteUserById(id);
        LOGGER.info("del{}", del);
        return ResponseEntity.ok(Utils.kv());
    }


    /**
     * 查看User详情
     * @param id
     * @return
     */
    @GetMapping(path = "/extend/{id}")
    public ResponseEntity selectUserExtend(@PathVariable("id") String id) {
        LOGGER.info("id:{}", id);
        AdminExtend adminExtend = adminService.selectUserExtendByUserId(id);
        LOGGER.info("adminExtend:{}", adminExtend);
        return ResponseEntity.ok(Utils.kv("data", adminExtend));
    }

    /**
     * 更新User详情
     * @param id
     * @return
     */
    @PutMapping(path = "/extend/{id}")
    public ResponseEntity updateUserExtend(@Valid @RequestBody AdminExtendBO adminExtendBO,@PathVariable("id") String id) {
        LOGGER.info("adminExtendBO:{}", adminExtendBO);
        adminExtendBO.setUserId(id);
        AdminExtend adminExtend = adminService.updateUserExtend(adminExtendBO);
        LOGGER.info("adminExtend:{}", adminExtend);
        return ResponseEntity.ok(Utils.kv("data", adminExtend));
    }

    /**
     * 查看LoginInfo信息
     * @param token
     * @return
     */
    @GetMapping(path = "/token/{token}")
    public ResponseEntity selectUser(@PathVariable("token") String token) {
        long start = System.currentTimeMillis();
        LOGGER.info("token:{}", token);
        LoginInfoBO loginInfo = adminService.selectLoginInfoByToken(token);
        LOGGER.info("loginInfo:{}", loginInfo);
        long end = System.currentTimeMillis();
        long res = end - start;
        LOGGER.info("响应用时:{}毫秒",res);
        return ResponseEntity.ok(Utils.kv("data", loginInfo));
    }
}
