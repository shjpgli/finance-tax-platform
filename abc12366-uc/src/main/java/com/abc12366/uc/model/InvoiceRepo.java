package com.abc12366.uc.model;
import java.io.Serializable;


/**
 *
 * 发票仓库
 *
 **/
@SuppressWarnings("serial")
public class InvoiceRepo implements Serializable {

	/****/
	private String id;

	/**发票代码**/
	private String invoiceCode;

	/**发票名称**/
	private String invoiceName;

	/**发票性质：1.纸质发票 2.电子发票**/
	private String property;

	/**发票段**/
	private String invoiceSection;

	/****/
	private java.util.Date createTime;

	/****/
	private java.util.Date lastUpdate;


	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
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

	public String getInvoiceSection() {
		return invoiceSection;
	}

	public void setInvoiceSection(String invoiceSection) {
		this.invoiceSection = invoiceSection;
	}
}
