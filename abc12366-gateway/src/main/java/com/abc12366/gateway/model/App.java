package com.abc12366.gateway.model;

import java.util.Date;

/**
 * 应用信息-每个访问应用需要注册才能访问
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-05 9:39 AM
 * @since 1.0.0
 */
public class App {

    // 主键UUID
    private String id;

    // 应用名称
    private String name;

    // 密码
    private String password;

    // 描述信息
    private String mark;

    private boolean status;

    private Date createDate;

    private Date modifyDate;

    public App() {
    }

    private App(Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setPassword(builder.password);
        setMark(builder.mark);
        setStatus(builder.status);
        setCreateDate(builder.createDate);
        setModifyDate(builder.modifyDate);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "App{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", mark='" + mark + '\'' +
                ", status=" + status +
                ", createDate=" + createDate +
                ", modifyDate=" + modifyDate +
                '}';
    }


    public static final class Builder {
        private String id;
        private String name;
        private String password;
        private String mark;
        private boolean status;
        private Date createDate;
        private Date modifyDate;

        public Builder() {
        }

        public Builder id(String val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder password(String val) {
            password = val;
            return this;
        }

        public Builder mark(String val) {
            mark = val;
            return this;
        }

        public Builder status(boolean val) {
            status = val;
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

        public App build() {
            return new App(this);
        }
    }
}
