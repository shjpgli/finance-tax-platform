package com.abc12366.uc.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.model.bo.ExperienceLogBO;
import com.abc12366.uc.model.bo.ExperienceLogQueryBO;
import com.abc12366.uc.service.ExperienceLogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-22
 * Time: 14:38
 */
@RestController
@RequestMapping(path = "/uexp/log", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class ExperienceLogController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceLogController.class);

    @Autowired
    private ExperienceLogService experienceLogService;

    @PostMapping
    public ResponseEntity insert(@RequestBody ExperienceLogBO experienceLogBO) {
        LOGGER.info("{}", experienceLogBO);
        ExperienceLogQueryBO experienceLogBOReturn = experienceLogService.insert(experienceLogBO);
        LOGGER.info("{}", experienceLogBOReturn);
        return ResponseEntity.ok(experienceLogBOReturn);
    }

    @GetMapping
    public ResponseEntity selectList(@RequestParam(required = false) String name,
                                     @RequestParam(required = false) String code,
                                     @RequestParam(required = false) String type,
                                     @RequestParam(required = false) String userId,
                                     @RequestParam(required = false, defaultValue = Constant.pageNum) int page,
                                     @RequestParam(required = false, defaultValue = Constant.pageSize) int size) {

        LOGGER.info("{}:{}:{}:{}:{}:{}", name, code, type, userId, page, size);
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
        if (userId != null && StringUtils.isEmpty(userId)) {
            userId = null;
        }
        map.put("name", name);
        map.put("code", code);
        map.put("type", type);
        map.put("userId", userId);
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<ExperienceLogQueryBO> logList = experienceLogService.selectList(map);
        LOGGER.info("{}", logList);
        return ResponseEntity.ok(Utils.kv("logList", logList, "total", ((Page) logList).getTotal()));
    }
}
