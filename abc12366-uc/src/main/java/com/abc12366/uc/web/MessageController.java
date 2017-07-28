package com.abc12366.uc.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.model.UserMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 消息控制器
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-20 4:25 PM
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/queue", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class MessageController {

    private final static Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Kafka 用户消息生产者
     *
     * @param data UserMessage
     */
    @PostMapping(path = "/usermsg")
    public ResponseEntity insert(@Valid @RequestBody UserMessage data) {
        LOGGER.info("{}", data);

        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send("user-message-topic", data);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {

            @Override
            public void onFailure(Throwable throwable) {
                LOGGER.info("Failed user-message-topic: " + data);
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                LOGGER.info("Success user-message-topic: " + result.getProducerRecord().value());
            }
        });

        return ResponseEntity.ok(Utils.kv());
    }
}
