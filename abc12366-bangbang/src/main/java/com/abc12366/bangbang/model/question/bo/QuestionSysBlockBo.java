package com.abc12366.bangbang.model.question.bo;

/**
 * @Author liuQi
 * @Date 2017/9/19 11:54
 */
public class QuestionSysBlockBo {

    /**PK**/
    private String id;

    /**内容**/
    private String content;

    /**内容状态**/
    private String status;

    /**来源id**/
    private String sourceId;

    /**来源类型**/
    private String sourceType;

    /**创建时间**/
    private java.util.Date createTime;

    /**审核用户**/
    private String updateAdmin;

    /**审核时间**/
    private java.util.Date updateTime;

    public String getContent() {
        return content;
    }

    public QuestionSysBlockBo setContent(String content) {
        this.content = content;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public QuestionSysBlockBo setStatus(String status) {
        this.status = status;
        return this;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public void setSourceId(String sourceId){
        this.sourceId = sourceId;
    }

    public String getSourceId(){
        return this.sourceId;
    }

    public void setSourceType(String sourceType){
        this.sourceType = sourceType;
    }

    public String getSourceType(){
        return this.sourceType;
    }

    public void setCreateTime(java.util.Date createTime){
        this.createTime = createTime;
    }

    public java.util.Date getCreateTime(){
        return this.createTime;
    }

    public void setUpdateAdmin(String updateAdmin){
        this.updateAdmin = updateAdmin;
    }

    public String getUpdateAdmin(){
        return this.updateAdmin;
    }

    public void setUpdateTime(java.util.Date updateTime){
        this.updateTime = updateTime;
    }

    public java.util.Date getUpdateTime(){
        return this.updateTime;
    }


}
