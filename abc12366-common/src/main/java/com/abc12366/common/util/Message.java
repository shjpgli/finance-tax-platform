package com.abc12366.common.util;

import java.io.IOException;

/**
 * 读取消息配置文件
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-17 5:17 PM
 * @since 1.0.0
 */
public class Message {

    private static String MESSAGE = "message.properties";

    static Properties properties;

    static {
        try {
            properties = new Properties(MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取配置参数值方法
     *
     * @param key
     * @return String
     */
    public static String getValue(int key) throws IOException {
        if (properties == null) {
            properties = new Properties(MESSAGE);
        }

        return properties.getValue(key);
    }
}
