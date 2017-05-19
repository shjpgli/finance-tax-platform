package com.abc12366.uc.model.bo;
import com.abc12366.uc.model.Order;

import java.io.Serializable;
import java.util.List;


/**
 * 
 * 发票信息
 * 
 **/
@SuppressWarnings("serial")
public class InvoiceBO implements Serializable {

	private String id;
	private String userId;
	private String invoiceId;
	private String name;
	private String content;
	private double amount;
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
	private String orderNum;
	private String compName;

    private List<Order> orderList;

    private String orderIds;

    private java.util.Date startTime;

    private java.util.Date endTime;

    public InvoiceBO() {
    }


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

	public void setInvoiceId(String invoiceId){
		this.invoiceId = invoiceId;
	}

	public String getInvoiceId(){
		return this.invoiceId;
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

	public void setAmount(double amount){
		this.amount = amount;
	}

	public double getAmount(){
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

	public void setOrderNum(String orderNum){
		this.orderNum = orderNum;
	}

	public String getOrderNum(){
		return this.orderNum;
	}

	public void setCompName(String compName){
		this.compName = compName;
	}

	public String getCompName(){
		return this.compName;
	}

    public String getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(String orderIds) {
        this.orderIds = orderIds;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
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
}
