package com.abc12366.uc.web;

/**
 * Admin: lingsuzhi <554600654@qq.com.com>
 * Date: 2017-09-18
 */

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.bo.LotteryLogBO;
import com.abc12366.uc.service.LotteryLogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/lotterylog", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class LotteryLogController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LotteryLogController.class);
    @Autowired
    private LotteryLogService lotteryLogService;

    @GetMapping
    public ResponseEntity selectList(@RequestParam(required = false) String userName,
                                     @RequestParam(required = false) String activityName,
                                     @RequestParam(required = false) String userId,
                                     @RequestParam(required = false) String startTime,
                                     @RequestParam(required = false) String  endTime,
                                     @RequestParam(required = false) Integer isluck,@RequestParam(required = false, defaultValue = Constant.pageNum) int page, @RequestParam(required = false, defaultValue = Constant.pageSize) int size) {
        Map<String, Object> map = new HashMap<>();
        map.put("isluck",isluck);
        if(userId != null && !userId.isEmpty())        map.put("userId",userId);
        if(userName != null && !userName.isEmpty())        map.put("userName",userName);
        if(activityName != null && !activityName.isEmpty())        map.put("activityName",activityName);
        if(startTime != null && !startTime.isEmpty())        map.put("startTime",startTime);
        if(endTime != null && !endTime.isEmpty())     {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date eTime = sdf.parse(endTime);
                endTime = sdf.format(eTime) + " 23:59:59";
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                eTime = sdf2.parse(endTime);
                map.put("endTime",eTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

         PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<LotteryLogBO> list = lotteryLogService.selectList(map);
        LOGGER.info("selectList:{}", list);
        return (list == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) list, "total", ((Page) list).getTotal()));
    }

    @GetMapping(path = "/getLottery")
    public ResponseEntity getLottery( @RequestParam(required = true) String logId,@RequestParam(required = false) String dizhiId) {
//领奖接口
        return null;
    }
    @PostMapping
    public ResponseEntity insert(@RequestBody LotteryLogBO lotteryLogBO) {
        LOGGER.info("insert:{}", lotteryLogBO);
        LotteryLogBO returnObj = lotteryLogService.insert(lotteryLogBO);
        return ResponseEntity.ok(Utils.kv("data", returnObj));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity update(@RequestBody LotteryLogBO lotteryLogBO,
                                 @PathVariable String id) {
        LOGGER.info("update：{} id:{}", lotteryLogBO, id);
        LotteryLogBO returnObj = lotteryLogService.update(lotteryLogBO, id);
        LOGGER.info("{}", returnObj);
        return ResponseEntity.ok(Utils.kv("data", returnObj));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        LOGGER.info("deleteDo:{}", id);
        lotteryLogService.delete(id);
        return ResponseEntity.ok(Utils.kv());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity selectOne(@PathVariable String id) {
        LotteryLogBO returnObj = lotteryLogService.selectOne(id);
        return ResponseEntity.ok(Utils.kv("data", returnObj));
    }
}
