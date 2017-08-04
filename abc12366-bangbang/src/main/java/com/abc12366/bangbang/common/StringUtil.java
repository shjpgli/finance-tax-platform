package com.abc12366.bangbang.common;

/**
 * @Author liuqi
 * @Date 2017/8/3 20:51
 */
public class StringUtil {

    public static String nullToString(Object obj) {
        if(obj == null) {
            return "";
        } else {
            return obj.toString();
        }
    }


}
