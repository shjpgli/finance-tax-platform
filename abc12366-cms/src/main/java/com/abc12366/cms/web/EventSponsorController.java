package com.abc12366.cms.web;

import com.abc12366.cms.model.bo.EventSponsorBo;
import com.abc12366.cms.service.EventSponsorService;
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
import java.util.List;

/**
 * 模型管理模块
 *
 * @author xieyanmao
 * @create 2017-05-08
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/eventSponsor",headers = Constant.VERSION_HEAD + "=1")
public class EventSponsorController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventSponsorController.class);

    @Autowired
    private EventSponsorService eventSponsorService;

    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<EventSponsorBo> dataList = eventSponsorService.selectList();
        LOGGER.info("{}", dataList);
        return ResponseEntity.ok(Utils.kv("dataList", (Page) dataList, "total", ((Page) dataList).getTotal()));
    }

    @PostMapping
    public ResponseEntity save(@RequestBody EventSponsorBo eventSponsorBo) {
        LOGGER.info("{}", eventSponsorBo);
        //新增评论信息
        eventSponsorBo = eventSponsorService.save(eventSponsorBo);
        LOGGER.info("{}", eventSponsorBo);
        return ResponseEntity.ok(Utils.kv("data", eventSponsorBo));
    }

    @GetMapping(path = "/{sponsorId}")
    public ResponseEntity selectOne(@PathVariable String sponsorId) {
        LOGGER.info("{}", sponsorId);
        //查询评论信息
        EventSponsorBo eventSponsorBo = eventSponsorService.selectEventSponsor(sponsorId);
        LOGGER.info("{}", eventSponsorBo);
        return ResponseEntity.ok(Utils.kv("data", eventSponsorBo));
    }

    @PutMapping(path = "/{sponsorId}")
    public ResponseEntity update(@PathVariable String sponsorId,
                                 @Valid @RequestBody EventSponsorBo eventSponsorBo) {

        LOGGER.info("{}", eventSponsorBo);
        //更新评论信息
        eventSponsorBo = eventSponsorService.update(eventSponsorBo);
        LOGGER.info("{}", eventSponsorBo);
        return ResponseEntity.ok(Utils.kv("data", eventSponsorBo));
    }

    @DeleteMapping(path = "/{sponsorId}")
    public ResponseEntity delete(@PathVariable String sponsorId) {
        LOGGER.info("{}", sponsorId);
        //删除评论信息
        String rtn = eventSponsorService.delete(sponsorId);
        LOGGER.info("{}", rtn);
        return ResponseEntity.ok(Utils.kv("data", rtn));
    }

}
