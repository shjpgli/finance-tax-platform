package com.abc12366.message.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

/**
 * 批量业务消息对象
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-07-27 11:21 AM
 * @since 1.0.0
 */
public class BusinessBatchMessage {

    private String id;

    @NotNull
    private List<String> userIds;

    @NotEmpty
    @Length(min = 1, max = 64)
    private String businessId;

    @NotEmpty
    @Length(min = 1, max = 500)
    private String content;

    @Length(min = 1, max = 1)
    private String status;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp lastUpdate;

    @Length(min = 1, max = 32)
    private String type;

    public BusinessBatchMessage() {
    }

    private BusinessBatchMessage(Builder builder) {
        setId(builder.id);
        setUserIds(builder.userIds);
        setBusinessId(builder.businessId);
        setContent(builder.content);
        setStatus(builder.status);
        setCreateTime(builder.createTime);
        setLastUpdate(builder.lastUpdate);
        setType(builder.type);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BusinessBatchMessage{" +
                "id='" + id + '\'' +
                ", userIds=" + userIds +
                ", businessId='" + businessId + '\'' +
                ", content='" + content + '\'' +
                ", status='" + status + '\'' +
                ", createTime=" + createTime +
                ", lastUpdate=" + lastUpdate +
                ", type='" + type + '\'' +
                '}';
    }

    public static final class Builder {
        private String id;
        private List<String> userIds;
        private String businessId;
        private String content;
        private String status;
        private Timestamp createTime;
        private Timestamp lastUpdate;
        private String type;

        public Builder() {
        }

        public Builder id(String val) {
            id = val;
            return this;
        }

        public Builder userIds(List<String> val) {
            userIds = val;
            return this;
        }

        public Builder businessId(String val) {
            businessId = val;
            return this;
        }

        public Builder content(String val) {
            content = val;
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

        public Builder type(String val) {
            type = val;
            return this;
        }

        public BusinessBatchMessage build() {
            return new BusinessBatchMessage(this);
        }
    }
}
