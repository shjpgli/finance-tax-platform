package com.abc12366.uc.model.bo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 退换货申请BO
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-08-11 11:26 AM
 * @since 1.0.0
 */
public class ExchangeApplicationBO {

    // PK
    private String id;
    // 订单号
    @NotEmpty
    @Length(min = 16, max = 64)
    private String orderNo;
    // 换货原因
    @NotEmpty
    @Length(min = 1, max = 1)
    private String reason;
    // 用户备注
    @Length(max = 500)
    private String userRemark;
    // 服务类型 1-换货 2-退货
    @NotEmpty
    @Length(min = 1, max = 1)
    private String type;

    // 订单号
    @NotEmpty
    @Length(min = 16, max = 64)
    private String addressId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUserRemark() {
        return userRemark;
    }

    public void setUserRemark(String userRemark) {
        this.userRemark = userRemark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ExchangeApplicationBO{" +
                "id='" + id + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", reason='" + reason + '\'' +
                ", userRemark='" + userRemark + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }
}
