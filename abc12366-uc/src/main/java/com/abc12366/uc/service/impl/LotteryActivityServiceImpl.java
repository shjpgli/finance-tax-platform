package com.abc12366.uc.service.impl;

/**
 * Admin: lingsuzhi <554600654@qq.com.com>
 * Date: 2017-09-21
 */

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.uc.mapper.db1.LotteryActivityMapper;
import com.abc12366.uc.mapper.db2.LotteryActivityRoMapper;
import com.abc12366.uc.model.LotteryActivity;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.service.*;
import com.abc12366.uc.util.UCConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public LotteryLogBO luckDraw(String userId, String activityId, String ip) {
        if (userId == null || userId.isEmpty()) {
            throw new ServiceException(9999, "userId参数错误，请查正");
        }
        if (activityId == null || activityId.isEmpty()) {
            throw new ServiceException(9999, "activityId参数错误，请查正");
        }
        if (ip == null || ip.isEmpty()) {
            throw new ServiceException(9999, "ip参数错误，请查正");
        }

//        P-hycj
//        PointCodex pointCodex = pointsService.selectCodexByRuleCode("P-hycj");
//        Integer point = pointCodex.getUpoint();
        LotteryActivityprizeBO obj = luckDrawEx(activityId);

        PointCalculateBO pointCalculateBO = new PointCalculateBO();
        pointCalculateBO.setUserId(userId);
        pointCalculateBO.setRuleId(UCConstant.POINT_RULE_LOTTERY_ID);
        Integer point = pointsService.calculate(pointCalculateBO);
        Date date = new Date();
        String remake = "";
        //库存 记日志
        //明天测试
        LotteryBO lottery = null;
        if (obj == null) {
            remake = "未抽中";
        } else {
             lottery =lotteryService.selectOne(obj.getLotteryId());
            if(lottery==null){
                return null;
            }
            //中奖的情况
            if (lottery.getStock() == null) {
                lottery.setStock(0);

            }
            if (lottery.getCount() == null) {
                lottery.setCount(0);
            }
            if (!obj.getStatus()) {
                remake = "奖品已禁用";
            } else if (lottery.getStock() <= lottery.getCount()) {
                remake = "总库存不足";
            } else {
                //判断商品是否过期
                if (obj.getStartTime() != null && obj.getEndTime() != null) {
                    if (date.getTime() < obj.getStartTime().getTime() || date.getTime() > obj.getEndTime().getTime()) {
                        remake = "奖品过期";
                    } else {
                        //首先判断是不是当天

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String oldDate = "";
                        if (lottery.getTimeDay() != null) {
                            oldDate = sdf.format(lottery.getTimeDay());
                        }
                        String nowDate = sdf.format(date);
                        if (!nowDate.equals(oldDate)) {
                            //假如不是当天
                            lottery.setTimeCount(0);
                            lottery.setTimeDay(new Date());
                        }
                        if (lottery.getTimeStock() <= lottery.getTimeCount()) {
                            remake = "当天库存不足";
                        } else {
                            lottery.setTimeCount(lottery.getTimeCount() + 1);
                            lottery.setCount(lottery.getCount() + 1);
                            lotteryService.update(lottery, lottery.getId());
                            //remake = "";
                        }
                    }
                }

            }

        }

        LotteryLogBO lotteryLogBO = new LotteryLogBO();
        lotteryLogBO.setUserId(userId);
        lotteryLogBO.setUpoint(point);
        lotteryLogBO.setActivityId(activityId);
        lotteryLogBO.setRemake(remake);
        lotteryLogBO.setIp(ip);
        if ("".equals(remake) && lottery != null)

        {//假如没有这个值 说明抽中了
            lotteryLogBO.setIsluck(1);
            lotteryLogBO.setLotteryId(lottery.getId());
            lotteryLogBO.setLotteryName(lottery.getName());
        } else

        {
            lotteryLogBO.setIsluck(0);
            obj = null;
        }

        LotteryLogBO logs = lotteryLogService.insert(lotteryLogBO);
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
