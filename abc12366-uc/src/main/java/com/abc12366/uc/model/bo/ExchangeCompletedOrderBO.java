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

    // 订单号
    private String orderNo;
    // 订单赠送积分
    private Integer giftPoints;
    // 订单创建时间
    private Timestamp createTime;
    // 订单修改时间
    private Timestamp lastUpdate;
    // 用户ID
    private String userId;
    // 用户现有积分
    private Integer points;
    // 产品ID
    private String productId;
    // 产品类型
    private String goodsType;

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

    public Integer getGiftPoints() {
        return giftPoints;
    }

    public void setGiftPoints(Integer giftPoints) {
        this.giftPoints = giftPoints;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "ExchangeCompletedOrderBO{" +
                "orderNo='" + orderNo + '\'' +
                ", giftPoints=" + giftPoints +
                ", createTime=" + createTime +
                ", lastUpdate=" + lastUpdate +
                ", userId='" + userId + '\'' +
                ", points=" + points +
                ", productId='" + productId + '\'' +
                ", goodsType='" + goodsType + '\'' +
                '}';
    }
}
