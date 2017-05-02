package com.abc12366.cms.vo;
import java.io.Serializable;


/**
 * 
 * CMS模型项表
 * 
 **/
@SuppressWarnings("serial")
public class ModelItemVO implements Serializable {

	/****/
	private String modelitemId;

	/****/
	private String modelId;

	/**字段**/
	private String field;

	/**名称**/
	private String itemLabel;

	/**排列顺序**/
	private Integer priority;

	/**默认值**/
	private String defValue;

	/**可选项**/
	private String optValue;

	/**长度**/
	private String textSize;

	/**文本行数**/
	private String areaRows;

	/**文本列数**/
	private String areaCols;

	/**帮助信息**/
	private String help;

	/**帮助位置**/
	private String helpPosition;

	/**数据类型**/
	private Integer dataType;

	/**是否独占一行**/
	private Integer isSingle;

	/**是否栏目模型项**/
	private Integer isChannel;

	/**是否自定义**/
	private Integer isCustom;

	/**是否显示**/
	private Integer isDisplay;

	/**是否必填项**/
	private Integer isRequired;

	/**图片宽度**/
	private Integer imageWidth;

	/**图片宽度**/
	private Integer imageHeight;



	public void setModelitemId(String modelitemId){
		this.modelitemId = modelitemId;
	}

	public String getModelitemId(){
		return this.modelitemId;
	}

	public void setModelId(String modelId){
		this.modelId = modelId;
	}

	public String getModelId(){
		return this.modelId;
	}

	public void setField(String field){
		this.field = field;
	}

	public String getField(){
		return this.field;
	}

	public void setItemLabel(String itemLabel){
		this.itemLabel = itemLabel;
	}

	public String getItemLabel(){
		return this.itemLabel;
	}

	public void setPriority(Integer priority){
		this.priority = priority;
	}

	public Integer getPriority(){
		return this.priority;
	}

	public void setDefValue(String defValue){
		this.defValue = defValue;
	}

	public String getDefValue(){
		return this.defValue;
	}

	public void setOptValue(String optValue){
		this.optValue = optValue;
	}

	public String getOptValue(){
		return this.optValue;
	}

	public void setTextSize(String textSize){
		this.textSize = textSize;
	}

	public String getTextSize(){
		return this.textSize;
	}

	public void setAreaRows(String areaRows){
		this.areaRows = areaRows;
	}

	public String getAreaRows(){
		return this.areaRows;
	}

	public void setAreaCols(String areaCols){
		this.areaCols = areaCols;
	}

	public String getAreaCols(){
		return this.areaCols;
	}

	public void setHelp(String help){
		this.help = help;
	}

	public String getHelp(){
		return this.help;
	}

	public void setHelpPosition(String helpPosition){
		this.helpPosition = helpPosition;
	}

	public String getHelpPosition(){
		return this.helpPosition;
	}

	public void setDataType(Integer dataType){
		this.dataType = dataType;
	}

	public Integer getDataType(){
		return this.dataType;
	}

	public void setIsSingle(Integer isSingle){
		this.isSingle = isSingle;
	}

	public Integer getIsSingle(){
		return this.isSingle;
	}

	public void setIsChannel(Integer isChannel){
		this.isChannel = isChannel;
	}

	public Integer getIsChannel(){
		return this.isChannel;
	}

	public void setIsCustom(Integer isCustom){
		this.isCustom = isCustom;
	}

	public Integer getIsCustom(){
		return this.isCustom;
	}

	public void setIsDisplay(Integer isDisplay){
		this.isDisplay = isDisplay;
	}

	public Integer getIsDisplay(){
		return this.isDisplay;
	}

	public void setIsRequired(Integer isRequired){
		this.isRequired = isRequired;
	}

	public Integer getIsRequired(){
		return this.isRequired;
	}

	public void setImageWidth(Integer imageWidth){
		this.imageWidth = imageWidth;
	}

	public Integer getImageWidth(){
		return this.imageWidth;
	}

	public void setImageHeight(Integer imageHeight){
		this.imageHeight = imageHeight;
	}

	public Integer getImageHeight(){
		return this.imageHeight;
	}

}
