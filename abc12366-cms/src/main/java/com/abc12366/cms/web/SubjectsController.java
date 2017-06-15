package com.abc12366.cms.web;

import com.abc12366.cms.model.questionnaire.Subjects;
import com.abc12366.cms.model.questionnaire.bo.SubjectsBO;
import com.abc12366.cms.service.SubjectsService;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * 题目控制类
 *
 * @author lizhongwei
 * @create 2017-06-14
 * @since 2.0.0
 */
@RestController
@RequestMapping(path = "/subjects", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class SubjectsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectsController.class);

    @Autowired
    private SubjectsService subjectsService;

    /**
     * 题目列表查询
     * @param questionId
     * @param title
     * @param pages
     * @return
     */
    @GetMapping(path = "/{questionId}")
    public ResponseEntity selectList(@PathVariable("questionId") String questionId,
                                     @RequestParam(value = "title", required = false) String title,
                                     @RequestParam(value = "pages", required = false) Integer pages) {
        SubjectsBO subjects = new SubjectsBO();
        subjects.setQuestionId(questionId);
        subjects.setTitle(title);
        subjects.setPages(pages);
        List<SubjectsBO> subjectsList = subjectsService.selectList(subjects);
        LOGGER.info("{}", subjectsList);
        return (subjectsList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", subjectsList));
    }


    /**
     * 查询题目详情
     *
     * @param id
     * @return
     */
    @GetMapping(path = "/select/{id}")
    public ResponseEntity<?> selectOne(@PathVariable String id) {
        LOGGER.info("{}", id);
        SubjectsBO subjectsBO = subjectsService.selectOne(id);
        LOGGER.info("{}", subjectsBO);
        return ResponseEntity.ok(Utils.kv("data", subjectsBO));
    }

    /**
     * 题目新增
     *
     * @param questionId
     * @return
     */
    @PostMapping(path = "/{questionId}")
    public ResponseEntity submitSubjects(@Valid @RequestBody SubjectsBO subjectsBO, @PathVariable("questionId") String questionId) {
        LOGGER.info("{}", subjectsBO);
        subjectsBO.setQuestionId(questionId);
        SubjectsBO bo = subjectsService.insert(subjectsBO);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    /**
     * 题目修改
     * @param subjectsBO
     * @param questionId
     * @param id
     * @return
     */
    @PutMapping(path = "/{id}/{questionId}")
    public ResponseEntity update(@Valid @RequestBody SubjectsBO subjectsBO, @PathVariable("questionId") String questionId, @PathVariable("id") String id) {
        LOGGER.info("{}", subjectsBO);
        subjectsBO.setId(id);
        subjectsBO.setQuestionId(questionId);
        SubjectsBO bo = subjectsService.update(subjectsBO);
        LOGGER.info("{}", bo);
        return new ResponseEntity<>(bo, HttpStatus.OK);
    }

    /**
     * 题目删除
     *
     * @param questionId
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{id}/{questionId}")
    public ResponseEntity update(@PathVariable("questionId") String questionId, @PathVariable("id") String id) {
        SubjectsBO subjectsBO = new SubjectsBO();
        subjectsBO.setQuestionId(questionId);
        subjectsBO.setId(id);
        subjectsService.delete(subjectsBO);
        return ResponseEntity.ok(Utils.kv());
    }

}
