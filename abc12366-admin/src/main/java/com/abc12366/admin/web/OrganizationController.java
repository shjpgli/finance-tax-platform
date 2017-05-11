package com.abc12366.admin.web;

import com.abc12366.admin.model.Organization;
import com.abc12366.admin.model.bo.OrganizationBO;
import com.abc12366.admin.service.OrganizationService;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @description：组织管理
 */
@Controller
@RequestMapping(path = "/org", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class OrganizationController {

    private static Logger LOGGER = LoggerFactory.getLogger(OrganizationController.class);

    @Autowired
    private OrganizationService organizationService;


    /**
     * 组织列表
     *
     * @return
     */
    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                     @RequestParam String name,@RequestParam Boolean status) {
        Organization organization = new Organization();
        organization.setName(name);
        organization.setStatus(status);
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<Organization> dataList = organizationService.selectList(organization);
        LOGGER.info("{}", dataList);
        return ResponseEntity.ok(Utils.kv("dataList", (Page) dataList, "total", ((Page) dataList).getTotal()));
    }

    /**
     * 添加组织
     *
     * @param organizationBO
     * @return
     */
    @PostMapping
    public ResponseEntity addOrganization(@Valid @RequestBody OrganizationBO organizationBO) {
        Organization organization = organizationService.addOrganization(organizationBO);
        LOGGER.info("{}", organization);
        return ResponseEntity.ok(organization);
    }

    /**
     * 查询单个组织
     *
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity selectOne(@PathVariable("id") String id) {
        Organization organization = organizationService.selectOrganizationById(id);
        LOGGER.info("{}", organization);
        return ResponseEntity.ok(organization);
    }

    /**
     * 修改组织
     *
     * @param organizationBO
     * @return
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity updateOrganization(@Valid @RequestBody OrganizationBO organizationBO,
                                             @PathVariable("id") String id) {
        organizationService.updateOrganization(organizationBO);
        LOGGER.info("修改组织成功");
        return ResponseEntity.ok(200);
    }

    /**
     * 删除组织--物理删除
     *
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteOrganizationById(@PathVariable("id") String id) {
        organizationService.deleteOrganizationById(id);
        LOGGER.info("删除组织成功");
        return ResponseEntity.ok(200);
    }
}
