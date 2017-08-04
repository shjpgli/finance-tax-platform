package com.abc12366.message.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
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

    // opsForXXX & boundXxxOps的区别：
    // 前者获取一个的operator，但是没有指定操作的对象（key），可以在一个连接（事务）内操作多个key以及对应的value；
    // 后者获取一个指定操作对象（key）的operator，在一个连接（事务）内只能操作这个key对应的value。
    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOperations;

//    private BoundValueOperations<String, String> boundValueOperations;

    @Resource(name = "redisTemplate")
    private ListOperations<String, String> listOperations;

    public void test(){
        if (redisTemplate.hasKey("foo")) {
            String foo = valueOperations.get("foo");
            LOGGER.info("foo: {}", foo);

            listOperations.leftPush("123", "leftPush");
            // 上面的操作等价于下面的操作
            redisTemplate.boundListOps("456").leftPush("boundListOps");
        }
    }
}
