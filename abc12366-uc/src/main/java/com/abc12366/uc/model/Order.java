package com.abc12366.uc.model;
import java.io.Serializable;


/**
 *
 * 用户订单
 *
 **/
@SuppressWarnings("serial")
public class Order implements Serializable {

    /**订单编号**/
    private String orderNo;

    /**用户ID**/
    private String userId;

    /**产品编号**/
    private String goodsId;

    /**成交价格**/
    private double dealPrice;

    /**产品数量**/
    private Integer num;

    /**订单状态：1.新订单 2.确认订单 3.取消订单 4.作废订单 5.完成订单 6.退款 7.部分退款**/
    private String orderStatus;

    /**支付状态：0.未支付 1.已支付**/
    private String payStatus;

    /**配送方式**/
    private String deliveryMethod;

    /**支付方式：WEIXIN、ALIPAY**/
    private String payMethod;

    /****/
    private java.util.Date createTime;

    /****/
    private java.util.Date lastUpdate;

    /**反馈信息**/
    private String feedback;

    /**产品名称**/
    private String name;

    /**字典ID主键**/
    private String dictId;

    /**会员等级**/
    private String uvipLevel;

    /**计件单位显示**/
    private String unit;

    /**产品分类ID**/
    private String categoryId;

    /**产品分类名称**/
    private String category;



    public void setOrderNo(String orderNo){
        this.orderNo = orderNo;
    }

    public String getOrderNo(){
        return this.orderNo;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getUserId(){
        return this.userId;
    }

    public void setGoodsId(String goodsId){
        this.goodsId = goodsId;
    }

    public String getGoodsId(){
        return this.goodsId;
    }

    public void setDealPrice(double dealPrice){
        this.dealPrice = dealPrice;
    }

    public double getDealPrice(){
        return this.dealPrice;
    }

    public void setNum(Integer num){
        this.num = num;
    }

    public Integer getNum(){
        return this.num;
    }

    public void setOrderStatus(String orderStatus){
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus(){
        return this.orderStatus;
    }

    public void setPayStatus(String payStatus){
        this.payStatus = payStatus;
    }

    public String getPayStatus(){
        return this.payStatus;
    }

    public void setDeliveryMethod(String deliveryMethod){
        this.deliveryMethod = deliveryMethod;
    }

    public String getDeliveryMethod(){
        return this.deliveryMethod;
    }

    public void setPayMethod(String payMethod){
        this.payMethod = payMethod;
    }

    public String getPayMethod(){
        return this.payMethod;
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

    public void setFeedback(String feedback){
        this.feedback = feedback;
    }

    public String getFeedback(){
        return this.feedback;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setDictId(String dictId){
        this.dictId = dictId;
    }

    public String getDictId(){
        return this.dictId;
    }

    public void setUvipLevel(String uvipLevel){
        this.uvipLevel = uvipLevel;
    }

    public String getUvipLevel(){
        return this.uvipLevel;
    }

    public void setUnit(String unit){
        this.unit = unit;
    }

    public String getUnit(){
        return this.unit;
    }

    public void setCategoryId(String categoryId){
        this.categoryId = categoryId;
    }

    public String getCategoryId(){
        return this.categoryId;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public String getCategory(){
        return this.category;
    }

}
