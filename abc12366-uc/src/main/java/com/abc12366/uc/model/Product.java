package com.abc12366.uc.model;
import java.io.Serializable;


/**
 *
 * 产品参数
 *
 **/
@SuppressWarnings("serial")
public class Product implements Serializable {

    /**产品ID**/
    private String goodsId;

    /**字典ID主键**/
    private String dictId;

    /**库存**/
    private Integer stock;

    /**市场价**/
    private double marketPrice;

    /**销售价**/
    private double sellingPrice;

    /**成本价**/
    private double costPrice;

    /**最终价格**/
    private double finalPrice;

    /**会员折扣**/
    private double discount;

    /**会员等级**/
    private String uvipLevel;

    /**重量：克**/
    private double weight;

    /****/
    private java.util.Date createTime;

    /****/
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
