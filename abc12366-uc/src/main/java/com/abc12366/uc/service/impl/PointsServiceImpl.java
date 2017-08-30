package com.abc12366.uc.service.impl;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.uc.mapper.db2.PointsRoMapper;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.service.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Admin: liuguiyao<435720953@qq.com>
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

    @Override
    public void compute(PointComputeBO pointComputeBO) {
        //查询出对应的积分规则
        List<PointCodex> pointCodexList = pointsRoMapper.selectCodexList(pointComputeBO);
        if (pointCodexList == null || pointCodexList.size() < 1) {
            return;
        }
        PointCodex pointCodex = pointCodexList.get(0);
        if (pointCodex.getUpoint() == null || pointCodex.getUpoint().toString().equals("")) {
//            return;
            throw new ServiceException(4854);
        }

        //查看获取经验值次数是否允许范围内
        String startTime = "";
        String endTime = "";
        String period = pointCodex.getPeriod().toUpperCase();
        if (period != null && !period.trim().equals("") && (period.equals("D") || period.equals("M") || period.equals("Y"))) {
            switch (pointCodex.getPeriod().toUpperCase()) {
                case "D":
                    startTime = "(SELECT CURDATE())";
                    endTime = "(SELECT CURDATE()+1)";
                    break;
                case "M":
                    startTime = "(SELECT DATE_ADD(CURDATE(),INTERVAL -DAY(CURDATE())+1 DAY))";
                    endTime = "(SELECT DATE_ADD(CURDATE() - DAY(CURDATE()) + 1, INTERVAL 1 MONTH))";
                    break;
                case "Y":
                    startTime = "(SELECT DATE_SUB(CURDATE(),INTERVAL DAYOFYEAR(NOW())-1 DAY))";
                    endTime = "(SELECT CONCAT(YEAR(NOW())+1,'-01-01'))";
                    break;
            }

            PointComputeLogParam param = new PointComputeLogParam();
            param.setUserId(pointComputeBO.getUserId());
            param.setTimeType(pointCodex.getPeriod().toUpperCase());
            param.setUpointCodexId(pointCodex.getId());
            param.setStartTime(startTime);
            param.setEndTime(endTime);
            List<PointComputeLog> computeLogList = pointsRoMapper.selectPointComputeLog(param);
            if (computeLogList != null && computeLogList.size() >= pointCodex.getDegree()) {
                return;
            }
        }
    }
}
