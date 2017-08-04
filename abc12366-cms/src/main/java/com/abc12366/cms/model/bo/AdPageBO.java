package com.abc12366.cms.model.bo;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * ���ͼƬ����
 * User: yuanluo<435720953@qq.com.com>
 * Date: 2017-07-26
 * Time: 11:06
 */
public class AdPageBO {

    private String id;

    @Size(max = 128)
    private String name;
    @Size(max = 128)
    private String url;
    @Size(max = 128)
    private String link;

    private Integer sort;
    @NotNull
    private Boolean showName;
   @NotNull
    private Boolean status;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp lastUpdate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Boolean getShowName() {
        return showName;
    }

    public void setShowName(Boolean showName) {
        this.showName = showName;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public AdPageBO() {
    }

    public AdPageBO(String id,String name, String url,String link, Integer sort, Boolean showName, Boolean status,Timestamp createTime,Timestamp lastUpdate) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.link = link;
        this.sort = sort;
        this.showName = showName;
        this.status = status;
        this.createTime = createTime;
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "ExperienceRuleInsertBO{" +
                "id='" + id + '\'' +
                ",name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", link=" + link +
                ", sort='" + sort + '\'' +
                ", showName='" + showName + '\'' +
                ", status=" + status + '\'' +
                ", createTime=" + createTime + '\'' +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}