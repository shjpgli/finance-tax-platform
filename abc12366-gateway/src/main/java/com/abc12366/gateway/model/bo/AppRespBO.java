package com.abc12366.gateway.model.bo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-05 1:03 PM
 * @since 1.0.0
 */
public class AppRespBO {

    // 主键UUID
    private String id;
    // 应用名称
    private String name;
    // 授权时间起
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    // 授权时间止
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    // 描述信息
    private String remark;
    // 创建时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    // 修改时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdate;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    @Override
    public String toString() {
        return "AppRespBO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
