package com.abc12366.uc.model.bo;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-28 4:34 PM
 * @since 1.0.0
 */
public class UserUpdateBO {

    @NotEmpty(message = "ID不能为空")
    private String id;

    private String phone;

    private boolean status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public UserUpdateBO() {

    }

    @Override
    public String toString() {
        return "UserUpdateBO{" +
                "id='" + id + '\'' +
                ", phone='" + phone + '\'' +
                ", status=" + status +
                '}';
    }
}
