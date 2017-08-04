package com.abc12366.uc.model.bo;

import com.abc12366.uc.model.InvoiceDetail;

import java.io.Serializable;
import java.util.List;


/**
 * 发票仓库
 **/
@SuppressWarnings("serial")
public class InvoiceRepoBO implements Serializable {

    /****/
    private String id;
    private String invoiceCode;
    private String invoiceName;
    private String property;
    private String invoiceSection;
    private java.util.Date createTime;
    private java.util.Date lastUpdate;
    /**插入成功多少条数**/
    private Integer successNum;

    private Integer repoNum;

    private List<InvoiceDetail> invoiceDetailList;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getInvoiceCode() {
        return this.invoiceCode;
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }

    public String getInvoiceName() {
        return this.invoiceName;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getProperty() {
        return this.property;
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

    public Integer getSuccessNum() {
        return successNum;
    }

    public void setSuccessNum(Integer successNum) {
        this.successNum = successNum;
    }

    public List<InvoiceDetail> getInvoiceDetailList() {
        return invoiceDetailList;
    }

    public void setInvoiceDetailList(List<InvoiceDetail> invoiceDetailList) {
        this.invoiceDetailList = invoiceDetailList;
    }

    public String getInvoiceSection() {
        return invoiceSection;
    }

    public void setInvoiceSection(String invoiceSection) {
        this.invoiceSection = invoiceSection;
    }

    public Integer getRepoNum() {
        return repoNum;
    }

    public void setRepoNum(Integer repoNum) {
        this.repoNum = repoNum;
    }
}
