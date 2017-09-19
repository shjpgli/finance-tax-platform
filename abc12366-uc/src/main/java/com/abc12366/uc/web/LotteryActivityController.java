package com.abc12366.uc.web;

/**
 * Admin: lingsuzhi <554600654@qq.com.com>
 * Date: 2017-09-18
 */



import com.abc12366.uc.model.bo.LotteryActivityBO;
import java.util.List;
import java.util.Map;
import com.abc12366.uc.service.LotteryActivityService;

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
@RequestMapping(path = "/lotteryactivity", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class LotteryActivityController{
private static final Logger LOGGER = LoggerFactory.getLogger(LotteryActivityController.class);
@Autowired
private LotteryActivityService lotteryActivityService;
@GetMapping
public ResponseEntity selectList(@RequestParam(required = false, defaultValue = Constant.pageNum) int page,@RequestParam(required = false, defaultValue = Constant.pageSize) int size) {
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
LOGGER.info("update：{} id:{}", lotteryActivityBO,id);
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
}
