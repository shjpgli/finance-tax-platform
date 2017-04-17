package com.abc12366.common.util;

import com.abc12366.common.model.BodyStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 工具类
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-05 9:44 AM
 * @since 1.0.0
 */
public class Utils {

    /**
     * 返回系统唯一UUUID
     *
     * @return uuid
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static Map kv(Object k, Object v) {
        Map<Object, Object> map = new HashMap<>();
        map.put(k, v);
        return map;
    }

    /**
     * 返回枚举类型代码
     *
     * @param code int
     * @return BodyStatus
     */
    public static BodyStatus code(int code){
        BodyStatus body = new BodyStatus();
        body.setCode(code);
        try {
            body.setMessage(Message.getValue(code));
        } catch (IOException e) {
            e.printStackTrace();
            body.setMessage(e.getMessage());
        }
        return body;
    }
}
