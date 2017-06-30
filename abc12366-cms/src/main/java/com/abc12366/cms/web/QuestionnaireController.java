package com.abc12366.cms.web;

import com.abc12366.cms.model.questionnaire.bo.QuestionnaireBO;
import com.abc12366.cms.service.QuestionnaireService;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * 问卷控制类
 *
 * @author lizhongwei
 * @create 2017-06-15
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/question", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class QuestionnaireController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionnaireController.class);

    @Autowired
    private QuestionnaireService questionnaireService;

    /**
     * 问卷列表查询
     * @return
     */
    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize) {
        QuestionnaireBO questionnaire = new QuestionnaireBO();
        List<QuestionnaireBO> questionnaireList = questionnaireService.selectList(questionnaire);
        LOGGER.info("{}", questionnaireList);
        return (questionnaireList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", questionnaireList));
    }


    /**
     * 查询问卷详情
     *
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> selectOne(@PathVariable String id) {
        LOGGER.info("{}", id);
        QuestionnaireBO questionnaireBO = questionnaireService.selectOne(id);
        LOGGER.info("{}", questionnaireBO);
        return ResponseEntity.ok(Utils.kv("data", questionnaireBO));
    }

    /**
     * 问卷新增
     *
     * @return
     */
    @PostMapping
    public ResponseEntity submitQuestionnaire(@Valid @RequestBody QuestionnaireBO questionnaireBO) {
        LOGGER.info("{}", questionnaireBO);
        QuestionnaireBO bo = questionnaireService.insert(questionnaireBO);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    /**
     * 问卷修改
     * @param questionnaireBO
     * @param id
     * @return
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity update(@Valid @RequestBody QuestionnaireBO questionnaireBO, @PathVariable("id") String id) {
        LOGGER.info("{}", questionnaireBO);
        questionnaireBO.setId(id);
        QuestionnaireBO bo = questionnaireService.update(questionnaireBO);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    /**
     * 问卷状态修改
     * @param status
     * @param id
     * @return
     */
    @PutMapping(path = "/status/{id}")
    public ResponseEntity updateStatus(@Valid @RequestBody Boolean status, @PathVariable("id") String id) {
        LOGGER.info("{}", status);
        questionnaireService.updateStatus(id,status);
        return ResponseEntity.ok(Utils.kv());
    }

    /**
     * 问卷状态修改
     * @param id
     * @return
     */
    @PutMapping(path = "/access/{id}")
    public ResponseEntity updateAccess(@PathVariable("id") String id) {
        questionnaireService.updateAccessRate(id);
        return ResponseEntity.ok(Utils.kv());
    }

    /**
     * 问卷状态修改
     * @param id
     * @return
     */
    @PutMapping(path = "/recovery/{id}")
    public ResponseEntity updateRecoveryRate(@PathVariable("id") String id) {
        LOGGER.info("{}", id);
        questionnaireService.updateRecoveryRate(id);
        return ResponseEntity.ok(Utils.kv());
    }

    /**
     * 问卷状态修改
     * @param id
     * @return
     */
    @GetMapping(path = "/recovery/{id}")
    public ResponseEntity recoveryRate(@PathVariable("id") String id) {
        LOGGER.info("{}", id);
        QuestionnaireBO questionnaireBO = questionnaireService.selectOne(id);
        Integer recoveryRate = questionnaireBO.getRecoveryRate();
        Integer accessRate = questionnaireBO.getAccessRate();
        String rate = accuracy(recoveryRate,accessRate,3);
        return ResponseEntity.ok(Utils.kv("date",rate));
    }

    public static String accuracy(double recoveryRate, double accessRate, int scale){
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
        //可以设置精确几位小数
        df.setMaximumFractionDigits(scale);
        //模式 例如四舍五入
        df.setRoundingMode(RoundingMode.HALF_UP);
        double accuracy_num = recoveryRate / accessRate * 100;
        return df.format(accuracy_num)+"%";
    }

    /**
     * 问卷删除
     *
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity update(@PathVariable("id") String id) {
        QuestionnaireBO questionnaireBO = new QuestionnaireBO();
        questionnaireBO.setId(id);
        questionnaireService.delete(questionnaireBO);
        return ResponseEntity.ok(Utils.kv());
    }

    /**
     * 问卷拷贝
     *
     * @return
     */
    @PostMapping(path = "/copy")
    public ResponseEntity copy(@Valid @RequestBody QuestionnaireBO questionnaireBO) {
        LOGGER.info("{}", questionnaireBO);
        QuestionnaireBO bo = questionnaireService.copy(questionnaireBO);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

}
