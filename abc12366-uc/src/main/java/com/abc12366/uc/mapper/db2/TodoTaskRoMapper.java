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

    List<TodoTaskFront> selectNormalTaskList(String userId);

    List<TodoTaskFront> selectOnetimeTaskList(String userId);

    List<TodoTaskFront> selectSpecialTaskList(String userId);

    List<TodoTaskFront> selectBangbangTaskList(String userId);
}
