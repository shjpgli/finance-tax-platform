package com.abc12366.cms.model.bo;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.io.Serializable;


/**
 * 
 * CMS模型项表
 * 
 **/
@SuppressWarnings("serial")
public class ModelItemBo implements Serializable {

	/**模型项Id**varchar(64)**/
	private String modelitemId;

	/**模型Id**varchar(64)**/
	private String modelId;

	/**字段**varchar(50)**/
	@NotEmpty(message = "field不能为空！")
	@Size(min = 0, max = 50)
	private String field;

	/**名称**varchar(100)**/
	@NotEmpty(message = "itemLabel不能为空！")
	@Size(min = 0, max = 100)
	private String itemLabel;

	/**排列顺序**int(11)**/
	private Integer priority;

	/**可选项**varchar(255)**/
	@Size(min = 0, max = 2550)
	private String optValue;

	/**长度**varchar(20)**/
	@Size(min = 0, max = 20)
	private String textSize;

	/**文本行数**varchar(3)**/
	@Size(min = 0, max = 3)
	private String areaRows;

	/**文本列数**varchar(3)**/
	@Size(min = 0, max = 3)
	private String areaCols;

	/**帮助信息**varchar(255)**/
	@Size(min = 0, max = 255)
	private String help;

	/**帮助位置**varchar(1)**/
	@Size(min = 0, max = 1)
	private String helpPosition;

	/**数据类型**int(11)**/
	@NotEmpty(message = "dataType不能为空！")
	private Integer dataType;

	/**是否独占一行**tinyint(1)**/
	private Integer isSingle;

	/**是否栏目模型项**tinyint(1)**/
	private Integer isChannel;

	/**是否自定义**tinyint(1)**/
	private Integer isCustom;

	/**是否显示**tinyint(1)**/
	private Integer isDisplay;

	/**是否必填项**tinyint(1)**/
	private Integer isRequired;

	public String getModelitemId() {
		return modelitemId;
	}

	public void setModelitemId(String modelitemId) {
		this.modelitemId = modelitemId;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getItemLabel() {
		return itemLabel;
	}

	public void setItemLabel(String itemLabel) {
		this.itemLabel = itemLabel;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getOptValue() {
		return optValue;
	}

	public void setOptValue(String optValue) {
		this.optValue = optValue;
	}

	public String getTextSize() {
		return textSize;
	}

	public void setTextSize(String textSize) {
		this.textSize = textSize;
	}

	public String getAreaRows() {
		return areaRows;
	}

	public void setAreaRows(String areaRows) {
		this.areaRows = areaRows;
	}

	public String getAreaCols() {
		return areaCols;
	}

	public void setAreaCols(String areaCols) {
		this.areaCols = areaCols;
	}

	public String getHelp() {
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public String getHelpPosition() {
		return helpPosition;
	}

	public void setHelpPosition(String helpPosition) {
		this.helpPosition = helpPosition;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public Integer getIsSingle() {
		return isSingle;
	}

	public void setIsSingle(Integer isSingle) {
		this.isSingle = isSingle;
	}

	public Integer getIsChannel() {
		return isChannel;
	}

	public void setIsChannel(Integer isChannel) {
		this.isChannel = isChannel;
	}

	public Integer getIsCustom() {
		return isCustom;
	}

	public void setIsCustom(Integer isCustom) {
		this.isCustom = isCustom;
	}

	public Integer getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}

	public Integer getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(Integer isRequired) {
		this.isRequired = isRequired;
	}
}
