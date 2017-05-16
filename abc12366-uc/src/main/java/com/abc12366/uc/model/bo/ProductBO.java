package com.abc12366.uc.model.bo;
import java.io.Serializable;


/**
 *
 *
 *
 **/
@SuppressWarnings("serial")
public class ProductBO implements Serializable {

    /****/
    private String id;

    /**产品名称**/
    private String name;

    /**产品介绍**/
    private String introduction;

    /**金额**/
    private double amount;

    /**所需积分**/
    private Integer points;

    /**产品分类**/
    private String category;

    /**所需会员等级**/
    private String vipLevel;

    /**产品详情**/
    private String details;

    /**产品总量**/
    private Integer total;

    /**产品状态**/
    private String status;

    /****/
    private java.util.Date createTime;

    /****/
    private java.util.Date lastUpdate;

    /**展示图片URL**/
    private String imageUrl;



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

    public void setIntroduction(String introduction){
        this.introduction = introduction;
    }

    public String getIntroduction(){
        return this.introduction;
    }

    public void setAmount(double amount){
        this.amount = amount;
    }

    public double getAmount(){
        return this.amount;
    }

    public void setPoints(Integer points){
        this.points = points;
    }

    public Integer getPoints(){
        return this.points;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public String getCategory(){
        return this.category;
    }

    public void setVipLevel(String vipLevel){
        this.vipLevel = vipLevel;
    }

    public String getVipLevel(){
        return this.vipLevel;
    }

    public void setDetails(String details){
        this.details = details;
    }

    public String getDetails(){
        return this.details;
    }

    public void setTotal(Integer total){
        this.total = total;
    }

    public Integer getTotal(){
        return this.total;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
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

    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

    public String getImageUrl(){
        return this.imageUrl;
    }

}
