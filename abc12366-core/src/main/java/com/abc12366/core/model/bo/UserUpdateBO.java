package com.abc12366.core.model.bo;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-28 4:34 PM
 * @since 1.0.0
 */
public class UserUpdateBO {

    @NotEmpty(message = "ID不能为空")
    private Long id;

    private String phone;

    private boolean enabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "UserUpdateBO{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
