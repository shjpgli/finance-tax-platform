package com.abc12366.uc.mapper.db1;

import com.abc12366.uc.model.TodoTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-09-12
 * Time: 15:43
 */
public interface TodoTaskMapper {
    int insert(TodoTask todoTask);

    int update(TodoTask todoTask);

    TodoTask selectOneTime(@Param("userId") String userId, @Param("sysTaskId") String sysTaskId);

    TodoTask selectSpecial(Map map);

    TodoTask selectOneByDay(@Param("userId") String userId, @Param("sysTaskId") String sysTaskId);

    TodoTask selectOneByMonth(@Param("userId") String userId, @Param("sysTaskId") String sysTaskId);

    TodoTask selectOneByYear(@Param("userId") String userId, @Param("sysTaskId") String sysTaskId);

    TodoTask selectOneByDayBySysTaskId(@Param("userId") String userId, @Param("sysTaskId") String sysTaskId);

    List<TodoTask> selectListOneTime(@Param("userId") String userId, @Param("sysTaskId") String sysTaskId);

    List<TodoTask> selectListByDay(@Param("userId") String userId, @Param("sysTaskId") String sysTaskId);

    List<TodoTask> selectListByMonth(@Param("userId") String userId, @Param("sysTaskId") String sysTaskId);

    List<TodoTask> selectListByYear(@Param("userId") String userId, @Param("sysTaskId") String sysTaskId);

    List<TodoTask> selectTimeLimitedOneByUserIdAndSysTaskId(@Param("userId") String userId, @Param("sysTaskId") String sysTaskId);
}
