package com.abc12366.cms.model.bo;

import java.io.Serializable;
import java.util.List;


/**
 * 
 * CMS活动信息
 * 
 **/
@SuppressWarnings("serial")
public class EventApplySaveBo implements Serializable {

	/**活动信息**/
	private EventApplyBo eventApply;

	/**活动模型项信息**/
	private List<EventApplyAttrBo> applyAttrList;

	public EventApplyBo getEventApply() {
		return eventApply;
	}

	public void setEventApply(EventApplyBo eventApply) {
		this.eventApply = eventApply;
	}

	public List<EventApplyAttrBo> getApplyAttrList() {
		return applyAttrList;
	}

	public void setApplyAttrList(List<EventApplyAttrBo> applyAttrList) {
		this.applyAttrList = applyAttrList;
	}
}
