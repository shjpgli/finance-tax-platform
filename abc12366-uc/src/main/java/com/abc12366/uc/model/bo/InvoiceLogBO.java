package com.abc12366.uc.model.bo;
import java.io.Serializable;


/**
 *
 * 发票操作日志记录
 *
 **/
@SuppressWarnings("serial")
public class InvoiceLogBO implements Serializable {

	/****/
	private String id;

	/**发票Id**/
	private String invoiceId;

	/**动作**/
	private String action;

	/**创建时间**/
	private java.util.Date createTime;

	/**创建用户**/
	private String createUser;

	/**备注**/
	private String remark;



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setInvoiceId(String invoiceId){
		this.invoiceId = invoiceId;
	}

	public String getInvoiceId(){
		return this.invoiceId;
	}

	public void setAction(String action){
		this.action = action;
	}

	public String getAction(){
		return this.action;
	}

	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}

	public String getCreateUser(){
		return this.createUser;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return this.remark;
	}

}
