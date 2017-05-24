package com.abc12366.admin.model;
import java.io.Serializable;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class Province implements Serializable {

	/**省ID**/
	private String provinceId;

	/**省名称**/
	private String province;



	public void setProvinceId(String provinceId){
		this.provinceId = provinceId;
	}

	public String getProvinceId(){
		return this.provinceId;
	}

	public void setProvince(String province){
		this.province = province;
	}

	public String getProvince(){
		return this.province;
	}

}
