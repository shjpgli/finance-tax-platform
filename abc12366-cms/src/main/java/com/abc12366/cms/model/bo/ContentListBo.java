package com.abc12366.cms.model.bo;
import java.io.Serializable;
import java.util.Date;


/**
 *
 * CMS内容表
 * add by xieyanmao on 2017-4-25
 *
 **/
@SuppressWarnings("serial")
public class ContentListBo implements Serializable {

	/**内容ID**/
	private String contentId;

	/**标题**/
	private String title;

	/**标题颜色**varchar(10)**/
	private String titleColor;

	/**栏目ID**/
	private String channelId;

	/**栏目名称**/
	private String channelName;

	/**固顶级别**/
	private Integer topLevel;

	/**属性ID**/
	private String typeId;

	/**作者**/
	private String author;

	/**总访问数**/
	private String views;

	/**发布日期**/
	private Date releaseDate;

	/**状态(0:草稿;1:审核中;2:审核通过;3:回收站;4:投稿;5:归档)**/
	private Integer status;

	/**推荐级别**/
	private Integer recommendLevel;


	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleColor() {
		return titleColor;
	}

	public void setTitleColor(String titleColor) {
		this.titleColor = titleColor;
	}

	public Integer getRecommendLevel() {
		return recommendLevel;
	}

	public void setRecommendLevel(Integer recommendLevel) {
		this.recommendLevel = recommendLevel;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Integer getTopLevel() {
		return topLevel;
	}

	public void setTopLevel(Integer topLevel) {
		this.topLevel = topLevel;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getViews() {
		return views;
	}

	public void setViews(String views) {
		this.views = views;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
