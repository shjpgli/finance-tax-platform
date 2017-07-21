package com.abc12366.uc.service.impl;

import com.abc12366.uc.mapper.db2.PointsRoMapper;
import com.abc12366.uc.model.bo.PointsBO;
import com.abc12366.uc.service.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-20
 * Time: 21:43
 */
@Service
public class PointsServiceImpl implements PointsService {

    @Autowired
    private PointsRoMapper pointsRoMapper;

    @Override
    public PointsBO selectOne(String userId) {
        return pointsRoMapper.selectOne(userId);
    }
}
