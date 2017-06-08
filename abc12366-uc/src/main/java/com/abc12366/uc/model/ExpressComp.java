package com.abc12366.uc.model;
import java.io.Serializable;


/**
 * 
 * 物流公司表
 * 
 **/
@SuppressWarnings("serial")
public class ExpressComp implements Serializable {

	/****/
	private String id;

	/**物流公司代号**/
	private String compCode;

	/**物流公司名称**/
	private String compName;

	/**物流公司URL**/
	private String compUrl;

	/**排序**/
	private Integer sort;

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

	public void setCompCode(String compCode){
		this.compCode = compCode;
	}

	public String getCompCode(){
		return this.compCode;
	}

	public void setCompName(String compName){
		this.compName = compName;
	}

	public String getCompName(){
		return this.compName;
	}

	public void setCompUrl(String compUrl){
		this.compUrl = compUrl;
	}

	public String getCompUrl(){
		return this.compUrl;
	}

	public void setSort(Integer sort){
		this.sort = sort;
	}

	public Integer getSort(){
		return this.sort;
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
