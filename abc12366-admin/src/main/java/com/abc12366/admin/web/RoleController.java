package com.abc12366.admin.web;

import com.abc12366.admin.model.Role;
import com.abc12366.admin.model.bo.RoleBO;
import com.abc12366.admin.service.RoleService;
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
 * 角色控制器
 *
 * @author lizhongwei
 * @create 2017-05-03 10:08 AM
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/role")
public class RoleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize) {
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<Role> dataList = roleService.selectList();
        LOGGER.info("{}", dataList);
        return ResponseEntity.ok(Utils.kv("dataList", (Page) dataList, "total", ((Page) dataList).getTotal()));
    }

    @PostMapping(path = "/selectOne/{role}")
    public ResponseEntity selectOne(@RequestBody RoleBO roleBO) {
        Role temp = roleService.selectOne(roleBO);
        LOGGER.info("{}", temp);
        return ResponseEntity.ok(temp);
    }

    @PostMapping(path = "/addRole/{roleBO}")
    public ResponseEntity addRole(@Valid @RequestBody RoleBO roleBO) {
        int insert = roleService.addRole(roleBO);
        LOGGER.info("{}", insert);
        return ResponseEntity.ok(insert);
    }

    @PostMapping(path = "/updateRole/{roleBO}")
    public ResponseEntity updateRole(@Valid @RequestBody RoleBO roleBO) {
        int upd = roleService.updateRole(roleBO);
        LOGGER.info("{}", upd);
        return ResponseEntity.ok(upd);
    }

    @DeleteMapping(path = "/deleteRoleById/{id}")
    public ResponseEntity deleteRoleById(@PathVariable("id")String id) {
        int del = roleService.deleteRoleById(id);
        LOGGER.info("{}", del);
        return ResponseEntity.ok(del);
    }


}
