package com.abc12366.uc.model.bo;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.io.Serializable;


/**
 *
 * 收货地址信息
 *
 **/
@SuppressWarnings("serial")
public class UserAddressBO implements Serializable {

    /**PK**/
    private String id;

    /**用户ID**/
    private String userId;

    /**收货人姓名**/
    @NotEmpty
    @Size(min = 2, max = 100)
    private String name;

    /**省**/
    @Size(min = 0, max = 15)
    private String province;

    /**市**/
    @Size(min = 0, max = 15)
    private String city;

    /**区**/
    @Size(min = 0, max = 15)
    private String area;

    /**详细地址**/
    @Size(min = 0, max = 100)
    private String detail;

    /**手机号码**/
    private String phone;

    /**是否默认地址**/
    private Boolean isDefault;

    /**地址状态**/
    private Boolean status;

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

    public void setIsDefault(Boolean isDefault){
        this.isDefault = isDefault;
    }

    public Boolean getIsDefault(){
        return this.isDefault;
    }

    public void setStatus(Boolean status){
        this.status = status;
    }

    public Boolean getStatus(){
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
