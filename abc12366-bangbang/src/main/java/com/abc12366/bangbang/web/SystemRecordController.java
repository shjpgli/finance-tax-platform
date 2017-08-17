package com.abc12366.bangbang.web;
/**
 * Admin: lingsuzhi <554600654@qq.com.com> Date: 2017-08-16
 */

import com.abc12366.bangbang.model.bo.SystemRecordInsertBO;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.bangbang.model.bo.SystemRecordBO;
import com.abc12366.bangbang.service.SystemRecordService;

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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(path = "/system/record", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class SystemRecordController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemRecordController.class);

    @Autowired
    private SystemRecordService systemRecordService;

    @GetMapping
    public ResponseEntity selectList(@RequestParam(required = false) String userId,
                                     @RequestParam(required = false) String ip,
                                     @RequestParam(required = false, defaultValue = Constant.pageNum) int page,
                                     @RequestParam(required = false, defaultValue = Constant.pageSize) int size) {

        LOGGER.info("{}:{}:{}:{}:{}", userId, ip, page, size);
        Map<String, Object> map = new HashMap<>();
        if (userId != null && StringUtils.isEmpty(userId)) {
            userId = null;
        }
        if (ip != null && StringUtils.isEmpty(ip)) {
            ip = null;
        }
        map.put("userId", userId);
        map.put("ip", ip);
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<SystemRecordBO> systemRecordList = systemRecordService.selectList(map);
        LOGGER.info("{}", systemRecordList);
        return (systemRecordList == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) systemRecordList, "total", ((Page) systemRecordList).getTotal
                        ()));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity selectOne(@PathVariable String id) {
        LOGGER.info("{}", id);
        SystemRecordBO systemRecordBO = systemRecordService.selectOne(id);
        LOGGER.info("{}", systemRecordBO);
        return ResponseEntity.ok(Utils.kv("data", systemRecordBO));
    }

    /**
     * 异步新增
     */
    @PostMapping
    public ResponseEntity insert(@Valid @RequestBody SystemRecordInsertBO systemRecordInsertBO) throws ExecutionException, InterruptedException {
        LOGGER.info("{}", systemRecordInsertBO);
        CompletableFuture<SystemRecordBO> systemRecordBOReturn = systemRecordService.insert(systemRecordInsertBO);
        CompletableFuture.allOf(systemRecordBOReturn);
        LOGGER.info("{}", systemRecordBOReturn.get());
        return ResponseEntity.ok(Utils.kv("data", systemRecordBOReturn.get()));
    }

}
