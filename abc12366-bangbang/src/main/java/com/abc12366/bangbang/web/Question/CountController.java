package com.abc12366.bangbang.web.Question;

import com.abc12366.bangbang.model.question.bo.QuestionCountBo;
import com.abc12366.bangbang.service.QueCountService;
import com.abc12366.bangbang.service.QueLikeService;
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
 * 问题回复点赞管理
 * User: xieyanmao
 * Date: 2017-09-15
 * Time: 17:23
 */
@RestController
@RequestMapping(path = "/queCount", headers = Constant.VERSION_HEAD + "=1")
public class CountController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CountController.class);

    @Autowired
    private QueCountService service;
    /**
     * 点赞汇总
     */
    @GetMapping(path = "/like")
    public ResponseEntity selectLike(
                                     @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<QuestionCountBo> likeBOList = service.selectLike();
        return (likeBOList == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) likeBOList, "total", ((Page) likeBOList).getTotal
                        ()));
    }
    /**
     * 粉丝汇总
     */
    @GetMapping(path = "/attention")
    public ResponseEntity selectAttention(
            @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
            @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<QuestionCountBo> likeBOList = service.selectAttention();
        return (likeBOList == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) likeBOList, "total", ((Page) likeBOList).getTotal
                        ()));
    }
    /**
     * 采纳汇总
     */
    @GetMapping(path = "/accept")
    public ResponseEntity selectAccept(
            @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
            @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<QuestionCountBo> likeBOList = service.selectAccept();
        return (likeBOList == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) likeBOList, "total", ((Page) likeBOList).getTotal
                        ()));
    }
}
