package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.bo.ExperienceLevelBO;

import java.util.List;
import java.util.Map;

/**
 * Admin: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-22
 * Time: 16:00
 */
public interface ExperienceLevelRoMapper {
    List<ExperienceLevelBO> selectList(Map<String, Object> map);

    ExperienceLevelBO selectOne(String id);
}
