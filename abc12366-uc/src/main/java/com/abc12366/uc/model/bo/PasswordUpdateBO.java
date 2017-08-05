package com.abc12366.uc.model.bo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Admin: liuguiyao<435720953@qq.com>
 * Date: 2017-07-19
 * Time: 10:15
 */
public class PasswordUpdateBO {

    @NotEmpty
    @Pattern(regexp = "^\\d{11}$")
    @Size(min = 11, max = 11)
    private String phone;
    @NotEmpty
    @Size(min = 8, max = 32)
    private String password;

    public PasswordUpdateBO() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
