package com.abc12366.uc.model;

import java.io.Serializable;


/**
 * 订单日志记录
 **/
@SuppressWarnings("serial")
public class OrderLog implements Serializable {

    /****/
    private String id;

    /**
     * 快递单号
     **/
    private String orderNo;

    /**
     * 动作
     **/
    private String action;

    /**
     * 创建时间
     **/
    private java.util.Date createTime;

    /**
     * 创建用户
     **/
    private String createUser;

    /**
     * 备注
     **/
    private String remark;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNo() {
        return this.orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public java.util.Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
