package com.abc12366.core.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class HelloConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloConsumer.class);

    // 消费多个topic
//    @KafkaListener(topics = {"proxy_topic_hello1", "proxy_topic_hello2"})
    @KafkaListener(topics = "proxy_topic_hello")
    public void hello(ConsumerRecord<String, String> record) {

        String hello = record.value();
        LOGGER.info("hello: " + hello);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("record: " + record.toString());
        }
    }
}
