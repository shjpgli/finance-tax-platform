package com.abc12366.bangbang.model.question.bo;

/**
 * @Author liuQi
 * @Date 2017/10/20 17:55
 */
public class QuestionMedalBo {

    /**PK**/
    private String id;

    /**勋章头像**/
    private String image;

    /**勋章名称**/
    private String name;

    /**获取条件**/
    private String acquireCondition;

    /**描述**/
    private String description;

    /**启用，停用**/
    private Integer status;

    /* 获取勋章的人数 */
    private Integer ownerCnt;


    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public void setImage(String image){
        this.image = image;
    }

    public String getImage(){
        return this.image;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public String getAcquireCondition() {
        return acquireCondition;
    }

    public QuestionMedalBo setAcquireCondition(String acquireCondition) {
        this.acquireCondition = acquireCondition;
        return this;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }

    public void setStatus(Integer status){
        this.status = status;
    }

    public Integer getStatus(){
        return this.status;
    }

    public Integer getOwnerCnt() {
        return ownerCnt;
    }

    public QuestionMedalBo setOwnerCnt(Integer ownerCnt) {
        this.ownerCnt = ownerCnt;
        return this;
    }
}
