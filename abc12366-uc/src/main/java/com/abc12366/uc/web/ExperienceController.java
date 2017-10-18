package com.abc12366.uc.web;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.bo.ExpCalculateBO;
import com.abc12366.uc.model.bo.ExpCodex;
import com.abc12366.uc.model.bo.ExpComputeBO;
import com.abc12366.uc.model.bo.MyExperienceBO;
import com.abc12366.uc.service.ExperienceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 用户经验值接口
 * Admin: liuguiyao<435720953@qq.com>
 * Date: 2017-07-25
 * Time: 16:22
 */
@RestController
@RequestMapping(path = "/experience", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class ExperienceController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserBindController.class);

    @Autowired
    private ExperienceService experienceService;

    /**
     * 获取我的经验值信息
     * @param userId
     * @return
     */
    @GetMapping(path = "/{userId}")
    public ResponseEntity getMyExperience(@PathVariable String userId) {
        LOGGER.info("{}", userId);
        MyExperienceBO myExperienceBO = experienceService.getMyExperience(userId);
        return ResponseEntity.ok(Utils.kv("data", myExperienceBO));
    }

//    @PostMapping(path = "/codex/{uexpruleId}")
//    public ResponseEntity codex(@PathVariable String uexpruleId,@Valid @RequestBody List<ExpCodex> codexList ) {
//        LOGGER.info("{}:{}", uexpruleId, codexList);
//        List<ExpCodex> expCodexList = experienceService.codex(uexpruleId, codexList);
//        return ResponseEntity.ok(Utils.kv("dataList", expCodexList));
//    }
//
//    @DeleteMapping(path = "/codex/{id}")
//    public ResponseEntity deleteCodex(@PathVariable String id) {
//        LOGGER.info("{}", id);
//        experienceService.deleteCodex(id);
//        return ResponseEntity.ok(Utils.kv());
//    }
//
//    @GetMapping(path = "/codex")
//    public ResponseEntity codexList() {
//        List<ExpCodex> codexList = experienceService.codexList();
//        return ResponseEntity.ok(Utils.kv("dataList", codexList));
//    }
//
//    @PostMapping(path = "/compute")
//    public ResponseEntity expCompute(@RequestBody ExpComputeBO expComputeBO){
//        LOGGER.info("{}", expComputeBO);
//        experienceService.compute(expComputeBO);
//        return ResponseEntity.ok(Utils.kv());
//    }

    /**
     * 根据经验值规则计算用户经验值
     * @param expCalculateBO
     * @return
     */
    @PostMapping(path = "/calculate")
    public ResponseEntity expCalculate(@RequestBody ExpCalculateBO expCalculateBO){
        LOGGER.info("{}", expCalculateBO);
        experienceService.calculate(expCalculateBO);
        return ResponseEntity.ok(Utils.kv());
    }
}
