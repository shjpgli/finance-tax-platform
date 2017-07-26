package com.abc12366.uc.service;

import com.abc12366.uc.model.bo.SysTaskBO;
import com.abc12366.uc.model.bo.SysTaskInsertAndUpdateBO;
import com.abc12366.uc.model.bo.SysTaskListBO;

import java.util.List;
import java.util.Map;

/**
 * User: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-23
 * Time: 17:52
 */
public interface SysTaskService {
    List<SysTaskBO> selectList(Map<String, String> map);

    List<SysTaskBO> selectDeployedList(Map<String, String> map);

    SysTaskBO selectOne(String id);

    SysTaskBO insert(SysTaskInsertAndUpdateBO sysTaskInsertBO);

    SysTaskBO update(SysTaskInsertAndUpdateBO sysTaskUpdateBO, String id);

    boolean delete(String id);

    List<SysTaskListBO> selectDeployedListByType(Map<String, String> map);
}
