package com.abc12366.bangbang.web.question;

import com.abc12366.bangbang.model.question.bo.QuestionBo;
import com.abc12366.bangbang.service.QueCollectService;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 问题收藏管理
 * User: xieyanmao
 * Date: 2017-09-15
 * Time: 17:23
 */
@RestController
@RequestMapping(path = "/queCollect", headers = Constant.VERSION_HEAD + "=1")
public class QueCollectController {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueCollectController.class);

    @Autowired
    private QueCollectService collectService;

    /**
     * 问题收藏
     */
    @PostMapping(path = "/{id}")
    public ResponseEntity insert(@PathVariable String id, HttpServletRequest request) {
        LOGGER.info("{}:{}", id, request);
        String collectCnt = collectService.insert(id, request);
        return ResponseEntity.ok(Utils.kv("data", collectCnt));
    }

    /**
     * 取消问题收藏
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable String id, HttpServletRequest request) {
        LOGGER.info("{}:{}", id, request);
        String collectCnt = collectService.delete(id, request);
        return ResponseEntity.ok(Utils.kv("data", collectCnt));
    }

    /**
     * 查询问题收藏列表
     */
    @GetMapping(path = "/{userId}")
    public ResponseEntity selectList(@PathVariable String userId,
                                     @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        LOGGER.info("{}:{}:{}", userId, page, size);
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<QuestionBo> collectBoList = collectService.selectList(userId);
        return (collectBoList == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) collectBoList, "total", ((Page) collectBoList).getTotal
                        ()));
    }

    /**
     * 查询问题是否已收藏
     */
    @GetMapping(path = "/count/{id}")
    public ResponseEntity selectExist(@PathVariable String id,HttpServletRequest request) {
        LOGGER.info("{}", id);
        int count = Integer.parseInt(collectService.selectExist(id,request));
        return ResponseEntity.ok(Utils.kv("data", count));
    }
}
