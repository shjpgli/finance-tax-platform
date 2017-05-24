package com.abc12366.cms.model;
import java.io.Serializable;


/**
 * 
 * CMS内容计数表
 * 
 **/
@SuppressWarnings("serial")
public class ContentCount implements Serializable {

	/**contentId**varchar(64)**/
	private String contentId;

	/**总访问数**int(11)**/
	private Integer views;

	/**月访问数**int(11)**/
	private Integer viewsMonth;

	/**周访问数**int(11)**/
	private Integer viewsWeek;

	/**日访问数**int(11)**/
	private Integer viewsDay;

	/**总评论数**int(11)**/
	private Integer comments;

	/**月评论数**int(11)**/
	private Integer commentsMonth;

	/**周评论数**smallint(6)**/
	private Integer commentsWeek;

	/**日评论数**smallint(6)**/
	private Integer commentsDay;

	/**总下载数**int(11)**/
	private Integer downloads;

	/**月下载数**int(11)**/
	private Integer downloadsMonth;

	/**周下载数**smallint(6)**/
	private Integer downloadsWeek;

	/**日下载数**smallint(6)**/
	private Integer downloadsDay;

	/**总顶数**int(11)**/
	private Integer ups;

	/**月顶数**int(11)**/
	private Integer upsMonth;

	/**周顶数**smallint(6)**/
	private Integer upsWeek;

	/**日顶数**smallint(6)**/
	private Integer upsDay;

	/**总踩数**int(11)**/
	private Integer downs;



	public void setContentId(String contentId){
		this.contentId = contentId;
	}

	public String getContentId(){
		return this.contentId;
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

	public void setComments(Integer comments){
		this.comments = comments;
	}

	public Integer getComments(){
		return this.comments;
	}

	public void setCommentsMonth(Integer commentsMonth){
		this.commentsMonth = commentsMonth;
	}

	public Integer getCommentsMonth(){
		return this.commentsMonth;
	}

	public void setCommentsWeek(Integer commentsWeek){
		this.commentsWeek = commentsWeek;
	}

	public Integer getCommentsWeek(){
		return this.commentsWeek;
	}

	public void setCommentsDay(Integer commentsDay){
		this.commentsDay = commentsDay;
	}

	public Integer getCommentsDay(){
		return this.commentsDay;
	}

	public void setDownloads(Integer downloads){
		this.downloads = downloads;
	}

	public Integer getDownloads(){
		return this.downloads;
	}

	public void setDownloadsMonth(Integer downloadsMonth){
		this.downloadsMonth = downloadsMonth;
	}

	public Integer getDownloadsMonth(){
		return this.downloadsMonth;
	}

	public void setDownloadsWeek(Integer downloadsWeek){
		this.downloadsWeek = downloadsWeek;
	}

	public Integer getDownloadsWeek(){
		return this.downloadsWeek;
	}

	public void setDownloadsDay(Integer downloadsDay){
		this.downloadsDay = downloadsDay;
	}

	public Integer getDownloadsDay(){
		return this.downloadsDay;
	}

	public void setUps(Integer ups){
		this.ups = ups;
	}

	public Integer getUps(){
		return this.ups;
	}

	public void setUpsMonth(Integer upsMonth){
		this.upsMonth = upsMonth;
	}

	public Integer getUpsMonth(){
		return this.upsMonth;
	}

	public void setUpsWeek(Integer upsWeek){
		this.upsWeek = upsWeek;
	}

	public Integer getUpsWeek(){
		return this.upsWeek;
	}

	public void setUpsDay(Integer upsDay){
		this.upsDay = upsDay;
	}

	public Integer getUpsDay(){
		return this.upsDay;
	}

	public void setDowns(Integer downs){
		this.downs = downs;
	}

	public Integer getDowns(){
		return this.downs;
	}

}
