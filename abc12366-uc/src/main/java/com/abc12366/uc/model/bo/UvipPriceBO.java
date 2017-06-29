package com.abc12366.uc.model.bo;
import java.io.Serializable;


/**
 * 
 * 会员价格表
 * 
 **/
@SuppressWarnings("serial")
public class UvipPriceBO implements Serializable {

	private String id;
	private String productId;
	private String vipLevel;
	private Double discount;
	private Double tradePrice;
	private Integer giftPoints;
	private java.util.Date createTime;
	private java.util.Date lastUpdate;



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

	public void setVipLevel(String vipLevel){
		this.vipLevel = vipLevel;
	}

	public String getVipLevel(){
		return this.vipLevel;
	}

	public void setDiscount(Double discount){
		this.discount = discount;
	}

	public Double getDiscount(){
		return this.discount;
	}

	public void setTradePrice(Double tradePrice){
		this.tradePrice = tradePrice;
	}

	public Double getTradePrice(){
		return this.tradePrice;
	}

	public void setGiftPoints(Integer giftPoints){
		this.giftPoints = giftPoints;
	}

	public Integer getGiftPoints(){
		return this.giftPoints;
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
