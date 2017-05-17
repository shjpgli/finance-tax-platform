package com.abc12366.uc.mapper.db1;

import com.abc12366.uc.model.PointsLog;
import com.abc12366.uc.model.PointsRule;
import com.abc12366.uc.model.bo.PointsLogBO;

/**
 * @author liuguiyao<435720953@qq.com.com>
 * @create 2017-05-15 10:18 PM
 * @since 2.0.0
 */
public interface PointsLogMapper {
    int insert(PointsLog pointsLog);

    int update(PointsLog pointsLog);
}
