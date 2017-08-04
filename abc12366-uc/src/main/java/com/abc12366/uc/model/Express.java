package com.abc12366.uc.model;

import java.io.Serializable;


/**
 * 发票快递单
 **/
@SuppressWarnings("serial")
public class Express implements Serializable {

    /**
     * ID
     **/
    private String id;

    /**
     * 用户订单号
     **/
    private String userOrderNo;

    /**
     * 用户ID
     **/
    private String userId;

    /**
     * 快递单号
     **/
    private String expressNo;

    /**
     * 快递公司ID
     **/
    private String deliveryId;

    /**
     * 快递状态
     **/
    private String status;

    /****/
    private java.util.Date createTime;

    /****/
    private java.util.Date lastUpdate;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserOrderNo() {
        return this.userOrderNo;
    }

    public void setUserOrderNo(String userOrderNo) {
        this.userOrderNo = userOrderNo;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExpressNo() {
        return this.expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getDeliveryId() {
        return this.deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public java.util.Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    public java.util.Date getLastUpdate() {
        return this.lastUpdate;
    }

    public void setLastUpdate(java.util.Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

}
