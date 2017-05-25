package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.bo.UserTaskBO;

import java.util.List;
import java.util.Map;

/**
 * User: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-24
 * Time: 11:54
 */
public interface UserTaskRoMapper {
    List<UserTaskBO> selectList(Map<String, String> map);
}
