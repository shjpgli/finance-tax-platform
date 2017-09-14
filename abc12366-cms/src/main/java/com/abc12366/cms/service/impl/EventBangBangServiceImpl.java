package com.abc12366.cms.service.impl;

import com.abc12366.cms.mapper.db2.EventBangBangMapper;
import com.abc12366.cms.model.event.SingleEventBo;
import com.abc12366.cms.service.EventBangBangService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by stuy on 2017/9/13.
 */
@Service
public class EventBangBangServiceImpl implements EventBangBangService{

    private static final Logger LOGGER = LoggerFactory.getLogger(EventServiceImpl.class);

    @Autowired
    private EventBangBangMapper eventMapper;


    @Override
    public SingleEventBo singleEvent() {
        return eventMapper.singleEvent();
    }

    @Override
    public List<SingleEventBo> singleEventList() {
        return eventMapper.singleEventList();
    }
}
