package com.abc12366.cms.web;

import com.abc12366.cms.model.bo.EventApplyBo;
import com.abc12366.cms.model.bo.EventApplySaveBo;
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

}
