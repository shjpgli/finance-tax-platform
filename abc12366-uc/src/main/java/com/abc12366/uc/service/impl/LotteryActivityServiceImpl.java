package com.abc12366.uc.service.impl;

/**
 * Admin: lingsuzhi <554600654@qq.com.com>
 * Date: 2017-09-21
 */

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.uc.model.LotteryActivity;
import com.abc12366.uc.model.bo.*;

import java.text.SimpleDateFormat;
import java.util.*;

import com.abc12366.uc.mapper.db1.LotteryActivityMapper;
import com.abc12366.uc.mapper.db2.LotteryActivityRoMapper;
import com.abc12366.uc.service.*;

import com.abc12366.uc.util.UCConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

//        P-hycj
//        PointCodex pointCodex = pointsService.selectCodexByRuleCode("P-hycj");
//        Integer point = pointCodex.getUpoint();

        PointCalculateBO pointCalculateBO = new PointCalculateBO();
        pointCalculateBO.setUserId(userId);
        pointCalculateBO.setRuleId(UCConstant.POINT_RULE_LOTTERY_ID);
        LotteryBO obj = luckDrawEx(activityId);
        Integer point = pointsService.calculate(pointCalculateBO);
        Date date = new Date();
        String remake = "";
        //库存 记日志
        if (obj == null) {
            remake = "未抽中";
        } else {
            //中奖的情况
            if (obj.getStock() == null) {
                obj.setStock(0);

            }
            if (obj.getCount() == null) {
                obj.setCount(0);
            }
            if (!obj.getStatus()) {
                remake = "奖品已禁用";
            } else if (obj.getStock() <= obj.getCount()) {
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
                        if (obj.getTimeDay() != null) {
                            oldDate = sdf.format(obj.getTimeDay());
                        }
                        String nowDate = sdf.format(date);
                        if (!nowDate.equals(oldDate)) {
                            //假如不是当天
                            obj.setTimeCount(0);
                            obj.setTimeDay(new Date());
                        }
                        if (obj.getTimeStock() <= obj.getTimeCount()) {
                            remake = "当天库存不足";
                        } else {
                            obj.setTimeCount(obj.getTimeCount() + 1);
                            obj.setCount(obj.getCount() + 1);
                            lotteryService.update(obj, obj.getId());
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
        if ("".equals(remake) && obj != null)

        {//假如没有这个值 说明抽中了
            lotteryLogBO.setIsluck(1);
            lotteryLogBO.setLotteryId(obj.getId());
            lotteryLogBO.setLotteryName(obj.getName());
        } else

        {
            lotteryLogBO.setIsluck(0);
            obj = null;
        }

        LotteryLogBO logs = lotteryLogService.insert(lotteryLogBO);
        return logs;
    }

    public LotteryBO luckDrawEx(String activityId) {
        Map<String, Object> map = new HashMap<>();
        map.put("activityId", activityId);
        List<LotteryBO> list = lotteryService.findLotteryByActivity(map);

        if (list.size() <= 0) {
            LOGGER.warn("必须拥有一个奖品");
            throw new RuntimeException("系统出现错误，请联系管理员");
        }
        Random random = new Random();
        //对奖品生成随机
        random.setSeed(System.currentTimeMillis());
        int rand = random.nextInt(1000000);//100万
        LotteryTimeBO lotteryTimeBO = lotteryTimeService.findbyTime(new Date());
        int timeLuck = 100;
        if (lotteryTimeBO != null) {
            timeLuck = lotteryTimeBO.getLuck();
        }
        if (timeLuck > 100 || timeLuck < 0) {
            timeLuck = 100;
        }
        rand = rand * timeLuck / 100;
        //现在根据时间段平衡了 随机数
        rand = 1000000 - rand;
        //100万里面 生成一个数50万，当前时间段概率为百分之80   结果就是40万   再用100万去减  就得到60万， 百分之30中奖的话 则没有中奖
        //100万里面 生成一个数80万，当前时间段概率百分之90，结果是72万  用100万去减，28万  百分之30的中奖的话，则中奖
        int oldLuck = 0;
        Collections.shuffle(list);//随机打乱
        for (LotteryBO obj : list) {
            Double tmpdbl = (obj.getLuck() * 10000);//考虑4位小数
            int luck = tmpdbl.intValue();
            if (luck + oldLuck > rand) {
                Integer stock = obj.getStock();
                return obj;
            }
            oldLuck += luck;
        }
        return null;
    }
}
