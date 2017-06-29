package com.abc12366.cms.service;


import com.abc12366.cms.model.bo.EventApplyBo;
import com.abc12366.cms.model.bo.EventApplySaveBo;

import java.util.List;
import java.util.Map;

public interface EventApplyService {

    List<EventApplyBo> selectList(Map<String, Object> map);

    EventApplySaveBo save(EventApplySaveBo eventApplySaveBo);

    EventApplySaveBo selectEventApply(String applyId);

    EventApplySaveBo update(EventApplySaveBo eventApplySaveBo);

    String delete(String applyId);

    String deleteList(String[] applyIds);

    String updateStatusList(String[] applyIds);

}
