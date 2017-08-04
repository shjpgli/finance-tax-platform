package com.abc12366.uc.model;
import java.io.Serializable;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class Area implements Serializable {

	/**地区ID**/
	private String areaId;

	/**地区名称**/
	private String area;

	/**市ID**/
	private String cityId;



	public void setAreaId(String areaId){
		this.areaId = areaId;
	}

	public String getAreaId(){
		return this.areaId;
	}

	public void setArea(String area){
		this.area = area;
	}

	public String getArea(){
		return this.area;
	}

	public void setCityId(String cityId){
		this.cityId = cityId;
	}

	public String getCityId(){
		return this.cityId;
	}

}
