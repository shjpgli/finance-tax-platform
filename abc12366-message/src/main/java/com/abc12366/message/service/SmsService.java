package com.abc12366.message.service;

import com.abc12366.message.model.bo.QueryStatusParam;
import com.abc12366.message.model.bo.SendCodeParam;
import com.abc12366.message.model.bo.SendTemplateParam;
import com.abc12366.message.model.bo.VerifyCodeParam;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-06-01
 * Time: 16:46
 */
public interface SmsService {
    ResponseEntity sendCode(SendCodeParam sendCodeParam) throws IOException;

    ResponseEntity verify(VerifyCodeParam verifyCodeParam) throws IOException;

    ResponseEntity sendTemplate(SendTemplateParam sendTemplateParam) throws IOException;

    ResponseEntity queryStatus(QueryStatusParam queryStatusParam) throws IOException;
}
