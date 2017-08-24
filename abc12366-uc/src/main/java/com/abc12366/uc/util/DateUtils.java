package com.abc12366.uc.util;

import com.abc12366.gateway.exception.ServiceException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
}
