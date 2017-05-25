package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.bo.SysTaskBO;

import java.util.List;
import java.util.Map;

/**
 * User: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-24
 * Time: 9:54
 */
public interface SysTaskRoMapper {
    List<SysTaskBO> selectList(Map<String, String> map);

    List<SysTaskBO> selectDeployedList(Map<String, String> map);

    SysTaskBO selectOne(String id);
}
