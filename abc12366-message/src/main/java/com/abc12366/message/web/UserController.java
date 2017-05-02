package com.abc12366.message.web;

import com.abc12366.common.web.BaseController;
import com.abc12366.message.model.HelloEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

/**
 * 测试控制器
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-20 4:25 PM
 * @since 1.0.0
 */
@RestController
public class UserController extends BaseController {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public UserController(RestTemplate restTemplate) {
        super(restTemplate);
    }

    /**
     * Kafka user生产者测试接口
     *
     * @param event 用户信息
     * @return HelloEvent
     */
    @PostMapping("/kafka")
    public HelloEvent insert(@RequestBody HelloEvent event){

        if(StringUtils.isEmpty(event.getMsg()) || StringUtils.isEmpty(event.getId())){
            throw new RuntimeException("Invalid User Request Event");
        }

        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send("proxy_topic_user", event);
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
