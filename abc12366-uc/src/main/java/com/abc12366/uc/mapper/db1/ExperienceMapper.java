package com.abc12366.uc.mapper.db1;

import com.abc12366.uc.model.bo.ExpCodex;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-08-24
 * Time: 15:20
 */
public interface ExperienceMapper {
    int insert(ExpCodex codex);

    int delete(String id);
}
