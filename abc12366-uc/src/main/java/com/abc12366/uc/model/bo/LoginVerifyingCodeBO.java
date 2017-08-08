package com.abc12366.uc.model.bo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Admin: liuguiyao<435720953@qq.com>
 * Date: 2017-06-01
 * Time: 10:40
 */
public class LoginVerifyingCodeBO implements Serializable {
    @NotEmpty
    private String type;
    @NotEmpty
    @Size(min = 11, max = 11)
    private String phone;
    @NotEmpty
    @Size(min = 6, max = 6)
    private String code;

    public LoginVerifyingCodeBO() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "LoginVerifyingCodeBO{" +
                "phone='" + phone + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
