package com.abc12366.uc.model.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * User: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-24
 * Time: 10:26
 */
public class SysTaskInsertAndUpdateBO {
    @NotEmpty
    @Size(max = 200)
    private String name;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    @Size(max = 4000)
    private String rule;
    private int points;
    @Size(max = 1)
    private String type;
    @NotNull
    private Boolean status;
    @Size(max = 200)
    private String imageUrl;

    public SysTaskInsertAndUpdateBO() {
    }

    public SysTaskInsertAndUpdateBO(String name, Date startTime, Date endTime, String rule, int points, String type, Boolean status, String imageUrl) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.rule = rule;
        this.points = points;
        this.type = type;
        this.status = status;
        this.imageUrl = imageUrl;
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

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "SysTaskInsertBO{" +
                "name='" + name + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", rule='" + rule + '\'' +
                ", points=" + points +
                ", type='" + type + '\'' +
                ", status=" + status +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
