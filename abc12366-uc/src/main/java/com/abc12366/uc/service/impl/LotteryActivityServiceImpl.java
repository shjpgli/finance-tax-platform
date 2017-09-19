package com.abc12366.uc.service.impl;

/**
 * Admin: lingsuzhi <554600654@qq.com.com>
 * Date: 2017-09-18
 */



import com.abc12366.uc.model.LotteryActivity;
import com.abc12366.uc.model.bo.LotteryActivityBO;
import java.util.List;
import java.util.Map;
import java.util.Date;
import com.abc12366.uc.mapper.db1.LotteryActivityMapper;
import com.abc12366.uc.mapper.db2.LotteryActivityRoMapper;
import com.abc12366.uc.service.LotteryActivityService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LotteryActivityServiceImpl implements LotteryActivityService{
private static final Logger LOGGER = LoggerFactory.getLogger(LotteryActivityServiceImpl.class);
@Autowired
private LotteryActivityMapper lotteryActivityMapper;
@Autowired
private LotteryActivityRoMapper lotteryActivityRoMapper;
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
}
