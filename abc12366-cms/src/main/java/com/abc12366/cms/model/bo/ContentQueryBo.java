package com.abc12366.cms.model.bo;
import java.io.Serializable;
import java.util.List;


/**
 *
 * CMS内容表
 * add by xieyanmao on 2017-4-27
 *
 **/
@SuppressWarnings("serial")
public class ContentQueryBo implements Serializable {
	private ContentBo content;//内容
	private ContentExtBo contentExt;//内容扩展
	private ContentTxtBo contentTxt;//内容文本
	private List<ContentAttrBo> contentAttrList;//内容扩展属性
	private List<ContentPictureBo> contentPictureList;//内容图片
	private List<FileBo> fileList;//内容附件
	private List<ContentGroupViewBo> groupList;//用户组

	/**专题ID**/
	private String topicId;

	public ContentBo getContent() {
		return content;
	}

	public void setContent(ContentBo content) {
		this.content = content;
	}

	public ContentExtBo getContentExt() {
		return contentExt;
	}

	public void setContentExt(ContentExtBo contentExt) {
		this.contentExt = contentExt;
	}

	public ContentTxtBo getContentTxt() {
		return contentTxt;
	}

	public void setContentTxt(ContentTxtBo contentTxt) {
		this.contentTxt = contentTxt;
	}

	public List<ContentAttrBo> getContentAttrList() {
		return contentAttrList;
	}

	public void setContentAttrList(List<ContentAttrBo> contentAttrList) {
		this.contentAttrList = contentAttrList;
	}

	public List<ContentPictureBo> getContentPictureList() {
		return contentPictureList;
	}

	public void setContentPictureList(List<ContentPictureBo> contentPictureList) {
		this.contentPictureList = contentPictureList;
	}

	public List<FileBo> getFileList() {
		return fileList;
	}

	public void setFileList(List<FileBo> fileList) {
		this.fileList = fileList;
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public List<ContentGroupViewBo> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<ContentGroupViewBo> groupList) {
		this.groupList = groupList;
	}
}
