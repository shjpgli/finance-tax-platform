package com.abc12366.uc.web;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.bo.MyExperienceBO;
import com.abc12366.uc.service.ExperienceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
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

    @GetMapping(path = "/{userId}")
    public ResponseEntity getMyExperience(@PathVariable String userId) {
        LOGGER.info("{}", userId);
        MyExperienceBO myExperienceBO = experienceService.getMyExperience(userId);
        return ResponseEntity.ok(Utils.kv("data", myExperienceBO));
    }

}
