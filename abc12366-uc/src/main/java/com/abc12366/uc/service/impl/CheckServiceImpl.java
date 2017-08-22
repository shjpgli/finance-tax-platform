package com.abc12366.uc.service.impl;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.mapper.db1.CheckMapper;
import com.abc12366.uc.mapper.db2.CheckRoMapper;
import com.abc12366.uc.model.Check;
import com.abc12366.uc.model.CheckRank;
import com.abc12366.uc.model.ReCheck;
import com.abc12366.uc.model.bo.PointsLogBO;
import com.abc12366.uc.service.CheckService;
import com.abc12366.uc.service.PointsLogService;
import com.abc12366.uc.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-08-21
 * Time: 14:28
 */
@Service
public class CheckServiceImpl implements CheckService {
    @Autowired
    private CheckMapper checkMapper;

    @Autowired
    private CheckRoMapper checkRoMapper;


    @Autowired
    private PointsLogService pointsLogService;


    @Transactional("db1TxManager")
    @Override
    public void check(Check check) {
        Calendar now = Calendar.getInstance();
        int day = now.get(Calendar.DAY_OF_MONTH);
        check.setOrderby(String.valueOf(day));
        //判断当天是否已经签到过
        if (isExist(check)) {
            throw new ServiceException(4850);
        }
        insert(check);
        int points = 5;

        //是否连续签到
        if (isContinueCheck(check.getUserId(), day - 1)) {
            //是否两天连续签到，获得10积分
            points = 10;
            //是否三天连续签到，获得15积分
            if (isContinueCheck(check.getUserId(), day - 2)) {
                points = 15;
                //是否连续四天或以上签到，获得20积分
                if (isContinueCheck(check.getUserId(), day - 3)) {
                    points = 20;
                }
            }
            //连续签到统计表
            continuingCheck(check.getUserId());
            //积分日志
            pointsLog(check.getUserId(), points);
            return;
        }

        //签到统计
        continuingCheck(check.getUserId());
        //记日志
        pointsLog(check.getUserId(), points);
    }

    @Transactional("db1TxManager")
    @Override
    public void reCheck(ReCheck recheck) {
        //每天只能补签三次
        Check checkTmp = new Check();
        checkTmp.setUserId(recheck.getUserId());
        List<Check> checkList = checkRoMapper.selectRecheck(checkTmp);
        if (checkList != null && checkList.size() >= 3) {
            throw new ServiceException(4851);
        }

        Date date = DateUtils.StrToDate(recheck.getDate());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Check check = new Check();
        check.setId(Utils.uuid());
        check.setUserId(recheck.getUserId());
        check.setCreateTime(new Date());
        check.setCheckDate(date);
        check.setOrderby(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        check.setIsReCheck(true);
        if (isExist(check)) {
            throw new ServiceException(4852);
        }

        recheckInsert(check);
        int points = -20;
        //签到统计
        continuingCheck(recheck.getUserId());
        //记日志
        recheckPointsLog(recheck.getUserId(), points);
    }

    @Override
    public List<CheckRank> rank() {
        return checkRoMapper.selectRankList();
    }

    private void pointsLog(String userId, int points) {
        PointsLogBO pointsLog = new PointsLogBO();
        pointsLog.setId(Utils.uuid());
        pointsLog.setCreateTime(new Date());
        pointsLog.setUserId(userId);
        pointsLog.setIncome(points);
        pointsLog.setLogType("CHECK_IN");
        pointsLog.setRemark("用户签到获取积分");
        pointsLogService.insert(pointsLog);
    }

    private void recheckPointsLog(String userId, int points) {
        PointsLogBO pointsLog = new PointsLogBO();
        pointsLog.setId(Utils.uuid());
        pointsLog.setCreateTime(new Date());
        pointsLog.setUserId(userId);
        pointsLog.setOutgo(-points);
        pointsLog.setLogType("RE_CHECK_IN");
        pointsLog.setRemark("用户补签消耗20积分");
        pointsLogService.insert(pointsLog);
    }

    private Check insert(Check c) {
        Check check = new Check();
        check.setUserId(c.getUserId());
        check.setId(Utils.uuid());
        Date date = new Date();
        check.setCreateTime(date);
        check.setCheckDate(date);
        check.setOrderby(String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)));
        check.setIsReCheck(false);
        checkMapper.insert(check);
        return check;
    }

    private Check recheckInsert(Check check) {
        checkMapper.insert(check);
        return check;
    }

    private boolean isContinueCheck(String userId, int order) {
        Check check = new Check();
        check.setUserId(userId);
        check.setOrderby(String.valueOf(order));
        return isExist(check);
    }

    public boolean isExist(Check check) {
        List<Check> checkList = checkRoMapper.selectByOrder(check);
        if (checkList != null && checkList.size() >= 1) {
            return true;
        }
        return false;
    }

    public void continuingCheck(String userId) {
        List<CheckRank> checkRankList = checkRoMapper.selectOneRank(userId);
        CheckRank checkRank = new CheckRank();
        checkRank.setUserId(userId);
        checkRank.setLastUpdate(new Date());
        if (checkRankList == null || checkRankList.size() < 1) {
            checkRank.setId(Utils.uuid());
            checkRank.setCount(1);
            checkMapper.insertRank(checkRank);
            return;
        }
        CheckRank checkRankTmp = checkRankList.get(0);
        checkRank.setCount(checkRankTmp.getCount() + 1);
        checkMapper.updateRank(checkRank);
    }

}
