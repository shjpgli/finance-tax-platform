package com.abc12366.uc.model.weixin.bo.message;

import org.hibernate.validator.constraints.NotEmpty;

public class Article {
	@NotEmpty
	private String newsId;
	@NotEmpty
	private String title;
	@NotEmpty
	private String description;
	@NotEmpty
	private String picUrl;
	@NotEmpty
	private String url;
	public String getNewsId() {
		return newsId;
	}
	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	

}
