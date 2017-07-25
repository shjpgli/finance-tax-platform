package com.abc12366.uc.service.impl;

import com.abc12366.uc.mapper.db2.ExperienceRoMapper;
import com.abc12366.uc.model.bo.MyExperienceBO;
import com.abc12366.uc.service.ExperienceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-25
 * Time: 16:33
 */
@Service
public class ExperienceServiceImpl implements ExperienceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceServiceImpl.class);

    @Autowired
    private ExperienceRoMapper experienceRoMapper;

    @Override
    public MyExperienceBO getMyExperience(String userId) {
        LOGGER.info("{}", userId);
        return experienceRoMapper.getMyExperience(userId);
    }
}
