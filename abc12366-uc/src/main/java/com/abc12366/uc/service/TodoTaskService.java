package com.abc12366.uc.service;

import com.abc12366.uc.model.TodoTask;
import com.abc12366.uc.model.bo.LoginBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-09-12
 * Time: 14:43
 */
public interface TodoTaskService {
    /**
     * 插入代办任务
     * @param todoTask
     */
     void insert(TodoTask todoTask);

    /**
     * 用户首次登录后生成用户日常任务
     * @param userId
     */
    void generateTodoTaskList(String userId, String type);

    /**
     * 查询用户代办任务列表
     * @param type
     * @param userId
     * @return
     */
    List<TodoTask> selectList(@Param("type")String type,@Param("userId")String userId);

    /**
     * 查询用户当天一项任务
     * @param userId
     * @param sysTaskId
     * @return
     */
    TodoTask selectOne(@Param("userId")String userId,@Param("sysTaskId") String sysTaskId);

    /**
     * 用户完成任务记日志方法，并且计算奖励
     * @param userId
     * @param sysTaskId
     */
    void doTask(String userId, String sysTaskId);

    /**
     * 用户完成任务记日志方法，不计算奖励
     * 多用于奖励规则比较复杂需要单做的业务方法里
     * @param userId
     * @param sysTaskId
     */
    void doTaskWithouComputeAward(String userId, String sysTaskId);

    /**
     * 更新用户当天一项任务
     * @param todoTask
     */
    void update(TodoTask todoTask);

    /**
     * 计算用户做任务获得的奖励
     * @param userId
     * @param todoTask
     */
    void computeAward(String userId, TodoTask todoTask);

    void generateAllTodoTaskList(LoginBO loginBO);

    List<TodoTask> selectNormalTaskList(String userId);

    List<TodoTask> selectOnetimeTaskList(String userId);

    List<TodoTask> selectSpecialTaskList(String userId);
}
