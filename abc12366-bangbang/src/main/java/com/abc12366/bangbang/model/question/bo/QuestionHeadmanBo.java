package com.abc12366.bangbang.model.question.bo;

import java.util.Date;
import java.util.List;

/**
 * @Author liuQi
 * @Date 2017/9/22 18:07
 */
public class QuestionHeadmanBo {

    /**PK**/
    private String id;

    /**掌门人名称**/
    private String name;

    /**掌门人电话**/
    private String phone;

    /**申请理由**/
    private String applyReason;

    /**申请时间**/
    private java.util.Date createTime;

    /**掌门人登录用户uc_user id**/
    private String userId;

    /**状态（apply:申请,success:通过,refuse:拒绝）**/
    private String status;

    /**修改时间**/
    private java.util.Date updateTime;

    /**后台操作人**/
    private String updateAdmin;

    /**分类id集合**/
    private List<String> classifyIds;

    /**分类名称集合**/
    private List<String> classifyNames;


    public String getId() {
        return id;
    }

    public QuestionHeadmanBo setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public QuestionHeadmanBo setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public QuestionHeadmanBo setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getApplyReason() {
        return applyReason;
    }

    public QuestionHeadmanBo setApplyReason(String applyReason) {
        this.applyReason = applyReason;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public QuestionHeadmanBo setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public QuestionHeadmanBo setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public QuestionHeadmanBo setStatus(String status) {
        this.status = status;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public QuestionHeadmanBo setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String getUpdateAdmin() {
        return updateAdmin;
    }

    public QuestionHeadmanBo setUpdateAdmin(String updateAdmin) {
        this.updateAdmin = updateAdmin;
        return this;
    }

    public List<String> getClassifyIds() {
        return classifyIds;
    }

    public QuestionHeadmanBo setClassifyIds(List<String> classifyIds) {
        this.classifyIds = classifyIds;
        return this;
    }

    public List<String> getClassifyNames() {
        return classifyNames;
    }

    public QuestionHeadmanBo setClassifyNames(List<String> classifyNames) {
        this.classifyNames = classifyNames;
        return this;
    }
}
