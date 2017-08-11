package com.abc12366.uc.model.bo;

import java.sql.Timestamp;

/**
 * 已完成订单
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-08-11 3:18 PM
 * @since 1.0.0
 */
public class ExchangeCompletedOrderBO {

    private String orderNo;
    private String productId;
    private String goodsType;
    private Timestamp createTime;
    private Timestamp lastUpdate;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "ExchangeCompletedOrderBO{" +
                "orderNo='" + orderNo + '\'' +
                ", productId='" + productId + '\'' +
                ", goodsType='" + goodsType + '\'' +
                ", createTime=" + createTime +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
