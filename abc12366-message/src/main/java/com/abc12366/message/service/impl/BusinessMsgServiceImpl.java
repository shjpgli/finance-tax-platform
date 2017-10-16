package com.abc12366.message.service.impl;

import com.abc12366.gateway.model.BodyStatus;
import com.abc12366.gateway.util.Utils;
import com.abc12366.message.mapper.db1.BusinessMsgMapper;
import com.abc12366.message.mapper.db2.BusinessMsgRoMapper;
import com.abc12366.message.model.BusinessBatchMessage;
import com.abc12366.message.model.BusinessMessage;
import com.abc12366.message.service.BusinessMsgService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户消息服务
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-07-27 2:36 PM
 * @since 1.0.0
 */
@Service
public class BusinessMsgServiceImpl implements BusinessMsgService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessMsgServiceImpl.class);

    @Autowired
    private BusinessMsgMapper businessMsgMapper;

    @Autowired
    private BusinessMsgRoMapper businessMsgRoMapper;

    @Override
    public List<BusinessMessage> selectList(BusinessMessage data, int page, int size) {
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        return businessMsgRoMapper.selectList(data);
    }

    @Override
    public BusinessMessage insert(BusinessMessage data) {
        if (data != null) {
            data.setId(Utils.uuid());
            data.setStatus("1");
            Timestamp now = new Timestamp(new Date().getTime());
            data.setCreateTime(now);
            data.setLastUpdate(now);
            businessMsgMapper.insert(data);
        }
        return data;
    }

    @Override
    public List<BusinessMessage> insert(BusinessBatchMessage data) {
        List<BusinessMessage> dataList = null;
        if (data != null && data.getUserIds().size() > 0) {
            dataList = new ArrayList<>();
            for (String userId: data.getUserIds()) {
                Timestamp now = new Timestamp(new Date().getTime());
                BusinessMessage bm = new BusinessMessage.Builder()
                        .id(Utils.uuid())
                        .userId(userId)
                        .businessId(data.getBusinessId())
                        .content(data.getContent())
                        .status("1")
                        .createTime(now)
                        .lastUpdate(now)
                        .type(data.getType())
                        .url(data.getUrl())
                        .build();
                dataList.add(bm);
            }
            businessMsgMapper.batchInsert(dataList);
        }
        return dataList;
    }

    //    @KafkaListener(topics = "business_message_topic")
    public void handleUserMessage(ConsumerRecord<String, String> record) {
        LOGGER.info("business_message_topic: " + record.value());
        BusinessMessage data = JSON.parseObject(record.value(), BusinessMessage.class);
        this.insert(data);
    }

    @Override
    public BusinessMessage update(BusinessMessage data) {
        BusinessMessage bm = businessMsgRoMapper.selectOne(data.getId());
        if (bm != null) {
            bm.setStatus(data.getStatus());
            bm.setLastUpdate(new Timestamp(new Date().getTime()));
            businessMsgMapper.update(bm);
        }
        return bm;
    }

    @Override
    public BusinessMessage selectOne(String id) {
        BusinessMessage data = businessMsgRoMapper.selectOne(id);
        if ("1".equals(data.getStatus())) { // 如果消息未读，置为已读
            data.setStatus("2");
            this.update(data);
        }
        return data;
    }

    @Override
    public BodyStatus delete(BusinessMessage data) {
        BusinessMessage bm = this.selectOne(data.getId());
        if (data.getUserId().equals(bm.getUserId())) { // 是否为本人操作
            bm.setStatus("0");
            this.update(bm);
            return Utils.bodyStatus(2000);
        } else {
            return Utils.bodyStatus(4024);
        }
    }
}
