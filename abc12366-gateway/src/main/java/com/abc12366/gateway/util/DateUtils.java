package com.abc12366.gateway.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-05-03 10:57 AM
 * @since 1.0.0
 */
public class DateUtils {

    public static String getDateFormat(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 添加年份
     *
     * @param date 需要添加年份的日期
     * @param year 需要添加的年份数
     * @return Date 添加年份数之后的日期
     */
    public static Date addYears(Date date, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 添加小时
     *
     * @param date 需要添加小时的日期
     * @param hour 需要添加的小时数
     * @return Date 添加小时数之后的日期
     */
    public static Date addHours(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hour);
        return calendar.getTime();
    }

    /**
     * 当前年月日字符串
     * @return
     */
    public static String getDataString(){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(new Date());
    }

    /**
     * 获取当前时间戳
     * @return
     */
    public static String getDataLong(int datalong,int i){
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = datalong + i*1000;
        return format.format(time);
    }
}
