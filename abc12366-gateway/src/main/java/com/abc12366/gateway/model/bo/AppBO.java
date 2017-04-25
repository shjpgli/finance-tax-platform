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
    @Size(min = 6, max = 32, message = "应用名称必须为6-32位")
    private String name;

    @NotEmpty(message = "密码不能为空")
    @Size(min = 8, max = 32, message = "应用密码必须为8-32位")
    private String password;

    @Size(max = 200, message = "描述信息限制在200个中文")
    private String remark;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "AppBO{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
