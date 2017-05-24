package com.abc12366.admin.model;
import java.io.Serializable;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class City implements Serializable {

	/**市ID**/
	private String cityId;

	/**市名称**/
	private String city;

	/**省ID**/
	private String provinceId;



	public void setCityId(String cityId){
		this.cityId = cityId;
	}

	public String getCityId(){
		return this.cityId;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return this.city;
	}


    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }
}
