package com.abc12366.core.model.bo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-27 4:18 PM
 * @since 1.0.0
 */
public class RegisterBO implements Serializable {

    @NotEmpty(message = "用户名不能为空")
    @Size(min = 6, max = 32, message = "用户名必须6-32位")
    private String username;

    @NotEmpty(message = "手机号不能为空")
    @Size(min = 11, max= 11, message = "手机号必须为11为数字")
    private String phone;

    @NotEmpty(message = "密码不能为空")
    @Size(min = 8, max = 32, message = "密码必须8-32位")
    private String password;

    public RegisterBO() {
    }

    public RegisterBO(String username, String phone, String password) {
        this.username = username;
        this.phone = phone;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    @Override
    public String toString() {
        return "RegisterBO{" +
                "username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
