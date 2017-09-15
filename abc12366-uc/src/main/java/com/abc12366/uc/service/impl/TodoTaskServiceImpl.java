package com.abc12366.uc.service.impl;

import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.mapper.db1.TodoTaskMapper;
import com.abc12366.uc.mapper.db2.TodoTaskRoMapper;
import com.abc12366.uc.mapper.db2.UserRoMapper;
import com.abc12366.uc.model.TodoTask;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.bo.ExperienceLogBO;
import com.abc12366.uc.model.bo.LoginBO;
import com.abc12366.uc.model.bo.PointsLogBO;
import com.abc12366.uc.model.bo.SysTaskBO;
import com.abc12366.uc.service.ExperienceLogService;
import com.abc12366.uc.service.PointsLogService;
import com.abc12366.uc.service.SysTaskService;
import com.abc12366.uc.service.TodoTaskService;
import com.abc12366.uc.util.UCConstant;
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

    @Autowired
    private UserRoMapper userRoMapper;

    @Transactional("db1TxManager")
    @Override
    public void doTask(String userId, String sysTaskId) {
        SysTaskBO sysTaskBO = sysTaskService.selectOne(sysTaskId);
        if (sysTaskBO == null) {
            return;
        }
        //做特殊任务
        if (sysTaskBO.getType().trim().equals(UCConstant.SPECIAL_TASK_TYPE)) {
            doTaskSpecial(userId, sysTaskBO);
        }
        //做其他任务
        String dateType = sysTaskBO.getDateType();
        switch (dateType.trim()) {
            case UCConstant.TASK_TIME_ONETIME_TYPE:
                doTaskOneTime(userId, sysTaskBO);
                break;
            case UCConstant.TASK_TIME_DAY_TYPE:
                doTaskDay(userId, sysTaskBO);
                break;
            case UCConstant.TASK_TIME_MONTH_TYPE:
                doTaskMonth(userId, sysTaskBO);
                break;
            case UCConstant.TASK_TIME_YEAR_TYPE:
                doTaskYear(userId, sysTaskBO);
                break;
        }
    }

    @Transactional("db1TxManager")
    @Override
    public void doTaskWithouComputeAward(String userId, String sysTaskId) {
        //用户每完成当天一项任务一次，更新已完成数，当已完成数等于该项任务数量，更新该项任务数量为已完成
        TodoTask todoTask = selectOne(userId, sysTaskId);

        //如果该项任务已完成，则返回
        if (todoTask==null||todoTask.getStatus().trim().equals(UCConstant.TASK_FINISHED)) {
            return;
        }

        todoTask.setFinishedCount(todoTask.getFinishedCount() + 1);
        if (todoTask.getFinishedCount() >= todoTask.getAllCount()) {
            todoTask.setStatus(UCConstant.TASK_FINISHED);
        }
        todoTask.setLastUpdate(new Date());
        update(todoTask);
    }

    @Override
    public void generateAllTodoTaskList(LoginBO loginBO) {
        User user = userRoMapper.selectByUsernameOrPhone(loginBO);
        if (user == null) {
            return;
        }
        String userId = user.getId();
        //1.生成一次性任务
        generateOneTimeTaskList(userId);

        //2.生成日度任务
        generateDayTaskList(userId);

        //3.生成月度任务
        generateMonthTaskList(userId);

        //4.生成年度任务
        generateYearTaskList(userId);

        //5.生成特殊任务
        generateSpecialTaskList(userId);
    }

    @Override
    public void update(TodoTask todoTask) {
        todoTaskMapper.update(todoTask);
    }

    @Override
    public TodoTask selectOne(String userId, String sysTaskId) {
        return todoTaskRoMapper.selectOneByDayBySysTaskId(userId, sysTaskId);
    }

    @Override
    public void computeAward(String userId, TodoTask todoTask) {
        //根据奖励的类型分别做计算
        if (todoTask.getAwardType().trim().equals(UCConstant.AWARD_TYPE_EXP)) {
            ExperienceLogBO experienceLog = new ExperienceLogBO();
            experienceLog.setUserId(userId);
            experienceLog.setRuleId(todoTask.getRuleId());
            experienceLog.setIncome(todoTask.getAward());
            experienceLog.setOutgo(0);

            experienceLogService.insert(experienceLog);
        } else if (todoTask.getAwardType().trim().equals(UCConstant.AWARD_TYPE_POINT)) {
            PointsLogBO pointsLog = new PointsLogBO();
            pointsLog.setUserId(userId);
            pointsLog.setRuleId(todoTask.getRuleId());
            pointsLog.setIncome(todoTask.getAward());
            pointsLog.setOutgo(0);
            pointsLogService.insert(pointsLog);
        }

    }

    //生成特殊任务
    private void generateSpecialTaskList(String userId) {
        String type = UCConstant.SPECIAL_TASK_TYPE;
        List<SysTaskBO> sysTaskBOList = sysTaskService.selectListByType(type);
        for(SysTaskBO sysTaskBO : sysTaskBOList){
            TodoTask todoTaskTmp = todoTaskRoMapper.selectOneByDayBySysTaskId(userId, sysTaskBO.getId());
            if(todoTaskTmp==null){
                TodoTask todoTask = new TodoTask();
                todoTask.setId(Utils.uuid());
                todoTask.setUserId(userId);
                todoTask.setSysTaskId(sysTaskBO.getId());
                todoTask.setAllCount(sysTaskBO.getCount());
                todoTask.setFinishedCount(0);
                todoTask.setAwardType(sysTaskBO.getAwardType());
                todoTask.setType(sysTaskBO.getType());
                todoTask.setAward(sysTaskBO.getAward());
                todoTask.setStatus(UCConstant.TASK_UNFINISHED);
                todoTask.setSkipUrl(sysTaskBO.getSkipURL());
                todoTask.setRuleId(sysTaskBO.getRuleId());
                Date date = new Date();
                todoTask.setCreateTime(date);
                todoTask.setLastUpdate(date);
                todoTask.setDateType(sysTaskBO.getDateType());
                if (sysTaskBO.getType().trim().equals(UCConstant.SPECIAL_TASK_TYPE)) {
                    long now = System.currentTimeMillis();
                    //如果特殊任务已过期则不需要生成了
                    if (now > sysTaskBO.getEndTime().getTime()) {
                        continue;
                    }
                    todoTask.setStartTime(sysTaskBO.getStartTime());
                    todoTask.setEndTime(sysTaskBO.getEndTime());
                }
                insert(todoTask);
            }
        }
    }

    //生成一次性任务
    private void generateOneTimeTaskList(String userId) {
        String dateType = UCConstant.TASK_TIME_ONETIME_TYPE;
        List<TodoTask> taskList = todoTaskRoMapper.selectListOneTime(userId, dateType);
        if (taskList != null && taskList.size() > 0) {
            return;
        }
        generateTaskListByDateType(userId, dateType);
    }

    //生成日度任务
    private void generateDayTaskList(String userId) {
        String dateType = UCConstant.TASK_TIME_DAY_TYPE;
        List<TodoTask> taskList = todoTaskRoMapper.selectListByDay(userId, dateType);
        if (taskList != null && taskList.size() > 0) {
            return;
        }
        generateTaskListByDateType(userId, dateType);
    }

    //生成年度任务
    private void generateYearTaskList(String userId) {
        String dateType = UCConstant.TASK_TIME_YEAR_TYPE;
        List<TodoTask> taskList = todoTaskRoMapper.selectListByYear(userId, dateType);
        if (taskList != null && taskList.size() > 0) {
            return;
        }
        generateTaskListByDateType(userId, dateType);
    }

    //生成月度任务
    private void generateMonthTaskList(String userId) {
        String dateType = UCConstant.TASK_TIME_MONTH_TYPE;
        List<TodoTask> taskList = todoTaskRoMapper.selectListByMonth(userId, dateType);
        if (taskList != null && taskList.size() > 0) {
            return;
        }
        generateTaskListByDateType(userId, dateType);
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
        for (SysTaskBO sysTaskBO : sysTaskBOList) {
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
            todoTask.setSkipUrl(sysTaskBO.getSkipURL());
            todoTask.setRuleId(sysTaskBO.getRuleId());
            Date date = new Date();
            todoTask.setCreateTime(date);
            todoTask.setLastUpdate(date);
            if (type.trim().equals(UCConstant.SPECIAL_TASK_TYPE)) {
                long now = System.currentTimeMillis();
                //如果特殊任务已过期则不需要生成了
                if (now > sysTaskBO.getEndTime().getTime()) {
                    continue;
                }
                todoTask.setStartTime(sysTaskBO.getStartTime());
                todoTask.setEndTime(sysTaskBO.getEndTime());
            }
            insert(todoTask);
        }
    }

    @Override
    public List<TodoTask> selectList(String type, String userId) {
        LOGGER.info("{}:{}", type, userId);
        return todoTaskRoMapper.selectList(type, userId);
    }

    public void finishTask(String userId, TodoTask todoTask) {
        //如果该项任务不存在或已完成，则返回
        if (todoTask == null || todoTask.getStatus().trim().equals(UCConstant.TASK_FINISHED)) {
            return;
        }

        //用户每完成当天一项任务一次，更新已完成数，当已完成数等于该项任务数量，更新该项任务数量为已完成
        todoTask.setFinishedCount(todoTask.getFinishedCount() + 1);
        if (todoTask.getFinishedCount() >= todoTask.getAllCount()) {
            todoTask.setStatus(UCConstant.TASK_FINISHED);
        }
        todoTask.setLastUpdate(new Date());
        update(todoTask);

        //计算做任务的奖励（主要是经验值和积分的变化）
        computeAward(userId, todoTask);
    }

    //做一次性任务
    @Transactional("db1TxManager")
    public void doTaskOneTime(String userId, SysTaskBO sysTaskBO) {
        TodoTask todoTask = todoTaskRoMapper.selectOneTime(userId, sysTaskBO.getId());

        finishTask(userId, todoTask);
    }

    //做频率为一天的任务
    @Transactional("db1TxManager")
    private void doTaskDay(String userId, SysTaskBO sysTaskBO) {
        TodoTask todoTask = todoTaskRoMapper.selectOneByDay(userId, sysTaskBO.getId());

        finishTask(userId, todoTask);

    }

    //做频率为一月的任务
    @Transactional("db1TxManager")
    private void doTaskMonth(String userId, SysTaskBO sysTaskBO) {
        TodoTask todoTask = todoTaskRoMapper.selectOneByMonth(userId, sysTaskBO.getId());

        finishTask(userId, todoTask);
    }

    //做频率为一年的任务
    @Transactional("db1TxManager")
    private void doTaskYear(String userId, SysTaskBO sysTaskBO) {
        TodoTask todoTask = todoTaskRoMapper.selectOneByYear(userId, sysTaskBO.getId());

        finishTask(userId, todoTask);
    }

    //做特殊任务
    @Transactional("db1TxManager")
    private void doTaskSpecial(String userId, SysTaskBO sysTaskBO) {
        //特殊任务如果未开始或过期，则不能完成
        long now = System.currentTimeMillis();
        if (now < sysTaskBO.getStartTime().getTime() || now > sysTaskBO.getEndTime().getTime()) {
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("sysTaskId", sysTaskBO.getId());
        map.put("startTime", sysTaskBO.getStartTime());
        TodoTask todoTask = todoTaskRoMapper.selectSpecial(map);

        finishTask(userId, todoTask);
    }

    private void generateTaskListByDateType(String userId, String dateType) {
        List<SysTaskBO> sysTaskBOList = sysTaskService.selectListByDateType(dateType);

        if (sysTaskBOList == null || sysTaskBOList.size() < 1) {
            return;
        }
        for (SysTaskBO sysTaskBO : sysTaskBOList) {
            TodoTask todoTask = new TodoTask();
            todoTask.setId(Utils.uuid());
            todoTask.setUserId(userId);
            todoTask.setSysTaskId(sysTaskBO.getId());
            todoTask.setAllCount(sysTaskBO.getCount());
            todoTask.setFinishedCount(0);
            todoTask.setAwardType(sysTaskBO.getAwardType());
            todoTask.setType(sysTaskBO.getType());
            todoTask.setAward(sysTaskBO.getAward());
            todoTask.setStatus(UCConstant.TASK_UNFINISHED);
            todoTask.setSkipUrl(sysTaskBO.getSkipURL());
            todoTask.setRuleId(sysTaskBO.getRuleId());
            Date date = new Date();
            todoTask.setCreateTime(date);
            todoTask.setLastUpdate(date);
            todoTask.setDateType(sysTaskBO.getDateType());
            if (sysTaskBO.getType().trim().equals(UCConstant.SPECIAL_TASK_TYPE)) {
                long now = System.currentTimeMillis();
                //如果特殊任务已过期则不需要生成了
                if (now > sysTaskBO.getEndTime().getTime()) {
                    continue;
                }
                todoTask.setStartTime(sysTaskBO.getStartTime());
                todoTask.setEndTime(sysTaskBO.getEndTime());
            }
            insert(todoTask);
        }
    }
}
