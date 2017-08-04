package com.abc12366.uc.model.bo;

import com.abc12366.uc.model.User;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 *
 * 用户订单
 *
 **/
@SuppressWarnings("serial")
public class OrderBO implements Serializable {

    private String id;
    private String orderNo;
    private String userId;
    private String orderStatus;
    private String deliveryMethod;
    private String payMethod;
    private String nowVipLevel;
    private java.util.Date createTime;
    private java.util.Date lastUpdate;
    private String username;
    private Integer isShipping;
    private Integer isFreeShipping;
    private Double deliveryFee;
    private Integer isInsured;
    private Double insuredFee;
    private Double totalPrice;
    private String addressId;
    private String expressNo;
    private String remark;
    private Integer giftPoints;
    private String tradeMethod;
    /**是否已开发票，true：是，false：否**/
    private Boolean isInvoice;
    private GoodsBO goodsBO;
    private User user;

    private List<OrderProductBO> orderProductBOList;
    private List<DictBO> dictBOList;
    private Date startTime;
    private Date endTime;

    private OrderLogBO orderLogBO;

    private InvoiceBO invoiceBO;

    private DeliveryMethodBO deliveryMethodBO;

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

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

    public void setOrderStatus(String orderStatus){
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus(){
        return this.orderStatus;
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

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }

    public void setIsShipping(Integer isShipping){
        this.isShipping = isShipping;
    }

    public Integer getIsShipping(){
        return this.isShipping;
    }

    public void setIsFreeShipping(Integer isFreeShipping){
        this.isFreeShipping = isFreeShipping;
    }

    public Integer getIsFreeShipping(){
        return this.isFreeShipping;
    }

    public void setDeliveryFee(Double deliveryFee){
        this.deliveryFee = deliveryFee;
    }

    public Double getDeliveryFee(){
        return this.deliveryFee;
    }

    public void setIsInsured(Integer isInsured){
        this.isInsured = isInsured;
    }

    public Integer getIsInsured(){
        return this.isInsured;
    }

    public void setInsuredFee(Double insuredFee){
        this.insuredFee = insuredFee;
    }

    public Double getInsuredFee(){
        return this.insuredFee;
    }

    public void setTotalPrice(Double totalPrice){
        this.totalPrice = totalPrice;
    }

    public Double getTotalPrice(){
        return this.totalPrice;
    }

    public void setAddressId(String addressId){
        this.addressId = addressId;
    }

    public String getAddressId(){
        return this.addressId;
    }

    public void setExpressNo(String expressNo){
        this.expressNo = expressNo;
    }

    public String getExpressNo(){
        return this.expressNo;
    }

    public void setRemark(String remark){
        this.remark = remark;
    }

    public String getRemark(){
        return this.remark;
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

    public List<OrderProductBO> getOrderProductBOList() {
        return orderProductBOList;
    }

    public void setOrderProductBOList(List<OrderProductBO> orderProductBOList) {
        this.orderProductBOList = orderProductBOList;
    }

    public List<DictBO> getDictBOList() {
        return dictBOList;
    }

    public void setDictBOList(List<DictBO> dictBOList) {
        this.dictBOList = dictBOList;
    }

    public String getNowVipLevel() {
        return nowVipLevel;
    }

    public void setNowVipLevel(String nowVipLevel) {
        this.nowVipLevel = nowVipLevel;
    }

    public OrderLogBO getOrderLogBO() {
        return orderLogBO;
    }

    public void setOrderLogBO(OrderLogBO orderLogBO) {
        this.orderLogBO = orderLogBO;
    }

    public Integer getGiftPoints() {
        return giftPoints;
    }

    public void setGiftPoints(Integer giftPoints) {
        this.giftPoints = giftPoints;
    }

    public InvoiceBO getInvoiceBO() {
        return invoiceBO;
    }

    public void setInvoiceBO(InvoiceBO invoiceBO) {
        this.invoiceBO = invoiceBO;
    }

    public String getTradeMethod() {
        return tradeMethod;
    }

    public void setTradeMethod(String tradeMethod) {
        this.tradeMethod = tradeMethod;
    }

    public DeliveryMethodBO getDeliveryMethodBO() {
        return deliveryMethodBO;
    }

    public void setDeliveryMethodBO(DeliveryMethodBO deliveryMethodBO) {
        this.deliveryMethodBO = deliveryMethodBO;
    }

    public Boolean getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(Boolean isInvoice) {
        this.isInvoice = isInvoice;
    }
}
