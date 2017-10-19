package com.abc12366.bangbang.model.question.bo;

/**
 * @Author liuQi
 * @Date 2017/9/19 11:50
 */
public class QuestionTipOffBo {

    /**PK**/
    private String id;

    /**举报内容**/
    private String content;

    /**举报原因**/
    private String reason;

    /**举报人ip**/
    private String createIP;

    /**举报人id**/
    private String createUser;

    /**举报人姓名**/
    private String createUsername;

    /**举报时间**/
    private java.util.Date createTime;

    /**来源id**/
    private String sourceId;

    /**来源类型（question：提问，answer：回答，comment：评论）**/
    private String sourceType;

    /**审核人id**/
    private String updateAdmin;

    /**审核时间**/
    private java.util.Date updateTime;

    /**内容    状态**/
    private String status;


    public String getContent() {
        return content;
    }

    public QuestionTipOffBo setContent(String content) {
        this.content = content;
        return this;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public String getCreateIP() {
        return createIP;
    }

    public QuestionTipOffBo setCreateIP(String createIP) {
        this.createIP = createIP;
        return this;
    }

    public void setReason(String reason){
        this.reason = reason;
    }

    public String getReason(){
        return this.reason;
    }

    public void setCreateUser(String createUser){
        this.createUser = createUser;
    }

    public String getCreateUser(){
        return this.createUser;
    }

    public void setCreateTime(java.util.Date createTime){
        this.createTime = createTime;
    }

    public java.util.Date getCreateTime(){
        return this.createTime;
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

    public String getStatus() {
        return status;
    }

    public QuestionTipOffBo setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getCreateUsername() {
        return createUsername;
    }

    public QuestionTipOffBo setCreateUsername(String createUsername) {
        this.createUsername = createUsername;
        return this;
    }
}
