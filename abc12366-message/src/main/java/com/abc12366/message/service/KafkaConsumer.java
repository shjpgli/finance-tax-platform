package com.abc12366.message.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * 测试Kafka消费者监听服务
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-23 5:01 PM
 * @since 1.0.0
 */
@Service
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    // 消费多个topic
//    @KafkaListener(topics = {"server_topic_hello1", "server_topic_hello2"})
    @KafkaListener(topics = "server_topic_hello")
    public void hello(ConsumerRecord<String, String> record) {

        String hello = record.value();
        LOGGER.info("hello: " + hello);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("record: " + record.toString());
        }
    }

    @KafkaListener(topics = "server_topic_user")
    public void consume(ConsumerRecord<String, String> record){

        LOGGER.info("user: " + record.value());
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("record: " + record.value());
        }
    }
}
