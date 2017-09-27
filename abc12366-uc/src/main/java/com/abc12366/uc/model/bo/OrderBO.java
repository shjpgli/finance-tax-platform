package com.abc12366.uc.model.bo;

import com.abc12366.uc.model.ExpressComp;
import com.abc12366.uc.model.OrderProductSpec;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 用户订单
 **/
@SuppressWarnings("serial")
public class OrderBO implements Serializable {

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
    private String cancelId;
    private String goodsId;
    private String[] status;
    /**推荐人姓名**/
    private String recommendUser;

    private String nowVipLevelName;

    private String expressCompId;

    private String goodsType;
    /**
     * 是否已开发票，true：是，false：否
     **/
    private Boolean isInvoice;
    private GoodsBO goodsBO;

    private UserBO user;
    private UserAddressBO userAddressBO;

    private List<OrderProductBO> orderProductBOList;
    private List<OrderProductSpec> orderProductSpecList;
    private List<DictBO> dictBOList;
    private List<TradeLogBO> tradeLogBOList;
    private Date startTime;
    private Date endTime;

    private List<OrderLogBO> orderLogBOList;

    private InvoiceBO invoiceBO;

    private DeliveryMethodBO deliveryMethodBO;

    private ExpressComp expressComp;

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

    public GoodsBO getGoodsBO() {
        return goodsBO;
    }

    public void setGoodsBO(GoodsBO goodsBO) {
        this.goodsBO = goodsBO;
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

    public List<OrderProductSpec> getOrderProductSpecList() {
        return orderProductSpecList;
    }

    public void setOrderProductSpecList(List<OrderProductSpec> orderProductSpecList) {
        this.orderProductSpecList = orderProductSpecList;
    }

    public List<OrderLogBO> getOrderLogBOList() {
        return orderLogBOList;
    }

    public void setOrderLogBOList(List<OrderLogBO> orderLogBOList) {
        this.orderLogBOList = orderLogBOList;
    }

    public List<TradeLogBO> getTradeLogBOList() {
        return tradeLogBOList;
    }

    public void setTradeLogBOList(List<TradeLogBO> tradeLogBOList) {
        this.tradeLogBOList = tradeLogBOList;
    }

    public UserAddressBO getUserAddressBO() {
        return userAddressBO;
    }

    public void setUserAddressBO(UserAddressBO userAddressBO) {
        this.userAddressBO = userAddressBO;
    }

    public String getRecommendUser() {
        return recommendUser;
    }

    public void setRecommendUser(String recommendUser) {
        this.recommendUser = recommendUser;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public UserBO getUser() {
        return user;
    }

    public void setUser(UserBO user) {
        this.user = user;
    }

    public String getNowVipLevelName() {
        return nowVipLevelName;
    }

    public void setNowVipLevelName(String nowVipLevelName) {
        this.nowVipLevelName = nowVipLevelName;
    }

    public String getExpressCompId() {
        return expressCompId;
    }

    public void setExpressCompId(String expressCompId) {
        this.expressCompId = expressCompId;
    }

    public ExpressComp getExpressComp() {
        return expressComp;
    }

    public void setExpressComp(ExpressComp expressComp) {
        this.expressComp = expressComp;
    }

    public String[] getStatus() {
        return status;
    }

    public void setStatus(String[] status) {
        this.status = status;
    }
}
