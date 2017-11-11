package com.abc12366.uc.model.invoice.bo;

import java.io.Serializable;


/**
 * 发票退票
 **/
@SuppressWarnings("serial")
public class InvoiceBackBO implements Serializable {

    private String id;
    private String userId;
    private String expressId;
    private String reason;
    private String remark;
    private String expressNo;
    private String expressComp;
    private String status;
    private java.util.Date createTime;
    private java.util.Date lastUpdate;

    /**
     * 操作人员ID
     **/
    private String operatorUser;

    private String sendExpressNo;
    private String invoiceId;
    private InvoiceBO invoiceBO;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExpressId() {
        return this.expressId;
    }

    public void setExpressId(String expressId) {
        this.expressId = expressId;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExpressComp() {
        return this.expressComp;
    }

    public void setExpressComp(String expressComp) {
        this.expressComp = expressComp;
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

    public InvoiceBO getInvoiceBO() {
        return invoiceBO;
    }

    public void setInvoiceBO(InvoiceBO invoiceBO) {
        this.invoiceBO = invoiceBO;
    }

    public String getSendExpressNo() {
        return sendExpressNo;
    }

    public void setSendExpressNo(String sendExpressNo) {
        this.sendExpressNo = sendExpressNo;
    }

    public String getOperatorUser() {
        return operatorUser;
    }

    public void setOperatorUser(String operatorUser) {
        this.operatorUser = operatorUser;
    }
}
