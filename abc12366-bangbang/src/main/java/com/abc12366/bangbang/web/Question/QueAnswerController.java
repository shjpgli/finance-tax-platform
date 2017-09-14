package com.abc12366.bangbang.web.Question;

import com.abc12366.bangbang.model.question.bo.QuestionAnswerBo;
import com.abc12366.bangbang.service.QueAnswerService;
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
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学堂课件管理模块
 *
 * @author xieyanmao
 * @create 2017-08-10
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/queAnswer", headers = Constant.VERSION_HEAD + "=1")
public class QueAnswerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueAnswerController.class);

    @Autowired
    private QueAnswerService queAnswerService;

    /**
     * 课件列表查询
     */
    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size,
                                     @RequestParam(value = "id", required = false) String id) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("id", id);//
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<QuestionAnswerBo> dataList = queAnswerService.selectList(dataMap);
        return ResponseEntity.ok(Utils.kv("dataList", (Page) dataList, "total", ((Page) dataList).getTotal()));

    }

    /**
     * 课件新增
     */
    @PostMapping
    public ResponseEntity save(@Valid @RequestBody QuestionAnswerBo answerBo) {
        //新增课件信息
        answerBo = queAnswerService.save(answerBo);
        return ResponseEntity.ok(Utils.kv("data", answerBo));
    }

    /**
     * 查询单个课件信息
     */
    @GetMapping(path = "/{answerId}")
    public ResponseEntity selectOne(@PathVariable String answerId) {
        //查询课件信息
        QuestionAnswerBo answerBo = queAnswerService.selectAnswer(answerId);
        return ResponseEntity.ok(Utils.kv("data", answerBo));
    }

    /**
     * 更新课件信息
     */
    @PutMapping(path = "/{answerId}")
    public ResponseEntity update(@PathVariable String answerId,
                                 @Valid @RequestBody QuestionAnswerBo answerBo) {
        //更新课件信息
        answerBo = queAnswerService.update(answerBo);
        return ResponseEntity.ok(Utils.kv("data", answerBo));
    }

    /**
     * 更新课件状态
     *
     * @param status
     * @param answerId
     * @return
     */
    @PutMapping(path = "/updateStatus/{answerId}")
    public ResponseEntity updateStatus(@Valid @RequestBody String status, @PathVariable("answerId") String answerId) {
        queAnswerService.updateStatus(answerId, status);
        return ResponseEntity.ok(Utils.kv("data", answerId));
    }

    /**
     * 删除课件信息
     */
    @DeleteMapping(path = "/{answerId}")
    public ResponseEntity delete(@PathVariable String answerId) {
        //删除课件信息
        String rtn = queAnswerService.delete(answerId);
        return ResponseEntity.ok(Utils.kv("data", rtn));
    }


}
