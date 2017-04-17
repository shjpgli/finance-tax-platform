package com.abc12366.gateway.model.bo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-05 1:03 PM
 * @since 1.0.0
 */
public class AppBO {

    @NotEmpty(message = "应用名称不能为空")
    @Size(min = 6, max = 32, message = "应用名称必须6-32位")
    private String name;

    @NotEmpty(message = "密码不能为空")
    @Size(min = 8, max = 32, message = "应用名称必须6-32位")
    private String password;

    private String mark;

    private AppBO(Builder builder) {
        setName(builder.name);
        setPassword(builder.password);
        setMark(builder.mark);
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

    @Override
    public String toString() {
        return "AppBO{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", mark='" + mark + '\'' +
                '}';
    }


    public static final class Builder {
        private String name;
        private String password;
        private String mark;

        public Builder() {
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

        public AppBO build() {
            return new AppBO(this);
        }
    }
}
