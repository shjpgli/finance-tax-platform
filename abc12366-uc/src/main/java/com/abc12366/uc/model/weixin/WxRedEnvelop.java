package com.abc12366.uc.model.weixin;

import java.util.Date;

/**
 * 红包信息表
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-09-14 10:54 AM
 * @since 1.0.0
 */
public class WxRedEnvelop {
    // （主键）
    private String id;
    // 红包口令
    private String secret;
    // 口令产生时间
    private Date createTime;
    // 红包活动编号（主键）
    private String activityId;
    // 活动起始时间
    private Date startTime;
    // 活动截止时间
    private Date endTime;
    // 红包金额
    private Double amount;
    // 红包发放模式（固定金额、随机金额）
    private String amountType;
    // 中奖概率（1%-100%）
    private String probability;
    // 红包中奖金额
    private Double sendAmount;
    // 发送状态 0:已中奖未发送
    private String sendStatus;
    // 发送次数
    private Integer sendTimes;
    // 发送时间
    private Date sendTime;
    // 领取状态 0:已中奖未领取 1.已领取 2.未中奖
    private String receiveStatus;
    // 用户输入口令的时间
    private Date receiveTime;
    // 用户IP
    private String ip;
    // 用户OPENID
    private String openId;

    public WxRedEnvelop() {
    }

    public WxRedEnvelop(String id, String secret, Date createTime, String activityId, Date startTime, Date endTime,
                        Double amount, String amountType, String probability, Double sendAmount, String sendStatus,
                        Integer sendTimes, Date sendTime, String receiveStatus, Date receiveTime, String ip, String
                                openId) {
        this.id = id;
        this.secret = secret;
        this.createTime = createTime;
        this.activityId = activityId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.amount = amount;
        this.amountType = amountType;
        this.probability = probability;
        this.sendAmount = sendAmount;
        this.sendStatus = sendStatus;
        this.sendTimes = sendTimes;
        this.sendTime = sendTime;
        this.receiveStatus = receiveStatus;
        this.receiveTime = receiveTime;
        this.ip = ip;
        this.openId = openId;
    }

    private WxRedEnvelop(Builder builder) {
        setId(builder.id);
        setSecret(builder.secret);
        setCreateTime(builder.createTime);
        setActivityId(builder.activityId);
        setStartTime(builder.startTime);
        setEndTime(builder.endTime);
        setAmount(builder.amount);
        setAmountType(builder.amountType);
        setProbability(builder.probability);
        setSendAmount(builder.sendAmount);
        setSendStatus(builder.sendStatus);
        setSendTimes(builder.sendTimes);
        setSendTime(builder.sendTime);
        setReceiveStatus(builder.receiveStatus);
        setReceiveTime(builder.receiveTime);
        setIp(builder.ip);
        setOpenId(builder.openId);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getAmountType() {
        return amountType;
    }

    public void setAmountType(String amountType) {
        this.amountType = amountType;
    }

    public String getProbability() {
        return probability;
    }

    public void setProbability(String probability) {
        this.probability = probability;
    }

    public Double getSendAmount() {
        return sendAmount;
    }

    public void setSendAmount(Double sendAmount) {
        this.sendAmount = sendAmount;
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    public Integer getSendTimes() {
        return sendTimes;
    }

    public void setSendTimes(Integer sendTimes) {
        this.sendTimes = sendTimes;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(String receiveStatus) {
        this.receiveStatus = receiveStatus;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public String toString() {
        return "RedEnvelop{" +
                "id='" + id + '\'' +
                ", secret='" + secret + '\'' +
                ", createTime=" + createTime +
                ", activityId='" + activityId + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", amount=" + amount +
                ", amountType='" + amountType + '\'' +
                ", probability='" + probability + '\'' +
                ", sendAmount=" + sendAmount +
                ", sendStatus='" + sendStatus + '\'' +
                ", sendTimes=" + sendTimes +
                ", sendTime=" + sendTime +
                ", receiveStatus='" + receiveStatus + '\'' +
                ", receiveTime='" + receiveTime + '\'' +
                ", ip='" + ip + '\'' +
                ", openId='" + openId + '\'' +
                '}';
    }

    public static final class Builder {
        private String id;
        private String secret;
        private Date createTime;
        private String activityId;
        private Date startTime;
        private Date endTime;
        private Double amount;
        private String amountType;
        private String probability;
        private Double sendAmount;
        private String sendStatus;
        private Integer sendTimes;
        private Date sendTime;
        private String receiveStatus;
        private Date receiveTime;
        private String ip;
        private String openId;

        public Builder() {
        }

        public Builder id(String val) {
            id = val;
            return this;
        }

        public Builder secret(String val) {
            secret = val;
            return this;
        }

        public Builder createTime(Date val) {
            createTime = val;
            return this;
        }

        public Builder activityId(String val) {
            activityId = val;
            return this;
        }

        public Builder startTime(Date val) {
            startTime = val;
            return this;
        }

        public Builder endTime(Date val) {
            endTime = val;
            return this;
        }

        public Builder amount(Double val) {
            amount = val;
            return this;
        }

        public Builder amountType(String val) {
            amountType = val;
            return this;
        }

        public Builder probability(String val) {
            probability = val;
            return this;
        }

        public Builder sendAmount(Double val) {
            sendAmount = val;
            return this;
        }

        public Builder sendStatus(String val) {
            sendStatus = val;
            return this;
        }

        public Builder sendTimes(Integer val) {
            sendTimes = val;
            return this;
        }

        public Builder sendTime(Date val) {
            sendTime = val;
            return this;
        }

        public Builder receiveStatus(String val) {
            receiveStatus = val;
            return this;
        }

        public Builder receiveTime(Date val) {
            receiveTime = val;
            return this;
        }

        public Builder ip(String val) {
            ip = val;
            return this;
        }

        public Builder openId(String val) {
            openId = val;
            return this;
        }

        public WxRedEnvelop build() {
            return new WxRedEnvelop(this);
        }
    }
}
