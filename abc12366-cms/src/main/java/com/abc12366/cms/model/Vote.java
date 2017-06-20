package com.abc12366.cms.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

/**
 * 投票模型
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-06-07 4:08 PM
 * @since 1.0.0
 */
public class Vote {

    private String id;

    // 投票名称
    @NotEmpty
    @Length(min = 2, max = 50)
    private String name;

    // 开始时间
    @NotNull
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp startTime;

    // 截止时间
    @NotNull
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp endTime;

    // 是否只有登录用户可以投票
    @NotNull
    private Boolean isLogin;

    // 投票通道
    @NotEmpty
    @Length(min = 1, max=10)
    private String channel;

    // 发布状态
    @NotNull
    private Boolean status;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp lastUpdate;

    private List<Subject> subjectList;

    // 参与人数（number of participants）
    private Integer nop;

    public Vote() {
    }

    public Vote(String id, String name, Timestamp startTime, Timestamp endTime, Boolean isLogin, Boolean status,
                String channel, Timestamp createTime, Timestamp lastUpdate) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isLogin = isLogin;
        this.channel = channel;
        this.status = status;
        this.createTime = createTime;
        this.lastUpdate = lastUpdate;
    }

    private Vote(Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setStartTime(builder.startTime);
        setEndTime(builder.endTime);
        setIsLogin(builder.isLogin);
        setStatus(builder.status);
        setCreateTime(builder.createTime);
        setLastUpdate(builder.lastUpdate);
        setChannel(builder.channel);
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
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

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Boolean getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Boolean isLogin) {
        this.isLogin = isLogin;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public Integer getNop() {
        return nop;
    }

    public void setNop(Integer nop) {
        this.nop = nop;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", isLogin=" + isLogin +
                ", status=" + status +
                ", channel=" + channel +
                ", nop=" + nop +
                ", createTime=" + createTime +
                ", lastUpdate=" + lastUpdate +
                '}';
    }

    public static final class Builder {
        private String id;
        private String name;
        private Timestamp startTime;
        private Timestamp endTime;
        private Boolean isLogin;
        private Boolean status;
        private String channel;
        private Timestamp createTime;
        private Timestamp lastUpdate;

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

        public Builder startTime(Timestamp val) {
            startTime = val;
            return this;
        }

        public Builder endTime(Timestamp val) {
            endTime = val;
            return this;
        }

        public Builder isLogin(Boolean val) {
            isLogin = val;
            return this;
        }

        public Builder status(Boolean val) {
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

        public Builder channel(String val) {
            channel = val;
            return this;
        }

        public Vote build() {
            return new Vote(this);
        }
    }
}
