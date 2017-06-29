package com.abc12366.uc.model.bo;
import java.io.Serializable;


/**
 * 
 * 发票使用明细表
 * 
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
