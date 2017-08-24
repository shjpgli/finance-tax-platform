package com.abc12366.uc.service.impl;

import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.mapper.db1.ExperienceMapper;
import com.abc12366.uc.mapper.db2.ExperienceRoMapper;
import com.abc12366.uc.model.bo.ExpCodex;
import com.abc12366.uc.model.bo.MyExperienceBO;
import com.abc12366.uc.service.ExperienceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Admin: liuguiyao<435720953@qq.com>
 * Date: 2017-07-25
 * Time: 16:33
 */
@Service
public class ExperienceServiceImpl implements ExperienceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceServiceImpl.class);

    @Autowired
    private ExperienceRoMapper experienceRoMapper;

    @Autowired
    private ExperienceMapper experienceMapper;

    @Override
    public MyExperienceBO getMyExperience(String userId) {
        LOGGER.info("{}", userId);
        return experienceRoMapper.getMyExperience(userId);
    }

    @Override
    public List<ExpCodex> codex(String uexpruleId, List<ExpCodex> codexList) {
        LOGGER.info("{}:{}", uexpruleId, codexList);
        //先批量删除
        experienceMapper.deleteByRuleId(uexpruleId);

        //再批量新增
        List<ExpCodex> expCodexList = new ArrayList<>();
        for(int i=0; i<codexList.size();i++){
            ExpCodex codex = new ExpCodex();
            BeanUtils.copyProperties(codexList.get(i), codex);
            codex.setId(Utils.uuid());
            experienceMapper.insert(codex);
            expCodexList.add(codex);
        }
        return expCodexList;
    }

    @Override
    public int deleteCodex(String id) {
        experienceMapper.delete(id);
        return 1;
    }

    @Override
    public List<ExpCodex> codexList(String uexpruleId) {
        return experienceRoMapper.codexList(uexpruleId);
    }
}
