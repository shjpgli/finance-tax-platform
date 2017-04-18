package com.abc12366.gateway.web;

import com.abc12366.common.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-27 3:58 PM
 * @since 1.0.0
 */
@RestController
public class IndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);


    @GetMapping("/")
    public ResponseEntity index() {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/error")
    public ResponseEntity error() {
        return new ResponseEntity(Utils.bodyStatus(4000), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/test")
    public ResponseEntity test() {
        return ResponseEntity.ok(Utils.kv("message", "OK"));
    }
}
