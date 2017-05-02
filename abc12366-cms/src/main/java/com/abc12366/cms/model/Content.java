package com.abc12366.cms.model;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;


/**
 *
 * CMS内容表
 * add by xieyanmao on 2017-4-25
 *
 **/
@SuppressWarnings("serial")
public class Content implements Serializable {

	/****/
	private String contentId;

	/**栏目ID**/
	@NotEmpty
	private String channelId;

	/**属性ID**/
	private String typeId;

	/**模型ID**/
	private String modelId;

	/**站点ID**/
	private String siteId;

	/**排序日期**/
	private java.util.Date sortDate;

	/**固顶级别**/
	private Integer topLevel;

	/**是否有标题图**/
	private Integer hasTitleImg;

	/**是否推荐**/
	private Integer isRecommend;

	/**状态(0:草稿;1:审核中;2:审核通过;3:回收站;4:投稿;5:归档)**/
	private Integer status;

	/**日访问数**/
	private Integer viewsDay;

	/**日评论数**/
	private Integer commentsDay;

	/**日下载数**/
	private Integer downloadsDay;

	/**日顶数**/
	private Integer upsDay;

	/**推荐级别**/
	private Integer recommendLevel;



	public void setContentId(String contentId){
		this.contentId = contentId;
	}

	public String getContentId(){
		return this.contentId;
	}

	public void setChannelId(String channelId){
		this.channelId = channelId;
	}

	public String getChannelId(){
		return this.channelId;
	}

	public void setTypeId(String typeId){
		this.typeId = typeId;
	}

	public String getTypeId(){
		return this.typeId;
	}

	public void setModelId(String modelId){
		this.modelId = modelId;
	}

	public String getModelId(){
		return this.modelId;
	}

	public void setSiteId(String siteId){
		this.siteId = siteId;
	}

	public String getSiteId(){
		return this.siteId;
	}

	public void setSortDate(java.util.Date sortDate){
		this.sortDate = sortDate;
	}

	public java.util.Date getSortDate(){
		return this.sortDate;
	}

	public void setTopLevel(Integer topLevel){
		this.topLevel = topLevel;
	}

	public Integer getTopLevel(){
		return this.topLevel;
	}

	public void setHasTitleImg(Integer hasTitleImg){
		this.hasTitleImg = hasTitleImg;
	}

	public Integer getHasTitleImg(){
		return this.hasTitleImg;
	}

	public void setIsRecommend(Integer isRecommend){
		this.isRecommend = isRecommend;
	}

	public Integer getIsRecommend(){
		return this.isRecommend;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setViewsDay(Integer viewsDay){
		this.viewsDay = viewsDay;
	}

	public Integer getViewsDay(){
		return this.viewsDay;
	}

	public void setCommentsDay(Integer commentsDay){
		this.commentsDay = commentsDay;
	}

	public Integer getCommentsDay(){
		return this.commentsDay;
	}

	public void setDownloadsDay(Integer downloadsDay){
		this.downloadsDay = downloadsDay;
	}

	public Integer getDownloadsDay(){
		return this.downloadsDay;
	}

	public void setUpsDay(Integer upsDay){
		this.upsDay = upsDay;
	}

	public Integer getUpsDay(){
		return this.upsDay;
	}

	public void setRecommendLevel(Integer recommendLevel){
		this.recommendLevel = recommendLevel;
	}

	public Integer getRecommendLevel(){
		return this.recommendLevel;
	}

}
