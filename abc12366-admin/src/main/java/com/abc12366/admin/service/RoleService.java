package com.abc12366.admin.service;

import com.abc12366.admin.model.Role;

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
     */
    List<Role> selectRole();

    /**
     * 添加权限
     *
     * @param role
     */
    void addRole(Role role);

    /**
     * 根据id删除权限
     *
     * @param id
     */
    void deleteRoleById(String id);

    /**
     * 根据id查询权限
     *
     * @param id
     * @return
     */
    Role selectRoleById(String id);

    /**
     * 更新权限
     *
     * @param role
     */
    void updateRole(Role role);

    /**
     * 根据权限id查询资源集合
     *
     * @param id
     * @return
     */
    List<Long> selectResourceIdListByRoleId(String id);

    /**
     * 更新权限和资源的关联关系
     *
     * @param id
     * @param resourceIds
     */
    void updateRoleResource(String id, String resourceIds);

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
    List<Map<Long, String>> selectRoleResourceListByRoleId(String roleId);

}
