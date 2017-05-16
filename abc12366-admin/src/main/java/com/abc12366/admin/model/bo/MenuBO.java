package com.abc12366.admin.model.bo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author liuguiyao
 * @create 2017-04-27 10:08 AM
 * @since 1.0.0
 */
public class MenuBO {

    private String menuId;
    @NotEmpty(message = "menuName不能为空")
    @Size(min = 2, max = 32, message = "菜单名称长度2-32位")
    private String menuName;
    //@NotEmpty(message = "menuUrl不能为空")
    @Size(min = 0, max = 128, message = "联系方式长度0-128位")
    private String menuUrl;
    private String parentId;
    @Size(min = 0, max = 500, message = "授权长度0-500位")
    private String perms;
    private String type;
    private String icon;
    private int sort;
    @NotNull(message = "status不能为空")
    private boolean status;
    @Size(min = 0, max = 100, message = "备注长度0-20位")
    private String remark;

    public MenuBO() {
    }

    public MenuBO(String menuId, String menuName, String menuUrl, String parentId, String perms, String type, String icon, int sort, boolean status, String remark) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuUrl = menuUrl;
        this.parentId = parentId;
        this.perms = perms;
        this.type = type;
        this.icon = icon;
        this.sort = sort;
        this.status = status;
        this.remark = remark;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "MenuBO{" +
                "menuId='" + menuId + '\'' +
                ", menuName='" + menuName + '\'' +
                ", menuUrl='" + menuUrl + '\'' +
                ", parentId='" + parentId + '\'' +
                ", perms='" + perms + '\'' +
                ", type='" + type + '\'' +
                ", icon='" + icon + '\'' +
                ", sort=" + sort +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                '}';
    }
}
