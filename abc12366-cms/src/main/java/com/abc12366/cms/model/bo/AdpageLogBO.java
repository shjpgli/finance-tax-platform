package com.abc12366.cms.model.bo;

import java.util.Date;

/**
 * Admin: lingsuzhi <554600654@qq.com.com>
 * Date: 2017-08-25
 */


public class AdpageLogBO{
 /** ID */
private String id;
 /** 广告Id */
private String adPageId;
 /** 用户Id */
private String userId;
 /** 访问IP */
private String ip;
 /** 创建时间 */
private Date createTime;
    public String  adPageName;
    public String    userName;

    public String getAdPageName() {
        return adPageName;
    }

    public void setAdPageName(String adPageName) {
        this.adPageName = adPageName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId(){
    return id;
}
public void setId(String id){
   this.id = id;
}
public String getAdPageId(){
    return adPageId;
}
public void setAdPageId(String adPageId){
   this.adPageId = adPageId;
}
public String getUserId(){
    return userId;
}
public void setUserId(String userId){
   this.userId = userId;
}
public String getIp(){
    return ip;
}
public void setIp(String ip){
   this.ip = ip;
}
public Date getCreateTime(){
    return createTime;
}
public void setCreateTime(Date createTime){
   this.createTime = createTime;
}

}
