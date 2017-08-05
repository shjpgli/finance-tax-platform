package com.abc12366.uc.model.bo;

import java.io.Serializable;


/**
 * 发票使用明细表
 **/
@SuppressWarnings("serial")
public class InvoiceDetailBO implements Serializable {

    private String id;
    private String invoiceNo;
    private String invoiceCode;
    private String invoiceName;
    private String property;
    private String status;
    private java.util.Date createTime;
    private java.util.Date lastUpdate;
    private String remark;
    /**
     * 发票仓库表外键
     **/
    private String invoiceRepoId;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvoiceNo() {
        return this.invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceCode() {
        return this.invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getInvoiceName() {
        return this.invoiceName;
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }

    public String getProperty() {
        return this.property;
    }

    public void setProperty(String property) {
        this.property = property;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getInvoiceRepoId() {
        return invoiceRepoId;
    }

    public void setInvoiceRepoId(String invoiceRepoId) {
        this.invoiceRepoId = invoiceRepoId;
    }
}
