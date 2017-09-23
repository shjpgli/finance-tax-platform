package com.abc12366.uc.util.wx;

import java.util.Map;

/**
 * 微信商户连接
 */
public class WxMchConnectFactory {
    private static final String HTTP_GET = "GET";
    private static final String HTTP_POST = "POST";

    public static <T extends Object> T post(WechatUrl url, Map<String, String> headParameters, Object bodyParameters,
                                            Class<T> _class) {
        WxMchConnect<T> connect = new WxMchConnect<>(url, HTTP_POST, headParameters, bodyParameters, _class);
        return doConnect(connect);
    }

    private static <T extends Object> T doConnect(WxMchConnect<T> connect){
        connect.initXml();
        try {
            connect.httpsRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connect.parseXmlObject();
    }
}
