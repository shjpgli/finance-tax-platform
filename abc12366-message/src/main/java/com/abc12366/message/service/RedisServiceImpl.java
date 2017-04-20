package com.abc12366.message.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-19 11:21 AM
 * @since 1.0.0
 */
@Service
public class RedisServiceImpl implements RedisService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisServiceImpl.class);

    @Autowired
    private RedisTemplate redisTemplate;
    
    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOperations;

    public void test(){
        if (redisTemplate.hasKey("foo")) {
            String foo = valueOperations.get("foo");
            LOGGER.info("foo: {}", foo);
        }
    }
}
