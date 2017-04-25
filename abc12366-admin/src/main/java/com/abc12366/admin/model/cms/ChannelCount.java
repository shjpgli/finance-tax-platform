package com.abc12366.admin.model.cms;
import java.io.Serializable;


/**
 * 
 * CMS栏目访问量计数表
 * 
 **/
@SuppressWarnings("serial")
public class ChannelCount implements Serializable {

	/****/
	private String channelId;

	/**总访问数**/
	private Integer views;

	/**月访问数**/
	private Integer viewsMonth;

	/**周访问数**/
	private Integer viewsWeek;

	/**日访问数**/
	private Integer viewsDay;

	/**内容发布数**/
	private Integer contentCountTotal;

	/**内容今日发布数**/
	private Integer contentCountDay;

	/**内容本周发布数**/
	private Integer contentCountWeek;

	/**内容本月发布数**/
	private Integer contentCountMonth;

	/**内容今年发布数**/
	private Integer contentCountYear;



	public void setChannelId(String channelId){
		this.channelId = channelId;
	}

	public String getChannelId(){
		return this.channelId;
	}

	public void setViews(Integer views){
		this.views = views;
	}

	public Integer getViews(){
		return this.views;
	}

	public void setViewsMonth(Integer viewsMonth){
		this.viewsMonth = viewsMonth;
	}

	public Integer getViewsMonth(){
		return this.viewsMonth;
	}

	public void setViewsWeek(Integer viewsWeek){
		this.viewsWeek = viewsWeek;
	}

	public Integer getViewsWeek(){
		return this.viewsWeek;
	}

	public void setViewsDay(Integer viewsDay){
		this.viewsDay = viewsDay;
	}

	public Integer getViewsDay(){
		return this.viewsDay;
	}

	public void setContentCountTotal(Integer contentCountTotal){
		this.contentCountTotal = contentCountTotal;
	}

	public Integer getContentCountTotal(){
		return this.contentCountTotal;
	}

	public void setContentCountDay(Integer contentCountDay){
		this.contentCountDay = contentCountDay;
	}

	public Integer getContentCountDay(){
		return this.contentCountDay;
	}

	public void setContentCountWeek(Integer contentCountWeek){
		this.contentCountWeek = contentCountWeek;
	}

	public Integer getContentCountWeek(){
		return this.contentCountWeek;
	}

	public void setContentCountMonth(Integer contentCountMonth){
		this.contentCountMonth = contentCountMonth;
	}

	public Integer getContentCountMonth(){
		return this.contentCountMonth;
	}

	public void setContentCountYear(Integer contentCountYear){
		this.contentCountYear = contentCountYear;
	}

	public Integer getContentCountYear(){
		return this.contentCountYear;
	}

}
