package com.abc12366.cms.model.bo;
import java.io.Serializable;


/**
 * 
 * CMS栏目表
 * 
 **/
@SuppressWarnings("serial")
public class ChannelBo implements Serializable {

	/**channelId**varchar(64)**/
	private String channelId;

	/**模型ID**varchar(64)**/
	private String modelId;

	/**站点ID**varchar(64)**/
	private String siteId;

	/**站点路径**varchar(20)**/
	private String sitePath;

	/**域名**varchar(50)**/
	private String domain;

	/**内容类型**varchar(2000)**/
	private String contentType;

	/**父栏目ID**varchar(64)**/
	private String parentId;

	/**访问路径**varchar(30)**/
	private String channelPath;

	/**排列顺序**int(11)**/
	private Integer priority;

	/**是否显示**tinyint(1)**/
	private Integer isDisplay;

	/**栏目名称**varchar(100)**/
	private String channelName;

    /**外部链接供查询列表用**varchar(100)**/
    private String link;

	/**文章总数**int(11)**/
	private Integer cnt;



	public void setChannelId(String channelId){
		this.channelId = channelId;
	}

	public String getChannelId(){
		return this.channelId;
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

	public void setParentId(String parentId){
		this.parentId = parentId;
	}

	public String getParentId(){
		return this.parentId;
	}

	public void setChannelPath(String channelPath){
		this.channelPath = channelPath;
	}

	public String getChannelPath(){
		return this.channelPath;
	}

	public void setPriority(Integer priority){
		this.priority = priority;
	}

	public Integer getPriority(){
		return this.priority;
	}

	public void setIsDisplay(Integer isDisplay){
		this.isDisplay = isDisplay;
	}

	public Integer getIsDisplay(){
		return this.isDisplay;
	}

	public void setChannelName(String channelName){
		this.channelName = channelName;
	}

	public String getChannelName(){
		return this.channelName;
	}

	public Integer getCnt() {
		return cnt;
	}

	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}

	public String getSitePath() {
		return sitePath;
	}

	public void setSitePath(String sitePath) {
		this.sitePath = sitePath;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
