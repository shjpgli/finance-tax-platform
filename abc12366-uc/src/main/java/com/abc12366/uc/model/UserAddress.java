package com.abc12366.uc.model;
import java.io.Serializable;


/**
 *
 * �ջ���ַ��Ϣ
 *
 **/
@SuppressWarnings("serial")
public class UserAddress implements Serializable {

    /**PK**/
    private String id;

    /**�û�ID**/
    private String userId;

    /**�ջ�������**/
    private String name;

    /**ʡ**/
    private String province;

    /**��**/
    private String city;

    /**��**/
    private String area;

    /**��ϸ��ַ**/
    private String detail;

    /**�ֻ�����**/
    private String phone;

    /**�Ƿ�Ĭ�ϵ�ַ**/
    private Integer isDefault;

    /**��ַ״̬**/
    private Integer status;

    /****/
    private java.util.Date createTime;

    /****/
    private java.util.Date lastUpdate;



    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getUserId(){
        return this.userId;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setProvince(String province){
        this.province = province;
    }

    public String getProvince(){
        return this.province;
    }

    public void setCity(String city){
        this.city = city;
    }

    public String getCity(){
        return this.city;
    }

    public void setArea(String area){
        this.area = area;
    }

    public String getArea(){
        return this.area;
    }

    public void setDetail(String detail){
        this.detail = detail;
    }

    public String getDetail(){
        return this.detail;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getPhone(){
        return this.phone;
    }

    public void setIsDefault(Integer isDefault){
        this.isDefault = isDefault;
    }

    public Integer getIsDefault(){
        return this.isDefault;
    }

    public void setStatus(Integer status){
        this.status = status;
    }

    public Integer getStatus(){
        return this.status;
    }

    public void setCreateTime(java.util.Date createTime){
        this.createTime = createTime;
    }

    public java.util.Date getCreateTime(){
        return this.createTime;
    }

    public void setLastUpdate(java.util.Date lastUpdate){
        this.lastUpdate = lastUpdate;
    }

    public java.util.Date getLastUpdate(){
        return this.lastUpdate;
    }

}
