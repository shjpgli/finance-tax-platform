package com.abc12366.uc.model.bo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * 退款BO
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-08-12 9:45 AM
 * @since 1.0.0
 */
public class ExchangeRefundBO {

    private String id;
    // 退款金额流向
    @NotEmpty
    @Length(min = 1, max = 10)
    private String refundType;

    // 退款金额
    @NotNull
    private Double amount;

    // 备注
    //@NotEmpty
    @Length(max = 500)
    private String adminRemark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getAdminRemark() {
        return adminRemark;
    }

    public void setAdminRemark(String adminRemark) {
        this.adminRemark = adminRemark;
    }

    @Override
    public String toString() {
        return "ExchangeRefundBO{" +
                "id='" + id + '\'' +
                ", refundType='" + refundType + '\'' +
                ", amount=" + amount +
                ", adminRemark='" + adminRemark + '\'' +
                '}';
    }
}
