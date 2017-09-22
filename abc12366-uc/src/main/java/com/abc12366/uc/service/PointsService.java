package com.abc12366.uc.service;


import com.abc12366.uc.model.bo.PointCalculateBO;
import com.abc12366.uc.model.bo.PointCodex;
import com.abc12366.uc.model.bo.PointComputeBO;
import com.abc12366.uc.model.bo.PointsBO;

import java.util.List;

/**
 * Admin: liuguiyao<435720953@qq.com>
 * Date: 2017-07-20
 * Time: 21:43
 */
public interface PointsService {
    PointsBO selectOne(String userId);

    void compute(PointComputeBO pointComputeBO);

    List<PointCodex> codex(String upointCodexId, List<PointCodex> codexList);

    void deleteCodex(String id);

    List<PointCodex> codexList();

    PointCodex selectCodexByRuleCode(String ruleCode);

    /**
     * 根据积分规则根据用户的操作改变其积分值，并记日志
     * @param pointCalculateBO
     * @return points
     */
    int calculate(PointCalculateBO pointCalculateBO);
}
