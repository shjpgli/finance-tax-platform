package com.abc12366.bangbang.model;
import java.io.Serializable;


/**
 * @Author liuqi
 * @Date 2017/8/2 17:58
 * 知识库表
 **/
public class KnowledgeBase{

	/**PK**/
	private String id;

	/**分类ID**/
	private String categoryCode;

	/**类别：QA问答，K知识**/
	private String type;

	/**推荐 QA(common常见,hot热点); K(top头条，hot热点)**/
	private String recommend;

	/**名称**/
	private String subject;

	/**内容**/
	private byte[] content;

	/**状态**/
	private Boolean status;

	/**生效时间**/
	private java.util.Date activeTime;

	/**知识来源**/
	private String source;

	/**是否对外开放**/
	private Boolean isOpen;

	/**浏览量**/
	private Long pv;

	/**有用投票数**/
	private Long usefulVotes;

	/**无用投票数**/
	private Long uselessVotes;

	/**分享数量**/
	private Long shareNum;

	/**创建时间**/
	private java.util.Date createTime;

	/**修改时间**/
	private java.util.Date updateTime;

	/**创建人**/
	private String createUser;

	/**修改人**/
	private String updateUser;



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return this.type;
	}

	public void setRecommend(String recommend){
		this.recommend = recommend;
	}

	public String getRecommend(){
		return this.recommend;
	}

	public void setSubject(String subject){
		this.subject = subject;
	}

	public String getSubject(){
		return this.subject;
	}

	public void setContent(byte[] content){
		this.content = content;
	}

	public byte[] getContent(){
		return this.content;
	}

	public void setStatus(Boolean status){
		this.status = status;
	}

	public Boolean getStatus(){
		return this.status;
	}

	public void setActiveTime(java.util.Date activeTime){
		this.activeTime = activeTime;
	}

	public java.util.Date getActiveTime(){
		return this.activeTime;
	}

	public void setSource(String source){
		this.source = source;
	}

	public String getSource(){
		return this.source;
	}

	public void setIsOpen(Boolean isOpen){
		this.isOpen = isOpen;
	}

	public Boolean getIsOpen(){
		return this.isOpen;
	}

	public void setPv(Long pv){
		this.pv = pv;
	}

	public Long getPv(){
		return this.pv;
	}

	public void setUsefulVotes(Long usefulVotes){
		this.usefulVotes = usefulVotes;
	}

	public Long getUsefulVotes(){
		return this.usefulVotes;
	}

	public void setUselessVotes(Long uselessVotes){
		this.uselessVotes = uselessVotes;
	}

	public Long getUselessVotes(){
		return this.uselessVotes;
	}

	public void setShareNum(Long shareNum){
		this.shareNum = shareNum;
	}

	public Long getShareNum(){
		return this.shareNum;
	}

	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}

	public java.util.Date getUpdateTime(){
		return this.updateTime;
	}

	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}

	public String getCreateUser(){
		return this.createUser;
	}

	public void setUpdateUser(String updateUser){
		this.updateUser = updateUser;
	}

	public String getUpdateUser(){
		return this.updateUser;
	}

}
