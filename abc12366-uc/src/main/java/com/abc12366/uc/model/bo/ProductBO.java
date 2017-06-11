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
    private Double marketPrice;
    private Double sellingPrice;
    private Double costPrice;
    private Double finalPrice;
    private Double discount;
    private String uvipLevel;
    private Double weight;
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

    public void setMarketPrice(Double marketPrice){
        this.marketPrice = marketPrice;
    }

    public Double getMarketPrice(){
        return this.marketPrice;
    }

    public void setSellingPrice(Double sellingPrice){
        this.sellingPrice = sellingPrice;
    }

    public Double getSellingPrice(){
        return this.sellingPrice;
    }

    public void setCostPrice(Double costPrice){
        this.costPrice = costPrice;
    }

    public Double getCostPrice(){
        return this.costPrice;
    }

    public void setFinalPrice(Double finalPrice){
        this.finalPrice = finalPrice;
    }

    public Double getFinalPrice(){
        return this.finalPrice;
    }

    public void setDiscount(Double discount){
        this.discount = discount;
    }

    public Double getDiscount(){
        return this.discount;
    }

    public void setUvipLevel(String uvipLevel){
        this.uvipLevel = uvipLevel;
    }

    public String getUvipLevel(){
        return this.uvipLevel;
    }

    public void setWeight(Double weight){
        this.weight = weight;
    }

    public Double getWeight(){
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
