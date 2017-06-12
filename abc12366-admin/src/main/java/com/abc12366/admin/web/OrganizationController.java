package com.abc12366.admin.web;

import com.abc12366.admin.model.Organization;
import com.abc12366.admin.model.bo.OrganizationBO;
import com.abc12366.admin.model.bo.OrganizationUpdateBO;
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
                                     @RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "status", required = false) Boolean status) {
        LOGGER.info("{}", name,status);
        Organization organization = new Organization();
        organization.setName(name);
        organization.setStatus(status);
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<OrganizationBO> organizationList = organizationService.selectList(organization);
        LOGGER.info("{}", organizationList);
        return organizationList == null ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) organizationList, "total", ((Page) organizationList).getTotal()));
    }

    /**
     * 添加组织
     *
     * @param organizationBO
     * @return
     */
    @PostMapping
    public ResponseEntity addOrganization(@Valid @RequestBody OrganizationBO organizationBO) {
        LOGGER.info("{}", organizationBO);
        Organization organization = organizationService.addOrganization(organizationBO);
        LOGGER.info("{}", organization);
        return ResponseEntity.ok(Utils.kv("data", organization));
    }

    /**
     * 查询单个组织
     *
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity selectOne(@PathVariable("id") String id) {
        LOGGER.info("{id}", id);
        OrganizationBO organizationBO = organizationService.selectOrganizationById(id);
        LOGGER.info("{organizationBO}", organizationBO);
        return ResponseEntity.ok(Utils.kv("data", organizationBO));
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
        LOGGER.info("id",id);
        OrganizationBO bo = organizationService.updateOrganization(organizationBO);
        LOGGER.info("修改组织成功",organizationBO);
        return ResponseEntity.ok(Utils.kv("data", organizationBO));
    }

    /**
     * 删除组织--物理删除
     *
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteOrganizationById(@PathVariable("id") String id) {
        LOGGER.info("id",id);
        organizationService.deleteOrganizationById(id);
        LOGGER.info("删除组织成功");
        return ResponseEntity.ok(Utils.kv());
    }

    /**
     * 启用、禁用
     * @return
     */
    @PutMapping(path = "/enable")
    public ResponseEntity enable(@Valid @RequestBody OrganizationUpdateBO updateBO) {
        LOGGER.info("{}", updateBO);
        organizationService.enable(updateBO);
        return ResponseEntity.ok(Utils.kv());
    }

    /**
     * 启用、禁用
     * @return
     */
    @PutMapping(path = "/disableAll")
    public ResponseEntity disableAll() {
        organizationService.disableAll();
        return ResponseEntity.ok(Utils.kv());
    }
}
