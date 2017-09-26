package com.abc12366.uc.model.bo;

import java.util.Date;

/**
 * Admin: lingsuzhi <554600654@qq.com.com>
 * Date: 2017-09-21
 */


public class LotteryActivityprizeBO{

    private String activityName;
    private String lotteryName;
    public String getActivityName() {
        return activityName;
    }
    private String lotteryImage;

    public String getLotteryImage() {
        return lotteryImage;
    }

    public void setLotteryImage(String lotteryImage) {
        this.lotteryImage = lotteryImage;
    }
    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getLotteryName() {
        return lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }



 /** PK */
private String id;
 /** 抽奖活动id */
private String activityId;
 /** 奖品id */
private String lotteryId;
 /** 保留字段 */
private String val1;
 /** 描述 */
private String description;
 /** 数据状态 */
private Boolean status;
 /** 创建时间 */
private Date createTime;
 /** 修改时间 */
private Date lastTime;

public String getId(){
    return id;
}
public void setId(String id){
   this.id = id;
}
public String getActivityId(){
    return activityId;
}
public void setActivityId(String activityId){
   this.activityId = activityId;
}
public String getLotteryId(){
    return lotteryId;
}
public void setLotteryId(String lotteryId){
   this.lotteryId = lotteryId;
}
public String getVal1(){
    return val1;
}
public void setVal1(String val1){
   this.val1 = val1;
}
public String getDescription(){
    return description;
}
public void setDescription(String description){
   this.description = description;
}
public Boolean getStatus(){
    return status;
}
public void setStatus(Boolean status){
   this.status = status;
}
public Date getCreateTime(){
    return createTime;
}
public void setCreateTime(Date createTime){
   this.createTime = createTime;
}
public Date getLastTime(){
    return lastTime;
}
public void setLastTime(Date lastTime){
   this.lastTime = lastTime;
}

@Override
public String toString() {
return "LotteryActivityprize {\r\n"+
"\""+"id"+"\""+":"+"\""+id+"\""+",\r\n"+
"\""+"activityId"+"\""+":"+"\""+activityId+"\""+",\r\n"+
"\""+"lotteryId"+"\""+":"+"\""+lotteryId+"\""+",\r\n"+
"\""+"val1"+"\""+":"+"\""+val1+"\""+",\r\n"+
"\""+"description"+"\""+":"+"\""+description+"\""+",\r\n"+
"\""+"status"+"\""+":"+"\""+status+"\""+",\r\n"+
"\""+"createTime"+"\""+":"+"\""+createTime+"\""+",\r\n"+
"\""+"lastTime"+"\""+":"+"\""+lastTime+"\""+"}";
}
}
