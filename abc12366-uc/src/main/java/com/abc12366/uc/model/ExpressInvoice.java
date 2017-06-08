package com.abc12366.uc.model;
import java.io.Serializable;


/**
 * 
 * 快递单与发票的关联信息
 * 
 **/
@SuppressWarnings("serial")
public class ExpressInvoice implements Serializable {

	/****/
	private String id;

	/**快递单ID**/
	private String expressId;

	/**发票ID**/
	private String invoiceId;

	/****/
	private java.util.Date createTime;

	/****/
	private String lastUpdate;



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setExpressId(String expressId){
		this.expressId = expressId;
	}

	public String getExpressId(){
		return this.expressId;
	}

	public void setInvoiceId(String invoiceId){
		this.invoiceId = invoiceId;
	}

	public String getInvoiceId(){
		return this.invoiceId;
	}

	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	public void setLastUpdate(String lastUpdate){
		this.lastUpdate = lastUpdate;
	}

	public String getLastUpdate(){
		return this.lastUpdate;
	}

}
