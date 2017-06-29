package com.abc12366.uc.model;
import java.io.Serializable;


/**
 * 
 * 用户退单表
 * 
 **/
@SuppressWarnings("serial")
public class OrderBack implements Serializable {

	/**PK**/
	private String id;

	/**FK**/
	private String userId;

	/**订单号**/
	private String orderNo;

	/**退单原因**/
	private String reason;

	/**备注**/
	private String remark;

	/**快递单号**/
	private String expressNo;

	/**快递公司**/
	private String expressComp;

	/**退单状态**/
	private String status;

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

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setOrderNo(String orderNo){
		this.orderNo = orderNo;
	}

	public String getOrderNo(){
		return this.orderNo;
	}

	public void setReason(String reason){
		this.reason = reason;
	}

	public String getReason(){
		return this.reason;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return this.remark;
	}

	public void setExpressNo(String expressNo){
		this.expressNo = expressNo;
	}

	public String getExpressNo(){
		return this.expressNo;
	}

	public void setExpressComp(String expressComp){
		this.expressComp = expressComp;
	}

	public String getExpressComp(){
		return this.expressComp;
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

}
