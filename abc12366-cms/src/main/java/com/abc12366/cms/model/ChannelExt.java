package com.abc12366.cms.model;
import java.io.Serializable;


/**
 * 
 * CMS栏目内容表
 * 
 **/
@SuppressWarnings("serial")
public class ChannelExt implements Serializable {

	/**channelId**varchar(64)**/
	private String channelId;

	/**名称**varchar(100)**/
	private String channelName;

	/**是否栏目静态化**char(1)**/
	private String isStaticChannel;

	/**是否内容静态化**char(1)**/
	private String isStaticContent;

	/**每页多少条记录**int(11)**/
	private Integer pageSize;

	/**外部链接**varchar(255)**/
	private String link;

	/**栏目页模板**varchar(100)**/
	private String tplChannel;

	/**内容页模板**varchar(100)**/
	private String tplContent;

	/**缩略图**varchar(100)**/
	private String titleImg;

	/**内容图**varchar(100)**/
	private String contentImg;

	/**内容是否有缩略图**tinyint(1)**/
	private Integer hasTitleImg;

	/**内容是否有内容图**tinyint(1)**/
	private Integer hasContentImg;

	/**内容标题图宽度**int(11)**/
	private Integer titleImgWidth;

	/**内容标题图高度**int(11)**/
	private Integer titleImgHeight;

	/**内容内容图宽度**int(11)**/
	private Integer contentImgWidth;

	/**内容内容图高度**int(11)**/
	private Integer contentImgHeight;

	/**评论(0:匿名;1:会员一次;2:关闭,3会员多次)**int(11)**/
	private Integer commentControl;

	/**顶踩(true:开放;false:关闭)**tinyint(1)**/
	private Integer allowUpdown;

	/**是否新窗口打开**tinyint(1)**/
	private Integer isBlank;

	/**分享(true:开放;false:关闭)**tinyint(1)**/
	private Integer allowShare;



	public void setChannelId(String channelId){
		this.channelId = channelId;
	}

	public String getChannelId(){
		return this.channelId;
	}

	public void setChannelName(String channelName){
		this.channelName = channelName;
	}

	public String getChannelName(){
		return this.channelName;
	}

	public void setIsStaticChannel(String isStaticChannel){
		this.isStaticChannel = isStaticChannel;
	}

	public String getIsStaticChannel(){
		return this.isStaticChannel;
	}

	public void setIsStaticContent(String isStaticContent){
		this.isStaticContent = isStaticContent;
	}

	public String getIsStaticContent(){
		return this.isStaticContent;
	}

	public void setPageSize(Integer pageSize){
		this.pageSize = pageSize;
	}

	public Integer getPageSize(){
		return this.pageSize;
	}

	public void setLink(String link){
		this.link = link;
	}

	public String getLink(){
		return this.link;
	}

	public void setTplChannel(String tplChannel){
		this.tplChannel = tplChannel;
	}

	public String getTplChannel(){
		return this.tplChannel;
	}

	public void setTplContent(String tplContent){
		this.tplContent = tplContent;
	}

	public String getTplContent(){
		return this.tplContent;
	}

	public void setTitleImg(String titleImg){
		this.titleImg = titleImg;
	}

	public String getTitleImg(){
		return this.titleImg;
	}

	public void setContentImg(String contentImg){
		this.contentImg = contentImg;
	}

	public String getContentImg(){
		return this.contentImg;
	}

	public void setHasTitleImg(Integer hasTitleImg){
		this.hasTitleImg = hasTitleImg;
	}

	public Integer getHasTitleImg(){
		return this.hasTitleImg;
	}

	public void setHasContentImg(Integer hasContentImg){
		this.hasContentImg = hasContentImg;
	}

	public Integer getHasContentImg(){
		return this.hasContentImg;
	}

	public void setTitleImgWidth(Integer titleImgWidth){
		this.titleImgWidth = titleImgWidth;
	}

	public Integer getTitleImgWidth(){
		return this.titleImgWidth;
	}

	public void setTitleImgHeight(Integer titleImgHeight){
		this.titleImgHeight = titleImgHeight;
	}

	public Integer getTitleImgHeight(){
		return this.titleImgHeight;
	}

	public void setContentImgWidth(Integer contentImgWidth){
		this.contentImgWidth = contentImgWidth;
	}

	public Integer getContentImgWidth(){
		return this.contentImgWidth;
	}

	public void setContentImgHeight(Integer contentImgHeight){
		this.contentImgHeight = contentImgHeight;
	}

	public Integer getContentImgHeight(){
		return this.contentImgHeight;
	}

	public void setCommentControl(Integer commentControl){
		this.commentControl = commentControl;
	}

	public Integer getCommentControl(){
		return this.commentControl;
	}

	public void setAllowUpdown(Integer allowUpdown){
		this.allowUpdown = allowUpdown;
	}

	public Integer getAllowUpdown(){
		return this.allowUpdown;
	}

	public void setIsBlank(Integer isBlank){
		this.isBlank = isBlank;
	}

	public Integer getIsBlank(){
		return this.isBlank;
	}

	public void setAllowShare(Integer allowShare){
		this.allowShare = allowShare;
	}

	public Integer getAllowShare(){
		return this.allowShare;
	}

}
