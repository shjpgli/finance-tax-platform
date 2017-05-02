package com.abc12366.message.web;

import com.abc12366.common.util.Utils;
import com.abc12366.common.web.BaseController;
import com.abc12366.message.model.HelloEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 测试控制器
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-20 4:25 PM
 * @since 1.0.0
 */
@RestController
public class HelloController extends BaseController {

    private final static Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public HelloController(RestTemplate restTemplate) {
        super(restTemplate);
    }

    /**
     * Kafka hello生产者测试接口
     *
     * @param name 用户名
     * @return HelloEvent
     */
    @GetMapping("/kafka/{name}")
    public HelloEvent hello(@PathVariable("name") String name){

        HelloEvent event = new HelloEvent();
        event.setId(Utils.uuid());
        event.setMsg("Hello, "+ name);

        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send("message_hello_topic", event);
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
