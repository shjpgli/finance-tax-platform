package com.abc12366.gateway.util;

import com.abc12366.gateway.exception.ServiceException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
     * 添加天
     *
     * @param date 需要添加天的日期
     * @param day  需要添加的天数
     * @return Date 添加天数之后的日期
     */
    public static Date addDays(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, day);
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
     *
     * @return yyyyMMdd
     */
    public static String getDataString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(new Date());
    }

    /**
     * 获取当前时间戳
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getDataLong(int datalong, int i) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = datalong + i * 1000;
        return format.format(time);
    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     */
    public static Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ServiceException(4805);
        }
        return date;
    }

    public static String dateToStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static String dateToString(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 获取日期年份
     */
    public static String dateYearToString(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return format.format(date);
    }

    /**
     * 获取当月最早时间
     *
     * @return
     */
    public static Date getFirstDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取次月最早时间
     *
     * @return
     */
    public static Date getFirstDayOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取当天最早时间
     *
     * @return
     */
    public static Date getFirstHourOfDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);

        return calendar.getTime();
    }

    /**
     * 获取次日最早时间
     *
     * @return
     */
    public static Date getFirstHourOfLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取当年最早时间
     *
     * @return
     */
    public static Date getFirstMonthOfYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);

        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取次年最早时间
     *
     * @return
     */
    public static Date getFirstMonthOfLastYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);

        calendar.add(Calendar.YEAR, 1);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * yyyyMMddHHmmssSSS+三位随机数
     *
     * @return
     */
    public static String getDateToString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        StringBuffer buffer = new StringBuffer();
        buffer.append("DD");
        buffer.append(df.format(new Date()));
        buffer.append(getRandom());
        return buffer.toString();
    }

    /**
     * yyyyMMddHHmmssSSS+三位随机数
     *
     * @return
     */
    public static String getJYLSH() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        StringBuffer buffer = new StringBuffer();
        buffer.append("JYLSH");
        buffer.append(df.format(new Date()));
        buffer.append(getRandom());
        return buffer.toString();
    }

    /**
     * 获取用户订单号
     *
     * @return
     */
    public static String getInvoiceOrderString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        StringBuffer buffer = new StringBuffer();
        buffer.append("FPDDH");
        buffer.append(df.format(new Date()));
        buffer.append(getRandom());
        return buffer.toString();
    }

    /**
     * 返回一个三位随机数的字符串
     *
     * @return
     */
    public static String getRandom() {
        int number = 0;
        while (true) {
            number = (int) (Math.random() * 1000);
            if (number >= 100 && number < 1000) {
                break;
            }
        }
        return String.valueOf(number);
    }

    /**
     * 日期往后减去num天
     *
     * @param num
     * @return
     */
    public static Date getAddDate(int num) {
        // 取时间
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        // 把日期往后增加一天.整数往后推,负数往前移动
        calendar.add(Calendar.DATE, -num);
        // 这个时间就是日期往后推一天的结果
        date = calendar.getTime();
        return date;
    }

    /**
     * 日期往后减去num天
     *
     * @param num
     * @return
     */
    public static Date getAddMonth(int num) {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -num);
        date = calendar.getTime();
        return date;
    }

    /**
     * 日期往后减去num年
     *
     * @param num
     * @return
     */
    public static Date getAddYear(int num) {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        // 把日期往后增加一年.整数往后推,负数往前移动
        calendar.add(Calendar.YEAR, -num);
        // 这个时间就是日期往后推一年的结果
        date = calendar.getTime();
        return date;
    }

    /**
     * 日期往后减num个小时
     *
     * @param num
     * @return
     */
    public static Date getAddTime(int num) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.HOUR_OF_DAY, -num);
        System.out.println(sdf.format(ca.getTime()));
        return ca.getTime();
    }

    /**
     * 时间是否在时间段里
     *
     * @param start
     * @param end
     * @param date
     * @return
     */
    public static boolean dateIn(Date start, Date end, Date date) {
        if (start == null || end == null) {
            return true;
        }
        if (date == null) {
            return false;
        }
        if (date.getTime() >= start.getTime() && date.getTime() <= end.getTime()) {
            return true;
        }
        return false;
    }

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
