package com.abc12366.cms.model;

import java.io.Serializable;


/**
 * CMS内容扩展属性表
 **/
@SuppressWarnings("serial")
public class ContentAttr implements Serializable {

    /**
     * contentId**varchar(64)
     **/
    private String contentId;

    /**
     * 名称**varchar(30)
     **/
    private String attrName;

    /**
     * 值**varchar(255)
     **/
    private String attrValue;

    public String getContentId() {
        return this.contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getAttrName() {
        return this.attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrValue() {
        return this.attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

}
