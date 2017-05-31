package com.abc12366.admin.web;

import com.abc12366.admin.model.Role;
import com.abc12366.admin.model.RoleMenu;
import com.abc12366.admin.model.bo.RoleBO;
import com.abc12366.admin.model.bo.RoleMenuBO;
import com.abc12366.admin.service.RoleService;
import com.abc12366.common.exception.ServiceException;
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
 * 角色控制器
 *
 * @author lizhongwei
 * @create 2017-05-03 10:08 AM
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/role", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class RoleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                     @RequestParam(value = "roleName", required = false) String roleName,
                                     @RequestParam(value = "status", required = false) Boolean status) {
        Role role = new Role();
        role.setRoleName(roleName);
        role.setStatus(status);
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<RoleBO> roleList = roleService.selectList(role);
        LOGGER.info("{}", roleList);
        return (roleList == null) && roleList.size() != 0 ?
                new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("roleList", (Page) roleList, "total", ((Page) roleList).getTotal()));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity selectOne(@PathVariable("id") String id) {
        RoleBO roleBO = new RoleBO();
        roleBO.setId(id);
        RoleBO temp = roleService.selectRoleById(id);
        LOGGER.info("{}", temp);
        return ResponseEntity.ok(temp);
    }

    @GetMapping(path = "/selectRoleByName/{roleName}")
    public ResponseEntity selectRoleByName(@PathVariable("roleName") String roleName) {
        RoleBO roleBO = new RoleBO();
        roleBO.setRoleName(roleName);
        Role temp = roleService.selectRoleByName(roleBO);
        LOGGER.info("{}", temp);
        return ResponseEntity.ok(temp);
    }


    @PostMapping
    public ResponseEntity addRole(@Valid @RequestBody RoleBO roleBO) {
        Role role = roleService.addRole(roleBO);
        LOGGER.info("{}", role);
        return ResponseEntity.ok(role);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity updateRole(@Valid @RequestBody RoleBO roleBO, @PathVariable("id") String id) {
        roleBO.setId(id);
        RoleBO upd = roleService.updateRole(roleBO);
        LOGGER.info("{}", upd);
        return ResponseEntity.ok(upd);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteRoleById(@PathVariable("id") String id) {
        int del = roleService.deleteRoleById(id);
        LOGGER.info("{}", del);
        return ResponseEntity.ok(null);
    }

    /**
     * 授权
     *
     * @param roleMenuBO
     * @return
     */
    @PostMapping("/grant")
    public ResponseEntity grant(@Valid @RequestBody RoleMenuBO roleMenuBO) {
        try {
            roleService.updateRoleMenu(roleMenuBO.getRoleId(), roleMenuBO.getMenuId());
            LOGGER.info("{}", roleMenuBO.toString());
            return ResponseEntity.ok(200);
        } catch (RuntimeException e) {
            throw new ServiceException(4107);
        }
    }
}
