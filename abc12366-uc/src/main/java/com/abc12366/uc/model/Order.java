package com.abc12366.uc.model;

import java.io.Serializable;


/**
 * 用户订单
 **/
@SuppressWarnings("serial")
public class Order implements Serializable {


    /**
     * 订单编号
     **/
    private String orderNo;

    /**
     * 用户ID
     **/
    private String userId;

    /**
     * 订单状态：1.新订单 2.确认订单 3.取消订单 4.作废订单 5.完成订单 6.退款 7.部分退款
     **/
    private String orderStatus;

    /**
     * 配送方式
     **/
    private String deliveryMethod;

    /**
     * 支付方式：WEIXIN、ALIPAY
     **/
    private String payMethod;

    /**
     * 当前会员等级
     **/
    private String nowVipLevel;

    /****/
    private java.util.Date createTime;

    /****/
    private java.util.Date lastUpdate;

    /**
     * 用户名
     **/
    private String username;

    /**
     * 是否需要寄送
     **/
    private Integer isShipping;

    /**
     * 是否免运费
     **/
    private Integer isFreeShipping;

    /**
     * 配送费
     **/
    private Double deliveryFee;

    /**
     * 是否保价
     **/
    private Integer isInsured;

    /**
     * 保价费用
     **/
    private Double insuredFee;

    /**
     * 成交总金额
     **/
    private Double totalPrice;

    /**
     * 配送地址ID
     **/
    private String addressId;

    /**
     * 运单号
     **/
    private String expressNo;

    /**
     * 备注
     **/
    private String remark;

    /**
     * 赠送积分
     **/
    private Integer giftPoints;

    /**
     * 交易方式：RMB、POINTS
     **/
    private String tradeMethod;

    /**
     * 是否已开发票，true：是，false：否
     **/
    private Boolean isInvoice;

    /**取消原因，字典表ID**/
    private String cancelId;

    /**
     * 商品ID
     **/
    private String goodsId;

    /**推荐人姓名**/
    private String recommendUser;

    /**物流公司ID**/
    private String expressCompId;

    public String getOrderNo() {
        return this.orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderStatus() {
        return this.orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDeliveryMethod() {
        return this.deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getPayMethod() {
        return this.payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
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

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getIsShipping() {
        return this.isShipping;
    }

    public void setIsShipping(Integer isShipping) {
        this.isShipping = isShipping;
    }

    public Integer getIsFreeShipping() {
        return this.isFreeShipping;
    }

    public void setIsFreeShipping(Integer isFreeShipping) {
        this.isFreeShipping = isFreeShipping;
    }

    public Double getDeliveryFee() {
        return this.deliveryFee;
    }

    public void setDeliveryFee(Double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public Integer getIsInsured() {
        return this.isInsured;
    }

    public void setIsInsured(Integer isInsured) {
        this.isInsured = isInsured;
    }

    public Double getInsuredFee() {
        return this.insuredFee;
    }

    public void setInsuredFee(Double insuredFee) {
        this.insuredFee = insuredFee;
    }

    public Double getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAddressId() {
        return this.addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getExpressNo() {
        return this.expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNowVipLevel() {
        return nowVipLevel;
    }

    public void setNowVipLevel(String nowVipLevel) {
        this.nowVipLevel = nowVipLevel;
    }

    public Integer getGiftPoints() {
        return giftPoints;
    }

    public void setGiftPoints(Integer giftPoints) {
        this.giftPoints = giftPoints;
    }

    public String getTradeMethod() {
        return tradeMethod;
    }

    public void setTradeMethod(String tradeMethod) {
        this.tradeMethod = tradeMethod;
    }

    public Boolean getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(Boolean isInvoice) {
        this.isInvoice = isInvoice;
    }

    public String getCancelId() {
        return cancelId;
    }

    public void setCancelId(String cancelId) {
        this.cancelId = cancelId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getRecommendUser() {
        return recommendUser;
    }

    public void setRecommendUser(String recommendUser) {
        this.recommendUser = recommendUser;
    }

    public String getExpressCompId() {
        return expressCompId;
    }

    public void setExpressCompId(String expressCompId) {
        this.expressCompId = expressCompId;
    }
}
