package com.abc12366.uc.model.bo;
import java.io.Serializable;


/**
 *
 * 产品参数
 *
 **/
@SuppressWarnings("serial")
public class ProductBO implements Serializable {

    private String goodsId;
    private String dictId;
    private Integer stock;
    private double marketPrice;
    private double sellingPrice;
    private double costPrice;
    private double finalPrice;
    private double discount;
    private String uvipLevel;
    private double weight;
    private java.util.Date createTime;
    private java.util.Date lastUpdate;



    public void setGoodsId(String goodsId){
        this.goodsId = goodsId;
    }

    public String getGoodsId(){
        return this.goodsId;
    }

    public void setDictId(String dictId){
        this.dictId = dictId;
    }

    public String getDictId(){
        return this.dictId;
    }

    public void setStock(Integer stock){
        this.stock = stock;
    }

    public Integer getStock(){
        return this.stock;
    }

    public void setMarketPrice(double marketPrice){
        this.marketPrice = marketPrice;
    }

    public double getMarketPrice(){
        return this.marketPrice;
    }

    public void setSellingPrice(double sellingPrice){
        this.sellingPrice = sellingPrice;
    }

    public double getSellingPrice(){
        return this.sellingPrice;
    }

    public void setCostPrice(double costPrice){
        this.costPrice = costPrice;
    }

    public double getCostPrice(){
        return this.costPrice;
    }

    public void setFinalPrice(double finalPrice){
        this.finalPrice = finalPrice;
    }

    public double getFinalPrice(){
        return this.finalPrice;
    }

    public void setDiscount(double discount){
        this.discount = discount;
    }

    public double getDiscount(){
        return this.discount;
    }

    public void setUvipLevel(String uvipLevel){
        this.uvipLevel = uvipLevel;
    }

    public String getUvipLevel(){
        return this.uvipLevel;
    }

    public void setWeight(double weight){
        this.weight = weight;
    }

    public double getWeight(){
        return this.weight;
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
