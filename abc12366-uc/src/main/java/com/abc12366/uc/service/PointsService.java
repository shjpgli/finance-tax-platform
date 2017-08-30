package com.abc12366.uc.service;


import com.abc12366.uc.model.bo.PointComputeBO;
import com.abc12366.uc.model.bo.PointsBO;

/**
 * Admin: liuguiyao<435720953@qq.com>
 * Date: 2017-07-20
 * Time: 21:43
 */
public interface PointsService {
    PointsBO selectOne(String userId);

    void compute(PointComputeBO pointComputeBO);
}
