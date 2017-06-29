package com.abc12366.uc.model.bo;
import java.io.Serializable;


/**
 * 
 * 订单日志记录
 * 
 **/
@SuppressWarnings("serial")
public class OrderLogBO implements Serializable {

	private String id;
	private String orderNo;
	private String action;
	private java.util.Date createTime;
	private String createUser;
	private String remark;

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
