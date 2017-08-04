package com.abc12366.uc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class DataUtils {

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

    public static String DateToStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date);
        return str;
    }

    /**
     * yyyyMMddHHmmssSSS+三位随机数
     * @return
     */
    public static  String getDateToString(){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");//设置日期格式
        StringBuffer buffer = new StringBuffer();
        buffer.append("DD");
        buffer.append(df.format(new Date()));
        buffer.append(getRandom());
        return buffer.toString();
    }

    /**
     * 获取用户订单号
     * @return
     */
    public static String getUserOrderString(){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");//设置日期格式
        StringBuffer buffer = new StringBuffer();
        buffer.append("YHDDH");
        buffer.append(df.format(new Date()));
        buffer.append(getRandom());
        return buffer.toString();
    }

    /**
     * 返回一个三位随机数的字符串
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
     * 字符串转换成日期
     * @param str
     * @return date
     */
    public static Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static void main(String[] args) {

        Date date = new Date();
        System.out.println("日期转字符串：" + DateToStr(date));
        System.out.println("字符串转日期：" + StrToDate("2017-07-30"));

    }

}
