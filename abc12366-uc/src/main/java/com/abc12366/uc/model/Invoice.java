package com.abc12366.uc.model;
import java.io.Serializable;


/**
 *
 * 发票信息
 *
 **/
@SuppressWarnings("serial")
public class Invoice implements Serializable {

    /**PK**/
    private String id;

    /**用户ID**/
    private String userId;

    /**用户名**/
    private String username;

    /**发票号码**/
    private String invoiceNo;

    /**发票代码**/
    private String invoiceCode;

    /**发票抬头：1.个人 2.公司**/
    private String name;

    /**发票内容：1.软件服务费 2.财税咨询费 3.技术服务费 4.财税培训费**/
    private String content;

    /**开票公司名称**/
    private String compName;

    /**发票金额**/
    private Double amount;

    /**发票类型：1.增值税普通发票 2.增值税专用发票**/
    private String type;

    /**发票性质：1.纸质发票 2.电子发票**/
    private String property;

    /**发票状态**/
    private String status;

    /**创建时间**/
    private java.util.Date createTime;

    /**修改时间**/
    private java.util.Date lastUpdate;

    /**纳税人识别号**/
    private String nsrsbh;

    /**公司名称**/
    private String nsrmc;

    /**注册地址**/
    private String address;

    /**注册电话**/
    private String phone;

    /**开户银行**/
    private String bank;

    /**用户快递地址ID**/
    private String addressId;

    /**用户订单号**/
    private String userOrderNo;

    /**配送方式**/
    private String deliveryMethod;

    /**是否需要寄送**/
    private Integer isShipping;

    /**是否免运费**/
    private Integer isFreeShipping;

    /**配送费**/
    private Double deliveryFee;

    /**是否保价**/
    private Integer isInsured;

    /**保价费**/
    private Double insuredFee;

    /**支付方式：WEIXIN、ALIPAY**/
    private String payMethod;


    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getUserId(){
        return this.userId;
    }

    public void setInvoiceNo(String invoiceNo){
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceNo(){
        return this.invoiceNo;
    }

    public void setInvoiceCode(String invoiceCode){
        this.invoiceCode = invoiceCode;
    }

    public String getInvoiceCode(){
        return this.invoiceCode;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getContent(){
        return this.content;
    }

    public void setCompName(String compName){
        this.compName = compName;
    }

    public String getCompName(){
        return this.compName;
    }

    public void setAmount(Double amount){
        this.amount = amount;
    }

    public Double getAmount(){
        return this.amount;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }

    public void setProperty(String property){
        this.property = property;
    }

    public String getProperty(){
        return this.property;
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

    public void setNsrsbh(String nsrsbh){
        this.nsrsbh = nsrsbh;
    }

    public String getNsrsbh(){
        return this.nsrsbh;
    }

    public void setNsrmc(String nsrmc){
        this.nsrmc = nsrmc;
    }

    public String getNsrmc(){
        return this.nsrmc;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getAddress(){
        return this.address;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getPhone(){
        return this.phone;
    }

    public void setBank(String bank){
        this.bank = bank;
    }

    public String getBank(){
        return this.bank;
    }

    public void setAddressId(String addressId){
        this.addressId = addressId;
    }

    public String getAddressId(){
        return this.addressId;
    }

    public void setUserOrderNo(String userOrderNo){
        this.userOrderNo = userOrderNo;
    }

    public String getUserOrderNo(){
        return this.userOrderNo;
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


    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public Double getInsuredFee() {
        return insuredFee;
    }

    public void setInsuredFee(Double insuredFee) {
        this.insuredFee = insuredFee;
    }

}
