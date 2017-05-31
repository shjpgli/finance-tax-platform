package com.abc12366.gateway.mapper.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by MY on 2017-05-27.
 */
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

}
