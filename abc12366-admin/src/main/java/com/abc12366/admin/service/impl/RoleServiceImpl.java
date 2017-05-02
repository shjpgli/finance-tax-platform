package com.abc12366.admin.service.impl;

import com.abc12366.admin.model.Role;
import com.abc12366.admin.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {

    private static Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);


    @Override
    public List<Role> selectRole() {
        return null;
    }

    @Override
    public void addRole(Role role) {

    }

    @Override
    public void deleteRoleById(String id) {

    }

    @Override
    public Role selectRoleById(String id) {
        return null;
    }

    @Override
    public void updateRole(Role role) {

    }

    @Override
    public List<Long> selectResourceIdListByRoleId(String id) {
        return null;
    }

    @Override
    public void updateRoleResource(String id, String resourceIds) {

    }

    @Override
    public List<String> selectRoleIdListByUserId(String userId) {
        return null;
    }

    @Override
    public List<Map<Long, String>> selectRoleResourceListByRoleId(String roleId) {
        return null;
    }
}
