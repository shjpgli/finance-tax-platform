package com.abc12366.uc.model;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * 用户换货
 **/
@SuppressWarnings("serial")
public class OrderExchange implements Serializable {

    /**
     * PK
     **/
    private String id;

    /**
     * FK
     **/
    private String userId;

    /**
     * 订单号
     **/
    private String orderNo;

    /**
     * 换货原因
     **/
    private String reason;

    /**
     * 用户备注
     **/
    private String userRemark;

    /**
     * 管理员备注
     **/
    private String adminRemark;

    /**
     * 用户换货快递单号
     **/
    private String expressNo;

    /**
     * 用户换货快递公司
     **/
    private String expressComp;

    /**
     * 用户换货快递单号
     **/
    private String toExpressNo;
    /**
     * 退单状态
     **/
    private String status;

    /****/
    private Timestamp createTime;

    /****/
    private Timestamp lastUpdate;

    public OrderExchange() {
    }

    public OrderExchange(String id, String userId, String orderNo, String reason, String userRemark, String
            adminRemark, String expressNo, String expressComp, String status, Timestamp createTime, Timestamp
            lastUpdate, String toExpressNo) {
        this.id = id;
        this.userId = userId;
        this.orderNo = orderNo;
        this.reason = reason;
        this.userRemark = userRemark;
        this.adminRemark = adminRemark;
        this.expressNo = expressNo;
        this.expressComp = expressComp;
        this.status = status;
        this.createTime = createTime;
        this.lastUpdate = lastUpdate;
        this.toExpressNo = toExpressNo;
    }

    private OrderExchange(Builder builder) {
        setId(builder.id);
        setUserId(builder.userId);
        setOrderNo(builder.orderNo);
        setReason(builder.reason);
        setUserRemark(builder.userRemark);
        setAdminRemark(builder.adminRemark);
        setExpressNo(builder.expressNo);
        setExpressComp(builder.expressComp);
        setStatus(builder.status);
        setCreateTime(builder.createTime);
        setLastUpdate(builder.lastUpdate);
        setToExpressNo(builder.toExpressNo);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderNo() {
        return this.orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getExpressNo() {
        return this.expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getExpressComp() {
        return this.expressComp;
    }

    public void setExpressComp(String expressComp) {
        this.expressComp = expressComp;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getLastUpdate() {
        return this.lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getUserRemark() {
        return userRemark;
    }

    public void setUserRemark(String userRemark) {
        this.userRemark = userRemark;
    }

    public String getAdminRemark() {
        return adminRemark;
    }

    public void setAdminRemark(String adminRemark) {
        this.adminRemark = adminRemark;
    }

    public String getToExpressNo() {
        return toExpressNo;
    }

    public void setToExpressNo(String toExpressNo) {
        this.toExpressNo = toExpressNo;
    }

    @Override
    public String toString() {
        return "OrderExchange{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", reason='" + reason + '\'' +
                ", userRemark='" + userRemark + '\'' +
                ", adminRemark='" + adminRemark + '\'' +
                ", expressNo='" + expressNo + '\'' +
                ", expressComp='" + expressComp + '\'' +
                ", toExpressNo='" + toExpressNo + '\'' +
                ", status='" + status + '\'' +
                ", createTime=" + createTime +
                ", lastUpdate=" + lastUpdate +
                '}';
    }

    public static final class Builder {
        private String id;
        private String userId;
        private String orderNo;
        private String reason;
        private String userRemark;
        private String adminRemark;
        private String expressNo;
        private String expressComp;
        private String status;
        private Timestamp createTime;
        private Timestamp lastUpdate;
        private String toExpressNo;

        public Builder() {
        }

        public Builder id(String val) {
            id = val;
            return this;
        }

        public Builder userId(String val) {
            userId = val;
            return this;
        }

        public Builder orderNo(String val) {
            orderNo = val;
            return this;
        }

        public Builder reason(String val) {
            reason = val;
            return this;
        }

        public Builder userRemark(String val) {
            userRemark = val;
            return this;
        }

        public Builder adminRemark(String val) {
            adminRemark = val;
            return this;
        }

        public Builder expressNo(String val) {
            expressNo = val;
            return this;
        }

        public Builder expressComp(String val) {
            expressComp = val;
            return this;
        }

        public Builder status(String val) {
            status = val;
            return this;
        }

        public Builder createTime(Timestamp val) {
            createTime = val;
            return this;
        }

        public Builder lastUpdate(Timestamp val) {
            lastUpdate = val;
            return this;
        }

        public Builder toExpressNo(String val) {
            toExpressNo = val;
            return this;
        }

        public OrderExchange build() {
            return new OrderExchange(this);
        }
    }
}
