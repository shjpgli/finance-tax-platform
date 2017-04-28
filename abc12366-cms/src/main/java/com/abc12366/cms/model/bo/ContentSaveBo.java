package com.abc12366.cms.model.bo;
import com.abc12366.cms.model.*;

import java.io.Serializable;
import java.util.List;


/**
 *
 * CMS内容表
 * add by xieyanmao on 2017-4-27
 *
 **/
@SuppressWarnings("serial")
public class ContentSaveBo implements Serializable {
	private Content content;
	private ContentExt contentExt;
	private ContentTxt contentTxt;
	private List<ContentAttr> contentAttrList;
	private List<ContentPicture> contentPictureList;
	private List<File> fileList;

	/**专题ID**/
	private String topicId;

	/**0 新增 1修改 2审核 3退回 4移动 5生成静态页 6删除到回收站 7归档 8出档 9推送共享**/
	private Integer operateType;

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public ContentExt getContentExt() {
		return contentExt;
	}

	public void setContentExt(ContentExt contentExt) {
		this.contentExt = contentExt;
	}

	public ContentTxt getContentTxt() {
		return contentTxt;
	}

	public void setContentTxt(ContentTxt contentTxt) {
		this.contentTxt = contentTxt;
	}

	public List<ContentAttr> getContentAttrList() {
		return contentAttrList;
	}

	public void setContentAttrList(List<ContentAttr> contentAttrList) {
		this.contentAttrList = contentAttrList;
	}

	public List<ContentPicture> getContentPictureList() {
		return contentPictureList;
	}

	public void setContentPictureList(List<ContentPicture> contentPictureList) {
		this.contentPictureList = contentPictureList;
	}

	public List<File> getFileList() {
		return fileList;
	}

	public void setFileList(List<File> fileList) {
		this.fileList = fileList;
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}
}
