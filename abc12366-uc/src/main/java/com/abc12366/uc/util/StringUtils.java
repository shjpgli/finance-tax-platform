package com.abc12366.uc.util;

import java.util.Random;

/**
 * Created by MY on 2017-05-18.
 */
public class StringUtils {
    public static String getRandomNumber(int digCount) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(digCount);
        for(int i=0; i < digCount; i++)
            sb.append((char)('0' + random.nextInt(10)));
        return sb.toString();
    }

    public static String getDateString(){
        return String.valueOf(System.currentTimeMillis());
    }

    public static String getOrderIdString(){
        StringBuffer buffer = new StringBuffer();
        //buffer.append("DDBH");
        buffer.append(getDateString());
        buffer.append(getRandomNumber(3));
        return buffer.toString();
    }

    public static String getInvoiceIdString(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("FPBH");
        buffer.append(getDateString());
        buffer.append(getRandomNumber(3));
        return buffer.toString();
    }

    public static String getUserOrderString(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("YHDDH");
        buffer.append(getDateString());
        buffer.append(getRandomNumber(3));
        return buffer.toString();
    }
}
