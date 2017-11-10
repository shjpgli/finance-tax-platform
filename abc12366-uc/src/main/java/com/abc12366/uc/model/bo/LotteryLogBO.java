package com.abc12366.uc.model.bo;

import java.util.Date;

/**
 * Admin: lingsuzhi <554600654@qq.com.com>
 * Date: 2017-09-18
 */


public class LotteryLogBO{
    private String kuaidiGS;
    private String kuaididanhao;
    private String sendRemake ;
    private String sendRemake2;

    public String getKuaidiGS() {
        return kuaidiGS;
    }

    public void setKuaidiGS(String kuaidiGS) {
        this.kuaidiGS = kuaidiGS;
    }

    public String getKuaididanhao() {
        return kuaididanhao;
    }

    public void setKuaididanhao(String kuaididanhao) {
        this.kuaididanhao = kuaididanhao;
    }

    public String getSendRemake() {
        return sendRemake;
    }

    public void setSendRemake(String sendRemake) {
        this.sendRemake = sendRemake;
    }

    public String getSendRemake2() {
        return sendRemake2;
    }

    public void setSendRemake2(String sendRemake2) {
        this.sendRemake2 = sendRemake2;
    }

    private String addressId;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    private Boolean lotterySend;

    public Boolean getLotterySend() {
        return lotterySend;
    }

    public void setLotterySend(Boolean lotterySend) {
        this.lotterySend = lotterySend;
    }

    private String remake;
    private String activityName;
private String lotteryLevel;

    public String getLotteryLevel() {
        return lotteryLevel;
    }

    public void setLotteryLevel(String lotteryLevel) {
        this.lotteryLevel = lotteryLevel;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake;
    }
private String userName ;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
