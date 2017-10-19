package com.abc12366.bangbang.web.question;

import com.abc12366.bangbang.model.question.QuestionExpert;
import com.abc12366.bangbang.model.question.bo.QuestionExpertBO;
import com.abc12366.bangbang.service.QuestionExpertService;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author liuQi
 * @Date 2017/9/15 10:24
 * 帮邦专家接口
 */
@RestController
@RequestMapping(path = "/questionExpert", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class QuestionExpertController {

    @Autowired
    private QuestionExpertService questionExpertService;

    /* 专家列表查询 */
    @GetMapping(path = "/list")
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size){
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<QuestionExpertBO> list = questionExpertService.selectList();

        return (list == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) list, "total", ((Page) list).getTotal()));
    }

    /* 专家列表查询 */
    @GetMapping(path = "/view/{id}")
    public ResponseEntity selectOne(@PathVariable String id){
        QuestionExpert expert = questionExpertService.selectOne(id);
        return ResponseEntity.ok(Utils.kv("data", expert));
    }

    /* 专家添加 */
    @PostMapping(path = "/add")
    public ResponseEntity add(@RequestBody QuestionExpert questionExpert) {
        questionExpertService.add(questionExpert);
        return ResponseEntity.ok(Utils.kv("data", questionExpert));
    }

    /* 专家删除 */
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        questionExpertService.delete(id);
        return ResponseEntity.ok(Utils.kv());
    }

    /* 专家修改 */
    @PutMapping(path = "/modify")
    public ResponseEntity modify(@RequestBody QuestionExpert questionExpert) {
        questionExpertService.modify(questionExpert);
        return ResponseEntity.ok(Utils.kv("data", questionExpert));
    }
}
