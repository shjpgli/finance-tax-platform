package com.abc12366.uc.model.bo;

import com.abc12366.uc.model.User;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 用户订单
 **/
@SuppressWarnings("serial")
public class OrderPayBO implements Serializable {

    private String orderNo;
    private String userId;
    /**
     * 是否支付成功，1：支付中，2：支付成功，3：支付失败，
     **/
    private int isPay;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getIsPay() {
        return isPay;
    }

    public void setIsPay(int isPay) {
        this.isPay = isPay;
    }
}
