package com.abc12366.uc.web;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.bo.PointsRuleBO;
import com.abc12366.uc.model.bo.PointsRuleInsertBO;
import com.abc12366.uc.model.bo.PointsRuleUpdateBO;
import com.abc12366.uc.service.PointsRuleService;
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
@RequestMapping(path = "/upoints/rule", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class PointsRuleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PointsRuleController.class);

    @Autowired
    private PointsRuleService pointsRuleService;

    @GetMapping
    public ResponseEntity selectList(@RequestParam(required = false) String name,
                                     @RequestParam(required = false) String code,
                                     @RequestParam(required = false) String type,
                                     @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size
    ) {
        LOGGER.info("{}:{}:{}:{}:{}", name, code, type, page, size);
        Map<String, String> map = new HashMap<>();
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
        List<PointsRuleBO> ruleList = pointsRuleService.selectList(map);
        LOGGER.info("{}", ruleList);
        return (ruleList == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) ruleList, "total", ((Page) ruleList).getTotal()));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity selectOne(@PathVariable String id) {
        LOGGER.info("{}", id);
        PointsRuleBO points_rule = pointsRuleService.selectOne(id);
        LOGGER.info("{}", points_rule);
        return ResponseEntity.ok(Utils.kv("data", points_rule));
    }

    @PostMapping
    public ResponseEntity insert(@Valid @RequestBody PointsRuleInsertBO pointsRuleInsertBO) {
        LOGGER.info("{}", pointsRuleInsertBO);
        PointsRuleBO rule = pointsRuleService.insert(pointsRuleInsertBO);
        LOGGER.info("{}", rule);
        return ResponseEntity.ok(Utils.kv("data", rule));
    }

    @PutMapping(path = "/{id}")
    ResponseEntity update(@RequestBody PointsRuleUpdateBO pointsRuleUpdateBO, @PathVariable String id) {
        LOGGER.info("{}", pointsRuleUpdateBO);
        PointsRuleBO rule = pointsRuleService.update(pointsRuleUpdateBO, id);
        LOGGER.info("{}", rule);
        return ResponseEntity.ok(Utils.kv("data", rule));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        LOGGER.info("{}", id);
        pointsRuleService.delete(id);
        return ResponseEntity.ok(Utils.kv());
    }

    //启用、禁用积分规则接口
    @PutMapping(path = "/{id}/{status}")
    public ResponseEntity enableOrDisable(@PathVariable String id, @PathVariable String status) {
        LOGGER.info("{}:{}", id, status);
        pointsRuleService.enableOrDisable(id, status);
        return ResponseEntity.ok(Utils.kv());
    }
}
