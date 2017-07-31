package com.abc12366.gateway.util;


import com.abc12366.gateway.model.bo.LoginInfoBO;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;


/**
 * @author zhushuai 2017-7-28
 */
public class WxConnectFactory {
    private static final String HTTP_GET = "GET";
    private static final String HTTP_POST = "POST";


    public static <T extends Object> T get(String url, Map<String, String> headparamters, Object bodyparamters, Class<T> _class) {
        WxConnect<T> connect = new WxConnect<>(url, HTTP_GET, headparamters, bodyparamters, _class);
        return doConect(connect);
    }


    public static <T extends Object> T post(String url, Map<String, String> headparamters, Object bodyparamters, Class<T> _class) {
        WxConnect<T> connect = new WxConnect<>(url, HTTP_POST, headparamters, bodyparamters, _class);
        return doConect(connect);
    }


    private static <T extends Object> T doConect(WxConnect<T> connect) {
        connect.initJson();
        connect.httpsRequest();
        return connect.parseObject();
    }


    public static void main(String[] args) {
        LoginInfoBO person=WxConnectFactory.get("", null, null, LoginInfoBO.class);
        System.out.println(JSONObject.toJSONString(person));
    }

}
