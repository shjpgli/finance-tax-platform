package com.abc12366.uc.service;

import com.abc12366.uc.model.bo.PointsRuleBO;
import com.abc12366.uc.model.bo.PointsRuleUpdateBO;

import java.util.List;
import java.util.Map;

/**
 * @author liuguiyao<435720953@qq.com.com>
 * @create 2017-05-16 10:18 PM
 * @since 2.0.0
 */
public interface PointsRuleService {
    List<PointsRuleBO> selectList(Map map);

    PointsRuleBO selectOne(String id);

    PointsRuleBO insert(PointsRuleBO pointsRuleBO);

    PointsRuleBO update(PointsRuleUpdateBO pointsRuleUpdateBO, String id);
}
