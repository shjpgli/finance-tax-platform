package com.abc12366.uc.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.model.bo.PointsLogBO;
import com.abc12366.uc.service.PointsLogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 积分规则日志接口
 *
 * @author liuguiyao<435720953@qq.com.com>
 * @create 2017-05-15 10:18 PM
 * @since 2.0.0
 */
@RestController
@RequestMapping(path = "/upoints/log", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class PointsLogController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PointsLogController.class);

    @Autowired
    private PointsLogService pointsLogService;

    @GetMapping
    public ResponseEntity selectList(@RequestParam(required = false) String name, @RequestParam(required = false) String code,
                                     @RequestParam(required = false) String type, @RequestParam(required = false) String userId,
                                     @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        LOGGER.info("{}:{}:{}:{}:{}:{}", name, code, type, userId, page, size);
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
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
        if (userId != null && StringUtils.isEmpty(userId)) {
            userId = null;
        }
        map.put("name", name);
        map.put("code", code);
        map.put("type", type);
        map.put("userId", userId);
        List<PointsLogBO> logList = pointsLogService.selectList(map);
        LOGGER.info("{}", logList);
        return (logList == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) logList, "total", ((Page) logList).getTotal()));
    }

    @PostMapping
    public ResponseEntity insert(@RequestBody PointsLogBO pointsLogBO) {
        LOGGER.info("{}", pointsLogBO);
        PointsLogBO points_log = pointsLogService.insert(pointsLogBO);
        LOGGER.info("{}", points_log);
        return ResponseEntity.ok(Utils.kv("data", points_log));
    }
}
