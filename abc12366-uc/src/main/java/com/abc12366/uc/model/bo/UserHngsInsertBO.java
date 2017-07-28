package com.abc12366.uc.model.bo;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-26
 * Time: 17:02
 */
public class UserHngsInsertBO {
    @NotEmpty
    private String bsh;
    @NotEmpty
    private String password;
    @NotEmpty
    private String role;

    public UserHngsInsertBO() {
    }

    public String getBsh() {
        return bsh;
    }

    public void setBsh(String bsh) {
        this.bsh = bsh;
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
