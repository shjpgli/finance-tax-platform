package com.abc12366.gateway.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesUtil {

    private static String GATEWAY_PROPERTIES = "application-gateway.properties";

    static com.abc12366.common.util.Properties properties;

    static {
        try {
            properties = new com.abc12366.common.util.Properties(GATEWAY_PROPERTIES);
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
    public static String getValue(String key) throws IOException {
        if (properties == null) {
            properties = new com.abc12366.common.util.Properties(GATEWAY_PROPERTIES);
        }

        return properties.getValue(String.valueOf(key));
    }  
} 