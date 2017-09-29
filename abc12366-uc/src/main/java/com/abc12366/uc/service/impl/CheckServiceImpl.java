package com.abc12366.uc.service.impl;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.mapper.db1.CheckMapper;
import com.abc12366.uc.mapper.db2.CheckRoMapper;
import com.abc12366.uc.model.Check;
import com.abc12366.uc.model.CheckRank;
import com.abc12366.uc.model.PrivilegeItem;
import com.abc12366.uc.model.ReCheck;
import com.abc12366.uc.model.bo.CheckListBO;
import com.abc12366.uc.model.bo.CheckListParam;
import com.abc12366.uc.model.bo.PointsLogBO;
import com.abc12366.uc.service.CheckService;
import com.abc12366.uc.service.PointsLogService;
import com.abc12366.uc.service.PrivilegeItemService;
import com.abc12366.uc.service.TodoTaskService;
import com.abc12366.uc.util.DateUtils;
import com.abc12366.uc.util.UCConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

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

    @Autowired
    private TodoTaskService todoTaskService;

    @Autowired
    private PrivilegeItemService privilegeItemService;


    @Transactional("db1TxManager")
    @Override
    public int check(Check check) {
        Calendar now = Calendar.getInstance();
        int day = now.get(Calendar.DAY_OF_MONTH);
        check.setOrderby(String.valueOf(day));
        check.setCheckDate(DateUtils.StrToDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));

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
//            continuingCheck(check.getUserId());
            //积分日志
//            pointsLog(check.getUserId(), points);
//            return;
        }

        //签到统计
        continuingCheck(check.getUserId());
        //记日志
        pointsLog(check.getUserId(), points);

        //完成任务埋点
        todoTaskService.doTaskWithouComputeAward(check.getUserId(), UCConstant.SYS_TASK_CHECK_ID);

        PrivilegeItem privilegeItem = privilegeItemService.selecOneByUser(check.getUserId());
        if (privilegeItem != null && privilegeItem.getHyjfjc() > 1) {
            //usablePoints = (int) (usablePoints * privilegeItem.getHyjfjc());
            points = (int) (points * privilegeItem.getHyjyzjc());
        }
        return points;
    }

    @Transactional("db1TxManager")
    @Override
    public void reCheck(ReCheck recheck) {
        Date date = DateUtils.StrToDate(recheck.getDate());
        //每天只能补签三次
        Check checkTmp = new Check();
        checkTmp.setUserId(recheck.getUserId());
        checkTmp.setCheckDate(date);
        List<Check> checkList = checkRoMapper.selectRecheck(checkTmp);
        if (checkList != null && checkList.size() >= 3) {
            throw new ServiceException(4851);
        }

        if (isRecheckExist(checkTmp)) {
            throw new ServiceException(4852);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Check check = new Check();
        check.setId(Utils.uuid());
        check.setUserId(recheck.getUserId());
        check.setCreateTime(new Date());
        check.setCheckDate(date);
        check.setOrderby(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        check.setIsReCheck(true);

        recheckInsert(check);
        int points = -20;
        //签到统计
        continuingCheck(recheck.getUserId(), calendar);
        //记日志
        recheckPointsLog(recheck.getUserId(), points);
    }

    @Override
    public List<CheckRank> rank(String yearTemp) {
        String year = yearTemp;
        if (yearTemp == null || yearTemp.trim().equals("")) {
            Calendar calendar = Calendar.getInstance();
            year = String.valueOf(calendar.get(Calendar.YEAR));
        }
        return checkRoMapper.selectRankList(year);
    }

    @Override
    public List<CheckListBO> checklist(String yearMonth) {
        String userId = Utils.getUserId();

        String[] timeArray = yearMonth.trim().split("-");
        int year = Integer.parseInt(timeArray[0]);
        int month = Integer.parseInt(timeArray[1]);
        int days = getDaysByYearMonth(year, month);

        Date startDate = DateUtils.StrToDate(yearMonth + "-01");
        Date endDate = DateUtils.StrToDate(year + "-" + (month + 1) + "-01");
        Map<String, Object> map = new HashMap<>();
        CheckListParam checkListParam = new CheckListParam();
        checkListParam.setUserId(userId);
        checkListParam.setStartDate(startDate);
        checkListParam.setEndDate(endDate);
        map.put("userId", userId);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        List<Check> checkList = checkRoMapper.selectCheckList(checkListParam);

        List<CheckListBO> checkListBOs = new ArrayList<>();

        //判断用户该月每天签到情况
        for (int i = 1; i <= days; i++) {
            CheckListBO checkListBO = new CheckListBO();
            String iExtend = String.valueOf(i);
            if (i < 10) {
                iExtend = "0" + i;
            }
            for (int j = 0; j < checkList.size(); j++) {
                Check check = checkList.get(j);
                Date checkDate = DateUtils.StrToDate(yearMonth + "-" + iExtend);
                checkListBO.setDate(checkDate);
                if (check.getCheckDate().equals(checkDate)) {
                    checkListBO.setIsCheck(true);
                }
            }
            checkListBOs.add(checkListBO);
        }
        return checkListBOs;
    }

    private void pointsLog(String userId, int points) {
        PointsLogBO pointsLog = new PointsLogBO();
        pointsLog.setId(Utils.uuid());
        pointsLog.setCreateTime(new Date());
        pointsLog.setRuleId(UCConstant.POINT_RULE_CHECK_ID);
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
        pointsLog.setRuleId(UCConstant.POINT_RULE_RECHECK_ID);
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
        //时间格式转换，为了数据库时间的比较
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), order);
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = format.format(date);
        Date checkDate = DateUtils.StrToDate(dateStr);
        check.setCheckDate(checkDate);
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
        Map<String, String> map = new HashMap<>();
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        map.put("userId", userId);
        map.put("year", year);
        List<CheckRank> checkRankList = checkRoMapper.selectOneRank(map);
        CheckRank checkRank = new CheckRank();
        checkRank.setUserId(userId);
        checkRank.setLastUpdate(new Date());
        checkRank.setYear(year);
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

    public void continuingCheck(String userId, Calendar calendar) {
        Map<String, String> map = new HashMap<>();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        map.put("userId", userId);
        map.put("year", year);
        List<CheckRank> checkRankList = checkRoMapper.selectOneRank(map);
        CheckRank checkRank = new CheckRank();
        checkRank.setUserId(userId);
        checkRank.setLastUpdate(new Date());
        checkRank.setYear(year);
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

    public boolean isRecheckExist(Check check) {
        List<Check> checkList = checkRoMapper.selectIsRecheck(check);
        if (checkList == null || checkList.size() <= 0) {
            return false;
        }
        return true;
    }

    public int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }
}
