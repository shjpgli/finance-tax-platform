package com.abc12366.uc.model;

import java.util.Date;

/**
 * Admin: lingsuzhi <554600654@qq.com.com>
 * Date: 2017-09-06
 */


public class LotteryLog{
 /** key */
private String id;
 /** 奖品id */
private String lotteryId;
 /** 用户id */
private String userId;
 /** 谢谢参与：0否，1是 */
private Integer notluck;
 /** 消耗积分 */
private Integer upoint;
 /** 发货状态 */
private String state;
 /** 创建时间 */
private Date createTime;

public String getId(){
    return id;
}
public void setId(String id){
   this.id = id;
}
public String getLotteryId(){
    return lotteryId;
}
public void setLotteryId(String lotteryId){
   this.lotteryId = lotteryId;
}
public String getUserId(){
    return userId;
}
public void setUserId(String userId){
   this.userId = userId;
}
public Integer getNotluck(){
    return notluck;
}
public void setNotluck(Integer notluck){
   this.notluck = notluck;
}
public Integer getUpoint(){
    return upoint;
}
public void setUpoint(Integer upoint){
   this.upoint = upoint;
}
public String getState(){
    return state;
}
public void setState(String state){
   this.state = state;
}
public Date getCreateTime(){
    return createTime;
}
public void setCreateTime(Date createTime){
   this.createTime = createTime;
}

@Override
public String toString() {
return "LotteryLog {\r\n"+
"\""+"id"+"\""+":"+"\""+id+"\""+",\r\n"+
"\""+"lotteryId"+"\""+":"+"\""+lotteryId+"\""+",\r\n"+
"\""+"userId"+"\""+":"+"\""+userId+"\""+",\r\n"+
"\""+"notluck"+"\""+":"+"\""+notluck+"\""+",\r\n"+
"\""+"upoint"+"\""+":"+"\""+upoint+"\""+",\r\n"+
"\""+"state"+"\""+":"+"\""+state+"\""+",\r\n"+
"\""+"createTime"+"\""+":"+"\""+createTime+"\""+"}";
}
}
