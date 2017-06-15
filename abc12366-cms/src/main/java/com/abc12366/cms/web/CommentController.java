package com.abc12366.cms.web;

import com.abc12366.cms.model.bo.CommentListBo;
import com.abc12366.cms.model.bo.CommentSaveBo;
import com.abc12366.cms.model.bo.CommentTjListBo;
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
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size,
                                     @RequestParam(value = "contentId", required = false) String contentId,
                                     @RequestParam(value = "isChecked", required = false) String isChecked,
                                     @RequestParam(value = "isRecommend", required = false) String isRecommend) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("contentId", contentId);//内容ID
        dataMap.put("isChecked", isChecked);//是否审核
        dataMap.put("isRecommend", isRecommend);//是否推荐
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        //查询评论列表
        List<CommentListBo> dataList = commentService.selectList(dataMap);
        LOGGER.info("{}", dataList);
        return ResponseEntity.ok(Utils.kv("dataList", (Page) dataList, "total", ((Page) dataList).getTotal()));

    }

    @PostMapping
    public ResponseEntity save(@RequestBody CommentSaveBo commentSaveBo) {
        LOGGER.info("{}", commentSaveBo);
        //新增评论信息
        commentSaveBo = commentService.save(commentSaveBo);
        LOGGER.info("{}", commentSaveBo);
        return ResponseEntity.ok(Utils.kv("data", commentSaveBo));
    }

    @GetMapping(path = "/{commentId}")
    public ResponseEntity selectOne(@PathVariable String commentId) {
        LOGGER.info("{}", commentId);
        //查询评论信息
        CommentSaveBo commentSaveBo = commentService.selectComment(commentId);
        LOGGER.info("{}", commentSaveBo);
        return ResponseEntity.ok(Utils.kv("data", commentSaveBo));
    }

    @GetMapping(path = "/tj")
    public ResponseEntity selectTj() {
        //查询评论统计信息
        CommentTjListBo commentTjListBo = commentService.selectTj();
        LOGGER.info("{}", commentTjListBo);
        return ResponseEntity.ok(Utils.kv("data", commentTjListBo));
    }

    @PutMapping(path = "/{commentId}")
    public ResponseEntity update(@PathVariable String commentId,
                                 @Valid @RequestBody CommentSaveBo commentSaveBo) {

        LOGGER.info("{}", commentSaveBo);
        //更新评论信息
        commentSaveBo = commentService.update(commentSaveBo);
        LOGGER.info("{}", commentSaveBo);
        return ResponseEntity.ok(Utils.kv("data", commentSaveBo));
    }

    @DeleteMapping(path = "/{commentId}")
    public ResponseEntity delete(@PathVariable String commentId) {
        LOGGER.info("{}", commentId);
        //删除评论信息
        String rtn = commentService.delete(commentId);
        LOGGER.info("{}", rtn);
        return ResponseEntity.ok(Utils.kv("data", rtn));
    }

    @DeleteMapping(path = "/deleteList")
    public ResponseEntity deleteList(@RequestParam(value = "commentIds", required = true) String[] commentIds) {
        LOGGER.info("{}", commentIds);
        //删除评论信息
        String rtn = commentService.deleteList(commentIds);
        LOGGER.info("{}", rtn);
        return ResponseEntity.ok(Utils.kv("data", rtn));
    }

    @PutMapping(path = "/spList")
    public ResponseEntity spList(@RequestParam(value = "commentIds", required = true) String[] commentIds) {
        LOGGER.info("{}", commentIds);
        //审批评论信息
        String rtn = commentService.spList(commentIds);
        LOGGER.info("{}", rtn);
        return ResponseEntity.ok(Utils.kv("data", rtn));
    }




}
