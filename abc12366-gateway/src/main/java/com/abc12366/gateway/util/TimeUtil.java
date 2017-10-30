package com.abc12366.gateway.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    /**
     * 根据时间获取时间毫秒
     *
     * @param date
     * @return long 返回毫秒数
     */
    public static long getDateStringToLong(Date date) {
        Date temp = null;
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        String inVal = inputFormat.format(date);
        try {
            // 将字符型转换成日期型
            temp = inputFormat.parse(inVal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp.getTime();
    }

    /**
     * 将时间戳转换为时间
     *
     * @param lt 毫秒数
     * @return Date 日期类型
     */
    public static Date getLongToDate(long lt) {
        return new Date(lt);
    }
}