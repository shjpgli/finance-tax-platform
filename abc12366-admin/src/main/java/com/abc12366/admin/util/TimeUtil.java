package com.abc12366.admin.util;

import com.abc12366.common.util.Constant;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by MY on 2017-06-22.
 */
public class TimeUtil {
    /**
     * 根据时间获取时间毫秒
     *
     * @param date
     * @return
     */
    public static long getDateStringToLong(Date date) {
        Date temp = null; // 定义时间类型
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        String inVal = inputFormat.format(date);
        try {
            temp = inputFormat.parse(inVal); // 将字符型转换成日期型
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp.getTime(); // 返回毫秒数
    }

    /*
     * 将时间戳转换为时间
     */
    public static Date getLongToDate(long lt){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        return new Date(lt);
    }

    public static void main(String[] args) {
        System.out.println(getLongToDate(getDateStringToLong(new Date())+ Constant.ADMIN_USER_TOKEN_VALID_SECONDS));
    }
}
