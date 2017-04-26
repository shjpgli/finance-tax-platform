package com.abc12366.gateway.model.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-27 4:20 PM
 * @since 1.0.0
 */
public class TokenBO implements Serializable {

    private String id;
    private String name;
    private Date createTime;
    private Date expireTime;

    public TokenBO() {
    }

    public TokenBO(String id, String name, Date createTime, Date expireTime) {
        this.id = id;
        this.name = name;
        this.createTime = createTime;
        this.expireTime = expireTime;
    }

    private TokenBO(Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setCreateTime(builder.createTime);
        setExpireTime(builder.expireTime);
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public static final class Builder {
        private String id;
        private String name;
        private Date createTime;
        private Date expireTime;

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

        public Builder createTime(Date val) {
            createTime = val;
            return this;
        }

        public Builder expireTime(Date val) {
            expireTime = val;
            return this;
        }

        public TokenBO build() {
            return new TokenBO(this);
        }
    }
}
