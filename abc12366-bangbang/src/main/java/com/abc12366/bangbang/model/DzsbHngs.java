package com.abc12366.bangbang.model;
import java.io.Serializable;


/**
 * 
 * VIEW
 * 
 **/
@SuppressWarnings("serial")
public class DzsbHngs implements Serializable {

	/****/
	private String id;

	/**纳税人识别号**/
	private String nsrsbh;

	/**纳税人名称**/
	private String nsrmc;

	/**税务机关代码**/
	private String swjgDm;

	/**税务机关名称**/
	private String swjgMc;



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setNsrsbh(String nsrsbh){
		this.nsrsbh = nsrsbh;
	}

	public String getNsrsbh(){
		return this.nsrsbh;
	}

	public void setNsrmc(String nsrmc){
		this.nsrmc = nsrmc;
	}

	public String getNsrmc(){
		return this.nsrmc;
	}

	public void setSwjgDm(String swjgDm){
		this.swjgDm = swjgDm;
	}

	public String getSwjgDm(){
		return this.swjgDm;
	}

	public void setSwjgMc(String swjgMc){
		this.swjgMc = swjgMc;
	}

	public String getSwjgMc(){
		return this.swjgMc;
	}

}
