package com.abc12366.uc.web;

/**
 * Admin: lingsuzhi <554600654@qq.com.com>
 * Date: 2017-09-21
 */

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.bo.LotteryActivityBO;
import com.abc12366.uc.model.bo.LotteryLogBO;
import com.abc12366.uc.service.LotteryActivityService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/lotteryactivity", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class LotteryActivityController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LotteryActivityController.class);
    @Autowired
    private LotteryActivityService lotteryActivityService;

    @GetMapping
    public ResponseEntity selectList(@RequestParam(required = false, defaultValue = Constant.pageNum) int page, @RequestParam(required = false, defaultValue = Constant.pageSize) int size) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<LotteryActivityBO> list = lotteryActivityService.selectList(map);
        LOGGER.info("selectList:{}", list);
        return (list == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) list, "total", ((Page) list).getTotal()));
    }

    @PostMapping
    public ResponseEntity insert(@RequestBody LotteryActivityBO lotteryActivityBO) {
        LOGGER.info("insert:{}", lotteryActivityBO);
        LotteryActivityBO returnObj = lotteryActivityService.insert(lotteryActivityBO);
        return ResponseEntity.ok(Utils.kv("data", returnObj));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity update(@RequestBody LotteryActivityBO lotteryActivityBO,
                                 @PathVariable String id) {
        LOGGER.info("update：{} id:{}", lotteryActivityBO, id);
        LotteryActivityBO returnObj = lotteryActivityService.update(lotteryActivityBO, id);
        LOGGER.info("{}", returnObj);
        return ResponseEntity.ok(Utils.kv("data", returnObj));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        LOGGER.info("deleteDo:{}", id);
        lotteryActivityService.delete(id);
        return ResponseEntity.ok(Utils.kv());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity selectOne(@PathVariable String id) {
        LotteryActivityBO returnObj = lotteryActivityService.selectOne(id);
        return ResponseEntity.ok(Utils.kv("data", returnObj));
    }
    @GetMapping(path = "/luckdraw")
    public ResponseEntity luckDraw(@RequestParam(required = true) String userId,@RequestParam(required = true) String activityId,@RequestParam(required = true) String ip) {
        LotteryLogBO returnObj =lotteryActivityService.luckDraw(userId,activityId,ip);
        return ResponseEntity.ok(Utils.kv("data", returnObj));
    }
}
