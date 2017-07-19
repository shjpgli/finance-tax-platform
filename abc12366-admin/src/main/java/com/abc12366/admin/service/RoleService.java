package com.abc12366.admin.service;

import com.abc12366.admin.model.Role;
import com.abc12366.admin.model.UserRole;
import com.abc12366.admin.model.bo.RoleBO;
import com.abc12366.admin.model.bo.UserRoleBO;

import java.util.List;
import java.util.Map;

/**
 * @description：权限管理
 * @author：lizhongwei
 */
public interface RoleService {

    /**
     * 查询权限树
     *
     * @return
     * @param role
     */
    List<RoleBO> selectList(Role role);

    /**
     * 添加权限
     *
     * @param roleBO
     */
    Role addRole(RoleBO roleBO);

    /**
     * 根据id删除权限
     *
     * @param id
     */
    int deleteRoleById(String id);

    /**
     * 根据id查询权限
     *
     * @param id
     * @return
     */
    RoleBO selectRoleById(String id);

    /**
     * 更新权限
     *
     * @param roleBO
     */
    RoleBO updateRole(RoleBO roleBO);

    /**
     * 根据权限id查询资源集合
     *
     * @param id
     * @return
     */
    List<String> selectMenuIdListByRoleId(String id);

    /**
     * 更新权限和资源的关联关系
     *
     * @param id
     * @param menuIds
     */
    void updateRoleMenu(String id, String menuIds);

    /**
     * 根据用户查询id查询权限集合
     *
     * @param userId
     * @return
     */
    List<String> selectRoleIdListByUserId(String userId);

    /**
     * 根据权限查询id查询资源路径集合
     *
     * @param roleId
     * @return
     */
    List<Map<String, String>> selectRoleResourceListByRoleId(String roleId);


    /**
     *
     * @param roleBO
     * @return
     */
    Role selectOne(RoleBO roleBO);

    /**
     * 根据角色名称查询角色
     * @param roleBO
     * @return
     */
    Role selectRoleByName(RoleBO roleBO);

    RoleBO selectUserByRoleId(String id);

    UserRoleBO updateUserRole(UserRoleBO userRoleBO);
}
