package com.abc12366.uc.model.bo;
import com.abc12366.uc.model.Product;

import java.io.Serializable;
import java.util.List;


/**
 * 
 * 产品表
 * 
 **/
@SuppressWarnings("serial")
public class GoodsBO implements Serializable {

	private String id;
	private String name;
	private String imageUrl;
	private String introduction;
	private String details;
	private String categoryId;
    private String categoryName;
	private Boolean status;
	private java.util.Date createTime;
	private java.util.Date lastUpdate;
	private Integer giftPoints;
	private Integer sort;
	private String unit;
	private String recommendType;
    private List<ProductBO> productBOList;
    private double totalStock;
    private double totalPrice;




	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setImageUrl(String imageUrl){
		this.imageUrl = imageUrl;
	}

	public String getImageUrl(){
		return this.imageUrl;
	}

	public void setIntroduction(String introduction){
		this.introduction = introduction;
	}

	public String getIntroduction(){
		return this.introduction;
	}

	public void setDetails(String details){
		this.details = details;
	}

	public String getDetails(){
		return this.details;
	}

	public void setCategoryId(String categoryId){
		this.categoryId = categoryId;
	}

	public String getCategoryId(){
		return this.categoryId;
	}

	public void setStatus(Boolean status){
		this.status = status;
	}

	public Boolean getStatus(){
		return this.status;
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

	public void setGiftPoints(Integer giftPoints){
		this.giftPoints = giftPoints;
	}

	public Integer getGiftPoints(){
		return this.giftPoints;
	}

	public void setSort(Integer sort){
		this.sort = sort;
	}

	public Integer getSort(){
		return this.sort;
	}

	public void setUnit(String unit){
		this.unit = unit;
	}

	public String getUnit(){
		return this.unit;
	}

	public void setRecommendType(String recommendType){
		this.recommendType = recommendType;
	}

	public String getRecommendType(){
		return this.recommendType;
	}

    public List<ProductBO> getProductBOList() {
        return productBOList;
    }

    public void setProductBOList(List<ProductBO> productBOList) {
        this.productBOList = productBOList;
    }

    public double getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(double totalStock) {
        this.totalStock = totalStock;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
