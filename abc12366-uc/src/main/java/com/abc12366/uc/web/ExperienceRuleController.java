package com.abc12366.uc.web;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.bo.ExperienceRuleBO;
import com.abc12366.uc.model.bo.ExperienceRuleInsertBO;
import com.abc12366.uc.model.bo.ExperienceRuleUpdateBO;
import com.abc12366.uc.service.ExperienceRuleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Admin: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-22
 * Time: 9:13
 */
@RestController
@RequestMapping(path = "/uexp/rule", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class ExperienceRuleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceRuleController.class);

    @Autowired
    private ExperienceRuleService experienceRuleService;

    @GetMapping
    public ResponseEntity selectList(@RequestParam(required = false) String name,
                                     @RequestParam(required = false) String code,
                                     @RequestParam(required = false) Boolean type,
                                     @RequestParam(required = false, defaultValue = Constant.pageNum) int page,
                                     @RequestParam(required = false, defaultValue = Constant.pageSize) int size) {
        LOGGER.info("{}:{}:{}:{}:{}", name, code, type, page, size);
        Map<String, Object> map = new HashMap<>();
        if (name != null && StringUtils.isEmpty(name)) {
            name = null;
        }
        if (code != null && StringUtils.isEmpty(code)) {
            code = null;
        }
        if (type != null && StringUtils.isEmpty(type)) {
            type = null;
        }
        map.put("name", name);
        map.put("code", code);
        map.put("type", type);
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<ExperienceRuleBO> ruleList = experienceRuleService.selectList(map);
        LOGGER.info("{}", ruleList);
        return (ruleList == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) ruleList, "total", ((Page) ruleList).getTotal()));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity selectOne(@PathVariable String id) {
        LOGGER.info("{}", id);
        ExperienceRuleBO experienceRuleReturnBO = experienceRuleService.selectOne(id);
        LOGGER.info("{}", experienceRuleReturnBO);
        return ResponseEntity.ok(Utils.kv("data", experienceRuleReturnBO));
    }

    @PostMapping
    public ResponseEntity insert(@Valid @RequestBody ExperienceRuleInsertBO experienceRuleInsertBO) {
        LOGGER.info("{}", experienceRuleInsertBO);
        ExperienceRuleBO experienceRuleReturn = experienceRuleService.insert(experienceRuleInsertBO);
        LOGGER.info("{}", experienceRuleReturn);
        return ResponseEntity.ok(Utils.kv("data", experienceRuleReturn));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity update(@Valid @RequestBody ExperienceRuleUpdateBO experienceRuleUpdateBO, @PathVariable
    String id) {
        LOGGER.info("{}:{}", experienceRuleUpdateBO, id);
        ExperienceRuleBO experienceRuleReturn = experienceRuleService.update(experienceRuleUpdateBO, id);
        LOGGER.info("{}", experienceRuleReturn);
        return ResponseEntity.ok(Utils.kv("data", experienceRuleReturn));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        LOGGER.info("{}", id);
        experienceRuleService.delete(id);
        return ResponseEntity.ok(Utils.kv());
    }

    @PutMapping(path = "/{id}/{status}")
    public ResponseEntity enableOrDisable(@PathVariable String id, @PathVariable String status) {
        LOGGER.info("{}:{}", id, status);
        experienceRuleService.enableOrDisable(id, status);
        return ResponseEntity.ok(Utils.kv());
    }
}
