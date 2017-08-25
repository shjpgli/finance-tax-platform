package com.abc12366.uc.service;

import com.abc12366.uc.model.bo.ExpCodex;
import com.abc12366.uc.model.bo.ExpComputeBO;
import com.abc12366.uc.model.bo.MyExperienceBO;

import java.util.List;

/**
 * Admin: liuguiyao<435720953@qq.com>
 * Date: 2017-07-25
 * Time: 16:33
 */
public interface ExperienceService {
    MyExperienceBO getMyExperience(String userId);

    List<ExpCodex> codex(String uexpruleId, List<ExpCodex> codex);

    int deleteCodex(String id);

    List<ExpCodex> codexList(String uexpruleId);

    void compute(ExpComputeBO expComputeBO);
}
