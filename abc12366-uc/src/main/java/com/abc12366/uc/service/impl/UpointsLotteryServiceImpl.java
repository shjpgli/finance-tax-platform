package com.abc12366.uc.service.impl;

/**
 * Admin: lingsuzhi <554600654@qq.com.com>
 * Date: 2017-09-05
 */


import com.abc12366.uc.model.UpointsLottery;
import com.abc12366.uc.model.bo.UpointsLotteryBO;

import java.util.List;
import java.util.Map;
import java.util.Date;

import com.abc12366.uc.mapper.db1.UpointsLotteryMapper;
import com.abc12366.uc.mapper.db2.UpointsLotteryRoMapper;
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
    @Override
    public UpointsLotteryBO selectOne(String id) {
        return upointsLotteryRoMapper.selectOne(id);
    }

    @Override
    public List<UpointsLotteryBO> selectList(Map map) {
        return upointsLotteryRoMapper.selectList(map);
    }
}
