package com.abc12366.uc.model.bo;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.bo.GoodsBO;

import java.io.Serializable;
import java.util.Date;


/**
 *
 * 用户订单
 *
 **/
@SuppressWarnings("serial")
public class OrderBO implements Serializable {

    private String orderNo;
    private String userId;
    private String goodsId;
    private double dealPrice;
    private Integer num;
    private String orderStatus;
    private String payStatus;
    private String deliveryMethod;
    private String payMethod;
    private java.util.Date createTime;
    private java.util.Date lastUpdate;
    private String feedback;
    private String name;
    private String dictId;
    private String uvipLevel;
    private String unit;
    private String categoryId;
    private String category;

    private GoodsBO goodsBO;
    private User user;

    private Date startTime;
    private Date endTime;

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

    public GoodsBO getGoodsBO() {
        return goodsBO;
    }

    public void setGoodsBO(GoodsBO goodsBO) {
        this.goodsBO = goodsBO;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
