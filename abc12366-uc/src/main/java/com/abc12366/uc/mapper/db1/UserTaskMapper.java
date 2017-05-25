package com.abc12366.uc.mapper.db1;

import com.abc12366.uc.model.UserTask;

import java.util.Map;

/**
 * User: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-24
 * Time: 11:55
 */
public interface UserTaskMapper {
    int insert(UserTask userTask);

    int delete(Map<String, String> map);

    int update(UserTask userTask);
}
