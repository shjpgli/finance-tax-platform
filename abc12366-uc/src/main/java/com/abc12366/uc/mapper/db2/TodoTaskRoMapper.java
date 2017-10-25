package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.TodoTask;
import com.abc12366.uc.model.TodoTaskFront;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-09-12
 * Time: 14:50
 */
public interface TodoTaskRoMapper {
    List<TodoTask> selectList(@Param("type") String type, @Param("userId") String userId);

    List<TodoTask> selectOne(@Param("userId") String userId, @Param("sysTaskId") String sysTaskId);

    TodoTask selectOneTime(@Param("userId") String userId, @Param("sysTaskId") String sysTaskId);

    List<TodoTask> selectListOneTime(@Param("userId") String userId, @Param("sysTaskId") String sysTaskId);

    TodoTask selectOneByDay(@Param("userId") String userId, @Param("sysTaskId") String sysTaskId);

    List<TodoTask> selectListByDay(@Param("userId") String userId, @Param("sysTaskId") String sysTaskId);

    TodoTask selectOneByMonth(@Param("userId") String userId, @Param("sysTaskId") String sysTaskId);

    List<TodoTask> selectListByMonth(@Param("userId") String userId, @Param("sysTaskId") String sysTaskId);

    TodoTask selectOneByYear(@Param("userId") String userId, @Param("sysTaskId") String sysTaskId);

    List<TodoTask> selectListByYear(@Param("userId") String userId, @Param("sysTaskId") String sysTaskId);

    TodoTask selectSpecial(Map map);

    TodoTask selectOneByDayBySysTaskId(@Param("userId") String userId, @Param("sysTaskId") String sysTaskId);

    List<TodoTaskFront> selectNormalTaskList(String userId);

    List<TodoTaskFront> selectOnetimeTaskList(String userId);

    List<TodoTaskFront> selectSpecialTaskList(String userId);

    List<TodoTask> selectListByUserIdAndSysId(@Param("userId") String userId, @Param("sysTaskId") String sysTaskId);

    List<TodoTaskFront> selectBangbangTaskList(String userId);
}
