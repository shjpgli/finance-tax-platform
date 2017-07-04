package com.abc12366.cms.web;

import com.abc12366.cms.model.bo.*;
import com.abc12366.cms.service.EventApplyService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@RequestMapping(path = "/eventApply",headers = Constant.VERSION_HEAD + "=1")
public class EventApplyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventApplyController.class);

    @Autowired
    private EventApplyService eventApplyService;

    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size,
                                     @RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "status", required = false) String status) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("name", name);//标题
        dataMap.put("status", status);//标题
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<EventApplyBo> dataList = eventApplyService.selectList(dataMap);
        LOGGER.info("{}", dataList);
        return ResponseEntity.ok(Utils.kv("dataList", (Page) dataList, "total", ((Page) dataList).getTotal()));
    }

    @GetMapping(path = "/selectbmtj")
    public ResponseEntity selectbmtj(@RequestParam(value = "eventId", required = false) String eventId) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("eventId", eventId);//活动ID
        EventbmtjBo data = eventApplyService.selectbmtj(dataMap);
        LOGGER.info("{}", data);
        return ResponseEntity.ok(Utils.kv("data", data));
    }

    @GetMapping(path = "/selectlltj")
    public ResponseEntity selectlltj(@RequestParam(value = "startTime", required = false) String startTime,
                                     @RequestParam(value = "endTime", required = false) String endTime) {
        Map<String, Object> dataMap = new HashMap<>();
        SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
        try {
            if(startTime != null && !"".equals(startTime)){
                Date startTime1 = sdf.parse(startTime);
                dataMap.put("startTime", startTime1.getTime()/1000);
            }
            if(endTime != null && !"".equals(endTime)){
                Date startTime2 = sdf.parse(endTime);
                dataMap.put("endTime", startTime2.getTime()/1000);
            }
        } catch (ParseException e) {
            LOGGER.error("时间类转换异常：{}", e);
            throw new RuntimeException("时间类型转换异常：{}", e);
        }
        EventlltjListBo data = eventApplyService.selectlltj(dataMap);
        LOGGER.info("{}", data);
        return ResponseEntity.ok(Utils.kv("data", data));
    }

    @PostMapping
    public ResponseEntity save(@RequestBody EventApplySaveBo eventApplySaveBo) {
        LOGGER.info("{}", eventApplySaveBo);
        //新增评论信息
        eventApplySaveBo = eventApplyService.save(eventApplySaveBo);
        LOGGER.info("{}", eventApplySaveBo);
        return ResponseEntity.ok(Utils.kv("data", eventApplySaveBo));
    }

    @GetMapping(path = "/{applyId}")
    public ResponseEntity selectOne(@PathVariable String applyId) {
        LOGGER.info("{}", applyId);
        //查询评论信息
        EventApplySaveBo eventApplySaveBo = eventApplyService.selectEventApply(applyId);
        LOGGER.info("{}", eventApplySaveBo);
        return ResponseEntity.ok(Utils.kv("data", eventApplySaveBo));
    }

    @PutMapping(path = "/{applyId}")
    public ResponseEntity update(@PathVariable String applyId,
                                 @Valid @RequestBody EventApplySaveBo eventApplySaveBo) {

        LOGGER.info("{}", eventApplySaveBo);
        //更新评论信息
        eventApplySaveBo = eventApplyService.update(eventApplySaveBo);
        LOGGER.info("{}", eventApplySaveBo);
        return ResponseEntity.ok(Utils.kv("data", eventApplySaveBo));
    }

    @DeleteMapping(path = "/{applyId}")
    public ResponseEntity delete(@PathVariable String applyId) {
        LOGGER.info("{}", applyId);
        //删除评论信息
        String rtn = eventApplyService.delete(applyId);
        LOGGER.info("{}", rtn);
        return ResponseEntity.ok(Utils.kv("data", rtn));
    }

    @PostMapping(path = "/deleteList")
    public ResponseEntity deleteList(@RequestBody IdsBo idsBo) {
        LOGGER.info("{}", idsBo);
        //删除评论信息
        String rtn = eventApplyService.deleteList(idsBo.getIds());
        LOGGER.info("{}", rtn);
        return ResponseEntity.ok(Utils.kv("data", idsBo));
    }

    @PutMapping(path = "/updateStatusList")
    public ResponseEntity updateStatusList(@RequestBody IdsBo idsBo) {
        LOGGER.info("{}", idsBo);
        //审批评论信息
        String rtn = eventApplyService.updateStatusList(idsBo.getIds());
        LOGGER.info("{}", rtn);
        return ResponseEntity.ok(Utils.kv("data", idsBo));
    }

}
