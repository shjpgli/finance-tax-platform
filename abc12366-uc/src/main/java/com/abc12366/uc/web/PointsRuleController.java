package com.abc12366.uc.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.model.bo.PointsRuleBO;
import com.abc12366.uc.model.bo.PointsRuleUpdateBO;
import com.abc12366.uc.service.PointsRuleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 积分规则接口
 *
 * @author liuguiyao<435720953@qq.com.com>
 * @create 2017-05-15 10:18 PM
 * @since 2.0.0
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
        Map map = new HashMap<String, String>();
        map.put("name", name);
        map.put("code", code);
        map.put("type", type);
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<PointsRuleBO> ruleList = pointsRuleService.selectList(map);
        LOGGER.info("{}", ruleList);
        return (ruleList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4004), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("userList", (Page) ruleList, "total", ((Page) ruleList).getTotal()));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity selectOne(@PathVariable String id) {
        LOGGER.info("{}", id);
        PointsRuleBO points_rule = pointsRuleService.selectOne(id);
        LOGGER.info("{}", points_rule);
        return (points_rule != null) ? ResponseEntity.ok(points_rule) : new ResponseEntity(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity insert(@RequestBody PointsRuleBO pointsRuleBO) {
        LOGGER.info("{}", pointsRuleBO);
        PointsRuleBO rule = pointsRuleService.insert(pointsRuleBO);
        LOGGER.info("{}", rule);
        return (rule != null) ? ResponseEntity.ok(rule) : new ResponseEntity(Utils.bodyStatus(4101), HttpStatus.BAD_REQUEST);
    }

    @PutMapping(path = "/{id}")
    ResponseEntity update(@RequestBody PointsRuleUpdateBO pointsRuleUpdateBO, @PathVariable String id) {
        LOGGER.info("{}", pointsRuleUpdateBO);
        PointsRuleBO rule = pointsRuleService.update(pointsRuleUpdateBO, id);
        LOGGER.info("{}", rule);
        return (rule != null) ? ResponseEntity.ok(rule) : new ResponseEntity(Utils.bodyStatus(4102), HttpStatus.BAD_REQUEST);
    }
}
