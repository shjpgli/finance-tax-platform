package com.abc12366.cms.model;
import java.io.Serializable;


/**
 * 
 * CMS站点表
 * 
 **/
@SuppressWarnings("serial")
public class Site implements Serializable {

	/**siteId**varchar(64)**/
	private String siteId;

	/**域名**varchar(50)**/
	private String domain;

	/**路径**varchar(20)**/
	private String sitePath;

	/**网站名称**varchar(100)**/
	private String siteName;

	/**简短名称**varchar(100)**/
	private String shortName;

	/**协议**varchar(20)**/
	private String protocol;

	/**动态页后缀**varchar(10)**/
	private String dynamicSuffix;

	/**静态页后缀**varchar(10)**/
	private String staticSuffix;

	/**静态页存放目录**varchar(50)**/
	private String staticDir;

	/**是否使用将首页放在根目录下**char(1)**/
	private String isIndexToRoot;

	/**是否静态化首页**char(1)**/
	private String isStaticIndex;

	/**后台本地化**varchar(10)**/
	private String localeAdmin;

	/**前台本地化**varchar(10)**/
	private String localeFront;

	/**模板方案**varchar(50)**/
	private String tplSolution;

	/**终审级别**tinyint(4)**/
	private Integer finalStep;

	/**审核后(1:不能修改删除;2:修改后退回;3:修改后不变)**tinyint(4)**/
	private Integer afterCheck;

	/**是否使用相对路径**char(1)**/
	private String isRelativePath;

	/**是否开启回收站**char(1)**/
	private String isRecycleOn;

	/**域名别名**varchar(255)**/
	private String domainAlias;

	/**域名重定向**varchar(255)**/
	private String domainRedirect;

	/**首页模板**varchar(255)**/
	private String tplIndex;

	/**站点关键字**varchar(255)**/
	private String keywords;

	/**站点描述**varchar(255)**/
	private String description;

	/**站点Logo**varchar(255)**/
	private String siteLogo;

	/**版权说明**varchar(100)**/
	private String copyrightExplain;

	/**备案号**varchar(30)**/
	private String recordNum;

	/**资源是否自动同步**tinyint(1)**/
	private Integer resourceSync;

	/**静态页是否自动同步**tinyint(1)**/
	private Integer staticSync;

	/**静态发布**tinyint(1)**/
	private Integer staticIssue;

	/**站点状态**tinyint(1)**/
	private Integer siteStatus;



	public void setSiteId(String siteId){
		this.siteId = siteId;
	}

	public String getSiteId(){
		return this.siteId;
	}

	public void setDomain(String domain){
		this.domain = domain;
	}

	public String getDomain(){
		return this.domain;
	}

	public void setSitePath(String sitePath){
		this.sitePath = sitePath;
	}

	public String getSitePath(){
		return this.sitePath;
	}

	public void setSiteName(String siteName){
		this.siteName = siteName;
	}

	public String getSiteName(){
		return this.siteName;
	}

	public void setShortName(String shortName){
		this.shortName = shortName;
	}

	public String getShortName(){
		return this.shortName;
	}

	public void setProtocol(String protocol){
		this.protocol = protocol;
	}

	public String getProtocol(){
		return this.protocol;
	}

	public void setDynamicSuffix(String dynamicSuffix){
		this.dynamicSuffix = dynamicSuffix;
	}

	public String getDynamicSuffix(){
		return this.dynamicSuffix;
	}

	public void setStaticSuffix(String staticSuffix){
		this.staticSuffix = staticSuffix;
	}

	public String getStaticSuffix(){
		return this.staticSuffix;
	}

	public void setStaticDir(String staticDir){
		this.staticDir = staticDir;
	}

	public String getStaticDir(){
		return this.staticDir;
	}

	public void setIsIndexToRoot(String isIndexToRoot){
		this.isIndexToRoot = isIndexToRoot;
	}

	public String getIsIndexToRoot(){
		return this.isIndexToRoot;
	}

	public void setIsStaticIndex(String isStaticIndex){
		this.isStaticIndex = isStaticIndex;
	}

	public String getIsStaticIndex(){
		return this.isStaticIndex;
	}

	public void setLocaleAdmin(String localeAdmin){
		this.localeAdmin = localeAdmin;
	}

	public String getLocaleAdmin(){
		return this.localeAdmin;
	}

	public void setLocaleFront(String localeFront){
		this.localeFront = localeFront;
	}

	public String getLocaleFront(){
		return this.localeFront;
	}

	public void setTplSolution(String tplSolution){
		this.tplSolution = tplSolution;
	}

	public String getTplSolution(){
		return this.tplSolution;
	}

	public void setFinalStep(Integer finalStep){
		this.finalStep = finalStep;
	}

	public Integer getFinalStep(){
		return this.finalStep;
	}

	public void setAfterCheck(Integer afterCheck){
		this.afterCheck = afterCheck;
	}

	public Integer getAfterCheck(){
		return this.afterCheck;
	}

	public void setIsRelativePath(String isRelativePath){
		this.isRelativePath = isRelativePath;
	}

	public String getIsRelativePath(){
		return this.isRelativePath;
	}

	public void setIsRecycleOn(String isRecycleOn){
		this.isRecycleOn = isRecycleOn;
	}

	public String getIsRecycleOn(){
		return this.isRecycleOn;
	}

	public void setDomainAlias(String domainAlias){
		this.domainAlias = domainAlias;
	}

	public String getDomainAlias(){
		return this.domainAlias;
	}

	public void setDomainRedirect(String domainRedirect){
		this.domainRedirect = domainRedirect;
	}

	public String getDomainRedirect(){
		return this.domainRedirect;
	}

	public void setTplIndex(String tplIndex){
		this.tplIndex = tplIndex;
	}

	public String getTplIndex(){
		return this.tplIndex;
	}

	public void setKeywords(String keywords){
		this.keywords = keywords;
	}

	public String getKeywords(){
		return this.keywords;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return this.description;
	}

	public void setSiteLogo(String siteLogo){
		this.siteLogo = siteLogo;
	}

	public String getSiteLogo(){
		return this.siteLogo;
	}

	public void setCopyrightExplain(String copyrightExplain){
		this.copyrightExplain = copyrightExplain;
	}

	public String getCopyrightExplain(){
		return this.copyrightExplain;
	}

	public void setRecordNum(String recordNum){
		this.recordNum = recordNum;
	}

	public String getRecordNum(){
		return this.recordNum;
	}

	public void setResourceSync(Integer resourceSync){
		this.resourceSync = resourceSync;
	}

	public Integer getResourceSync(){
		return this.resourceSync;
	}

	public void setStaticSync(Integer staticSync){
		this.staticSync = staticSync;
	}

	public Integer getStaticSync(){
		return this.staticSync;
	}

	public void setStaticIssue(Integer staticIssue){
		this.staticIssue = staticIssue;
	}

	public Integer getStaticIssue(){
		return this.staticIssue;
	}

	public void setSiteStatus(Integer siteStatus){
		this.siteStatus = siteStatus;
	}

	public Integer getSiteStatus(){
		return this.siteStatus;
	}

}
