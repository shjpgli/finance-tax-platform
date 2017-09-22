package com.abc12366.uc.model;

import java.util.Date;

/**
 * Admin: lingsuzhi <554600654@qq.com.com>
 * Date: 2017-09-18
 */


public class LotteryLog{
    private String remake;

    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake;
    }
 /** key */
private String id;
 /** 活动id */
private String activityId;
 /** 奖品id */
private String lotteryId;
 /** 抽奖时间 */
private Date createTime;
 /** 奖品名称 */
private String lotteryName;
 /** 是否中奖：0否，1是 */
private Integer isluck;
 /** 领取状态 */
private String state;
 /** 用户id */
private String userId;
 /** 消耗积分 */
private Integer upoint;
 /** ip */
private String ip;

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
public Date getCreateTime(){
    return createTime;
}
public void setCreateTime(Date createTime){
   this.createTime = createTime;
}
public String getLotteryName(){
    return lotteryName;
}
public void setLotteryName(String lotteryName){
   this.lotteryName = lotteryName;
}
public Integer getIsluck(){
    return isluck;
}
public void setIsluck(Integer isluck){
   this.isluck = isluck;
}
public String getState(){
    return state;
}
public void setState(String state){
   this.state = state;
}
public String getUserId(){
    return userId;
}
public void setUserId(String userId){
   this.userId = userId;
}
public Integer getUpoint(){
    return upoint;
}
public void setUpoint(Integer upoint){
   this.upoint = upoint;
}
public String getIp(){
    return ip;
}
public void setIp(String ip){
   this.ip = ip;
}

@Override
public String toString() {
return "LotteryLog {\r\n"+
"\""+"id"+"\""+":"+"\""+id+"\""+",\r\n"+
"\""+"activityId"+"\""+":"+"\""+activityId+"\""+",\r\n"+
"\""+"lotteryId"+"\""+":"+"\""+lotteryId+"\""+",\r\n"+
"\""+"createTime"+"\""+":"+"\""+createTime+"\""+",\r\n"+
"\""+"lotteryName"+"\""+":"+"\""+lotteryName+"\""+",\r\n"+
"\""+"isluck"+"\""+":"+"\""+isluck+"\""+",\r\n"+
"\""+"state"+"\""+":"+"\""+state+"\""+",\r\n"+
"\""+"userId"+"\""+":"+"\""+userId+"\""+",\r\n"+
"\""+"upoint"+"\""+":"+"\""+upoint+"\""+",\r\n"+
"\""+"ip"+"\""+":"+"\""+ip+"\""+"}";
}
}
