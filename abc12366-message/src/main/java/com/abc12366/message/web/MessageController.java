package com.abc12366.message.web;

import com.abc12366.common.web.BaseController;
import com.abc12366.message.model.InstantMessage;
import com.abc12366.message.model.bo.UserBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-22 2:05 PM
 * @since 1.0.0
 */
@Controller
@RequestMapping("/")
public class MessageController extends BaseController {

    private final static Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    private RestTemplate restTemplate;

    @Autowired
    public MessageController(RestTemplate restTemplate) {
        super(restTemplate);
        this.restTemplate = restTemplate;
    }

    @MessageMapping("/im")
    public void im(InstantMessage im, UserBO currentUser) {
        im.setFrom(currentUser.getUsername());
//        this.messagingTemplate.convertAndSendToUser(im.getTo(), "/queue/messages", im);
//        this.messagingTemplate.convertAndSendToUser(im.getFrom(), "/queue/messages", im);
    }

    @SubscribeMapping("/users")
    public List subscribeMessages() throws Exception {

//        ResponseEntity responseEntity = exchange("/activeuser", GET, new HttpEntity<>(), String.class);
//        LOGGER.info("{}", responseEntity);

//        List<String> list = null;
//        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
//            List<ActiveUser> activeUserList = JSON.parseArray(responseEntity.getBody().toString(), ActiveUser.class);
//            list = activeUserList.stream().map(ActiveUser::getUsername).collect(Collectors.toList());
//        }
//        return list;
        return null;
    }
}
