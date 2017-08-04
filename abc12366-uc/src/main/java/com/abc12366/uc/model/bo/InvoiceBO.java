package com.abc12366.uc.model.bo;

import com.abc12366.uc.model.InvoiceDetail;
import com.abc12366.uc.model.InvoiceLog;
import com.abc12366.uc.model.UserAddress;

import java.io.Serializable;
import java.util.List;


/**
 * 发票信息
 **/
@SuppressWarnings("serial")
public class InvoiceBO implements Serializable {

    private String id;
    private String userId;
    private String username;
    private String invoiceNo;
    private String invoiceCode;
    private String name;
    private String content;
    private String compName;
    private Double amount;
    private String type;
    private String property;
    private String status;
    private java.util.Date createTime;
    private java.util.Date lastUpdate;
    private String nsrsbh;
    private String nsrmc;
    private String address;
    private String phone;
    private String bank;
    private String addressId;
    /**
     * 用户订单号(运单号)
     **/
    private String userOrderNo;
    private String deliveryMethod;
    private Integer isShipping;
    private Integer isFreeShipping;
    private Double deliveryFee;
    private Integer isInsured;
    private Double InsuredFee;
    private String payMethod;
    /**
     * 收货人
     **/
    private String consignee;

    private List<OrderBO> orderBOList;
    private String orderNos;
    private java.util.Date startTime;
    private java.util.Date endTime;
    private UserAddress userAddress;
    private DeliveryMethodBO deliveryMethodBO;

    private InvoiceDetail invoiceDetail;
    /**
     * 是否已开发票，true：是，false：否
     **/
    private Boolean isInvoice;

    private InvoiceLog invoiceLog;

    /**
     * 是否同意开票
     **/
    private Boolean isBilling;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getInvoiceNo() {
        return this.invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceCode() {
        return this.invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCompName() {
        return this.compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProperty() {
        return this.property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getNsrsbh() {
        return this.nsrsbh;
    }

    public void setNsrsbh(String nsrsbh) {
        this.nsrsbh = nsrsbh;
    }

    public String getNsrmc() {
        return this.nsrmc;
    }

    public void setNsrmc(String nsrmc) {
        this.nsrmc = nsrmc;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBank() {
        return this.bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAddressId() {
        return this.addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getUserOrderNo() {
        return this.userOrderNo;
    }

    public void setUserOrderNo(String userOrderNo) {
        this.userOrderNo = userOrderNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public Integer getIsShipping() {
        return isShipping;
    }

    public void setIsShipping(Integer isShipping) {
        this.isShipping = isShipping;
    }

    public Integer getIsFreeShipping() {
        return isFreeShipping;
    }

    public void setIsFreeShipping(Integer isFreeShipping) {
        this.isFreeShipping = isFreeShipping;
    }

    public Double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(Double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public Integer getIsInsured() {
        return isInsured;
    }

    public void setIsInsured(Integer isInsured) {
        this.isInsured = isInsured;
    }

    public Double getInsuredFee() {
        return InsuredFee;
    }

    public void setInsuredFee(Double insuredFee) {
        InsuredFee = insuredFee;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public java.util.Date getStartTime() {
        return startTime;
    }

    public void setStartTime(java.util.Date startTime) {
        this.startTime = startTime;
    }

    public java.util.Date getEndTime() {
        return endTime;
    }

    public void setEndTime(java.util.Date endTime) {
        this.endTime = endTime;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getOrderNos() {
        return orderNos;
    }

    public void setOrderNos(String orderNos) {
        this.orderNos = orderNos;
    }

    public List<OrderBO> getOrderBOList() {
        return orderBOList;
    }

    public void setOrderBOList(List<OrderBO> orderBOList) {
        this.orderBOList = orderBOList;
    }

    public UserAddress getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
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

    public InvoiceDetail getInvoiceDetail() {
        return invoiceDetail;
    }

    public void setInvoiceDetail(InvoiceDetail invoiceDetail) {
        this.invoiceDetail = invoiceDetail;
    }

    public InvoiceLog getInvoiceLog() {
        return invoiceLog;
    }

    public void setInvoiceLog(InvoiceLog invoiceLog) {
        this.invoiceLog = invoiceLog;
    }

    public Boolean getIsBilling() {
        return isBilling;
    }

    public void setIsBilling(Boolean isBilling) {
        this.isBilling = isBilling;
    }
}
