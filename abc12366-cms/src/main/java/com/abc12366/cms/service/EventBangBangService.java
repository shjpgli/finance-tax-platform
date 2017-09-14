package com.abc12366.cms.service;


import com.abc12366.cms.model.event.*;

import java.util.List;
import java.util.Map;

/**
 * Created by stuy on 2017/9/13.
 */
public interface EventBangBangService {

    SingleEventBo singleEvent();

    List<SingleEventBo> singleEventList();

    EventRecordBbBo addEventRecord(EventRecordBbBo eventRecord);

    EventBrowseCountBo browseCount(Map<String,String> map);

    EventIdBo selectEventId(Map map);

    EventApplyBbBo saveEventApply(EventApplyBbBo eventApplyBbBo);

    int selectEventApplyStatus(Map map);

}
