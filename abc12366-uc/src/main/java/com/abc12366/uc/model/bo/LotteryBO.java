package com.abc12366.uc.model.bo;

import java.util.Date;

/**
 * Admin: lingsuzhi <554600654@qq.com.com>
 * Date: 2017-09-20
 */


public class LotteryBO{

 /** key */
private String id;
 /** 活动id */
private String activityId;
 /** 奖项等级 */
private Integer level;
 /** 奖品总数 */
private Integer stock;
 /** 已发放数量 */
private Integer count;
 /** 时段投放个数 */
private String timeStockId;
 /** 时间段已发数量 */
private Integer timeCount;
 /** 中奖限制,0标识不限制 */
private Integer limits;
 /** 名称 */
private String name;
 /** 描述 */
private String description;
 /** 图片 */
private String image;
 /** 成本价 */
private Double cost;
 /** 奖品类别 */
private String types;
 /** 开始时间 */
private Date startTime;
 /** 结束时间 */
private Date endTime;
 /** 概率 百分比 */
private Double luck;
 /** 创建时间 */
private Date createTime;
 /** 状态 */
private Boolean status;
 /** 是否邮寄 */
private Boolean send;

    /** 活动名称 */
private String activityName;

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

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
public Integer getLevel(){
    return level;
}
public void setLevel(Integer level){
   this.level = level;
}
public Integer getStock(){
    return stock;
}
public void setStock(Integer stock){
   this.stock = stock;
}
public Integer getCount(){
    return count;
}
public void setCount(Integer count){
   this.count = count;
}
public String getTimeStockId(){
    return timeStockId;
}
public void setTimeStockId(String timeStockId){
   this.timeStockId = timeStockId;
}
public Integer getTimeCount(){
    return timeCount;
}
public void setTimeCount(Integer timeCount){
   this.timeCount = timeCount;
}
public Integer getLimits(){
    return limits;
}
public void setLimits(Integer limits){
   this.limits = limits;
}
public String getName(){
    return name;
}
public void setName(String name){
   this.name = name;
}
public String getDescription(){
    return description;
}
public void setDescription(String description){
   this.description = description;
}
public String getImage(){
    return image;
}
public void setImage(String image){
   this.image = image;
}
public Double getCost(){
    return cost;
}
public void setCost(Double cost){
   this.cost = cost;
}
public String getTypes(){
    return types;
}
public void setTypes(String types){
   this.types = types;
}
public Date getStartTime(){
    return startTime;
}
public void setStartTime(Date startTime){
   this.startTime = startTime;
}
public Date getEndTime(){
    return endTime;
}
public void setEndTime(Date endTime){
   this.endTime = endTime;
}
public Double getLuck(){
    return luck;
}
public void setLuck(Double luck){
   this.luck = luck;
}
public Date getCreateTime(){
    return createTime;
}
public void setCreateTime(Date createTime){
   this.createTime = createTime;
}
public Boolean getStatus(){
    return status;
}
public void setStatus(Boolean status){
   this.status = status;
}
public Boolean getSend(){
    return send;
}
public void setSend(Boolean send){
   this.send = send;
}

@Override
public String toString() {
return "Lottery {\r\n"+
"\""+"id"+"\""+":"+"\""+id+"\""+",\r\n"+
"\""+"activityId"+"\""+":"+"\""+activityId+"\""+",\r\n"+
"\""+"level"+"\""+":"+"\""+level+"\""+",\r\n"+
"\""+"stock"+"\""+":"+"\""+stock+"\""+",\r\n"+
"\""+"count"+"\""+":"+"\""+count+"\""+",\r\n"+
"\""+"timeStockId"+"\""+":"+"\""+timeStockId+"\""+",\r\n"+
"\""+"timeCount"+"\""+":"+"\""+timeCount+"\""+",\r\n"+
"\""+"limits"+"\""+":"+"\""+limits+"\""+",\r\n"+
"\""+"name"+"\""+":"+"\""+name+"\""+",\r\n"+
"\""+"description"+"\""+":"+"\""+description+"\""+",\r\n"+
"\""+"image"+"\""+":"+"\""+image+"\""+",\r\n"+
"\""+"cost"+"\""+":"+"\""+cost+"\""+",\r\n"+
"\""+"types"+"\""+":"+"\""+types+"\""+",\r\n"+
"\""+"startTime"+"\""+":"+"\""+startTime+"\""+",\r\n"+
"\""+"endTime"+"\""+":"+"\""+endTime+"\""+",\r\n"+
"\""+"luck"+"\""+":"+"\""+luck+"\""+",\r\n"+
"\""+"createTime"+"\""+":"+"\""+createTime+"\""+",\r\n"+
"\""+"status"+"\""+":"+"\""+status+"\""+",\r\n"+
"\""+"send"+"\""+":"+"\""+send+"\""+"}";
}
}
