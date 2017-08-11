package com.abc12366.bangbang.model.bo;

/**
 * @Author liuqi
 * @Date 2017/8/3 10:56
 */
public class KnowledgeBaseParamBO {

    private String categoryCode;  //知识库 分类编号

    private String type;         //知识库  类别（知识 和 问题）

    private String keywords;    //关键字（知识库标题 模糊匹配）

    private Boolean isOpen;     //是否对外开放

    public KnowledgeBaseParamBO(String categoryCode, String type, String keywords, Boolean isOpen) {
        this.categoryCode = categoryCode;
        this.type = type;
        this.keywords = keywords;
        this.isOpen = isOpen;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
