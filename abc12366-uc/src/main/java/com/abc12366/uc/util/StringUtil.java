/**
 * Project Name:excelutil
 * File Name:StringUtil.java
 * Package Name:com.lkx.util
 * Date:2017年6月7日上午9:47:06
 * Copyright (c) 2017~2020, likaixuan@test.com.cn All Rights Reserved.
 */

package com.abc12366.uc.util;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * ClassName:StringUtil
 * Date:     2017年6月7日 上午9:47:06
 *
 * @author likaixuan
 * @version V1.0
 * @see
 * @since JDK 1.7
 */
public class StringUtil {
    /**
     * 首字母转小写
     *
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder())
                    .append(Character.toLowerCase(s.charAt(0)))
                    .append(s.substring(1)).toString();
        }
    }

    /**
     * 首字母转大写
     *
     * @param s
     * @return
     */
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder())
                    .append(Character.toUpperCase(s.charAt(0)))
                    .append(s.substring(1)).toString();
        }
    }

    /**
     * replace:(替换字符串函数)
     *
     * @param strSource 源字符串
     * @param strFrom   要替换的子串
     * @param strTo     替换为的字符串
     * @return
     * @since JDK 1.7
     */
    public static String replace(String strSource, String strFrom,
                                 String strTo) {
        // 如果要替换的子串为空，则直接返回源串
        if (strFrom == null || strFrom.equals(""))
            return strSource;
        String strDest = "";
        // 要替换的子串长度
        int intFromLen = strFrom.length();
        int intPos;
        // 循环替换字符串
        while ((intPos = strSource.indexOf(strFrom)) != -1) {
            // 获取匹配字符串的左边子串
            strDest = strDest + strSource.substring(0, intPos);
            // 加上替换后的子串
            strDest = strDest + strTo;
            // 修改源串为匹配子串后的子串
            strSource = strSource.substring(intPos + intFromLen);
        }
        // 加上没有匹配的子串
        strDest = strDest + strSource;
        // 返回
        return strDest;
    }

    /**
     * 字符退换操作
     *
     * @param operator 操作符
     * @return 操作符
     */
    public static String operReplace(String operator) {
        if ("equals".equalsIgnoreCase(operator)) {
            return "=";
        } else if ("gte".equalsIgnoreCase(operator)) {
            return ">=";
        } else if ("lte".equalsIgnoreCase(operator)) {
            return "<=";
        } else if ("lt".equalsIgnoreCase(operator)) {
            return "<";
        } else if ("gt".equalsIgnoreCase(operator)) {
            return ">";
        } else {
            return "=";
        }
    }

    /**
     * List转为逗号分隔的字符串
     *
     * @param strs List<String>
     * @return 字符串
     */
    public static String list2Str(List<String> strs) {
        return strs != null && strs.size() > 0 ? StringUtils.join(strs.toArray(), ",") : null;
    }
}

