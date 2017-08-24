package com.abc12366.uc.model;

import java.io.Serializable;


/**
 *
 *
 *
 **/
@SuppressWarnings("serial")
public class OrderProduct implements Serializable {

    /**
     * FK
     **/
    private String orderNo;

    /**
     * FK
     **/
    private String productId;

    /**
     * 销售价
     **/
    private Double sellingPrice;

    /**
     * 单价
     **/
    private Double unitPrice;

    /**
     * 购买数量
     **/
    private Integer num;

    /**
     * 折扣
     **/
    private Double discount;

    /**
     * 成交价格
     **/
    private Double dealPrice;

    /**
     * 商品名称
     **/
    private String name;

    /**
     * 商品分类
     **/
    private String categoryId;

    /**
     * 分类名称
     **/
    private String category;

    /**
     * 商品重量，单位：克
     **/
    private Double weight;

    /****/
    private java.util.Date createTime;

    /****/
    private java.util.Date lastUpdate;

    /**FK，商品ID**/
    private String goodsId;

    /**规格信息**/
    private String specInfo;

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
}
