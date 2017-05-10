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
public class ChannelQueryBo implements Serializable {
	private ChannelBo channel;//栏目
	private ChannelExtBo channelExt;//栏目扩展
	private List<ChannelAttrBo> channelAttrList;//栏目扩展属性

	public ChannelBo getChannel() {
		return channel;
	}

	public void setChannel(ChannelBo channel) {
		this.channel = channel;
	}

	public ChannelExtBo getChannelExt() {
		return channelExt;
	}

	public void setChannelExt(ChannelExtBo channelExt) {
		this.channelExt = channelExt;
	}

	public List<ChannelAttrBo> getChannelAttrList() {
		return channelAttrList;
	}

	public void setChannelAttrList(List<ChannelAttrBo> channelAttrList) {
		this.channelAttrList = channelAttrList;
	}

}
