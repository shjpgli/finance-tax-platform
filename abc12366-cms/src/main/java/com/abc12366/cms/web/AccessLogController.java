package com.abc12366.cms.web;

import com.abc12366.cms.model.questionnaire.AccessLog;
import com.abc12366.cms.model.questionnaire.bo.AccessLogBO;
import com.abc12366.cms.service.AccessLogService;
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
 * 访问记录控制类
 *
 * @author lizhongwei
 * @create 2017-06-14
 * @since 2.0.0
 */
@RestController
@RequestMapping(path = "/accessLog", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class AccessLogController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessLogController.class);

    @Autowired
    private AccessLogService accessLogService;

    /**
     * 访问记录列表查询
     * @param questionId
     * @return
     */
    @GetMapping(path = "/{questionId}")
    public ResponseEntity selectList(@PathVariable("questionId") String questionId) {
        AccessLog accessLog = new AccessLog();
        accessLog.setQuestionId(questionId);
        List<AccessLogBO> accessLogList = accessLogService.selectList(accessLog);
        LOGGER.info("{}", accessLogList);
        return (accessLogList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", accessLogList));
    }

    /**
     * 访问记录访问量
     * @param questionId
     * @return
     */
    @GetMapping(path = "/statis/{questionId}")
    public ResponseEntity answerAvg(@PathVariable("questionId") String questionId) {
        AccessLog accessLog = new AccessLog();
        accessLog.setQuestionId(questionId);
        List<AccessLogBO> logStatis = accessLogService.selectAccessLogStatis(accessLog);
        LOGGER.info("{}", logStatis);
        return (logStatis == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", logStatis));
    }



    /**
     * 访问记录新增
     *
     * @return
     */
    @PostMapping(path = "/{questionId}")
    public ResponseEntity submitAccessLog(@Valid @RequestBody AccessLogBO accessLogBO,@PathVariable("questionId") String questionId) {
        LOGGER.info("{}", accessLogBO);
        accessLogBO.setQuestionId(questionId);
        AccessLogBO bo = accessLogService.insert(accessLogBO);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

}
