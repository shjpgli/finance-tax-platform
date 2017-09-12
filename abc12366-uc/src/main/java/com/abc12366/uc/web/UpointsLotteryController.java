package com.abc12366.uc.web;

/**
 * Admin: lingsuzhi <554600654@qq.com.com>
 * Date: 2017-09-05
 */


import com.abc12366.uc.model.bo.UpointsLotteryBO;

import java.util.List;
import java.util.Map;

import com.abc12366.uc.service.UpointsLotteryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(path = "/upointslottery", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class UpointsLotteryController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpointsLotteryController.class);
    @Autowired
    private UpointsLotteryService upointsLotteryService;

    @GetMapping
    public ResponseEntity selectList(@RequestParam(required = false, defaultValue = Constant.pageNum) int page, @RequestParam(required = false, defaultValue = Constant.pageSize) int size) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<UpointsLotteryBO> list = upointsLotteryService.selectList(map);
        LOGGER.info("selectList:{}", list);
        return (list == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) list, "total", ((Page) list).getTotal()));
    }

    @PostMapping
    public ResponseEntity insert(@RequestBody UpointsLotteryBO upointsLotteryBO) {
        LOGGER.info("insert:{}", upointsLotteryBO);
        UpointsLotteryBO returnObj = upointsLotteryService.insert(upointsLotteryBO);
        return ResponseEntity.ok(Utils.kv("data", returnObj));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity update(@RequestBody UpointsLotteryBO upointsLotteryBO,
                                 @PathVariable String id) {
        LOGGER.info("update：{} id:{}", upointsLotteryBO, id);
        UpointsLotteryBO returnObj = upointsLotteryService.update(upointsLotteryBO, id);
        LOGGER.info("{}", returnObj);
        return ResponseEntity.ok(Utils.kv("data", returnObj));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        LOGGER.info("deleteDo:{}", id);
        upointsLotteryService.delete(id);
        return ResponseEntity.ok(Utils.kv());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity selectOne(@PathVariable String id) {
        UpointsLotteryBO returnObj = upointsLotteryService.selectOne(id);
        return ResponseEntity.ok(Utils.kv("data", returnObj));
    }
    @PostMapping(path = "/init")
    public ResponseEntity init() {

        upointsLotteryService.inits();
        return ResponseEntity.ok(Utils.kv());
    }
    @GetMapping(path = "/getval/{userId}")
    public ResponseEntity getval(@PathVariable String userId) {
        if(userId ==null || userId.isEmpty()){
            throw new RuntimeException("参数错误，请查正");
        }

        UpointsLotteryBO returnObj =upointsLotteryService.getval(userId);
        return ResponseEntity.ok(Utils.kv("data", returnObj));
    }
}
