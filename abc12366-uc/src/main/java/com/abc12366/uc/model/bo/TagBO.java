package com.abc12366.uc.model.bo;

import java.util.Date;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-06-15
 * Time: 10:32
 */
public class TagBO {
    private String id;
    private String tagName;
    private boolean status;
    private Date createTime;
    private Date lastUpdate;
    private String category;
    private String description;
    private String rule;
    private String tagedCount;
    private int weight;

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public TagBO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getTagedCount() {
        return tagedCount;
    }

    public void setTagedCount(String tagedCount) {
        this.tagedCount = tagedCount;
    }

    @Override
    public String toString() {
        return "TagBO{" +
                "id='" + id + '\'' +
                ", tagName='" + tagName + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", lastUpdate=" + lastUpdate +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", rule='" + rule + '\'' +
                ", tagedCount='" + tagedCount + '\'' +
                ", weight=" + weight +
                '}';
    }
}
