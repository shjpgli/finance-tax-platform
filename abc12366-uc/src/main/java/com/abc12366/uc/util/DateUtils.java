package com.abc12366.uc.util;

import com.abc12366.gateway.exception.ServiceException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-08-02
 * Time: 16:38
 */
public class DateUtils {
    public static Date StrToDate(String str) {
        if (str == null || str.trim().equals("")) {
            throw new ServiceException(4803);
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            throw new ServiceException(4803);
        }
        return date;
    }

    public static Date StrToDateFormat(String str) {
        if (str == null || str.trim().equals("")) {
            throw new ServiceException(4804);
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Date date;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            throw new ServiceException(4804);
        }
        return date;
    }

    public static String dateToString(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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
}
