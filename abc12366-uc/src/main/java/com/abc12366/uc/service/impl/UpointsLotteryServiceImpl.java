package com.abc12366.uc.service.impl;

/**
 * Admin: lingsuzhi <554600654@qq.com.com>
 * Date: 2017-09-05
 */


import com.abc12366.uc.model.UpointsLottery;
import com.abc12366.uc.model.bo.LotteryLogBO;
import com.abc12366.uc.model.bo.UpointsLotteryBO;

import java.util.*;

import com.abc12366.uc.mapper.db1.UpointsLotteryMapper;
import com.abc12366.uc.mapper.db2.UpointsLotteryRoMapper;
import com.abc12366.uc.service.LotteryLogService;
import com.abc12366.uc.service.UpointsLotteryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpointsLotteryServiceImpl implements UpointsLotteryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpointsLotteryServiceImpl.class);
    @Autowired
    private UpointsLotteryMapper upointsLotteryMapper;
    @Autowired
    private UpointsLotteryRoMapper upointsLotteryRoMapper;
    @Autowired
    private LotteryLogService lotteryLogService;
    @Override
    public UpointsLotteryBO update(UpointsLotteryBO upointsLotteryBO, String id) {
        UpointsLottery obj = new UpointsLottery();
        BeanUtils.copyProperties(upointsLotteryBO, obj);
        obj.setId(id);
        obj.setCreateTime(null);
        obj.setLastUpdate(new Date());
        int result = upointsLotteryMapper.update(obj);
        if (result != 1) {
            LOGGER.warn("修改失败，参数：" + obj);
            throw new RuntimeException("seviceErr:修改失败");
        }
        UpointsLotteryBO returnObj = new UpointsLotteryBO();
        BeanUtils.copyProperties(obj, returnObj);
        return returnObj;
    }

    @Override
    public boolean delete(String id) {
        Integer result = upointsLotteryMapper.delete(id);
        return (result == 1);
    }

    @Override
    public UpointsLotteryBO insert(UpointsLotteryBO upointsLotteryBO) {
        UpointsLottery obj = new UpointsLottery();
        BeanUtils.copyProperties(upointsLotteryBO, obj);
        Date date = new Date();
        obj.setId(java.util.UUID.randomUUID().toString());
        obj.setCreateTime(date);
        obj.setLastUpdate(date);
        int result = upointsLotteryMapper.insert(obj);
        if (result != 1) {
            LOGGER.warn("新增失败，参数：" + obj);
            throw new RuntimeException("seviceErr:新增失败");
        }
        UpointsLotteryBO returnObj = new UpointsLotteryBO();
        BeanUtils.copyProperties(obj, returnObj);
        return returnObj;
    }

    /**
     * 初始化
     */
    @Override
    public void inits( ) {
        UpointsLotteryBO obj = new UpointsLotteryBO();
        for(int i=0;i<12;i++) {
           obj.setName("奖品" + i);
           obj.setStock(1);
           obj.setOrderId(i);
           obj.setNotluck(1);
           obj.setLuck(10.0);
           this.insert(obj );
        }
    }
    /**
     * 获取随机数
     */
    @Override
    synchronized public UpointsLotteryBO getval(String userId,Integer point){
        UpointsLotteryBO obj = getvalEx();
        //减库存 记日志
        if(obj.getNotluck() !=1){
            int stock = 0;
            if(obj.getStock() != null){
                stock = obj.getStock() - 1;
            }
            obj.setStock( stock);
            this.update(obj,obj.getId());
        }

        LotteryLogBO lotteryLogBO = new LotteryLogBO();
        lotteryLogBO.setLotteryId(obj.getId());
        lotteryLogBO.setUserId(userId);
        lotteryLogBO.setNotluck(obj.getNotluck());
        lotteryLogBO.setUpoint(point);
        lotteryLogService.insert(lotteryLogBO);
        return obj;
    }

    public UpointsLotteryBO getvalEx(){
        List<UpointsLotteryBO> list = this.selectList(null);

        //把谢谢参与排除
        List<UpointsLotteryBO> listLuck = new ArrayList<UpointsLotteryBO>() ;
        List<UpointsLotteryBO> listNotluck = new ArrayList<UpointsLotteryBO>() ;
        for (UpointsLotteryBO obj : list){
            if(obj.getNotluck() == 1){
                listNotluck.add(obj);
            }else            {
                listLuck.add(obj);
            }
        }
        if (listNotluck.size() <= 0){
            LOGGER.warn("必须拥有一个不中奖的值");
            throw new RuntimeException("系统出现错误，请联系管理员");
        }
        Random random = new Random();
        //对奖品生成随机
        for (UpointsLotteryBO obj : listLuck){
            Integer stock = obj.getStock();
            if(stock>0){//假如有库存
                if(obj.getLuck() != null) {
                    Double tmpdbl = (obj.getLuck() * 100000);//考虑5位小数
                    int luck = tmpdbl.intValue();
                    //现在得到一个扩大10万倍的概率值   比如百分之10    就是100 0000  应该生成1000万内的随机数

                    random.setSeed(System.currentTimeMillis());
                    int rand = random.nextInt(10000000);
                    if(rand < luck){//
                        //中奖
                        return obj;
                    }
                }
            }
        }
        //这里是都没中奖的情况
        int index = random.nextInt(listNotluck.size());
        return listNotluck.get(index);
    }
    @Override
    public UpointsLotteryBO selectOne(String id) {
        return upointsLotteryRoMapper.selectOne(id);
    }

    @Override
    public List<UpointsLotteryBO> selectList(Map map) {
        return upointsLotteryRoMapper.selectList(map);
    }
}
