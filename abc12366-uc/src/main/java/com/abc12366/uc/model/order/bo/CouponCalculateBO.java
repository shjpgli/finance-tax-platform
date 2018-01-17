package com.abc12366.uc.model.order.bo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * 使用优惠劵计算金额对象
 *
 * @author lijun <ljun51@outlook.com>
 * @date 2018-01-16 5:53 PM
 * @since 1.0.0
 */
public class CouponCalculateBO {

    /**
     * 优惠劵ID
     */
    @NotEmpty
    @Size(min = 1, max = 5)
    private List<String> couponIds;

    /**
     * 用户ID
     */
    @NotEmpty
    @Length(max = 64)
    private String userId;

    /**
     * 商品品目
     */
    @NotEmpty
    @Length(max = 64)
    private String categoryId;

    /**
     * 使用优惠劵之前的金额
     */
    @NotEmpty
    private Double amount;

    public List<String> getCouponIds() {
        return couponIds;
    }

    public void setCouponIds(List<String> couponIds) {
        this.couponIds = couponIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CouponOrderBO{" +
                "couponIds=" + couponIds +
                ", userId='" + userId + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", amount=" + amount +
                '}';
    }
}
