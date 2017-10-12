package com.abc12366.uc.util;

import java.util.Date;

/**
 * Created by Administrator on 2017/10/10 0010.
 */
public class LszUtil {
    /**
     * 时间是否在时间段里
     * @param start
     * @param end
     * @param date
     * @return
     */
    public static boolean dateIn(Date start, Date end, Date date){
        if(start == null || end == null) return true;
        if(date == null)return false;
        if(date.getTime()>= start.getTime() && date.getTime()<= end.getTime()){
            return true;
        }
        return false;
    }
}
