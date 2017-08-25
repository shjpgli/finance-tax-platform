package com.abc12366.uc.model.bo;

import java.io.Serializable;
import java.util.List;


/**
 *
 *
 *
 **/
@SuppressWarnings("serial")
public class OrderProductBO implements Serializable {

    private String orderNo;
    private String productId;
    private Double sellingPrice;
    private Double unitPrice;
    private Integer num;
    private Double discount;
    private Double dealPrice;
    private String name;
    private String categoryId;
    private String category;
    private Double weight;
    private java.util.Date createTime;
    private java.util.Date lastUpdate;

    /**FK，商品ID**/
    private String goodsId;

    /**规格信息**/
    private String specInfo;

    /**展示图片URL**/
    private String imageUrl;

    /**是否可退货，0，可退，1，不可退**/
    private String isReturn;

    /**是否可换货，0：可换，1：不可换**/
    private String isExchange;

    /**
     * 商品类型，1.虚拟，2.实物，3.服务，4.会员服务，5.会员充值，6.学堂服务
     **/
    private String goodsType;

    private GoodsBO goodsBO;

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Double getSellingPrice() {
        return this.sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Double getUnitPrice() {
        return this.unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getNum() {
        return this.num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Double getDiscount() {
        return this.discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getDealPrice() {
        return this.dealPrice;
    }

    public void setDealPrice(Double dealPrice) {
        this.dealPrice = dealPrice;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getWeight() {
        return this.weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public java.util.Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    public java.util.Date getLastUpdate() {
        return this.lastUpdate;
    }

    public void setLastUpdate(java.util.Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getSpecInfo() {
        return specInfo;
    }

    public void setSpecInfo(String specInfo) {
        this.specInfo = specInfo;
    }

    public GoodsBO getGoodsBO() {
        return goodsBO;
    }

    public void setGoodsBO(GoodsBO goodsBO) {
        this.goodsBO = goodsBO;
    }

    public String getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(String isReturn) {
        this.isReturn = isReturn;
    }

    public String getIsExchange() {
        return isExchange;
    }

    public void setIsExchange(String isExchange) {
        this.isExchange = isExchange;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }
}
