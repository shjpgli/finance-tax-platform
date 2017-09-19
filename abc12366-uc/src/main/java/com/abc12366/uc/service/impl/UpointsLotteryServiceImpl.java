//package com.abc12366.uc.service.impl;
//
///**
// * Admin: lingsuzhi <554600654@qq.com.com>
// * Date: 2017-09-05
// */
//
//
//import com.abc12366.uc.model.UpointsLottery;
//import com.abc12366.uc.model.bo.LotteryLogBO;
//import com.abc12366.uc.model.bo.LotteryTimeBO;
//import com.abc12366.uc.model.bo.PointCodex;
//import com.abc12366.uc.model.bo.UpointsLotteryBO;
//
//import java.util.*;
//
//import com.abc12366.uc.mapper.db1.UpointsLotteryMapper;
//import com.abc12366.uc.mapper.db2.UpointsLotteryRoMapper;
//import com.abc12366.uc.service.LotteryLogService;
//import com.abc12366.uc.service.LotteryTimeService;
//import com.abc12366.uc.service.PointsService;
//import com.abc12366.uc.service.UpointsLotteryService;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UpointsLotteryServiceImpl implements UpointsLotteryService {
//    private static final Logger LOGGER = LoggerFactory.getLogger(UpointsLotteryServiceImpl.class);
//    @Autowired
//    private UpointsLotteryMapper upointsLotteryMapper;
//    @Autowired
//    private UpointsLotteryRoMapper upointsLotteryRoMapper;
//    @Autowired
//    private LotteryLogService lotteryLogService;
//    @Autowired
//    private PointsService pointsService;
//    @Autowired
//    private LotteryTimeService lotteryTimeService;
//    @Override
//    public UpointsLotteryBO update(UpointsLotteryBO upointsLotteryBO, String id) {
//        UpointsLottery obj = new UpointsLottery();
//        BeanUtils.copyProperties(upointsLotteryBO, obj);
//        obj.setId(id);
//        obj.setCreateTime(null);
//        obj.setLastUpdate(new Date());
//        int result = upointsLotteryMapper.update(obj);
//        if (result != 1) {
//            LOGGER.warn("修改失败，参数：" + obj);
//            throw new RuntimeException("seviceErr:修改失败");
//        }
//        UpointsLotteryBO returnObj = new UpointsLotteryBO();
//        BeanUtils.copyProperties(obj, returnObj);
//        return returnObj;
//    }
//
//    @Override
//    public boolean delete(String id) {
//        Integer result = upointsLotteryMapper.delete(id);
//        return (result == 1);
//    }
//
//    @Override
//    public UpointsLotteryBO insert(UpointsLotteryBO upointsLotteryBO) {
//        UpointsLottery obj = new UpointsLottery();
//        BeanUtils.copyProperties(upointsLotteryBO, obj);
//        Date date = new Date();
//        obj.setId(java.util.UUID.randomUUID().toString());
//        obj.setCreateTime(date);
//        obj.setLastUpdate(date);
//        int result = upointsLotteryMapper.insert(obj);
//        if (result != 1) {
//            LOGGER.warn("新增失败，参数：" + obj);
//            throw new RuntimeException("seviceErr:新增失败");
//        }
//        UpointsLotteryBO returnObj = new UpointsLotteryBO();
//        BeanUtils.copyProperties(obj, returnObj);
//        return returnObj;
//    }
//
//    /**
//     * 初始化
//     */
//    @Override
//    public void inits( ) {
//        UpointsLotteryBO obj = new UpointsLotteryBO();
//        for(int i=0;i<12;i++) {
//           obj.setName("奖品" + i);
//           obj.setStock(1);
//           obj.setOrderId(i);
//           obj.setNotluck(1);
//           obj.setLuck(10.0);
//           this.insert(obj );
//        }
//    }
//    /**
//     * 获取随机数
//     */
//    @Override
//    synchronized public UpointsLotteryBO getval(String userId){
//        //
////        P-hycj
//
//        PointCodex pointCodex = pointsService.selectCodexByRuleCode("P-hycj");
//        Integer point = pointCodex.getUpoint();
//        UpointsLotteryBO obj = getvalEx();
//        //减库存 记日志
//        if(obj != null) {
//            int stock = 0;
//            if (obj.getStock() != null) {
//                stock = obj.getStock() - 1;
//            }
//            obj.setStock(stock);
//            this.update(obj, obj.getId());
//
//        }
//        LotteryLogBO lotteryLogBO = new LotteryLogBO();
//        lotteryLogBO.setUserId(userId);
//        if(obj != null) {
//            lotteryLogBO.setNotluck(0);
//            lotteryLogBO.setLotteryId(obj.getId());
//        }else{
//            lotteryLogBO.setNotluck(1);
//            lotteryLogBO.setLotteryId(null);
//        }
//        lotteryLogBO.setUpoint(point);
//        lotteryLogService.insert(lotteryLogBO);
//        return obj;
//    }
//
//    public UpointsLotteryBO getvalEx(){
//        List<UpointsLotteryBO> list = this.selectList(null);
//
//
//        if (list.size() <= 0){
//            LOGGER.warn("必须拥有一个奖品");
//            throw new RuntimeException("系统出现错误，请联系管理员");
//        }
//        Random random = new Random();
//        //对奖品生成随机
//        random.setSeed(System.currentTimeMillis());
//        int rand = random.nextInt(1000000);//100万
//        LotteryTimeBO lotteryTimeBO =lotteryTimeService.findbyTime(new Date( ));
//        int timeLuck = 100;
//        if (lotteryTimeBO != null ) {
//            timeLuck = lotteryTimeBO.getLuck();
//        }
//        if (timeLuck >100 || timeLuck<0){
//            timeLuck = 100;
//        }
//        rand = rand  * timeLuck /100;
//        //现在根据时间段平衡了 随机数
//        int oldLuck = 0;
//        for (UpointsLotteryBO obj :                list) {
//            Double tmpdbl = (obj.getLuck() * 10000);//考虑4位小数
//            int luck = tmpdbl.intValue();
//            if (luck + oldLuck >rand) {
//                Integer stock = obj.getStock();
//                if(stock>0) {//假如有库存
//                    return obj;
//                }else{
//                    return null;
//                }
//            }
//            oldLuck +=luck;
//        }
//
////        UpointsLotteryBO upointsLotteryBO = new UpointsLotteryBO();
////        upointsLotteryBO.set
//        return null;
//    }
//    @Override
//    public UpointsLotteryBO selectOne(String id) {
//        return upointsLotteryRoMapper.selectOne(id);
//    }
//
//    @Override
//    public List<UpointsLotteryBO> selectList(Map map) {
//        return upointsLotteryRoMapper.selectList(map);
//    }
//}
