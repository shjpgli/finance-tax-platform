package com.abc12366.uc.service.impl;

/**
 * Admin: lingsuzhi <554600654@qq.com.com>
 * Date: 2017-09-18
 */



import com.abc12366.uc.model.Lottery;
import com.abc12366.uc.model.bo.LotteryBO;
import java.util.List;
import java.util.Map;
import java.util.Date;
import com.abc12366.uc.mapper.db1.LotteryMapper;
import com.abc12366.uc.mapper.db2.LotteryRoMapper;
import com.abc12366.uc.service.LotteryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LotteryServiceImpl implements LotteryService{
private static final Logger LOGGER = LoggerFactory.getLogger(LotteryServiceImpl.class);
@Autowired
private LotteryMapper lotteryMapper;
@Autowired
private LotteryRoMapper lotteryRoMapper;
@Override
public LotteryBO update(LotteryBO lotteryBO, String id) {
Lottery obj = new Lottery();
BeanUtils.copyProperties(lotteryBO, obj);
obj.setId(id);
obj.setCreateTime(null);
int result = lotteryMapper.update(obj);
if (result != 1) {
LOGGER.warn("修改失败，参数：" + obj);
throw new RuntimeException("seviceErr:修改失败");
}
LotteryBO returnObj = new LotteryBO();
BeanUtils.copyProperties(obj, returnObj);
return returnObj;
}
@Override
public boolean delete(String id) {
Integer result = lotteryMapper.delete(id);
return (result == 1);
}
@Override
public LotteryBO insert(LotteryBO lotteryBO) {
Lottery obj = new Lottery();
BeanUtils.copyProperties(lotteryBO, obj);
Date date = new Date();
obj.setId(java.util.UUID.randomUUID().toString());
obj.setCreateTime(date);
int result = lotteryMapper.insert(obj);
if (result != 1) {
LOGGER.warn("新增失败，参数：" + obj);
throw new RuntimeException("seviceErr:新增失败");
}
LotteryBO returnObj = new LotteryBO();
BeanUtils.copyProperties(obj, returnObj);
return returnObj;
}
@Override
public LotteryBO selectOne(String id) {
return lotteryRoMapper.selectOne(id);
}
@Override
public List<LotteryBO> selectList(Map map) {
return lotteryRoMapper.selectList(map);
}
}
