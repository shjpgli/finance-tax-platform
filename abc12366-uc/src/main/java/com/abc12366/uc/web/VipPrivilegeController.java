package com.abc12366.uc.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.model.bo.VipPrivilegeBO;
import com.abc12366.uc.model.bo.VipPrivilegeInsertAndUpdateBO;
import com.abc12366.uc.service.VipPrivilegeService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-22
 * Time: 9:13
 */
@RestController
@RequestMapping(path = "/uvip/privilege", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class VipPrivilegeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(VipPrivilegeController.class);

    @Autowired
    private VipPrivilegeService vipPrivilegeService;

    @GetMapping
    public ResponseEntity selectList(@RequestParam(required = false) String name,
                                     @RequestParam(required = false) String level,
                                     @RequestParam(required = false) Boolean status,
                                     @RequestParam(required = false, defaultValue = Constant.pageNum) int page,
                                     @RequestParam(required = false, defaultValue = Constant.pageSize) int size) {
        LOGGER.info("{}:{}:{}:{}:{}", name, level, status, page, size);
        Map<String, Object> map = new HashMap<>();
        if (name != null && StringUtils.isEmpty(name)) {
            name = null;
        }
        if (level != null && StringUtils.isEmpty(level)) {
            level = null;
        }
        if (status != null && StringUtils.isEmpty(status)) {
            status = null;
        }
        map.put("name", name);
        map.put("level", level);
        map.put("status", status);
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<VipPrivilegeBO> privilegeList = vipPrivilegeService.selectList(map);
        LOGGER.info("{}", privilegeList);
        return ResponseEntity.ok(Utils.kv("privilegeList", privilegeList, "total", ((Page) privilegeList).getTotal()));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity selectOne(@PathVariable String id) {
        LOGGER.info("{}", id);
        VipPrivilegeBO vipPrivilegeBO = vipPrivilegeService.selectOne(id);
        LOGGER.info("{}", vipPrivilegeBO);
        return ResponseEntity.ok(vipPrivilegeBO);
    }

    @PostMapping
    public ResponseEntity insert(@Valid @RequestBody VipPrivilegeInsertAndUpdateBO vipPrivilegeInsertBO) {
        LOGGER.info("{}", vipPrivilegeInsertBO);
        VipPrivilegeBO vipPrivilegeBOReturn = vipPrivilegeService.insert(vipPrivilegeInsertBO);
        LOGGER.info("{}", vipPrivilegeBOReturn);
        return ResponseEntity.ok(vipPrivilegeBOReturn);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity update(@Valid @RequestBody VipPrivilegeInsertAndUpdateBO vipPrivilegeUpdateBO, @PathVariable String id) {
        LOGGER.info("{}", vipPrivilegeUpdateBO);
        VipPrivilegeBO vipPrivilegeBOReturn = vipPrivilegeService.update(vipPrivilegeUpdateBO, id);
        LOGGER.info("{}", vipPrivilegeBOReturn);
        return ResponseEntity.ok(vipPrivilegeBOReturn);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        LOGGER.info("{}", id);
        boolean result = vipPrivilegeService.delete(id);
        return result ? ResponseEntity.ok(null) : new ResponseEntity(Utils.bodyStatus(4102), HttpStatus.BAD_REQUEST);
    }
}
