package com.abc12366.uc.model.bo;

import java.util.Date;

/**
 * @author liuguiyao<435720953@qq.com.com>
 * @create 2017-05-18 10:18 PM
 * @since 2.0.0
 */
public class UserReturnBO {
    private String id;
    private String username;
    private String phone;
    private Date createTime;
    private Date lastUpdate;

    public UserReturnBO() {
    }

    public UserReturnBO(String id, String username, String phone, Date createTime, Date lastUpdate) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.createTime = createTime;
        this.lastUpdate = lastUpdate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "UserReturnBO{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", createTime=" + createTime +
                ", lastUpdate=" + lastUpdate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserReturnBO that = (UserReturnBO) o;

        if (!id.equals(that.id)) return false;
        if (!username.equals(that.username)) return false;
        if (!phone.equals(that.phone)) return false;
        if (!createTime.equals(that.createTime)) return false;
        return lastUpdate.equals(that.lastUpdate);

    }
}
