package com.abc12366.message.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.message.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-27 3:58 PM
 * @since 1.0.0
 */
@RestController
@RequestMapping(headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class IndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private RedisService redisService;

    @GetMapping("/")
    public ResponseEntity index() {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/test")
    public ResponseEntity test() {
        return ResponseEntity.ok(Utils.kv("message", "OK"));
    }

    @GetMapping("/redis")
    public ResponseEntity redis() {
        redisService.test();
        return ResponseEntity.ok(null);
    }
}
