package com.abc12366.uc.model;

import java.io.Serializable;


/**
 * 发票退票
 **/
@SuppressWarnings("serial")
public class InvoiceBack implements Serializable {

    /****/
    private String id;

    /**
     * 用户ID
     **/
    private String userId;

    /**
     * 快递ID
     **/
    private String expressId;

    /**
     * 退货原因
     **/
    private String reason;

    /**
     * 备注
     **/
    private String remark;

    /**
     * 退回快递单号
     **/
    private String expressNo;

    /**
     * 退回快递公司
     **/
    private String expressComp;

    /**
     * 快递状态
     **/
    private String status;


    /****/
    private java.util.Date createTime;

    /****/
    private java.util.Date lastUpdate;

    private String invoiceId;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setExpressId(String expressId) {
        this.expressId = expressId;
    }

    public String getExpressId() {
        return this.expressId;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return this.reason;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setExpressComp(String expressComp) {
        this.expressComp = expressComp;
    }

    public String getExpressComp() {
        return this.expressComp;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    public java.util.Date getCreateTime() {
        return this.createTime;
    }

    public void setLastUpdate(java.util.Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public java.util.Date getLastUpdate() {
        return this.lastUpdate;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }
}
