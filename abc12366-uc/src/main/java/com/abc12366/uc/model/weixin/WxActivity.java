package com.abc12366.uc.model.weixin;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 微信红包活动
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-09-14 10:39 AM
 * @since 1.0.0
 */
public class WxActivity {

    // 活动编号（系统产生）
    private String id;
    // 活动名称
    @NotEmpty
    @Length(min = 2, max = 16)
    private String name;
    // 活动描述
    @Length(max = 500)
    private String description;
    // 活动起始时间
    @NotNull
    private Date startTime;
    // 活动截止时间
    @NotNull
    private Date endTime;
    // 规则类型
    @NotEmpty
    @Length(min = 1, max = 1)
    private String ruleType;
    // 生成规则定义
    @NotEmpty
    @Length(min = 2, max = 50)
    private String rule;
    // 固定金额／随机金额
    @NotEmpty
    @Length(min = 1, max = 1)
    private String amountType;
    // 随机最大金额/固定金额
    @NotNull
    private Double amount;
    // 中奖概率（1%-100%）
    @NotEmpty
    @Length(min = 2, max = 10)
    private String probability;
    // 活动启动/停止状态（启动状态时平台才产生口令）
    @NotNull
    private Boolean status;
    // 红包祝福语
    @NotEmpty
    @Length(max = 16)
    private String wishing;
    // 红包备注
    @NotEmpty
    @Length(max = 16)
    private String remark;
    // 红包数量
    @NotNull
    private Integer num;
    // 每天抽奖次数限制
    @NotNull
    private Integer times;
    // 创建时间
    private Date createTime;
    // 修改时间
    private Date lastUpdate;

    public WxActivity() {
    }

    public WxActivity(String id, String name, String description, Date startTime, Date endTime, String ruleType, String
            rule, String amountType, Double amount, String probability, Boolean status, String
                              wishing, String remark, Integer num, Date createTime, Date lastUpdate, Integer times) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.ruleType = ruleType;
        this.rule = rule;
        this.amountType = amountType;
        this.amount = amount;
        this.probability = probability;
        this.status = status;
        this.wishing = wishing;
        this.remark = remark;
        this.num = num;
        this.createTime = createTime;
        this.lastUpdate = lastUpdate;
        this.times = times;
    }

    private WxActivity(Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setDescription(builder.description);
        setStartTime(builder.startTime);
        setEndTime(builder.endTime);
        setRuleType(builder.ruleType);
        setRule(builder.rule);
        setAmountType(builder.amountType);
        setAmount(builder.amount);
        setProbability(builder.probability);
        setStatus(builder.status);
        setWishing(builder.wishing);
        setRemark(builder.remark);
        setNum(builder.num);
        setCreateTime(builder.createTime);
        setLastUpdate(builder.lastUpdate);
        setTimes(builder.times);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getAmountType() {
        return amountType;
    }

    public void setAmountType(String amountType) {
        this.amountType = amountType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getProbability() {
        return probability;
    }

    public void setProbability(String probability) {
        probability = probability;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getWishing() {
        return wishing;
    }

    public void setWishing(String wishing) {
        this.wishing = wishing;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", ruleType='" + ruleType + '\'' +
                ", rule='" + rule + '\'' +
                ", amountType='" + amountType + '\'' +
                ", amount=" + amount +
                ", probability='" + probability + '\'' +
                ", status=" + status +
                ", wishing='" + wishing + '\'' +
                ", remark='" + remark + '\'' +
                ", num=" + num +
                ", createTime=" + createTime +
                ", lastUpdate=" + lastUpdate +
                ", times=" + times +
                '}';
    }

    public static final class Builder {
        private String id;
        private String name;
        private String description;
        private Date startTime;
        private Date endTime;
        private String ruleType;
        private String rule;
        private String amountType;
        private Double amount;
        private String probability;
        private Boolean status;
        private String wishing;
        private String remark;
        private Integer num;
        private Date createTime;
        private Date lastUpdate;
        private Integer times;

        public Builder() {
        }

        public Builder id(String val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
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

        public Builder ruleType(String val) {
            ruleType = val;
            return this;
        }

        public Builder rule(String val) {
            rule = val;
            return this;
        }

        public Builder amountType(String val) {
            amountType = val;
            return this;
        }

        public Builder Amount(Double val) {
            amount = val;
            return this;
        }

        public Builder Probability(String val) {
            probability = val;
            return this;
        }

        public Builder status(Boolean val) {
            status = val;
            return this;
        }

        public Builder wishing(String val) {
            wishing = val;
            return this;
        }

        public Builder remark(String val) {
            remark = val;
            return this;
        }

        public Builder num(Integer val) {
            num = val;
            return this;
        }

        public Builder createTime(Date val) {
            createTime = val;
            return this;
        }

        public Builder lastUpdate(Date val) {
            lastUpdate = val;
            return this;
        }

        public Builder times(Integer val) {
            times = val;
            return this;
        }

        public WxActivity build() {
            return new WxActivity(this);
        }
    }
}
