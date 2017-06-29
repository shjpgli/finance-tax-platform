package com.abc12366.uc.model;
import java.io.Serializable;


/**
 * 
 * 订单产品规格
 * 
 **/
@SuppressWarnings("serial")
public class OrderProductSpec implements Serializable {

	/**FK**/
	private String orderId;

	/**FK**/
	private String productId;

	/**FK**/
	private String specId;



	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return this.orderId;
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
