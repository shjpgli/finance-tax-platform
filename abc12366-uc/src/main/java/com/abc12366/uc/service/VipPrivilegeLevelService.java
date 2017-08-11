package com.abc12366.uc.service;

import com.abc12366.uc.model.bo.VipLevelBO;
import com.abc12366.uc.model.bo.VipPrivilegeLevelBO;
import com.abc12366.uc.model.bo.VipPrivilegeLevelInsertBO;
import com.abc12366.uc.model.bo.VipPrivilegeLevelUpdateBO;

import java.util.List;
import java.util.Map;

/**
 * Admin: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-22
 * Time: 9:21
 */
public interface VipPrivilegeLevelService {
    List<VipPrivilegeLevelBO> selectListN(Map map);
    VipPrivilegeLevelBO selectLevelIdPrivilegeId(VipPrivilegeLevelBO obj);
    Integer updateByPrivilege(List<VipPrivilegeLevelBO> list);
    VipPrivilegeLevelBO addOrUpdate(VipPrivilegeLevelBO obj);
    List<VipPrivilegeLevelBO> selectListByLevelId(String levelId);
    List<List<VipPrivilegeLevelBO>> selectList();
    VipPrivilegeLevelBO selectOne(String id);
    VipPrivilegeLevelBO selectOneN(String id);

    VipPrivilegeLevelBO insert(VipPrivilegeLevelInsertBO vipPrivilegeLevelInsertBO);
    int deleteByLevel(String levelId );
    int deleteByPrivilege(String privilegeId );
    VipPrivilegeLevelBO update(VipPrivilegeLevelUpdateBO vipPrivilegeLevelUpdateBO, String id);
    List<VipPrivilegeLevelBO> selectListbyPrivlege(String privilege);
    boolean delete(String id);

    void enableOrDisable(String id, String status);
}
