package com.abc12366.uc.model.bo;
import com.abc12366.uc.model.Product;
import com.abc12366.uc.model.User;

import java.io.Serializable;


/**
 * 
 * 用户订单
 * 
 **/
@SuppressWarnings("serial")
public class OrderBO implements Serializable {

	/****/
	private String id;

	/**用户ID**/
	private String userId;

    private User user;
	/**订单编号**/
	private String orderId;

	/**产品ID**/
	private String productId;

    private Product product;
	/**金额**/
	private double amount;

	/**消费积分**/
	private Integer points;

	/**产品数量**/
	private Integer num;

	/**订单状态**/
	private String status;

	/****/
	private java.util.Date createTime;

	private java.util.Date startTime;

	private java.util.Date endTime;

	/****/
	private java.util.Date lastUpdate;

	/**反馈信息**/
	private String feedback;



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

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return this.orderId;
	}

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return this.productId;
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

	public void setNum(Integer num){
		this.num = num;
	}

	public Integer getNum(){
		return this.num;
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

	public void setFeedback(String feedback){
		this.feedback = feedback;
	}

	public String getFeedback(){
		return this.feedback;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
