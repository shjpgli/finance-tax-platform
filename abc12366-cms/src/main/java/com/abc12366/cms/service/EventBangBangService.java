package com.abc12366.cms.service;


import com.abc12366.cms.model.event.SingleEventBo;

import java.util.List;

/**
 * Created by stuy on 2017/9/13.
 */
public interface EventBangBangService {

    SingleEventBo singleEvent();

    List<SingleEventBo> singleEventList();
}
