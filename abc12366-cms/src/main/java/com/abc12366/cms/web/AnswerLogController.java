package com.abc12366.cms.web;

import com.abc12366.cms.model.questionnaire.bo.AnswerLogBO;
import com.abc12366.cms.service.AnswerLogService;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 答题记录控制类
 *
 * @author lizhongwei
 * @create 2017-06-14
 * @since 2.0.0
 */
@RestController
@RequestMapping(path = "/answer", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class AnswerLogController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnswerLogController.class);

    @Autowired
    private AnswerLogService answerLogService;

    /**
     * 答题记录列表查询
     * @param questionId
     * @return
     */
    @GetMapping(path = "/list/{questionId}")
    public ResponseEntity selectList(@PathVariable("questionId") String questionId) {
        AnswerLogBO answerLog = new AnswerLogBO();
        answerLog.setQuestionId(questionId);
        List<AnswerLogBO> answerLogList = answerLogService.selectList(answerLog);
        LOGGER.info("{}", answerLogList);
        return (answerLogList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", answerLogList));
    }

    /**
     * 答题记录列表查询
     * @param questionId
     * @return
     */
    @GetMapping(path = "/avg/{questionId}")
    public ResponseEntity answerAvg(@PathVariable("questionId") String questionId) {
        AnswerLogBO answerLog = new AnswerLogBO();
        answerLog.setQuestionId(questionId);
        AnswerLogBO answerLogBO = answerLogService.answerAvg(answerLog);
        LOGGER.info("{}", answerLogBO);
        return (answerLogBO == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", answerLogBO));
    }


    /**
     * 查询答题记录详情
     *
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> selectOne(@PathVariable String id) {
        LOGGER.info("{}", id);
        AnswerLogBO answerLogBO = answerLogService.selectOne(id);
        LOGGER.info("{}", answerLogBO);
        return ResponseEntity.ok(Utils.kv("data", answerLogBO));
    }

    /**
     * 答题记录新增
     *
     * @return
     */
    @PostMapping(path = "/insert")
    public ResponseEntity submitAnswerLog(@Valid @RequestBody AnswerLogBO answerLogBO) {
        LOGGER.info("{}", answerLogBO);
        AnswerLogBO bo = answerLogService.insert(answerLogBO);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    /**
     * 答题记录修改
     * @param answerLogBO
     * @return
     */
    @PutMapping(path = "/batch/{id}")
    public ResponseEntity batch(@Valid @RequestBody AnswerLogBO answerLogBO) {
        LOGGER.info("{}", answerLogBO);
        AnswerLogBO bo = answerLogService.batch(answerLogBO);
        LOGGER.info("{}", bo);
        return new ResponseEntity<>(bo, HttpStatus.OK);
    }

    /**
     * 答题记录修改
     * @param answerLogBO
     * @param id
     * @return
     */
    @PutMapping(path = "/{id}/{questionId}")
    public ResponseEntity update(@Valid @RequestBody AnswerLogBO answerLogBO, @PathVariable("id") String id) {
        LOGGER.info("{}", answerLogBO);
        answerLogBO.setId(id);
        AnswerLogBO bo = answerLogService.update(answerLogBO);
        LOGGER.info("{}", bo);
        return new ResponseEntity<>(bo, HttpStatus.OK);
    }

    /**
     * 答题记录删除
     *
     * @param questionId
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{id}/{questionId}")
    public ResponseEntity update(@PathVariable("questionId") String questionId, @PathVariable("id") String id) {
        AnswerLogBO answerLogBO = new AnswerLogBO();
        answerLogBO.setQuestionId(questionId);
        answerLogBO.setId(id);
        answerLogService.delete(answerLogBO);
        return ResponseEntity.ok(Utils.kv());
    }


}
