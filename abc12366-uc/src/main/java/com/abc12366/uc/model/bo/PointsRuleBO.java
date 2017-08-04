package com.abc12366.uc.model.bo;

import java.util.Date;

/**
 * @author liuguiyao<435720953@qq.com.com>
 * @create 2017-05-16 10:18 PM
 * @since 2.0.0
 */
public class PointsRuleBO {
    private String id;
    private String name;
    private String code;
    private int points;
    private String description;
    private String type;
    private boolean status;
    private Date createTime;
    private Date lastUpdate;

    public PointsRuleBO() {
    }

    public PointsRuleBO(String id, String name, String code, int points, String description, String type, boolean
            status, Date createTime, Date lastUpdate) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.points = points;
        this.description = description;
        this.type = type;
        this.status = status;
        this.createTime = createTime;
        this.lastUpdate = lastUpdate;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
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

    @Override
    public String toString() {
        return "UPointsRuleBO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", points=" + points +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
