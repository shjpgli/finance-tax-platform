package com.abc12366.uc.mapper.db2;


import com.abc12366.uc.model.bo.PointsBO;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-20
 * Time: 21:47
 */
public interface PointsRoMapper {
    PointsBO selectOne(String userId);
}
