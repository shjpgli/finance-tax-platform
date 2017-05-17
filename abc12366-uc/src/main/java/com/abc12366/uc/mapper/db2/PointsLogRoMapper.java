package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.bo.PointsLogBO;

import java.util.List;
import java.util.Map;

/**
 * @author liuguiyao<435720953@qq.com.com>
 * @create 2017-05-15 10:18 PM
 * @since 2.0.0
 */
public interface PointsLogRoMapper {
    List<PointsLogBO> selectList(Map map);

}
