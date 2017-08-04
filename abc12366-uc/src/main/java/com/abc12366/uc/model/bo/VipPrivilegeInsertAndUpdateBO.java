package com.abc12366.uc.model.bo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Admin: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-23
 * Time: 9:55
 */
public class VipPrivilegeInsertAndUpdateBO {
    @Size(max = 100)
    private String name;
    @Size(min = 2, max = 5)
    private String level;
    @Size(max = 1000)
    private String remark;
    @NotNull
    private Boolean status;

    public VipPrivilegeInsertAndUpdateBO() {
    }

    public VipPrivilegeInsertAndUpdateBO(String name, String level, String remark, Boolean status) {
        this.name = name;
        this.level = level;
        this.remark = remark;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "VipPrivilegeInsertAndUpdateBO{" +
                "name='" + name + '\'' +
                ", level='" + level + '\'' +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                '}';
    }
}
