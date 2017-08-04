package com.abc12366.message.service.impl;

import com.abc12366.gateway.model.BodyStatus;
import com.abc12366.gateway.util.Utils;
import com.abc12366.message.mapper.db1.UserMsgMapper;
import com.abc12366.message.mapper.db2.UserMsgRoMapper;
import com.abc12366.message.model.UserMessage;
import com.abc12366.message.service.UserMsgService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
public class UserMsgServiceImpl implements UserMsgService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserMsgServiceImpl.class);

    @Autowired
    private UserMsgMapper userMsgMapper;

    @Autowired
    private UserMsgRoMapper userMsgRoMapper;

    @Override
    public List<UserMessage> selectList(UserMessage data, int page, int size) {
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        return userMsgRoMapper.selectList(data);
    }

    @Override
    public UserMessage insert(UserMessage data) {
        if (data != null) {
            data.setId(Utils.uuid());
            data.setStatus("1");
            Timestamp now = new Timestamp(new Date().getTime());
            data.setCreateTime(now);
            data.setLastUpdate(now);
            userMsgMapper.insert(data);
        }
        return data;
    }

    @KafkaListener(topics = "user_message_topic")
    public void handleUserMessage(ConsumerRecord<String, String> record) {
        LOGGER.info("user_message_topic: " + record.value());
        UserMessage data = JSON.parseObject(record.value(), UserMessage.class);
        this.insert(data);
    }

    @Override
    public UserMessage update(UserMessage data) {
        UserMessage um = userMsgRoMapper.selectOne(data.getId());
        if (um != null) {
            um.setStatus(data.getStatus());
            um.setLastUpdate(new Timestamp(new Date().getTime()));
            userMsgMapper.update(um);
        }
        return um;
    }

    @Override
    public UserMessage selectOne(String id) {
        UserMessage data = userMsgRoMapper.selectOne(id);
        if ("1".equals(data.getStatus())) { // 如果消息未读，置为已读
            data.setStatus("2");
            this.update(data);
        }
        return data;
    }

    @Override
    public BodyStatus delete(UserMessage data) {
        UserMessage um = this.selectOne(data.getId());
        if (data.getToUserId().equals(um.getToUserId())) { // 是否为本人操作
            um.setStatus("0");
            this.update(um);
            return Utils.bodyStatus(2000);
        } else {
            return Utils.bodyStatus(4024);
        }
    }
}
