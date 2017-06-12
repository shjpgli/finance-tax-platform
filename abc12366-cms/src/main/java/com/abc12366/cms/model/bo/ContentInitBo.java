package com.abc12366.cms.model.bo;
import java.io.Serializable;
import java.util.List;


/**
 *
 * CMS内容表
 * add by xieyanmao on 2017-4-25
 *
 **/
@SuppressWarnings("serial")
public class ContentInitBo implements Serializable {
	private List<ModelItemBo> modelItems;
	private List<ChannelBo> channels;

	public List<ModelItemBo> getModelItems() {
		return modelItems;
	}

	public void setModelItems(List<ModelItemBo> modelItems) {
		this.modelItems = modelItems;
	}

	public List<ChannelBo> getChannels() {
		return channels;
	}

	public void setChannels(List<ChannelBo> channels) {
		this.channels = channels;
	}
}
