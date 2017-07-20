package com.abc12366.cms.model.bo;
import java.io.Serializable;
import java.util.Date;


/**
 * 
 * CMS活动表
 * 
 **/
@SuppressWarnings("serial")
public class EventBo implements Serializable {

	/**活动ID**varchar(64)**/
	private String eventId;

	/**活动分类**varchar(64)**/
	private String category;

	/**活动形式**varchar(200)**/
	private String shape;

	/**活动名称**varchar(200)**/
	private String title;

	/**省份/市**varchar(50)**/
	private String province;

	/**市/县**varchar(50)**/
	private String city;

	/**活动地点**varchar(200)**/
	private String address;

	/**活动图片**varchar(200)**/
	private String picture;

	/**创建时间**datetime**/
	private java.util.Date createtime;

	/**修改时间**datetime**/
	private java.util.Date updatetime;

	/**开始时间**datetime**/
	private java.util.Date begintime;

	/**结束时间**datetime**/
	private java.util.Date endtime;

	/**活动标签**varchar(200)**/
	private String tag;

	/**活动摘要**varchar(500)**/
	private String summary;

	/**详细内容**longtext**/
	private String description;

	/**参与人数限制**int(11)**/
	private Integer peoppleNum;

	/**是否审核**tinyint(1)**/
	private Integer isCheck;

	/**是否限制用户等级**tinyint(1)**/
	private Integer isUserGrade;

	/**用户等级限制**varchar(64)**/
	private String userGrade;

	/**活动主办方ID**varchar(200)**/
	private String sponsorId;

    /**报名截止时间**datetime**/
    private java.util.Date bmendtime;

	/**活动状态**varchar(64)**/
	private String status;



	public void setEventId(String eventId){
		this.eventId = eventId;
	}

	public String getEventId(){
		return this.eventId;
	}

	public void setCategory(String category){
		this.category = category;
	}

	public String getCategory(){
		return this.category;
	}

	public void setShape(String shape){
		this.shape = shape;
	}

	public String getShape(){
		return this.shape;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return this.title;
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

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return this.address;
	}

	public void setPicture(String picture){
		this.picture = picture;
	}

	public String getPicture(){
		return this.picture;
	}

	public void setCreatetime(java.util.Date createtime){
		this.createtime = createtime;
	}

	public java.util.Date getCreatetime(){
		return this.createtime;
	}

	public void setUpdatetime(java.util.Date updatetime){
		this.updatetime = updatetime;
	}

	public java.util.Date getUpdatetime(){
		return this.updatetime;
	}

	public void setBegintime(java.util.Date begintime){
		this.begintime = begintime;
	}

	public java.util.Date getBegintime(){
		return this.begintime;
	}

	public void setEndtime(java.util.Date endtime){
		this.endtime = endtime;
	}

	public java.util.Date getEndtime(){
		return this.endtime;
	}

	public void setTag(String tag){
		this.tag = tag;
	}

	public String getTag(){
		return this.tag;
	}

	public void setSummary(String summary){
		this.summary = summary;
	}

	public String getSummary(){
		return this.summary;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return this.description;
	}

	public void setPeoppleNum(Integer peoppleNum){
		this.peoppleNum = peoppleNum;
	}

	public Integer getPeoppleNum(){
		return this.peoppleNum;
	}

	public void setIsCheck(Integer isCheck){
		this.isCheck = isCheck;
	}

	public Integer getIsCheck(){
		return this.isCheck;
	}

	public void setIsUserGrade(Integer isUserGrade){
		this.isUserGrade = isUserGrade;
	}

	public Integer getIsUserGrade(){
		return this.isUserGrade;
	}

	public void setUserGrade(String userGrade){
		this.userGrade = userGrade;
	}

	public String getUserGrade(){
		return this.userGrade;
	}

	public void setSponsorId(String sponsorId){
		this.sponsorId = sponsorId;
	}

	public String getSponsorId(){
		return this.sponsorId;
	}

    public Date getBmendtime() {
        return bmendtime;
    }

    public void setBmendtime(Date bmendtime) {
        this.bmendtime = bmendtime;
    }

    public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return this.status;
	}

}
