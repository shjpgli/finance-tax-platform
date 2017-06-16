package com.abc12366.cms.web;

import com.abc12366.cms.model.bo.IdsBo;
import com.abc12366.cms.model.bo.TopicBo;
import com.abc12366.cms.model.bo.TopicListBo;
import com.abc12366.cms.service.TopicService;
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
@RequestMapping(path = "/topic",headers = Constant.VERSION_HEAD + "=1")
public class TopicController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TopicController.class);

    @Autowired
    private TopicService topicService;

    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<TopicBo> dataList = topicService.selectList();
        LOGGER.info("{}", dataList);
        return ResponseEntity.ok(Utils.kv("dataList", (Page) dataList, "total", ((Page) dataList).getTotal()));

    }

    @PostMapping
    public ResponseEntity save(@RequestBody TopicBo topicBo) {
        LOGGER.info("{}", topicBo);
        //新增评论信息
        topicBo = topicService.save(topicBo);
        LOGGER.info("{}", topicBo);
        return ResponseEntity.ok(Utils.kv("data", topicBo));
    }

    @GetMapping(path = "/{topicId}")
    public ResponseEntity selectOne(@PathVariable String topicId) {
        LOGGER.info("{}", topicId);
        //查询评论信息
        TopicBo topicBo = topicService.selectTopic(topicId);
        LOGGER.info("{}", topicBo);
        return ResponseEntity.ok(Utils.kv("data", topicBo));
    }

    @PutMapping(path = "/{topicId}")
    public ResponseEntity update(@PathVariable String topicId,
                                 @Valid @RequestBody TopicBo topicBo) {

        LOGGER.info("{}", topicBo);
        //更新评论信息
        topicBo = topicService.update(topicBo);
        LOGGER.info("{}", topicBo);
        return ResponseEntity.ok(Utils.kv("data", topicBo));
    }

    @PutMapping(path = "/updateList")
    public ResponseEntity updateList(@Valid @RequestBody TopicListBo topicListBo) {

        LOGGER.info("{}", topicListBo);
        //更新评论信息
        topicListBo = topicService.updateList(topicListBo);
        LOGGER.info("{}", topicListBo);
        return ResponseEntity.ok(Utils.kv("data", topicListBo));
    }

    @DeleteMapping(path = "/{topicId}")
    public ResponseEntity delete(@PathVariable String topicId) {
        LOGGER.info("{}", topicId);
        //删除评论信息
        String rtn = topicService.delete(topicId);
        LOGGER.info("{}", rtn);
        return ResponseEntity.ok(Utils.kv("data", rtn));
    }

    @PostMapping(path = "/deleteList")
    public ResponseEntity deleteList(@RequestBody IdsBo idsBo) {
        LOGGER.info("{}", idsBo);
        //删除评论信息
        String rtn = topicService.deleteList(idsBo.getIds());
        LOGGER.info("{}", rtn);
        return ResponseEntity.ok(Utils.kv("data", idsBo));
    }




}
