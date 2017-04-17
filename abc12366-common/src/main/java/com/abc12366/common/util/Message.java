package com.abc12366.common.util;

/**
 * 消息代码常量
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-15 5:35 PM
 * @since 1.0.0
 */
public class Message {

    // 处理成功代码:2xxx
    public static String C2000 = "处理成功";

    // 客户端错误代码:4xxx
    public static String C4000 = "访问需要Access-Token请求头";
    public static String C4001 = "未验证的接口访问操作，请联系管理员";
    public static String C4002 = "未授权的接口访问操作，请联系管理员";
    public static String C4003 = "被屏蔽的IP地址，请联系管理员";

    // 服务端错误代码:5xxx
    public static String C5000 = "服务器内部错误";
}
