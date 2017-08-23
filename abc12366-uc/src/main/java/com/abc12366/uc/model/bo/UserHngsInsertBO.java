package com.abc12366.uc.model.bo;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Admin: liuguiyao<435720953@qq.com>
 * Date: 2017-07-26
 * Time: 17:02
 */
public class UserHngsInsertBO {
    @NotEmpty
    private String bsy;
    @NotEmpty
    private String password;
    @NotEmpty
    private String role;

    private String userId;

    public UserHngsInsertBO() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBsy() {
        return bsy;
    }

    public void setBsy(String bsy) {
        this.bsy = bsy;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
