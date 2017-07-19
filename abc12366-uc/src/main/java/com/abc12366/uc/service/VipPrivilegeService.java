package com.abc12366.uc.service;

import com.abc12366.uc.model.bo.VipPrivilegeBO;
import com.abc12366.uc.model.bo.VipPrivilegeInsertAndUpdateBO;

import java.util.List;
import java.util.Map;

/**
 * User: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-22
 * Time: 9:21
 */
public interface VipPrivilegeService {
    List<VipPrivilegeBO> selectList(Map map);

    VipPrivilegeBO selectOne(String id);

    VipPrivilegeBO insert(VipPrivilegeInsertAndUpdateBO vipPrivilegeInsertBO);

    VipPrivilegeBO update(VipPrivilegeInsertAndUpdateBO vipPrivilegeUpdateBO, String id);

    boolean delete(String id);

    void enableOrDisable(String id, String status);
}
