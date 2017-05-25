package com.abc12366.uc.service.impl;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.mapper.db1.UserTaskMapper;
import com.abc12366.uc.mapper.db2.UserTaskRoMapper;
import com.abc12366.uc.model.UserTask;
import com.abc12366.uc.model.bo.UserTaskBO;
import com.abc12366.uc.model.bo.UserTaskInsertBO;
import com.abc12366.uc.model.bo.UserTaskUpdateBO;
import com.abc12366.uc.service.UserTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * User: liuguiyao<435720953@qq.com.com>
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
        return true;
    }
}
