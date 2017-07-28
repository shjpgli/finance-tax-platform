package com.abc12366.message.util;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-20
 * Time: 0:04
 */
public class MessageConstant {
    //手机验证码长度
    public static final int VERIFY_CODE_LENGTH = 6;

    //网易发短信模板第一个填充参数内容
    public static final String VERIFY_CODE_FILL_CONTENT = "财税专家";

    //网易通知类短信模板id
    //public static final String NEREASE_TEMPLATE_ID = "6149";
    //public static final String NEREASE_TEMPLATE_ID = "3062409";
    //public static final String NEREASE_TEMPLATE_ID = "3033";
    //public static final String NEREASE_TEMPLATE_ID = "3051628";
    public static final String NEREASE_TEMPLATE_ID = "3059771";//33333

    //短信验证码有效时长，单位：秒
    public static final int VERIFY_CODE_VALID_SECONDS = 5 * 60;


    //验证码键名
    public static final String MOBILE_CODE = "mobilecode";

    //验证码手机号
    public static final String MOBILE_PHONE = "mobilephone";

    //网易短信通知接口类别（记日志用）
    public static final String NETEASE_MESSAGE_API = "netease_message_api";

    //阿里云短信通知接口类别（记日志用）
    public static final String ALIYUN_MESSAGE_API = "aliyun_message_api";
}
