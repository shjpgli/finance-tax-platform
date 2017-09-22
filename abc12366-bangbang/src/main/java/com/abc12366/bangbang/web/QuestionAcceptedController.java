package com.abc12366.bangbang.web;

import com.abc12366.bangbang.model.question.QuestionAccepted;
import com.abc12366.bangbang.model.question.bo.QuestionAcceptedBO;
import com.abc12366.bangbang.service.QuestionAcceptedService;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author lizhongwei
 * @Date 2017/9/21 13:39
 */
@RestController
@RequestMapping(path = "/accepted", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class QuestionAcceptedController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionAcceptedController.class);


    @Autowired
    private QuestionAcceptedService questionAcceptedService;


    /**
    *  问题受理 分页查询
    *  支持 来源类型, 反馈类型 查询
    */
    @GetMapping(path = "/list")
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size,
                                     @RequestParam(value = "sourceType", required = false) String sourceType,
                                     @RequestParam(value = "QuestionAcceptedType", required = false) String QuestionAcceptedType) {
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);

        QuestionAcceptedBO param = new QuestionAcceptedBO();
        List<QuestionAccepted> list = questionAcceptedService.selectList(param);

        return (list == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) list, "total", ((Page) list).getTotal()));
    }


    /***
     * 添加问题受理接口
     */
    @PostMapping(path = "/add")
    public ResponseEntity add(@RequestBody QuestionAccepted QuestionAccepted) {
        questionAcceptedService.add(QuestionAccepted);
        return ResponseEntity.ok(Utils.kv("data", QuestionAccepted));
    }

    /**
    * 删除问题受理 接口
    */
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        questionAcceptedService.delete(id);
        return ResponseEntity.ok(Utils.kv());
    }

}
