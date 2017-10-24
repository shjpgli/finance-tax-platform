package com.abc12366.message.web;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.message.service.SendMsgLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 发送短信日志接口控制器
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-10-24
 * Time: 15:58
 */
@RestController
@RequestMapping(path = "/mobile/msg/log", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class SendMsgLogController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SendMsgLogController.class);

    @Autowired
    private SendMsgLogService sendMsgLogService;

    @GetMapping()
    public ResponseEntity selectList(@RequestParam(required = false) String phone,
                                     @RequestParam(required = false) String status,
                                     @RequestParam(required = false) String channel,
                                     @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {

        return ResponseEntity.ok(Utils.kv());
    }
}
