package com.abc12366.uc.service.impl;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.RedisConstant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.mapper.db1.UserTaskMapper;
import com.abc12366.uc.mapper.db2.UserTaskRoMapper;
import com.abc12366.uc.model.MyTaskSurvey;
import com.abc12366.uc.model.UserTask;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.service.UserTaskService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Admin: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-24
 * Time: 11:41
 */
@Service
public class UserTaskServiceImpl implements UserTaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserTaskServiceImpl.class);

    @Autowired
    private UserTaskRoMapper userTaskRoMapper;

    @Autowired
    private UserTaskMapper userTaskMapper;


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public UserTaskBO insert(UserTaskInsertBO userTaskInsertBO, String userId) {
        if (userTaskInsertBO == null) {
            LOGGER.warn("新增失败，参数为：" + null);
            throw new ServiceException(4101);
        }
        UserTask userTask = new UserTask();
        BeanUtils.copyProperties(userTaskInsertBO, userTask);
        Date date = new Date();
        userTask.setId(Utils.uuid());
        userTask.setUserId(userId);
        userTask.setCreateTime(date);
        userTask.setLastUpdate(date);
        int result = userTaskMapper.insert(userTask);
        if (result != 1) {
            LOGGER.warn("新增失败，参数为：", userTask);
            throw new ServiceException(4101);
        }

        // 删除redis用户信息
        redisTemplate.delete(userId + "_Tasks");

        UserTaskBO userTaskBO = new UserTaskBO();
        BeanUtils.copyProperties(userTask, userTaskBO);
        return userTaskBO;
    }

    @Override
    public UserTaskBO update(UserTaskUpdateBO userTaskUpdateBO, String userId, String id) {
        if (userTaskUpdateBO == null) {
            LOGGER.warn("修改失败，参数:" + null);
            throw new ServiceException(4102);
        }

        UserTask userTask = new UserTask();
        BeanUtils.copyProperties(userTaskUpdateBO, userTask);
        userTask.setId(id);
        userTask.setUserId(userId);
        userTask.setLastUpdate(new Date());
        int result = userTaskMapper.update(userTask);
        if (result < 1) {
            LOGGER.warn("修改失败，参数:", userTask);
            throw new ServiceException(4102);
        }

        // 删除redis用户信息
        redisTemplate.delete(userId + "_Tasks");

        UserTaskBO userTaskBO = new UserTaskBO();
        BeanUtils.copyProperties(userTask, userTaskBO);
        return userTaskBO;
    }

    @Override
    public boolean delete(Map<String, String> map) {
        List<UserTaskBO> userTaskBOList = userTaskRoMapper.selectList(map);
        if (userTaskBOList.size() < 1) {
            LOGGER.warn("删除失败，不存在可删除数据，参数:", map);
            throw new ServiceException(4103);
        }
        int reuslt = userTaskMapper.delete(map);
        if (reuslt < 1) {
            LOGGER.warn("删除失败，参数:", map);
            throw new ServiceException(4103);
        }

        // 删除redis用户信息
        redisTemplate.delete(map.get("userId") + "_Tasks");
        return true;
    }

    @Override
    public MyTaskBO selectMyTask(String userId) {
        MyTaskBO myTaskBO = userTaskRoMapper.selectMyTask(userId);
        //没有任何此用户的任务数据，则返回空
        if (myTaskBO == null) {
            return null;
        }
        //计算该用户当月完成任务获取积分的排名。-1代表没有排名
        List<TaskRangeBO> taskRangeBOList = userTaskRoMapper.selectTaskRangeList();
        if (taskRangeBOList == null || taskRangeBOList.size() == 0) {
            myTaskBO.setTaskRange("0");
            return myTaskBO;
        }
        myTaskBO.setTaskRange("0");
        for (int i = 1; i <= taskRangeBOList.size(); i++) {
            TaskRangeBO taskRangeBO = taskRangeBOList.get(i - 1);
            if (taskRangeBO.getUserId().equals(userId)) {
                myTaskBO.setTaskRange(i + "");
            }
        }
        return myTaskBO;
    }

    @Override
    public MyTaskSurvey selectMyTaskSurvey(String userId) {
        MyTaskSurvey myTaskSurvey = null;
        if (redisTemplate.hasKey(userId + "_Tasks")) {
            myTaskSurvey = JSONObject.parseObject(redisTemplate.opsForValue().get(userId + "_Tasks"), MyTaskSurvey
                    .class);
        } else {
            myTaskSurvey = userTaskRoMapper.selectMyTaskSurvey(userId);

            //没有任何此用户的任务数据，则返回空
            if (myTaskSurvey == null) {
                return null;
            }
            //计算该用户当月完成任务获取积分的排名。-1代表没有排名
            List<TaskRangeBO> taskRangeBOList = userTaskRoMapper.selectTaskRangeList();
            if (taskRangeBOList == null || taskRangeBOList.size() == 0) {
                myTaskSurvey.setTaskRange("0");
                return myTaskSurvey;
            }
            myTaskSurvey.setTaskRange("0");
            for (int i = 1; i <= taskRangeBOList.size(); i++) {
                TaskRangeBO taskRangeBO = taskRangeBOList.get(i - 1);
                if (taskRangeBO.getUserId().equals(userId)) {
                    myTaskSurvey.setTaskRange(i + "");
                }
            }
            redisTemplate.opsForValue().set(userId + "_Tasks", JSONObject.toJSONString(myTaskSurvey), RedisConstant
                            .USER_INFO_TIME_ODFAY,
                    TimeUnit.DAYS);
        }
        return myTaskSurvey;
    }
}
