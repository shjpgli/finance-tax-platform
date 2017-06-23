package com.abc12366.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    public final static String USER_ID = "User-Id";
    // 用户token有效期：3600*2秒
    public final static int USER_TOKEN_VALID_SECONDS = 3600 * 2;


    // Admin用户验证头
    public final static String ADMIN_TOKEN_HEAD = "Admin-Token";

    // Admin用户token有效期：3600*2*1000毫秒
    public final static long ADMIN_USER_TOKEN_VALID_SECONDS = 3600 * 2 * 1000;

    // 分页-当前页
    public final static String pageNum = "1";
    // 分页-每页大小
    public final static String pageSize = "10";

    //系统设置默认密码
    public final static String defaultPwd = "ABC12306";
    //时间-当前日期
    public final static String startTime = getTodaySting();

    public final static String endTime = getTodaySting();


    public final static String defaultFolder = "E:/abc12306";


    /**
     * 获取当前系统当天日期String
     *
     * @return
     */
    public static String getTodaySting() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 获取当前系统当天日期
     *
     * @param date
     * @return
     */
    public static Date getToday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 0);
        date = calendar.getTime();
        return date;
    }

    /**
     * 获取当前系统前一天日期
     *
     * @param date
     * @return
     */
    public static Date getYesterDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return date;
    }

    /**
     * 获取当前系统下一天日期
     *
     * @param date
     * @return
     */
    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return date;
    }
}
