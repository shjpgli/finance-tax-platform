package com.abc12366.bangbang.model.bo;

/**
 * @Author liuqi
 * @Date 2017/8/3 10:56
 */
public class KnowledgeBaseParamBO {

    private String categoryId;  //知识库 分类ID

    private String keywords;    //关键字（知识库标题 模糊匹配）

    public KnowledgeBaseParamBO(String categoryId, String keywords){
        this.categoryId = categoryId;
        this.keywords = keywords;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
