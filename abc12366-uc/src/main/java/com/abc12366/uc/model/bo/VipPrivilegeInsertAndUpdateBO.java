package com.abc12366.uc.model.bo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    private String icon;//icon
    @Size(max = 1000)
    private String description;//描述
    @NotNull
    private Boolean status;//数据状态
    private Integer sort;//排序 int(4)

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
    public VipPrivilegeInsertAndUpdateBO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public VipPrivilegeInsertAndUpdateBO(String name, String icon, String description, Boolean status) {
        this.name = name;
        this.icon = icon;
        this.description = description;
        this.status = status;
    }

    @Override
    public String toString() {
        return "VipPrivilegeInsertAndUpdateBO [name=" + name + ", icon=" + icon + ", description=" + description + ", status="
                + status + "]";
    }


}
