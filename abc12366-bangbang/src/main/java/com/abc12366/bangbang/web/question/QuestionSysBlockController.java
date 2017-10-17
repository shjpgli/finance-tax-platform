package com.abc12366.bangbang.web.question;

import com.abc12366.bangbang.model.question.bo.QuestionSysBlockBo;
import com.abc12366.bangbang.service.QuestionSysBlockService;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author liuQi
 * @Date 2017/10/17 17:21
 * 系统过滤帮帮内容（包括问题，回答，评论）
 */
@RestController
@RequestMapping(path = "/questionSysBlock", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class QuestionSysBlockController {

    @Autowired
    private QuestionSysBlockService questionSysBlockService;

    /* 列表查询 */
    @GetMapping(path = "/list")
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size){
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<QuestionSysBlockBo> list = questionSysBlockService.selectList();

        return (list == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) list, "total", ((Page) list).getTotal()));
    }

}
