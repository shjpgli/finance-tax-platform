package com.abc12366.cms.web;

import com.abc12366.cms.model.bo.CommentBo;
import com.abc12366.cms.model.bo.CommentListBo;
import com.abc12366.cms.service.CommentService;
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
 * 内容管理模块
 *
 * @author xieyanmao
 * @create 2017-05-02
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/comment",headers = Constant.VERSION_HEAD + "=1")
public class CommentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "pageNum", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = Constant.pageSize) int pageSize,
                                     @RequestParam(value = "contentId", required = false) String contentId,
                                     @RequestParam(value = "isChecked", required = false) String isChecked,
                                     @RequestParam(value = "isRecommend", required = false) String isRecommend) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("contentId", contentId);
        dataMap.put("isChecked", isChecked);
        dataMap.put("isRecommend", isRecommend);
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<CommentListBo> dataList = commentService.selectList(dataMap);
        LOGGER.info("{}", dataList);
        return ResponseEntity.ok(Utils.kv("dataList", (Page) dataList, "total", ((Page) dataList).getTotal()));

    }

    @PostMapping
    public ResponseEntity save(@RequestBody CommentBo commentBo) {
        LOGGER.info("{}", commentBo);
        String rtn = commentService.save(commentBo);
        LOGGER.info("{}", rtn);
        return ResponseEntity.ok(rtn);
    }

    @GetMapping(path = "/{commentId}")
    public ResponseEntity selectOne(@PathVariable String commentId) {
        LOGGER.info("{}", commentId);
        CommentBo commentBo = commentService.selectComment(commentId);
        LOGGER.info("{}", commentBo);
        return ResponseEntity.ok(commentBo);
    }

    @PutMapping(path = "/{commentId}")
    public ResponseEntity update(@PathVariable String commentId,
                                 @Valid @RequestBody CommentBo commentBo) {

        LOGGER.info("{}", commentBo);
        String rtn = commentService.update(commentBo);
        LOGGER.info("{}", rtn);
        return ResponseEntity.ok(rtn);
    }

    @DeleteMapping(path = "/{commentId}")
    public ResponseEntity delete(@PathVariable String commentId) {
        LOGGER.info("{}", commentId);
        String rtn = commentService.delete(commentId);
        LOGGER.info("{}", rtn);
        return ResponseEntity.ok(rtn);
    }




}
