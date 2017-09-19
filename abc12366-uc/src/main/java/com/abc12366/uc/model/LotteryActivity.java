package com.abc12366.uc.model;

import java.util.Date;

/**
 * Admin: lingsuzhi <554600654@qq.com.com>
 * Date: 2017-09-18
 */


public class LotteryActivity{
 /** key */
private String id;
 /** 名称 */
private String name;
 /** 奖品类别 */
private String types;
 /** 描述 */
private String description;
 /** 活动抽奖次数 */
private Integer count;
 /** 标签 */
private String label;
 /** 参与的用户等级 */
private String userLevel;
 /** 活动发布链接 */
private String url;
 /** 开始时间 */
private Date startTime;
 /** 结束时间 */
private Date endTime;
 /** 状态 */
private Integer status;
 /** 创建时间 */
private Date createTime;

public String getId(){
    return id;
}
public void setId(String id){
   this.id = id;
}
public String getName(){
    return name;
}
public void setName(String name){
   this.name = name;
}
public String getTypes(){
    return types;
}
public void setTypes(String types){
   this.types = types;
}
public String getDescription(){
    return description;
}
public void setDescription(String description){
   this.description = description;
}
public Integer getCount(){
    return count;
}
public void setCount(Integer count){
   this.count = count;
}
public String getLabel(){
    return label;
}
public void setLabel(String label){
   this.label = label;
}
public String getUserLevel(){
    return userLevel;
}
public void setUserLevel(String userLevel){
   this.userLevel = userLevel;
}
public String getUrl(){
    return url;
}
public void setUrl(String url){
   this.url = url;
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
public Integer getStatus(){
    return status;
}
public void setStatus(Integer status){
   this.status = status;
}
public Date getCreateTime(){
    return createTime;
}
public void setCreateTime(Date createTime){
   this.createTime = createTime;
}

@Override
public String toString() {
return "LotteryActivity {\r\n"+
"\""+"id"+"\""+":"+"\""+id+"\""+",\r\n"+
"\""+"name"+"\""+":"+"\""+name+"\""+",\r\n"+
"\""+"types"+"\""+":"+"\""+types+"\""+",\r\n"+
"\""+"description"+"\""+":"+"\""+description+"\""+",\r\n"+
"\""+"count"+"\""+":"+"\""+count+"\""+",\r\n"+
"\""+"label"+"\""+":"+"\""+label+"\""+",\r\n"+
"\""+"userLevel"+"\""+":"+"\""+userLevel+"\""+",\r\n"+
"\""+"url"+"\""+":"+"\""+url+"\""+",\r\n"+
"\""+"startTime"+"\""+":"+"\""+startTime+"\""+",\r\n"+
"\""+"endTime"+"\""+":"+"\""+endTime+"\""+",\r\n"+
"\""+"status"+"\""+":"+"\""+status+"\""+",\r\n"+
"\""+"createTime"+"\""+":"+"\""+createTime+"\""+"}";
}
}
