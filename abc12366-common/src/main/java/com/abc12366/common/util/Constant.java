package com.abc12366.common.util;

/**
 * 常量定义
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-28 5:12 PM
 * @since 1.0.0
 */
public class Constant {

    // 接口版本常量
    public final static String VERSION_HEAD = "Version";
    public final static String VERSION_1 = "1";
    public final static String VERSION_2 = "2";

    // App验证头
    public final static String APP_TOKEN_HEAD = "Access-Token";
    // App ID
    public final static String APP_ID = "App-Id";
    // App默认有效期：3年
    public final static int APP_VALID_YEARS = 3;
    // App token有效期：2小时
    public final static long APP_TOKEN_VALID_SECONDS = 3600 * 2;
    // 用户验证头
    public final static String USER_TOKEN_HEAD = "User-Token";
    // 用户token有效期：168小时
    public final static int USER_TOKEN_VALID_HOURS = 7 * 24;

    // 分页-当前页
    public final static String pageNum = "1";
    // 分页-每页大小
    public final static String pageSize = "10";
}
