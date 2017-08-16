package com.abc12366.bangbang.mapper.db2;

import com.abc12366.bangbang.model.bo.SystemRecordBO;

import java.util.List;
import java.util.Map;

/**
 * Admin: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-22
 * Time: 9:57
 */
public interface SystemRecordRoMapper {
    List<SystemRecordBO> selectList(Map map);

    SystemRecordBO selectOne(String id);
}
