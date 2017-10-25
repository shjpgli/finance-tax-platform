package com.abc12366.cms.service.impl;

import com.abc12366.cms.mapper.db1.EventBangBangUpdateMapper;
import com.abc12366.cms.mapper.db2.EventBangBangMapper;
import com.abc12366.cms.model.event.*;
import com.abc12366.cms.service.EventBangBangService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by stuy on 2017/9/13.
 */
@Service
public class EventBangBangServiceImpl implements EventBangBangService{

    private static final Logger LOGGER = LoggerFactory.getLogger(EventServiceImpl.class);

    @Autowired
    private EventBangBangMapper eventMapper;

    @Autowired
    private EventBangBangUpdateMapper eventBangBangUpdateMapper;

    @Override
    public SingleEventBo singleEvent(Map map) {
        return eventMapper.singleEvent(map);
    }

    @Override
    public List<SingleEventBo> singleEventList(Map map) {
        return eventMapper.singleEventList(map);
    }


    @Transactional("db1TxManager")
    public EventRecordBbBo addEventRecord(EventRecordBbBo eventRecord) {
        try {
            String uuid = UUID.randomUUID().toString().replace("-", "");
            eventRecord.setRecordId(uuid);
            eventRecord.setBrowsetime(new Date());
            eventBangBangUpdateMapper.saveEventRecord(eventRecord);
        } catch (Exception e) {
            LOGGER.debug("异常：{}"+e);
            throw new RuntimeException("异常：{}", e);
        }
        return eventRecord;

    }

    @Override
    public EventBrowseCountBo browseCount(Map<String, String> map) {
        EventBrowseCountBo browsecount = eventMapper.browseCount(map);
        return browsecount;
    }

    @Override
    public EventIdBo selectEventId(Map map) {
        EventIdBo event = eventMapper.selectEventId(map);
        map.put("sponsorid",event.getSponsorid());
        EventSponsorBbBo eventsponsor = eventMapper.selectEventSponsor(map);
        event.setEventSponsorBbBo(eventsponsor);
        List<EventApplyFieldBo> modelitem = eventMapper.selectModelItem(map);
        event.setEventApplyFieldBoList(modelitem);
        EventBrowseCountBo browse = eventMapper.browseCount(map);
        event.setEventBrowseCountBo(browse);
        List<EventIdBo> list = eventMapper.selectEventRelevant(map);
        event.setEventIdBoList(list);
        int count=eventMapper.selectEventApplyStatus(map);
        event.setIsApply(count);
        return event;
    }

    @Transactional("db1TxManager")
    public EventApplyBbBo saveEventApply(EventApplyBbBo eventApplyBbBo) {
        try {
            String uuid = UUID.randomUUID().toString().replace("-", "");
            eventApplyBbBo.setApplyId(uuid);
            eventApplyBbBo.setApplytime(new Date());
            eventBangBangUpdateMapper.saveEventApply(eventApplyBbBo);
            List<EventApplyCentenBo> eventapplyCenten = eventApplyBbBo.getEventApplyCentenBo();
            for(EventApplyCentenBo eventApplyCentenBo : eventapplyCenten){
                eventApplyCentenBo.setApplyId(uuid);
                eventBangBangUpdateMapper.saveEventApplyAttr(eventApplyCentenBo);
            }
        } catch (Exception e) {
            LOGGER.debug("异常：{}"+e);
            throw new RuntimeException("异常：{}", e);
        }
        return eventApplyBbBo;
    }

    @Override
    public int selectEventApplyStatus(Map map) {
        return eventMapper.selectEventApplyStatus(map);
    }
}
