package com.abc12366.uc.web;

/**
 * Admin: lingsuzhi <554600654@qq.com.com>
 * Date: 2017-09-18
 */



import com.abc12366.uc.model.bo.LotteryLogBO;
import java.util.List;
import java.util.Map;
import com.abc12366.uc.service.LotteryLogService;

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
@RequestMapping(path = "/lotterylog", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class LotteryLogController{
private static final Logger LOGGER = LoggerFactory.getLogger(LotteryLogController.class);
@Autowired
private LotteryLogService lotteryLogService;
@GetMapping
public ResponseEntity selectList(@RequestParam(required = false, defaultValue = Constant.pageNum) int page,@RequestParam(required = false, defaultValue = Constant.pageSize) int size) {
Map<String, Object> map = new HashMap<>();
PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
List<LotteryLogBO> list = lotteryLogService.selectList(map);
LOGGER.info("selectList:{}", list);
return (list == null) ?
ResponseEntity.ok(Utils.kv()) :
ResponseEntity.ok(Utils.kv("dataList", (Page) list, "total", ((Page) list).getTotal()));
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
LOGGER.info("update：{} id:{}", lotteryLogBO,id);
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
