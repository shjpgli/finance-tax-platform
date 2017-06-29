package com.abc12366.uc.model.bo;
import java.io.Serializable;


/**
 *
 * 订单与发票关联表
 *
 **/
@SuppressWarnings("serial")
public class OrderInvoiceBO implements Serializable {

	private String id;
	private String orderNo;
	private String invoiceId;
	private java.util.Date createTime;
	private java.util.Date lastUpdate;



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setOrderNo(String orderNo){
		this.orderNo = orderNo;
	}

	public String getOrderNo(){
		return this.orderNo;
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

	public void setLastUpdate(java.util.Date lastUpdate){
		this.lastUpdate = lastUpdate;
	}

	public java.util.Date getLastUpdate(){
		return this.lastUpdate;
	}

}
