package com.abc12366.cms.service;

import com.abc12366.cms.mapper.db1.EventApplyAttrMapper;
import com.abc12366.cms.mapper.db1.EventApplyMapper;
import com.abc12366.cms.mapper.db2.EventApplyAttrRoMapper;
import com.abc12366.cms.mapper.db2.EventApplyRoMapper;
import com.abc12366.cms.model.EventApply;
import com.abc12366.cms.model.EventApplyAttr;
import com.abc12366.cms.model.bo.EventApplyAttrBo;
import com.abc12366.cms.model.bo.EventApplyBo;
import com.abc12366.cms.model.bo.EventApplySaveBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by xieyanmao on 2017/5/8.
 */
@Service
public class EventApplyServiceImpl implements EventApplyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventApplyServiceImpl.class);

    @Autowired
    private EventApplyMapper eventApplyMapper;

    @Autowired
    private EventApplyRoMapper eventApplyRoMapper;

    @Autowired
    private EventApplyAttrMapper eventApplyAttrMapper;

    @Autowired
    private EventApplyAttrRoMapper eventApplyAttrRoMapper;

    @Override
    public List<EventApplyBo> selectList(Map<String,Object> map) {
        //查询活动列表
        List<EventApplyBo> dataList =  eventApplyRoMapper.selectList(map);
        return dataList;
    }

    @Override
    public EventApplySaveBo save(EventApplySaveBo eventApplySaveBo) {
        //保存活动信息
        String uuid = UUID.randomUUID().toString().replace("-", "");
        EventApplyBo eventApplyBo = eventApplySaveBo.getEventApply();
        EventApply eventApply= new EventApply();
        eventApplyBo.setApplyId(uuid);
        eventApplyBo.setApplytime(new Date());
        try {
            BeanUtils.copyProperties(eventApplyBo, eventApply);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        eventApplyMapper.insert(eventApply);
        List<EventApplyAttrBo> eventApplyAttrBoList =eventApplySaveBo.getApplyAttrList();
        if(eventApplyAttrBoList != null){
            for(EventApplyAttrBo eventApplyAttrBo : eventApplyAttrBoList){
                eventApplyAttrBo.setApplyId(uuid);
                EventApplyAttr eventApplyAttr = new EventApplyAttr();
                try {
                    BeanUtils.copyProperties(eventApplyAttrBo, eventApplyAttr);
                } catch (Exception e) {
                    LOGGER.error("类转换异常：{}", e);
                    throw new RuntimeException("类型转换异常：{}", e);
                }
                eventApplyAttrMapper.insert(eventApplyAttr);
            }
        }

        return eventApplySaveBo;
    }

    @Override
    public EventApplySaveBo selectEventApply(String applyId) {
        //查询主办方信息
        EventApplySaveBo eventApplySaveBo = new EventApplySaveBo();
        EventApply eventApply = eventApplyRoMapper.selectByPrimaryKey(applyId);
        EventApplyBo eventApplyBo = new EventApplyBo();
        try {
            BeanUtils.copyProperties(eventApply, eventApplyBo);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        eventApplySaveBo.setEventApply(eventApplyBo);
        List<EventApplyAttrBo> eventApplyAttrBoList = new ArrayList<EventApplyAttrBo>();
        List<EventApplyAttr> eventApplyAttrList = eventApplyAttrRoMapper.selectByPrimaryKey(applyId);
        if(eventApplyAttrList != null){
            for(EventApplyAttr eventApplyAttr : eventApplyAttrList){
                EventApplyAttrBo eventApplyAttrBo = new EventApplyAttrBo();
                try {
                    BeanUtils.copyProperties(eventApplyAttr, eventApplyAttrBo);
                } catch (Exception e) {
                    LOGGER.error("类转换异常：{}", e);
                    throw new RuntimeException("类型转换异常：{}", e);
                }
                eventApplyAttrBoList.add(eventApplyAttrBo);
            }
        }
        eventApplySaveBo.setApplyAttrList(eventApplyAttrBoList);
        return eventApplySaveBo;
    }

    @Override
    public EventApplySaveBo update(EventApplySaveBo eventApplySaveBo) {
        //更新活动信息
        EventApplyBo eventApplyBo = eventApplySaveBo.getEventApply();
        EventApply eventApply= new EventApply();
        try {
            BeanUtils.copyProperties(eventApplyBo, eventApply);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        eventApplyMapper.insert(eventApply);
        eventApplyAttrMapper.deleteByPrimaryKey(eventApplyBo.getApplyId());
        List<EventApplyAttrBo> eventApplyAttrBoList =eventApplySaveBo.getApplyAttrList();
        if(eventApplyAttrBoList != null){
            for(EventApplyAttrBo eventApplyAttrBo : eventApplyAttrBoList){
                eventApplyAttrBo.setApplyId(eventApplyBo.getApplyId());
                EventApplyAttr eventApplyAttr = new EventApplyAttr();
                try {
                    BeanUtils.copyProperties(eventApplyAttrBo, eventApplyAttr);
                } catch (Exception e) {
                    LOGGER.error("类转换异常：{}", e);
                    throw new RuntimeException("类型转换异常：{}", e);
                }
                eventApplyAttrMapper.insert(eventApplyAttr);
            }
        }

        return eventApplySaveBo;
    }

    @Override
    public String delete(String applyId) {
        eventApplyAttrMapper.deleteByPrimaryKey(applyId);
        int r = eventApplyMapper.deleteByPrimaryKey(applyId);
        LOGGER.info("{}", r);
        return "";
    }

}
