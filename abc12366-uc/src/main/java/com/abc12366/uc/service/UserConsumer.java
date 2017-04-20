package com.abc12366.uc.service;

import com.abc12366.uc.mapper.db1.UserMapper;
import com.abc12366.uc.model.User;
import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * 测试消费者监听服务
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-23 5:01 PM
 * @since 1.0.0
 */
@Service
public class UserConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserConsumer.class);

    @Autowired
    private UserMapper userMapper;

    @KafkaListener(topics = "proxy_topic_user")
    public void handle(ConsumerRecord<String, String> record) {

        LOGGER.info("user: " + record.value());
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("record: " + record.value());
        }
        User user = JSON.parseObject(record.value(), User.class);
        userMapper.insert(user);
    }
}
