package com.abc12366.core.web;

import com.abc12366.core.model.User;
import com.abc12366.core.model.bo.HelloRequestEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * 测试控制器
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-20 4:25 PM
 * @since 1.0.0
 */
@RestController
public class KafkaController {

    private final static Logger LOGGER = LoggerFactory.getLogger(KafkaController.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Kafka hello生产者测试接口
     *
     * @param name 用户名
     * @return HelloRequestEvent
     */
    @GetMapping("/kafka/{name}")
    public HelloRequestEvent hello(@PathVariable("name") String name){

        HelloRequestEvent event = new HelloRequestEvent();
        event.setId(UUID.randomUUID().toString());
        event.setMsg("Hello, "+ name);
        event.setAttempts(0);

        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send("server_topic_hello", event);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println(">>>Failed to send event:" + event);
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                System.out.println(">>>Success to send event:" + result.getProducerRecord().value());
            }
        });

        return event;
    }

    /**
     * Kafka user生产者测试接口
     *
     * @param event 用户信息
     * @return HelloRequestEvent
     */
    @PostMapping("/kafka")
    public User insert(@RequestBody User event){

        if(StringUtils.isEmpty(event.getUsername()) || StringUtils.isEmpty(event.getId())){
            throw new RuntimeException("Invalid User Request Event");
        }

        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send("server_topic_user", event);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println(">>>Failed to send event:" + event);
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                System.out.println(">>>Success to send event:" + result.getProducerRecord().value());
            }
        });

        return event;
    }
}
