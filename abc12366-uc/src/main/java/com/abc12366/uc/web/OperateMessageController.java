package com.abc12366.uc.web;

import com.abc12366.gateway.util.Constant;
import com.abc12366.uc.model.admin.bo.OperateMessageBO;
import com.abc12366.uc.service.admin.OperateMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-12-18
 * Time: 10:49
 */
@RestController
@RequestMapping(path = "/operate/message", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class OperateMessageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperateMessageController.class);

    private OperateMessageService operateMessageService;

    public ResponseEntity insert(@RequestBody OperateMessageBO operateMessageBO){
        LOGGER.info("发送运营消息：{}",operateMessageBO);
        operateMessageService.insert(operateMessageBO);
        return null;
    }
}
