package com.abc12366.uc.service.impl;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.mapper.db1.PointMapper;
import com.abc12366.uc.mapper.db2.PointsRoMapper;
import com.abc12366.uc.mapper.db2.UserRoMapper;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.service.PointsLogService;
import com.abc12366.uc.service.PointsService;
import com.abc12366.uc.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Admin: liuguiyao<435720953@qq.com>
 * Date: 2017-07-20
 * Time: 21:43
 */
@Service
public class PointsServiceImpl implements PointsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PointsServiceImpl.class);

    @Autowired
    private PointsRoMapper pointsRoMapper;

    @Autowired
    private UserRoMapper userRoMapper;

    @Autowired
    private PointsLogService pointsLogService;

    @Autowired
    private PointMapper pointMapper;

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
        if (pointCodex.getUpoint() == null || pointCodex.getUpoint().toString().equals("") || pointCodex.getPeriod() == null || pointCodex.getPeriod().trim().equals("")) {
//            return;
            throw new ServiceException(4855);
        }

        //查看获取经验值次数是否允许范围内
        String period = pointCodex.getPeriod().toUpperCase();
        if (period.trim().equals("") || (!period.equals("D") && !period.equals("M") && !period.equals("Y") && !period.equals("A"))) {
            return;
        }
        if (!period.trim().equals("A")) {
            Date startTime = new Date();
            Date endTime = new Date();
            if (!period.trim().equals("") && (period.equals("D") || period.equals("M") || period.equals("Y"))) {
                switch (pointCodex.getPeriod().toUpperCase()) {
                    case "D":
                        startTime = DateUtils.getFirstHourOfDay();
                        endTime = DateUtils.getFirstHourOfLastDay();
                        break;
                    case "M":
                        startTime = DateUtils.getFirstDayOfMonth();
                        endTime = DateUtils.getFirstDayOfLastMonth();
                        break;
                    case "Y":
                        startTime = DateUtils.getFirstMonthOfYear();
                        endTime = DateUtils.getFirstMonthOfLastYear();
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
        //根据规则计算用户积分值变化
        User user = userRoMapper.selectOne(pointComputeBO.getUserId());
        if (user == null) {
//            return;
            throw new ServiceException(4018);
        }

        PointsLogBO pointsLog = new PointsLogBO();
        pointsLog.setUserId(pointComputeBO.getUserId());
        pointsLog.setRuleId(pointCodex.getUpointruleCode());
        pointsLog.setRemark(pointCodex.getRemark());
        if (pointCodex.getUpoint() < 0) {
            pointsLog.setIncome(0);
            pointsLog.setOutgo(-pointCodex.getUpoint());
        } else {
            pointsLog.setIncome(pointCodex.getUpoint());
            pointsLog.setOutgo(0);
        }
        pointsLogService.insert(pointsLog);

        //记录用户特定行为升级用户等级日志
        PointComputeLog pointComputeLog = new PointComputeLog();
        pointComputeLog.setId(Utils.uuid());
        pointComputeLog.setUserId(pointComputeBO.getUserId());
        pointComputeLog.setUpointCodexId(pointCodex.getId());
        pointComputeLog.setTimeType(pointCodex.getPeriod().toUpperCase());
        pointComputeLog.setCreateTime(new Date());
        pointMapper.insertComputeLog(pointComputeLog);
    }

    @Override
    public List<PointCodex> codex(String upointCodexId, List<PointCodex> codexList) {
        LOGGER.info("{}:{}", upointCodexId, codexList);
        //先批量删除
        pointMapper.deleteByRuleCode(upointCodexId);

        //再批量新增
        List<PointCodex> pointCodexList = new ArrayList<>();
        for (int i = 0; i < codexList.size(); i++) {
            PointCodex codex = new PointCodex();
            BeanUtils.copyProperties(codexList.get(i), codex);
            codex.setId(Utils.uuid());
            pointMapper.insert(codex);
            pointCodexList.add(codex);
        }
        return pointCodexList;
    }

    @Override
    public void deleteCodex(String id) {
        pointMapper.deleteCodex(id);
    }

    @Override
    public List<PointCodex> codexList() {
        return pointsRoMapper.codexList();
    }

    @Override
    public PointCodex selectCodexByRuleCode(String ruleCode) {
        LOGGER.info("{}", ruleCode);
        return pointsRoMapper.selectCodexByRuleCode(ruleCode);
    }
}