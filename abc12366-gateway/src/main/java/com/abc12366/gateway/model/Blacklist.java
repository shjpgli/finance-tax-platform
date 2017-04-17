package com.abc12366.gateway.model;

import java.util.Date;

/**
 * 黑名单实体
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-06 2:02 PM
 * @since 1.0.0
 */
public class Blacklist {

    private String id;
    private String ip;
    private String mark;
    private boolean status;
    private Date createDate;
    private Date modifyDate;

    private Blacklist(Builder builder) {
        setId(builder.id);
        setIp(builder.ip);
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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
        return "Blacklist{" +
                "id='" + id + '\'' +
                ", ip='" + ip + '\'' +
                ", mark='" + mark + '\'' +
                ", status=" + status +
                ", createDate=" + createDate +
                ", modifyDate=" + modifyDate +
                '}';
    }

    public static final class Builder {
        private String id;
        private String ip;
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

        public Builder ip(String val) {
            ip = val;
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

        public Blacklist build() {
            return new Blacklist(this);
        }
    }
}
