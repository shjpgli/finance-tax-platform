package com.abc12366.message.web;

import com.abc12366.common.util.Constant;
import com.abc12366.message.model.bo.*;
import com.abc12366.message.service.SmsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-05-27
 * Time: 10:42
 */
@RestController
@RequestMapping(path = "/sms", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class SmsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsController.class);

    @Autowired
    private SmsService smsService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(path = "/sendcode")
    public ResponseEntity sendCode(@Valid @RequestBody SendCodeParam sendCodeParam) throws IOException {
        LOGGER.info("{}", sendCodeParam);

        ResponseEntity response = smsService.sendCode(sendCodeParam);
        if (response == null || !response.hasBody()) {
            return (ResponseEntity) ResponseEntity.badRequest();
        }
        SendCodeResponseBO sendCodeResponseBO = objectMapper.readValue(((String) response.getBody()).getBytes(), SendCodeResponseBO.class);
        LOGGER.info("{}", sendCodeResponseBO);
        return ResponseEntity.ok(sendCodeResponseBO);
    }

    @PostMapping(path = "/verifycode")
    ResponseEntity verifyCode(@Valid @RequestBody VerifyCodeParam verifyCodeParam) throws IOException {
        LOGGER.info("{}", verifyCodeParam);
        ResponseEntity response = smsService.verify(verifyCodeParam);
        if (response == null || !response.hasBody()) {
            return (ResponseEntity) ResponseEntity.badRequest();
        }
        VerifyCodeResponseBO verifyCodeResponseBO = objectMapper.readValue(((String) response.getBody()).getBytes(), VerifyCodeResponseBO.class);
        LOGGER.info("{}", verifyCodeResponseBO);
        return ResponseEntity.ok(verifyCodeResponseBO);
    }

    @PostMapping(path = "/sendtemplate")
    ResponseEntity sendTemplate(@Valid @RequestBody SendTemplateParam sendTemplateParam) throws IOException {
        LOGGER.info("{}", sendTemplateParam);

        ResponseEntity response = smsService.sendTemplate(sendTemplateParam);
        if (response == null || !response.hasBody()) {
            return (ResponseEntity) ResponseEntity.badRequest();
        }
        SendTemplateResponseBO verifyCodeResponseBO = objectMapper.readValue(((String) response.getBody()).getBytes(), SendTemplateResponseBO.class);
        LOGGER.info("{}", verifyCodeResponseBO);
        return ResponseEntity.ok(verifyCodeResponseBO);
    }

    @PostMapping(path = "/querystatus")
    ResponseEntity queryStatus(@Valid @RequestBody QueryStatusParam queryStatusParam) throws IOException {
        LOGGER.info("{}", queryStatusParam);

        ResponseEntity response = smsService.queryStatus(queryStatusParam);
        if (response == null || !response.hasBody()) {
            return (ResponseEntity) ResponseEntity.badRequest();
        }
        QueryStatusResponseBO queryStatusResponseBO = objectMapper.readValue(((String) response.getBody()).getBytes(), QueryStatusResponseBO.class);
        LOGGER.info("{}", queryStatusResponseBO);
        return ResponseEntity.ok(queryStatusResponseBO);
    }
}
