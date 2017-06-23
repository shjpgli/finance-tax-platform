package com.abc12366.cms.web;

import com.abc12366.cms.model.bo.EventListBo;
import com.abc12366.cms.model.bo.EventSaveBo;
import com.abc12366.cms.service.EventService;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模型管理模块
 *
 * @author xieyanmao
 * @create 2017-05-08
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/event",headers = Constant.VERSION_HEAD + "=1")
public class EventController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size,
                                     @RequestParam(value = "title", required = false) String title,
                                     @RequestParam(value = "category", required = false) String category,
                                     @RequestParam(value = "begintime", required = false) String begintime,
                                     @RequestParam(value = "endtime", required = false) String endtime,
                                     @RequestParam(value = "status", required = false) String status) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("title", title);//标题
        dataMap.put("status", status);//标题
        dataMap.put("category", category);//标题
        dataMap.put("begintime", begintime);//标题
        dataMap.put("endtime", endtime);//标题
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<EventListBo> dataList = eventService.selectList(dataMap);
        LOGGER.info("{}", dataList);
        return ResponseEntity.ok(Utils.kv("dataList", (Page) dataList, "total", ((Page) dataList).getTotal()));
    }

    @PostMapping
    public ResponseEntity save(@RequestBody EventSaveBo eventSaveBo) {
        LOGGER.info("{}", eventSaveBo);
        //新增评论信息
        eventSaveBo = eventService.save(eventSaveBo);
        LOGGER.info("{}", eventSaveBo);
        return ResponseEntity.ok(Utils.kv("data", eventSaveBo));
    }

    @GetMapping(path = "/{eventId}")
    public ResponseEntity selectOne(@PathVariable String eventId) {
        LOGGER.info("{}", eventId);
        //查询评论信息
        EventSaveBo eventSaveBo = eventService.selectEvent(eventId);
        LOGGER.info("{}", eventSaveBo);
        return ResponseEntity.ok(Utils.kv("data", eventSaveBo));
    }

    @PutMapping(path = "/{eventId}")
    public ResponseEntity update(@PathVariable String eventId,
                                 @Valid @RequestBody EventSaveBo eventSaveBo) {

        LOGGER.info("{}", eventSaveBo);
        //更新评论信息
        eventSaveBo = eventService.update(eventSaveBo);
        LOGGER.info("{}", eventSaveBo);
        return ResponseEntity.ok(Utils.kv("data", eventSaveBo));
    }

    @DeleteMapping(path = "/{eventId}")
    public ResponseEntity delete(@PathVariable String eventId) {
        LOGGER.info("{}", eventId);
        //删除评论信息
        String rtn = eventService.delete(eventId);
        LOGGER.info("{}", rtn);
        return ResponseEntity.ok(Utils.kv("data", rtn));
    }

}
