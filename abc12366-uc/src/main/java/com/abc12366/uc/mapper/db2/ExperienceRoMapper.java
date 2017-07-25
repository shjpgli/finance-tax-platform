package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.bo.MyExperienceBO;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-25
 * Time: 16:38
 */
public interface ExperienceRoMapper {
    MyExperienceBO getMyExperience(String userId);
}
