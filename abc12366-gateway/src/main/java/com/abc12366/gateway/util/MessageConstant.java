package com.abc12366.gateway.util;

/**
 * 消息相关常量定义
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-28 5:12 PM
 * @since 1.0.0
 */
public class MessageConstant {
    //手机验证码长度
    public static final int VERIFY_CODE_LENGTH = 6;

    //短信验证码有效时长，单位：秒
    public static final int VERIFY_CODE_VALID_SECONDS = 5 * 60;

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

    // 又拍template_id,发送验证码模板
    public final static String MESSAGE_UPYUN_TEMPLATE_296 = "296";
    // 又拍template_id,发送业务通知短信模板
    public final static String MESSAGE_UPYUN_TEMPLATE_529 = "529";

    // 发送订单和发票通知短信模板
    public final static String MESSAGE_UPYUN_TEMPLATE_615 = "615";

    //商品订单
    public static final String SPDD = "SPDD";
    //纸质发票订单
    public static final String ZZFPDD = "ZZFPDD";
    //电子发票订单
    public static final String DZFPDD = "DZFPDD";
    //课程报名
    public static final String KCBM = "KCBM";
    //课程签到
    public static final String KCQD = "KCQD";
    //系统提醒
    public static final String XTTX = "XTTX";
    public static final String VIEW_DETAILS = "查看详情";

    //web消息
    public static final String YWTX_WEB = "web";
    //微信消息
    public static final String YWTX_WECHAT = "wechat";
    //短信消息
    public static final String YWTX_MESSAGE = "message";
    //业务提醒代码
    public static final String YWTX_CODE = "A_YWTX";
    //系统提醒代码
    public static final String XTTX_CODE = "A_XTTX";


    //消息类型，1：系统消息，2：帮帮消息
    public static final String SYS_MESSAGE = "1";
    public final static String BB_MESSAGE = "2";
    //业务类型：帮帮
    public final static String BUSI_TYPE_BANGBANG = "BANGBANG-AWARD";
    
    //阿里云短信验证码模板
    public final static String ALIYUNTEMP_YZM="SMS_100835124";
    
    //阿里云短信业务消息模板
    public final static String ALIYUNTEMP_DXTZ="SMS_114060175";


    //运营消息业务类型
    public final static String YYXX_BUSITYPE = "YYXX";
    //运营消息-系统消息类型
    public final static String YYXX_WEB="web";
    //运营消息-微信消息类型
    public final static String YYXX_WECHAT="wechat";
    //运营消息-短信消息类型
    public final static String YYXX_MESSAGE="message";
}
