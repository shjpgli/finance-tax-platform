package com.abc12366.uc.model.bo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Admin: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-23
 * Time: 9:55
 */
public class VipPrivilegeInsertAndUpdateBO {

    @NotEmpty
    @Size(max = 100)
    private String name;//特权名称
    @Size( max = 100)
    private String css;//class
    @Size(max = 1000)
    private String defaults;//缺省
    @NotNull
    private Boolean status;//数据状态

    public VipPrivilegeInsertAndUpdateBO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public String getDefaults() {
        return defaults;
    }

    public void setDefaults(String defaults) {
        this.defaults = defaults;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public VipPrivilegeInsertAndUpdateBO(String name, String css, String defaults, Boolean status) {
        this.name = name;
        this.css = css;
        this.defaults = defaults;
        this.status = status;
    }

    @Override
    public String toString() {
        return "VipPrivilegeInsertAndUpdateBO [name=" + name + ", css=" + css + ", defaults=" + defaults + ", status="
                + status + "]";
    }


}
