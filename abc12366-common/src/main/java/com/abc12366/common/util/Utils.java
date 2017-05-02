package com.abc12366.common.util;

import com.abc12366.common.model.BodyStatus;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.util.Assert;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 工具类
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-05 9:44 AM
 * @since 1.0.0
 */
public class Utils {

    /**
     * 返回系统唯一UUUID
     *
     * @return uuid
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 返回Map形式的对象，参数必须为偶数个
     *
     * @param kvs 键值对
     * @return Map
     */
    public static Map kv(Object... kvs) {
        if (kvs.length / 2 != 0) {
            new RuntimeException("Params must be key, value pairs.");
        }
        Map<Object, Object> map = new HashMap<>();
        for (int i = 0; i < kvs.length; i += 2) {
            map.put(kvs[i], kvs[i + 1]);
        }
        return map;
    }

    /**
     * 返回枚举类型代码
     *
     * @param code int
     * @return BodyStatus
     */
    public static BodyStatus bodyStatus(int code) {
        BodyStatus body = new BodyStatus();
        body.setCode(code);
        try {
            body.setMessage(Message.getValue(code));
        } catch (IOException e) {
            e.printStackTrace();
            body.setMessage(e.getMessage());
        }
        return body;
    }

    /**
     * md5字符串
     *
     * @param str 需要计算的字符串
     * @return String
     * @throws NoSuchAlgorithmException
     */
    public static String md5(String str) throws Exception {
        Assert.notNull(str, "MD5 string is not empty");
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes());
        return new BigInteger(1, md.digest()).toString(16);
    }

    /**
     * Base64 编码
     *
     * @param str 需要编码的字符串
     * @return String
     */
    public static String encode(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    /**
     * Base64 解码
     *
     * @param str 需要解码的字符串
     * @return String
     */
    public static String decode(String str) {
        return new String(Base64.getDecoder().decode(str));
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
     * 生成token
     *
     * @param obj 可以转换成json的对象
     * @return String Base64编码后的字符串
     */
    public static String token(Object obj) {
        String json = JSON.toJSONString(obj);
        return encode(json);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("md5: " + md5("888888"));
        String str = encode("abc中文");
        System.out.println("decode: " + str);
        System.out.println("decode: " + decode(str));
        System.out.println("-----------------------------------------");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("now: " + sdf.format(date));
        System.out.println("now + 3: " + sdf.format(addYears(date, 3)));
        System.out.println("-----------------------------------------");
        System.out.println("now: " + sdf.format(date));
        System.out.println("now + 24: " + sdf.format(addHours(date, 24)));
    }
}
