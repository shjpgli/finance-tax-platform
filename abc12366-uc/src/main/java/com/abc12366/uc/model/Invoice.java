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

	/**发票编号**/
	private String invoiceId;

	/**发票抬头：1.个人 2.公司**/
	private String name;

	/**发票内容：1.软件服务费 2.财税咨询费 3.技术服务费 4.财税培训费**/
	private String content;

	/**发票金额**/
	private double amount;

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

	/**用户订单号，自动生成，唯一**/
	private String orderNum;

	/**公司名称**/
	private String compName;



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

}
