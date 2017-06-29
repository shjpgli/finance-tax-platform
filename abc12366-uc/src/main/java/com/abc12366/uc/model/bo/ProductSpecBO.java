package com.abc12366.uc.model.bo;
import java.io.Serializable;


/**
 * 
 * 产品规格表
 * 
 **/
@SuppressWarnings("serial")
public class ProductSpecBO implements Serializable {

	private String id;
	private String productId;
	private String specId;



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return this.productId;
	}

	public void setSpecId(String specId){
		this.specId = specId;
	}

	public String getSpecId(){
		return this.specId;
	}

}
