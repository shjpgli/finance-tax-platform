package com.abc12366.cms.model;
import java.io.Serializable;


/**
 *
 * CMS内容类型表
 * add by xieyanmao on 2017-4-25
 *
 **/
@SuppressWarnings("serial")
public class ContentType implements Serializable {

	/****/
	private String typeId;

	/**名称**/
	private String typeName;

	/**图片宽**/
	private Integer imgWidth;

	/**图片高**/
	private Integer imgHeight;

	/**是否有图片**/
	private Integer hasImage;

	/**是否禁用**/
	private Integer isDisabled;



	public void setTypeId(String typeId){
		this.typeId = typeId;
	}

	public String getTypeId(){
		return this.typeId;
	}

	public void setTypeName(String typeName){
		this.typeName = typeName;
	}

	public String getTypeName(){
		return this.typeName;
	}

	public void setImgWidth(Integer imgWidth){
		this.imgWidth = imgWidth;
	}

	public Integer getImgWidth(){
		return this.imgWidth;
	}

	public void setImgHeight(Integer imgHeight){
		this.imgHeight = imgHeight;
	}

	public Integer getImgHeight(){
		return this.imgHeight;
	}

	public void setHasImage(Integer hasImage){
		this.hasImage = hasImage;
	}

	public Integer getHasImage(){
		return this.hasImage;
	}

	public void setIsDisabled(Integer isDisabled){
		this.isDisabled = isDisabled;
	}

	public Integer getIsDisabled(){
		return this.isDisabled;
	}

}
