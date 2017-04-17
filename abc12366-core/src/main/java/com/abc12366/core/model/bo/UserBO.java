package com.abc12366.core.model.bo;

import java.util.Date;
import java.util.List;

/**
 * 用户实体类
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-20 4:14 PM
 * @since 1.0.0
 */
public class UserBO {

    private Long id;

    private String username;

//    private String password;

    private String phone;

    private boolean enabled;

    private Date lastPasswordResetDate;

    private Date createDate;

    private Date modifyDate;

    private List<String> roles;

    public UserBO() {
    }

    public UserBO(Long id, String username, String phone, boolean enabled,
                  Date lastPasswordResetDate, Date createDate, Date modifyDate, List<String> roles) {
        this.id = id;
        this.username = username;
//        this.password = password;
        this.phone = phone;
        this.enabled = enabled;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.roles = roles;
    }

    private UserBO(Builder builder) {
        setId(builder.id);
        setUsername(builder.username);
//        setPassword(builder.password);
        setPhone(builder.phone);
        setEnabled(builder.enabled);
        setLastPasswordResetDate(builder.lastPasswordResetDate);
        setCreateDate(builder.createDate);
        setModifyDate(builder.modifyDate);
        setRoles(builder.roles);
    }

    public UserBO(UserBO user) {
        this.id= user.getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public String getPassword() {
//        return password;
//    }

//    public void setPassword(String password) {
//        this.password = password;
//    }

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

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
//                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", enabled=" + enabled +
                ", lastPasswordResetDate=" + lastPasswordResetDate +
                ", createDate=" + createDate +
                ", modifyDate=" + modifyDate +
                ", roles=" + roles +
                '}';
    }

    public static final class Builder {
        private Long id;
        private String username;
//        private String password;
        private String phone;
        private boolean enabled;
        private Date lastPasswordResetDate;
        private Date createDate;
        private Date modifyDate;
        private List<String> roles;

        public Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder username(String val) {
            username = val;
            return this;
        }

//        public Builder password(String val) {
//            password = val;
//            return this;
//        }

        public Builder phone(String val) {
            phone = val;
            return this;
        }

        public Builder enabled(boolean val) {
            enabled = val;
            return this;
        }

        public Builder lastPasswordResetDate(Date val) {
            lastPasswordResetDate = val;
            return this;
        }

        public Builder createDate(Date val) {
            createDate = val;
            return this;
        }

        public Builder modifyDate(Date val) {
            modifyDate = val;
            return this;
        }

        public Builder roles(List<String> val) {
            roles = val;
            return this;
        }

        public UserBO build() {
            return new UserBO(this);
        }
    }
}
