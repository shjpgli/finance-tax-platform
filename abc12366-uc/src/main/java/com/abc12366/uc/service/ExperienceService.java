package com.abc12366.uc.service;

import com.abc12366.uc.model.bo.ExpCodex;
import com.abc12366.uc.model.bo.MyExperienceBO;

/**
 * Admin: liuguiyao<435720953@qq.com>
 * Date: 2017-07-25
 * Time: 16:33
 */
public interface ExperienceService {
    MyExperienceBO getMyExperience(String userId);

    ExpCodex codex(ExpCodex codex);

    int deleteCodex(String id);
}
