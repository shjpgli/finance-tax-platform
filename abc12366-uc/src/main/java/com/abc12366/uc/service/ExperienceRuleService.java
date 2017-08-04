package com.abc12366.uc.service;

import com.abc12366.uc.model.bo.ExperienceRuleBO;
import com.abc12366.uc.model.bo.ExperienceRuleInsertBO;
import com.abc12366.uc.model.bo.ExperienceRuleUpdateBO;

import java.util.List;
import java.util.Map;

/**
 * Admin: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-22
 * Time: 11:24
 */
public interface ExperienceRuleService {
    List<ExperienceRuleBO> selectList(Map<String, Object> map);

    ExperienceRuleBO selectOne(String id);

    ExperienceRuleBO insert(ExperienceRuleInsertBO experienceRuleInsertBO);

    ExperienceRuleBO update(ExperienceRuleUpdateBO experienceRuleUpdateBO, String id);

    int delete(String id);

    void enableOrDisable(String id, String status);
}
