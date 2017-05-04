package com.abc12366.common.util;

import java.text.SimpleDateFormat;
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
}
