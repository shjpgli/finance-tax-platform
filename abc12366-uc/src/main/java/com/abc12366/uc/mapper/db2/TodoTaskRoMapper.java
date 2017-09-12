package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.TodoTask;

import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-09-12
 * Time: 14:50
 */
public interface TodoTaskRoMapper {
    List<TodoTask> selectList(String type, String userId);

    List<TodoTask> selectOne(String type, String userId, String sysTaskId);
}
