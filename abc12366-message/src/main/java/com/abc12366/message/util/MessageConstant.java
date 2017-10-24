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

    //短信通道：阿里：ali，网易：wy，又拍：yp
    public static final String MSG_CHANNEL_ALI = "ali";
    public static final String MSG_CHANNEL_NETEASE = "wy";
    public static final String MSG_CHANNEL_YOUPAI = "yp";

    //短信业务类型：发送业务通知短信
    public static final String MOBILE_MSG_BUSI_TYPE = "业务通知";
    //发送状态（0待发送，1发送成功，4发送失败）
    public static final String SEND_MSG_STATUS_SUCCESS = "1";
    public static final String SEND_MSG_STATUS_FAIL = "4";

    //短信接口异常
    public static final String SEND_MSG_CHANNEL_ERROR_CODE = "111";
    public static final String SEND_MSG_CHANNEL_ERROR_YOUPAI = "又拍短信接口异常";
    public static final String SEND_MSG_CHANNEL_ERROR_ALI = "阿里云短信接口异常";
    public static final String SEND_MSG_CHANNEL_ERROR_NETEASE = "网易云短信接口异常";

    //短信发送成功编码和文字描述
    public static final String SEND_MSG_SUCCESS_CODE = "200";
    public static final String SEND_MSG_SUCCESS_CONTENT = "发送成功";
}
