package com.abc12366.uc.model;
import java.io.Serializable;


/**
 *
 * 发票快递单
 *
 **/
@SuppressWarnings("serial")
public class Express implements Serializable {

    /**ID**/
    private String id;

    /**用户订单号**/
    private String userOrderNo;

    /**用户ID**/
    private String userId;

    /**快递单号**/
    private String expressNo;

    /**快递公司ID**/
    private String deliveryId;

    /**快递状态**/
    private String status;

    /****/
    private java.util.Date createTime;

    /****/
    private java.util.Date lastUpdate;



    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public void setUserOrderNo(String userOrderNo){
        this.userOrderNo = userOrderNo;
    }

    public String getUserOrderNo(){
        return this.userOrderNo;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getUserId(){
        return this.userId;
    }

    public void setExpressNo(String expressNo){
        this.expressNo = expressNo;
    }

    public String getExpressNo(){
        return this.expressNo;
    }

    public void setDeliveryId(String deliveryId){
        this.deliveryId = deliveryId;
    }

    public String getDeliveryId(){
        return this.deliveryId;
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

}
