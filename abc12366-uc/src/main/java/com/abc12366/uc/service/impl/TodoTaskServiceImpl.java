package com.abc12366.uc.service.impl;

import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.mapper.db1.TodoTaskMapper;
import com.abc12366.uc.mapper.db2.TodoTaskRoMapper;
import com.abc12366.uc.model.TodoTask;
import com.abc12366.uc.model.bo.SysTaskBO;
import com.abc12366.uc.service.ExperienceLogService;
import com.abc12366.uc.service.PointsLogService;
import com.abc12366.uc.service.SysTaskService;
import com.abc12366.uc.service.TodoTaskService;
import com.abc12366.uc.util.UCConstant;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-09-12
 * Time: 14:43
 */
@Service
public class TodoTaskServiceImpl implements TodoTaskService {
    private static Logger LOGGER = LoggerFactory.getLogger(TodoTaskServiceImpl.class);

    @Autowired
    private TodoTaskMapper todoTaskMapper;

    @Autowired
    private TodoTaskRoMapper todoTaskRoMapper;

    @Autowired
    private SysTaskService sysTaskService;

    @Autowired
    private PointsLogService pointsLogService;

    @Autowired
    private ExperienceLogService experienceLogService;

    @Override
    public TodoTask selectOne(@Param("type") String type, @Param("userId") String userId, @Param("sysTaskId") String sysTaskId) {
        List<TodoTask> taskList = todoTaskRoMapper.selectOne(type, userId, sysTaskId);
        return taskList.get(0);
    }

    @Transactional("db1TxManager")
    @Override
    public void doTask(String userId, String sysTaskId) {
        //用户每完成当天一项任务一次，更新已完成数，当已完成数等于该项任务数量，更新该项任务数量为已完成
        SysTaskBO sysTaskBO = sysTaskService.selectOne(sysTaskId);
        TodoTask todoTask = selectOne(userId, sysTaskBO.getId(), sysTaskId);

        //如果该项任务已完成，则返回
        if(todoTask.getStatus().trim().equals(UCConstant.TASK_FINISHED)){
            return;
        }

        todoTask.setFinishedCount(todoTask.getFinishedCount()+1);
        if(todoTask.getFinishedCount()>=todoTask.getAllCount()){
            todoTask.setStatus(UCConstant.TASK_FINISHED);
        }
        todoTask.setLastUpdate(new Date());
        update(todoTask);

        //计算做任务的奖励（主要是经验值和积分的变化）

    }

    @Override
    public void computeAward(String userId, TodoTask todoTask) {

    }

    @Override
    public void update(TodoTask todoTask) {
        todoTaskMapper.update(todoTask);
    }

    @Override
    public void insert(TodoTask todoTask) {
        LOGGER.info("{}", todoTask);
        todoTaskMapper.insert(todoTask);
    }

    @Transactional("db1TxManager")
    @Override
    public void generateTodoTaskList(String userId, String type) {
        //如果用户的日常任务已生成则返回
        List<TodoTask> taskList = todoTaskRoMapper.selectList(type, userId);
        if (taskList != null && taskList.size() > 0) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("type", type);
        List<SysTaskBO> sysTaskBOList = sysTaskService.selectDeployedList(map);
        for(SysTaskBO sysTaskBO:sysTaskBOList){
            TodoTask todoTask = new TodoTask();
            todoTask.setId(Utils.uuid());
            todoTask.setUserId(userId);
            todoTask.setSysTaskId(sysTaskBO.getId());
            todoTask.setAllCount(sysTaskBO.getCount());
            todoTask.setFinishedCount(0);
            todoTask.setAwardType(sysTaskBO.getAwardType());
            todoTask.setType(type);
            todoTask.setAward(sysTaskBO.getAward());
            todoTask.setStatus(UCConstant.TASK_UNFINISHED);
            Date date = new Date();
            todoTask.setCreateTime(date);
            todoTask.setLastUpdate(date);
            insert(todoTask);
        }
    }

    @Override
    public List<TodoTask> selectList(String type, String userId) {
        LOGGER.info("{}:{}", type, userId);
        List<TodoTask> todoTaskList = todoTaskRoMapper.selectList(type, userId);
        return todoTaskList;
    }
}
