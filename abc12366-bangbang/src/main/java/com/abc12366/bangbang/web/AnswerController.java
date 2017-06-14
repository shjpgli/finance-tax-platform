package com.abc12366.bangbang.web;

import com.abc12366.bangbang.model.bo.*;
import com.abc12366.bangbang.service.AnswerService;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-06-12
 * Time: 11:41
 */
@RestController
@RequestMapping(headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class AnswerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnswerController.class);

    @Autowired
    private AnswerService answerService;

    @PostMapping(path = "/answer/{askId}")
    public ResponseEntity insert(@PathVariable String askId, @Valid @RequestBody AnswerInsertBO answerInsertBO) {
        LOGGER.info("{}:{}", askId, answerInsertBO);
        answerInsertBO.setAskId(askId);
        AnswerBO answerBO = answerService.insert(answerInsertBO);
        return (answerBO == null) ? new ResponseEntity(Utils.bodyStatus(4102), HttpStatus.BAD_REQUEST) : ResponseEntity.ok(answerBO);
    }

    @GetMapping(path = "/answers")
    public ResponseEntity selectListForAdmin(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String askId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status,
            @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
            @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        LOGGER.info("{}:{}:{}:{}:{}:{}", userId, askId, type, status, page, size);
        if (userId != null && StringUtils.isEmpty(userId)) {
            userId = null;
        }
        if (askId != null && StringUtils.isEmpty(askId)) {
            askId = null;
        }
        if (type != null && StringUtils.isEmpty(type)) {
            type = null;
        }
        if (status != null && StringUtils.isEmpty(status)) {
            status = null;
        }

        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        AnswersQueryParamBO answersQueryParamBO = new AnswersQueryParamBO(userId, askId, type, status);
        List<AnswerBO> answerList = answerService.selectListForAdmin(answersQueryParamBO);
        LOGGER.info("{}", answerList);
        return (answerList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("askList", (Page) answerList, "total", ((Page) answerList).getTotal()));
    }

    @GetMapping(path = "/answer")
    public ResponseEntity selectListForUser(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String askId,
            @RequestParam(required = false) String type,
            @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
            @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        LOGGER.info("{}:{}:{}:{}:{}:{}", userId, askId, type, page, size);
        if (userId != null && StringUtils.isEmpty(userId)) {
            userId = null;
        }
        if (askId != null && StringUtils.isEmpty(askId)) {
            askId = null;
        }
        if (type != null && StringUtils.isEmpty(type)) {
            type = null;
        }

        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        AnswerQueryParamBO answerQueryParamBO = new AnswerQueryParamBO(userId, askId, type);
        List<AnswerBO> answerList = answerService.selectListForUser(answerQueryParamBO);
        LOGGER.info("{}", answerList);
        return (answerList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("askList", (Page) answerList, "total", ((Page) answerList).getTotal()));
    }

    @GetMapping(path = "/answer/{id}")
    public ResponseEntity selectOne(@PathVariable String id) {
        LOGGER.info("{}", id);
        AnswerBO answerBO = answerService.selectOne(id);
        return ResponseEntity.ok(answerBO);
    }

    @PutMapping(path = "/answer/{id}")
    public ResponseEntity update(@PathVariable("id") String id, @Valid @RequestBody AnswerUpdateBO answerUpdateBO, HttpServletRequest request) {
        LOGGER.info("{}", id);
        String userId = request.getHeader(Constant.USER_TOKEN_HEAD);
        AnswerBO answerBO = answerService.update(id, answerUpdateBO, userId);
        return (answerBO == null) ? new ResponseEntity(Utils.bodyStatus(4102), HttpStatus.BAD_REQUEST) : ResponseEntity.ok(answerBO);
    }

    @DeleteMapping(path = "/answer/{id}")
    public ResponseEntity delete(@PathVariable("id") String id, HttpServletRequest request) {
        LOGGER.info("{}", id);
        String userId = request.getHeader(Constant.USER_TOKEN_HEAD);
        int result = answerService.delete(id, userId);
        return (result != 1) ? new ResponseEntity(Utils.bodyStatus(4102), HttpStatus.BAD_REQUEST) : ResponseEntity.ok(null);
    }

    @PutMapping(path = "/block/answer/{id}")
    public ResponseEntity block(@PathVariable("id") String id, HttpServletRequest request) {
        LOGGER.info("{}:{}", id);
        String userId = request.getHeader(Constant.USER_TOKEN_HEAD);
        int result = answerService.block(id, userId);
        return (result != 1) ? new ResponseEntity(Utils.bodyStatus(4102), HttpStatus.BAD_REQUEST) : ResponseEntity.ok(null);
    }

    @PutMapping(path = "/accepted/answer/{id}")
    public ResponseEntity accept(@PathVariable("id") String id, HttpServletRequest request) throws IOException {
        LOGGER.info("{}:{}", id);
        String userId = request.getHeader(Constant.USER_TOKEN_HEAD);
        int result = answerService.accept(id, userId);
        return (result != 1) ? new ResponseEntity(Utils.bodyStatus(4102), HttpStatus.BAD_REQUEST) : ResponseEntity.ok(null);
    }
}
