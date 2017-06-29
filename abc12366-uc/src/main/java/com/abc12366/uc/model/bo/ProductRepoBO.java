package com.abc12366.uc.model.bo;
import java.io.Serializable;


/**
 * 
 * 产品库存表
 * 
 **/
@SuppressWarnings("serial")
public class ProductRepoBO implements Serializable {

	private String id;
	private String goodsId;
	private String productId;
	private Integer income;
	private Integer outcome;
	private Integer stock;
	private String remark;
	private java.util.Date createTime;
	private java.util.Date lastUpdate;



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setGoodsId(String goodsId){
		this.goodsId = goodsId;
	}

	public String getGoodsId(){
		return this.goodsId;
	}

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return this.productId;
	}

	public void setIncome(Integer income){
		this.income = income;
	}

	public Integer getIncome(){
		return this.income;
	}

	public void setOutcome(Integer outcome){
		this.outcome = outcome;
	}

	public Integer getOutcome(){
		return this.outcome;
	}

	public void setStock(Integer stock){
		this.stock = stock;
	}

	public Integer getStock(){
		return this.stock;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return this.remark;
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
