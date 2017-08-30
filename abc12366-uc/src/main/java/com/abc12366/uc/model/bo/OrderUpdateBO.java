package com.abc12366.uc.model.bo;

import com.abc12366.uc.model.OrderProductSpec;
import com.abc12366.uc.model.User;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 用户订单
 **/
@SuppressWarnings("serial")
public class OrderUpdateBO implements Serializable {

    private String orderNo;
    private String userId;
    /**推荐人姓名**/
    @Size(min = 1,max = 64)
    private String recommendUser;

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

    public String getRecommendUser() {
        return recommendUser;
    }

    public void setRecommendUser(String recommendUser) {
        this.recommendUser = recommendUser;
    }
}
