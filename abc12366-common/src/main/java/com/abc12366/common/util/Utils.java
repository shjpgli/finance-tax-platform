package com.abc12366.common.util;

import com.abc12366.common.model.BodyStatus;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    public static void main(String[] args) {
        kv();
    }
    /**
     * 返回Map形式的对象，参数必须为偶数个
     *
     * @param kvs 键值对
     * @return Map
     */
    public static Map kv(Object... kvs) {

        Map<Object, Object> map = new HashMap<>();
        map.put("code",2000);
        try {
            map.put("message",Message.getValue(2000));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (kvs.length % 2 != 0) {
            throw new RuntimeException("Params must be key, value pairs.");
        }
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
     * 生成token
     * return String token
     */
    public static String token() throws Exception {
        return md5(uuid());
    }

    public static String token(String str) throws Exception {
        return md5(str);
    }

    /**
     * 生成盐值
     *
     * @return String Base64编码后的字符串
     */
    public static String salt() {
        double salt = Math.random();
        String saltValue = salt + "";
        return saltValue.substring(saltValue.indexOf("0.") + 2, 8);
    }

    public static String getAddr(HttpServletRequest request) {
        String addr = request.getRemoteAddr();
        if (!StringUtils.isEmpty(request.getHeader(Constant.CLIENT_IP))) {
            addr = request.getHeader(Constant.CLIENT_IP);
        }
        return addr;
    }

    public static String getUserAgent(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (!StringUtils.isEmpty(request.getHeader(Constant.CLIENT_USER_AGENT))) {
            userAgent = request.getHeader(Constant.CLIENT_USER_AGENT);
        }
        return userAgent;
    }
}
