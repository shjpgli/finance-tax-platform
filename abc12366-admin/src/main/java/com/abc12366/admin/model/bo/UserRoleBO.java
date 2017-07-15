package com.abc12366.admin.model.bo;

import com.abc12366.admin.model.Role;

import java.io.Serializable;


/**
 * 系统用户角色关联表
 **/
@SuppressWarnings("serial")
public class UserRoleBO implements Serializable {

    /**
     * ID
     **/
    private String id;

    /**
     * 用户ID
     **/
    private String userId;

    /**
     * 角色ID
     **/
    private String roleId;

    private Role role;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleId() {
        return this.roleId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
