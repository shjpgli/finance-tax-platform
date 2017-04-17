package com.abc12366.core.model.bo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-27 4:18 PM
 * @since 1.0.0
 */
public class LoginBO implements Serializable {

    @NotEmpty(message = "用户名不能为空")
    @Size(min = 6, max = 32, message = "用户名必须6-32位")
    private String usernameOrPhone;

    @NotEmpty(message = "密码不能为空")
    @Size(min = 8, max = 32, message = "密码必须8-32位")
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