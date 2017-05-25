package com.abc12366.gateway.model;
import java.io.Serializable;


/**
 * 
 * IP地址阀值设置
 * 
 **/
@SuppressWarnings("serial")
public class IpSetting implements Serializable {

	/**PK**/
	private String id;

	/**时间间隔设置**/
	private java.util.Date setTime;

	/**相同地址阀值**/
	private Integer sameThreshold;

	/**不同地址阀值**/
	private Integer distinctThreshold;

	/**禁用时间**/
	private java.util.Date disableTime;



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setSetTime(java.util.Date setTime){
		this.setTime = setTime;
	}

	public java.util.Date getSetTime(){
		return this.setTime;
	}

	public void setSameThreshold(Integer sameThreshold){
		this.sameThreshold = sameThreshold;
	}

	public Integer getSameThreshold(){
		return this.sameThreshold;
	}

	public void setDistinctThreshold(Integer distinctThreshold){
		this.distinctThreshold = distinctThreshold;
	}

	public Integer getDistinctThreshold(){
		return this.distinctThreshold;
	}

	public void setDisableTime(java.util.Date disableTime){
		this.disableTime = disableTime;
	}

	public java.util.Date getDisableTime(){
		return this.disableTime;
	}

}
