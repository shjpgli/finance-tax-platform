package com.abc12366.cms.model;
import java.io.Serializable;


/**
 * 
 * 来源
 * 
 **/
@SuppressWarnings("serial")
public class Origin implements Serializable {

	/****/
	private String originId;

	/**来源名称**/
	private String originName;

	/**来源文章总数**/
	private Integer refCount;



	public void setOriginId(String originId){
		this.originId = originId;
	}

	public String getOriginId(){
		return this.originId;
	}

	public void setOriginName(String originName){
		this.originName = originName;
	}

	public String getOriginName(){
		return this.originName;
	}

	public void setRefCount(Integer refCount){
		this.refCount = refCount;
	}

	public Integer getRefCount(){
		return this.refCount;
	}

}
