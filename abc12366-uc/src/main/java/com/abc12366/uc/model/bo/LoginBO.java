package com.abc12366.uc.model.bo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-27 4:18 PM
 * @since 1.0.0
 */
public class LoginBO implements Serializable {

    @NotEmpty
    @Size(min = 6, max = 32)
    private String usernameOrPhone;

    @NotEmpty
    private String password;

    public LoginBO() {
    }

    public LoginBO(String usernameOrPhone, String password) {
        this.usernameOrPhone = usernameOrPhone;
        this.password = password;
    }

    public String getUsernameOrPhone() {
        return usernameOrPhone;
    }

    public void setUsernameOrPhone(String usernameOrPhone) {
        this.usernameOrPhone = usernameOrPhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginBO{" +
                "usernameOrPhone='" + usernameOrPhone + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}