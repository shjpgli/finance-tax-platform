package com.abc12366.message.web;

import com.abc12366.message.model.bo.ApiLogBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-20 4:25 PM
 * @since 1.0.0
 */
@RestController
public class LogController {

    private final static Logger LOGGER = LoggerFactory.getLogger(LogController.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Kafka apilog生产者测试接口
     *
     * @param bo
     */
    @PostMapping("/kafka")
    public void insertApiLog(@RequestBody ApiLogBO bo){

        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send("gateway_apilog_topic", bo);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {

            @Override
            public void onFailure(Throwable throwable) {
                LOGGER.info("Failed to send event:" + bo);
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                LOGGER.info("Success to send event:" + result.getProducerRecord().value());
            }
        });
    }
}
