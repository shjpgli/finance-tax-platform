package com.abc12366.uc.service.impl;

/**
 * Admin: lingsuzhi <554600654@qq.com.com>
 * Date: 2017-09-21
 */

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.uc.mapper.db1.LotteryActivityMapper;
import com.abc12366.uc.mapper.db2.LotteryActivityRoMapper;
import com.abc12366.uc.model.Lottery;
import com.abc12366.uc.model.LotteryActivity;
import com.abc12366.uc.model.LotteryActivityprize;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.service.*;
import com.abc12366.uc.util.LszUtil;
import com.abc12366.gateway.util.UCConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LotteryActivityServiceImpl implements LotteryActivityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LotteryActivityServiceImpl.class);
    @Autowired
    private LotteryActivityMapper lotteryActivityMapper;
    @Autowired
    private LotteryActivityRoMapper lotteryActivityRoMapper;
    @Autowired
    private PointsService pointsService;
    @Autowired
    private LotteryLogService lotteryLogService;
    @Autowired
    private LotteryService lotteryService;
    @Autowired
    private LotteryTimeService lotteryTimeService;
    @Autowired
    private LotteryActivityprizeService lotteryActivityprizeService;

    @Override
    public LotteryActivityBO update(LotteryActivityBO lotteryActivityBO, String id) {
        LotteryActivity obj = new LotteryActivity();
        BeanUtils.copyProperties(lotteryActivityBO, obj);
        obj.setId(id);
        obj.setCreateTime(null);
        int result = lotteryActivityMapper.update(obj);
        if (result != 1) {
            LOGGER.warn("修改失败，参数：" + obj);
            throw new RuntimeException("seviceErr:修改失败");
        }
        LotteryActivityBO returnObj = new LotteryActivityBO();
        BeanUtils.copyProperties(obj, returnObj);
        return returnObj;
    }

    @Override
    public boolean delete(String id) {
        Integer result = lotteryActivityMapper.delete(id);
        return (result == 1);
    }

    @Override
    public LotteryActivityBO insert(LotteryActivityBO lotteryActivityBO) {
        LotteryActivity obj = new LotteryActivity();
        BeanUtils.copyProperties(lotteryActivityBO, obj);
        Date date = new Date();
        obj.setId(java.util.UUID.randomUUID().toString());
        obj.setCreateTime(date);
        int result = lotteryActivityMapper.insert(obj);
        if (result != 1) {
            LOGGER.warn("新增失败，参数：" + obj);
            throw new RuntimeException("seviceErr:新增失败");
        }
        LotteryActivityBO returnObj = new LotteryActivityBO();
        BeanUtils.copyProperties(obj, returnObj);
        return returnObj;
    }

    @Override
    public LotteryActivityBO selectOne(String id) {
        return lotteryActivityRoMapper.selectOne(id);
    }

    @Override
    public List<LotteryActivityBO> selectList(Map map) {
        return lotteryActivityRoMapper.selectList(map);
    }
    //随机抽取一个奖品
    public   LotteryActivityprizeBO lotteryOne(String activityId) {
        Map<String, Object> map = new HashMap<>();
        map.put("activityId", activityId);
        List<LotteryActivityprizeBO> lottery = lotteryActivityprizeService.selectList(map);

        if (lottery.size() <= 0) {
            LOGGER.warn("必须拥有一个奖品");
            throw new RuntimeException("奖品为空，出现错误");
        }
        if (lottery == null || lottery.isEmpty()) {
            return null;
        }
        Date date = new Date();
        List<LotteryActivityprizeBO> awardToList= new ArrayList<LotteryActivityprizeBO>();
        for (LotteryActivityprizeBO obj : lottery) {
            Integer balance = obj.getBalance();
            if(balance == null)balance=0;
            Integer amount = obj.getAmount();
            if(amount == null)amount=0;
            if (!obj.getStatus()) {
                //remake = "奖品已禁用";
            } else if (amount<= balance) {//总数量 小于 已出数量
               // remake = "总库存不足";
            } else {
                //判断商品是否过期
                if (obj.getStartTime() != null && obj.getEndTime() != null) {
                    if (date.getTime() < obj.getStartTime().getTime() || date.getTime() > obj.getEndTime().getTime()) {
                       // remake = "奖品过期";
                    } else {
                        awardToList.add(obj);
                    }
                }
            }
        }
        double weight = 0;
        for (LotteryActivityprizeBO awardTo : awardToList) {

            weight += awardTo.getStock();
        }

        if (weight == 0) {
            return null;
        }
        //每次的种子数都是奖品的总量，所以只要奖品没被抽中，当前时间片的中奖时间点始终是固定的
        Random random = new Random( System.currentTimeMillis());
        int num = random.nextInt((int) weight);

        for (LotteryActivityprizeBO awardTo : awardToList) {
            //数量少的奖品在一开始被抽走的概率很小，数量越多越容易被抽中
            num -= awardTo.getStock();
            if (num < 0) {
                return awardTo;
            }
        }
        return null;
    }

private  boolean luckDo(LotteryActivityBO lottery){
        //首先判断是不是当天
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String oldDate = "";
    if (lottery.getTimeDay() != null) {
        oldDate = sdf.format(lottery.getTimeDay());
    }
    String nowDate = sdf.format(date);
    if (!nowDate.equals(oldDate)) {
        //假如不是当天
        lottery.setTimeCount(0);
        lottery.setTimeDay(date);
    }
    if (lottery.getTimeStock() <= lottery.getTimeCount()) {
        //当天库存不足
        return false;
    } else {

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        long rand = random.nextInt(86400000);//一天是 86400秒
        String nowDays = sdf.format(date);
        try {
            Date nowDay = sdf.parse(nowDays);
            long timel = date.getTime() - nowDay.getTime();//今天过去的时间数  毫秒
            if  (timel < rand){
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

}
    @Override
    public synchronized LotteryLogBO luckDraw(String userId, String activityId, String ip) {
        if (userId == null || userId.isEmpty()) {
            throw new ServiceException(9999, "userId参数错误，请查证");
        }
        if (activityId == null || activityId.isEmpty()) {
            throw new ServiceException(9999, "activityId参数错误，请查证");
        }
        if (ip == null || ip.isEmpty()) {
            throw new ServiceException(9999, "ip参数错误，请查证");
        }
//判断活动是否启用 是否到期
        LotteryActivityBO lotteryActivityBO = this.selectOne(activityId);
        if(lotteryActivityBO == null){
            throw new ServiceException(9999, "抽奖活动不存在，请查证");
        }
        if(!lotteryActivityBO.getStatus()){
            throw new ServiceException(9999, "抽奖活动已停用");
        }


        if(!LszUtil.dateIn(lotteryActivityBO.getStartTime(),lotteryActivityBO.getEndTime(),new Date())){
            throw new ServiceException(9999, "抽奖活动已过期");

        }
//        P-hycj
//        PointCodex pointCodex = pointsService.selectCodexByRuleCode("P-hycj");
//        Integer point = pointCodex.getUpoint();
        //抽一个奖品
       // LotteryActivityprizeBO obj = luckDrawEx(activityId);
        LotteryActivityprizeBO obj =  lotteryOne(activityId);
        PointCalculateBO pointCalculateBO = new PointCalculateBO();
        pointCalculateBO.setUserId(userId);
        pointCalculateBO.setRuleCode(UCConstant.POINT_RULE_LOTTERY_CODE);
        //扣积分
        Integer point = pointsService.calculate(pointCalculateBO);
        Date date = new Date();
        String remake = "";
        //库存 记日志
        //明天测试
        LotteryBO lottery = null;
        Random ra = new Random(System.currentTimeMillis());
        int tmpRa = ra.nextInt(100);
        if (tmpRa<50 || obj == null || !luckDo(lotteryActivityBO)) {//即使时间到了 也有百分之50的几率 不中奖
            remake = "未抽中";
        } else {
             lottery =lotteryService.selectOne(obj.getLotteryId());
            if(lottery==null){
                return null;// 这种情况是  奖品被删除
            }
         }

        LotteryLogBO lotteryLogBO = new LotteryLogBO();
        lotteryLogBO.setUserId(userId);
        lotteryLogBO.setUpoint(point);
        lotteryLogBO.setActivityId(activityId);
        lotteryLogBO.setRemake(remake);
        lotteryLogBO.setIp(ip);
        if ("".equals(remake) && lottery != null)
        {//假如这个值为空 说明抽中了
            lotteryLogBO.setIsluck(1);
            lotteryLogBO.setLotteryId(lottery.getId());
            lotteryLogBO.setLotteryName(lottery.getName());

            if (obj.getBalance() == null || obj.getBalance()<0){
                obj.setBalance(0);
            }
            obj.setBalance(obj.getBalance() + 1);
            lotteryActivityprizeService.update(obj,obj.getId());
            lotteryActivityBO.setTimeCount(lotteryActivityBO.getTimeCount()+1);
        } else

        {
            lotteryLogBO.setIsluck(0);
            obj = null;
        }

        LotteryLogBO logs = lotteryLogService.insert(lotteryLogBO);
        lotteryActivityBO.setCount((lotteryActivityBO.getCount() + 1));
        lotteryActivityBO.setLuckTime(date);
        this.update(lotteryActivityBO,lotteryActivityBO.getId());
        return logs;
    }

    public LotteryActivityprizeBO luckDrawEx(String activityId) {
        Map<String, Object> map = new HashMap<>();
        map.put("activityId", activityId);
        List<LotteryActivityprizeBO> list = lotteryActivityprizeService.selectList(map);

        if (list.size() <= 0) {
            LOGGER.warn("必须拥有一个奖品");
            throw new RuntimeException("奖品为空，出现错误，请联系管理员");
        }
        final int randMax = 1000000;//100万
        Random random = new Random();
        //对奖品生成随机
        random.setSeed(System.currentTimeMillis());
        int rand = random.nextInt(randMax);//100万
        //获取活动时间段概率
        LotteryTimeBO lotteryTimeBO = lotteryTimeService.findbyTime(activityId,new Date());
        int timeLuck = 100;
        if (lotteryTimeBO != null) {
            timeLuck = lotteryTimeBO.getLuck();
        }
        if (timeLuck > 100 || timeLuck < 0) {
            timeLuck = 100;
        }

        if (rand > randMax * timeLuck / 100){
            return null;//时间段排除
        }

        random.setSeed(System.currentTimeMillis());
        rand = random.nextInt(randMax);//100万
        int oldLuck = 0;
        Collections.shuffle(list);//随机打乱
        for (LotteryActivityprizeBO obj : list) {
            Double tmpdbl = (obj.getLuck() * 10000);//考虑4位小数
            int luck = tmpdbl.intValue();
            if (luck + oldLuck > rand) {
                return obj;
            }
            oldLuck += luck;
        }
        return null;
    }
}
