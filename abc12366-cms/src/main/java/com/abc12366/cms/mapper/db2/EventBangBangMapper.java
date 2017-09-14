package com.abc12366.cms.mapper.db2;


import com.abc12366.cms.model.event.SingleEventBo;

import java.util.List;

/**
 * Created by stuy on 2017/9/13.
 */
public interface EventBangBangMapper {

    SingleEventBo singleEvent();

    List<SingleEventBo> singleEventList();
}
