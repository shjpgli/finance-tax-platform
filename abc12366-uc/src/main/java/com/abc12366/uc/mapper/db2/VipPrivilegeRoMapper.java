package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.bo.VipPrivilegeBO;

import java.util.List;
import java.util.Map;

/**
 * User: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-22
 * Time: 9:57
 */
public interface VipPrivilegeRoMapper {
    List<VipPrivilegeBO> selectList(Map map);

    VipPrivilegeBO selectOne(String id);
}
