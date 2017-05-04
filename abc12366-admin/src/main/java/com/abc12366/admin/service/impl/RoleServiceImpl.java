package com.abc12366.admin.service.impl;

import com.abc12366.admin.mapper.db1.RoleMapper;
import com.abc12366.admin.mapper.db1.RoleMenuMapper;
import com.abc12366.admin.mapper.db1.UserRoleMapper;
import com.abc12366.admin.mapper.db2.RoleMenuRoMapper;
import com.abc12366.admin.mapper.db2.RoleRoMapper;
import com.abc12366.admin.mapper.db2.UserRoleRoMapper;
import com.abc12366.admin.model.Role;
import com.abc12366.admin.model.RoleMenu;
import com.abc12366.admin.model.bo.RoleBO;
import com.abc12366.admin.service.RoleService;
import com.abc12366.common.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {

    private static Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleRoMapper roleRoMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private RoleMenuRoMapper roleMenuRoMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserRoleRoMapper userRoleRoMapper;


    @Override
    public List<Role> selectList() {
        return roleRoMapper.selectList();
    }

    @Override
    public int addRole(RoleBO roleBO) {
        Role role = new Role();
        BeanUtils.copyProperties(roleBO,role);
        role.setId(Utils.uuid());
        Date date = new Date();
        role.setCreateTime(date);
        role.setUpdateTime(date);
        int insert = roleMapper.insert(role);
        if (insert != 1) {
            logger.warn("插入失败，参数：{}", role.toString());
            throw new RuntimeException("插入失败");
        }
        return insert;
    }

    @Override
    public int deleteRoleById(String id) {
        int del = roleMapper.deleteRoleById(id);
        if (del != 1) {
            logger.warn("删除失败，id：{}", id);
            throw new RuntimeException("删除失败");
        }
        return del;
    }

    @Override
    public Role selectRoleById(String id) {
        return roleRoMapper.selectRoleById(id);
    }

    @Override
    public int updateRole(RoleBO roleBO) {
        Role role = new Role();
        BeanUtils.copyProperties(roleBO,role);
        role.setUpdateTime(new Date());
        int update = roleMapper.updateRole(role);
        if (update != 1) {
            logger.warn("更新失败，参数：{}", roleBO.toString());
            throw new RuntimeException("更新失败");
        }
        return update;
    }

    @Override
    public List<String> selectMenuIdListByRoleId(String id) {
        return roleRoMapper.selectMenuIdListByRoleId(id);
    }

    @Override
    public void updateRoleMenu(String id, String resourceIds) {
        List<String> roleMenuIdList = roleRoMapper.selectRoleMenuIdListByRoleId(id);
        if (roleMenuIdList != null && (!roleMenuIdList.isEmpty())) {
            for (String roleMenuId : roleMenuIdList) {
                roleMenuMapper.deleteById(roleMenuId);
            }
        }
        String[] resources = resourceIds.split(",");
        RoleMenu roleMenu = new RoleMenu();
        for (String menuId : resources) {
            roleMenu.setRoleId(id);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);
        }
    }

    @Override
    public List<String> selectRoleIdListByUserId(String userId) {
        return userRoleRoMapper.selectRoleIdListByUserId(userId);
    }

    @Override
    public List<Map<String, String>> selectRoleResourceListByRoleId(String roleId) {
        return roleRoMapper.selectRoleResourceListByRoleId(roleId);
    }

    @Override
    public Role selectOne(RoleBO roleBO) {
        Role role = new Role();
        BeanUtils.copyProperties(roleBO,role);
        return roleRoMapper.selectOne(role);
    }
}
