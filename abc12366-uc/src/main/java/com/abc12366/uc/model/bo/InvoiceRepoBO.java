package com.abc12366.uc.model.bo;
import com.abc12366.uc.model.InvoiceRepo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * 发票仓库
 * 
 **/
@SuppressWarnings("serial")
public class InvoiceRepoBO implements Serializable {

	/****/
	private String id;
	private String invoiceNo;
	private String invoiceCode;
	private String invoiceName;
	private String property;
	private String status;
	private java.util.Date createTime;
	private java.util.Date lastUpdate;
    private String startNo;
    private String endNo;
    private List<InvoiceRepo> failList = new ArrayList<InvoiceRepo>();
    private Integer successNum;



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setInvoiceNo(String invoiceNo){
		this.invoiceNo = invoiceNo;
	}

	public String getInvoiceNo(){
		return this.invoiceNo;
	}

	public void setInvoiceCode(String invoiceCode){
		this.invoiceCode = invoiceCode;
	}

	public String getInvoiceCode(){
		return this.invoiceCode;
	}

	public void setInvoiceName(String invoiceName){
		this.invoiceName = invoiceName;
	}

	public String getInvoiceName(){
		return this.invoiceName;
	}

	public void setProperty(String property){
		this.property = property;
	}

	public String getProperty(){
		return this.property;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return this.status;
	}

	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	public void setLastUpdate(java.util.Date lastUpdate){
		this.lastUpdate = lastUpdate;
	}

	public java.util.Date getLastUpdate(){
		return this.lastUpdate;
	}

    public Integer getSuccessNum() {
        return successNum;
    }

    public void setSuccessNum(Integer successNum) {
        this.successNum = successNum;
    }

    public String getStartNo() {
        return startNo;
    }

    public void setStartNo(String startNo) {
        this.startNo = startNo;
    }

    public String getEndNo() {
        return endNo;
    }

    public void setEndNo(String endNo) {
        this.endNo = endNo;
    }

    public List<InvoiceRepo> getFailList() {
        return failList;
    }

    public void setFailList(List<InvoiceRepo> failList) {
        this.failList = failList;
    }
}
