package com.abc12366.gateway.util;

/**
 * UC常用静态变量
 * User: liuguiyao<435720953@qq.com>
 * Date: 20170912
 * Time: 15:24
 */
public class UCConstant {
    //uc用户新手任务类型(多为一次性任务)
    public final static String NEW_USER_TASK_TYPE = "1";
    //uc用户日常任务类型
    public final static String NORMAL_TASK_TYPE = "2";
    //uc用户特殊任务类型
    public final static String SPECIAL_TASK_TYPE = "3";
    //uc用户帮帮任务类型
    public final static String BANGBANG_TASK_TYPE = "4";


    //uc用户任务时间类型：一次性任务类型
    public final static String TASK_TIME_ONETIME_TYPE = "O";
    //uc用户任务时间类型：以天为单位任务类型
    public final static String TASK_TIME_DAY_TYPE = "D";
    //uc用户任务时间类型：以月为单位任务类型
    public final static String TASK_TIME_MONTH_TYPE = "M";
    //uc用户任务时间类型：以年为单位任务类型
    public final static String TASK_TIME_YEAR_TYPE = "Y";


    //uc用户任务状态：未完成状态
    public final static String TASK_UNFINISHED = "0";
    //uc用户任务状态：未完成状态
    public final static String TASK_FINISHED = "1";

    // 订单自动确认收货，单位：天
    public final static Integer ORDER_RECEIPT_DAYS = 15;

    // 订单自动取消时间：小时
    public final static Integer ORDER_CANCEL_TIME = 2;


    //UC用户任务奖励类型：0：经验值，1：积分
    public final static String AWARD_TYPE_EXP = "0";
    public final static String AWARD_TYPE_POINT = "1";

    //会员时间，单位：年
    public final static Integer USER_VIP_EXPIRE_DATE = 1;

    //电子申报绑定时间，单位：月
    public final static Integer DZSB_BIND_DATE = 3;

    //系统任务编码
    //1.首次修改登录密码
    public final static String SYS_TASK_FIRST_UPDATE_PASSWROD_CODE = "T-scxgdl";
    //2.首次上传用户头像图片
    public final static String SYS_TASK_FIRST_UPLOAD_PICTURE_CODE = "T-scsctx";
    //3.首次手机认证
    public final static String SYS_TASK_FIRST_PHONE_VALIDATE_CODE = "T-scsjrz";
    //4.首次申报缴税
    public final static String SYS_TASK_SCSBJS_CODE = "T-scsbjs";
    //5.首次消费
    public final static String SYS_TASK_FIRST_CONSUME_CODE = "T-scxf";
    //6.首次下载安装ABC4000
    public final static String SYS_TASK_SCXZAZ_CODE = "T-scxzaz";
    //7.网上零申报
    public final static String SYS_TASK_WSLSB_CODE = "T-wslsb";
    //8.网上缴税
    public final static String SYS_TASK_WSJS_CODE = "T-wsjs";
    //9.固定资产折旧管理
    public final static String SYS_TASK_GDZCZJGL_CODE = "T-gdzxcj";
    //10.海关完税凭证采集
    public final static String SYS_TASK_HGWSPZCJ_CODE = "T-hgwspz";
    //11.查询服务器数据
    public final static String SYS_TASK_CXFWQSJ_CODE = "T-cxwfqsj";
    //12.获取申报结果
    public final static String SYS_TASK_HQSBJG_CODE = "T-hqsbjg";
    //13.分支机构设置
    public final static String SYS_TASK_FZJGSZ_CODE = "T-fzjgsz";
    //14.预缴税款
    public final static String SYS_TASK_YJSK_CODE = "T-yjsk";
    //15.每日回答问题
    public final static String SYS_TASK_MRHDWT_CODE = "T-mrhdwt";
    //16.每日疑难提问
    public final static String SYS_TASK_MRYNTW_CODE = "T-mryntw";
    //17.每日问答收藏
    public final static String SYS_TASK_MRWDSC_CODE = "T-mrwdsc";
    //18.介质申报
    public final static String SYS_TASK_JZSB_CODE = "T-jzsb";
    //19.系统修复
    public final static String SYS_TASK_XTXF_CODE = "T-xtxf";
    //20.每日评论任务
    public final static String SYS_TASK_MRPL_CODE = "T-mrpl";
    //21.每日签到
    public final static String SYS_TASK_CHECK_CODE = "T-mrqd";
    //22.每日收藏课程
    public final static String SYS_TASK_COURSE_COLLECT_CODE = "T-mrsckc";
    //23.每日评论问题
    public final static String SYS_TASK_ASK_COMMENT_CODE = "T-mrplwd";
    //24.每日浏览资讯
    public final static String SYS_TASK_BROSE_NEWS_CODE = "T-mrllzx";
    //25.每日评论课程
    public final static String SYS_TASK_COURSE_COMMENT_CODE = "T-mrplkc";
    //26.消费超过1000人民币
    public final static String SYS_TASK_CONSUME_BEYOND_1000_CODE = "T-ycxxf";
    //27.消费超过3000人民币
    public final static String SYS_TASK_CONSUME_BEYOND_3000_CODE = "ycxxf3k";
    //28.消费超过5000人民币
    public final static String SYS_TASK_CONSUME_BEYOND_5000_CODE = "T-ycxxf5k";
    //29.打印完税凭证
    public final static String SYS_TASK_DYWSPZ_CODE = "T-dywspz";
    //30.作废报表
    public final static String SYS_TASK_ZFBB_CODE = "T-zfbb";
    //31.每日分享课程
    public final static String SYS_TASK_COURSE_SHARE_CODE = "T-mrfxkc";
    //32.绑定税号
    public final static String SYS_TASK_COURSE_BDSH_CODE = "T-mrfxkc";
    //33.每日登录
    public final static String SYS_TASK_LOGIN_CODE = "T-mrdl";
    //34.网上申报
    public final static String SYS_TASK_WSSB_CODE = "T-wssb";
    //35.首次实名认证
    public final static String SYS_TASK_FIRST_REALNAME_VALIDATE_CODE = "T-scsmrz";
    //36.关注财税专家公众号
    public final static String SYS_TASK_GZCSZJGZH_CODE = "T-gzcs";
    //37.首次邮箱认证(暂不做)
    public final static String SYS_TASK_FIRST_MAIL_VALIDATE_CODE = "T-scyxrz";
    //38.下载企业信息
    public final static String SYS_TASK_XZQYXX_CODE = "T-xzqyxx";
    //40.ABC4000实名认证
    public final static String SYS_TASK_ABC4000_SMRZ_CODE = "T-smrz";
    //41.每日课程学习
    public final static String SYS_TASK_COURSE_LEARNING_CODE = "T-mrkcxx";


    //登录tdps密码加密约定码
    public final static String TDPS_LOGIN_PWD_APPOINT_CODE = "abchngs";


    //经验值计算规则编码
    //1.每日登录（由于登陆经验值计算是单做的，此条规则现在只用于展现）
    public final static String EXP_RULE_LOGIN_CODE = "E-mrdl";
    //2.网上申报
    public final static String EXP_RULE_WSSB_CODE = "E-WSSB";
    //3.预缴税款
    public final static String EXP_RULE_YJSK_CODE = "E-yjsk";
    //4.系统修复
    public final static String EXP_RULE_XTXF_CODE = "E-xtxf";
    //5.实名认证
    public final static String EXP_RULE_SMRZ_CODE = "E-smrz";
    //6.每日问答收藏
    public final static String EXP_RULE_MRWDSC_CODE = "E-mrwdsc";
    //7.下载企业信息
    public final static String EXP_RULE_XZQYXX_CODE = "E-qyxx";
    //8.每日问答分享
    public final static String EXP_RULE_MRWDFX_CODE = "E-mrwdfx";
    //9.每日分享课程
    public final static String EXP_RULE_MRFXKC_CODE = "E-mrfxrw";
    //10.绑定税号
    public final static String EXP_RULE_BDSH_CODE = "E-BDSH";
    //11.每日收藏课程
    public final static String EXP_RULE_MRSCKC_CODE = "E-mrsc";
    //12.每日课程学习
    public final static String EXP_RULE_MRKCXX_CODE = "E-KCXX1";
    //13.每日浏览资讯
    public final static String EXP_RULE_MRLLZX_CODE = "E-mrllzx";
    //14.介质申报
    public final static String EXP_RULE_JZSB_CODE = "E-jszb";
    //15.海关完税凭证采集
    public final static String EXP_RULE_HGWSPZCJ_CODE = "E-hg";
    //16.网上零申报
    public final static String EXP_RULE_WSLSB_CODE = "E-wslsb";
    //17.网上缴税
    public final static String EXP_RULE_WSJS_CODE = "E-wsjs";
    //18.作废报表
    public final static String EXP_RULE_ZFBB_CODE = "E-bbzf";
    //19.分支机构设置
    public final static String EXP_RULE_FZJGSZ_CODE = "E-fzjg";
    //20.获取申报结果
    public final static String EXP_RULE_HQSBJG_CODE = "E-sbjg";
    //21.打印完税凭证
    public final static String EXP_RULE_DYWSPZ_CODE = "E-wspz";
    //22.查询服务器数据
    public final static String EXP_RULE_CXFWQSJ_CODE = "E-fwqsj";
    //23.固定资产折旧管理
    public final static String EXP_RULE_GDZCZJGL_CODE = "E-gdzc";
    //24.在线解答
    public final static String EXP_RULE_ZXJD_CODE = "E-ZXJD";
    //25.在线提问
    public final static String EXP_RULE_ZXTW_CODE = "E-ZXTW";
    //26.每日回答问题
    public final static String EXP_RULE_MRHDWT_CODE = "E-hdwt";



    //用户实名认证状态
    //未认证：0
    public final static String USER_REALNAME_UNVALIDATED = "0";
    //待认证：1
    public final static String USER_REALNAME_TO_VALIDATE = "1";
    //已认证：2
    public final static String USER_REALNAME_VALIDATED = "2";
    //认证失败：3
    public final static String USER_REALNAME_FAIL_VALIDATE = "3";


    //积分计算规则ID
    //1.帮帮批量奖励用户积分P-bpljljf
    public final static String POINT_RULE_BANGBANG_BATCH_AWARD_CODE = "P-bpljljf";
    //2.首次申报缴税
    public final static String POINT_RULE_SCSBJS_CODE = "P-sbjs";
    //3.首次邮箱认证
    public final static String POINT_RULE_SCYXRZ_CODE = "P-yxrz";
    //4.用户下单送积分P-xdsjf
    public final static String POINT_RULE_ORDER_CODE = "P-xdsjf";
    //5.首次消费
    public final static String POINT_RULE_SCXF_CODE = "P-yxrz";
    //6.首次修改登录密码
    public final static String POINT_RULE_SCXGDLMM_CODE = "P-xgjymm";
    //7.首次手机认证
    public final static String POINT_RULE_SCSJRZ_CODE = "P-scsjrz";
    //8.退单返还积分P-fhjf
    public final static String POINT_RULE_ORDER_RETURN_CODE = "P-fhjf";
    //每日签到
    public final static String POINT_RULE_CHECK_CODE = "P-mrqd";
    //12.用户等级升级奖励P-yhdjsjjl
    public final static String POINT_RULE_EXP_UP_CODE = "P-yhdjsjjl";
    //11.每日评论问答
    public final static String POINT_RULE_MRPLHD_CODE = "P-mrplwd";
    //12.每日评论课程
    public final static String POINT_RULE_MRPLKC_CODE = "P-mrplkc";
    //13.抽奖扣积分P-cjkjf
    public final static String POINT_RULE_LOTTERY_CODE = "P-cjkjf";
    //14.积分兑换（该条规则不用于积分计算，仅用于展示）
    public final static String POINT_RULE_EXCHANGE_CODE = "P-jfdh";
    //15.用户补签到
    public final static String POINT_RULE_RECHECK_CODE = "P-recheck";
    //16.一次性消费3000
    public final static String POINT_RULE_YCXXF3000_CODE = "P-ycxxx3";
    //17.每日评论
    public final static String POINT_RULE_MRPL_CODE = "P-mrpl";
    //18.一次性消费5000
    public final static String POINT_RULE_YCXXF5000_CODE = "P-ycxxx5";
    //19.一次性消费1000
    public final static String POINT_RULE_YCXXF1000_CODE = "P-ycxxf1";
    //19.首次下载安装ABC4000
    public final static String POINT_RULE_SCXZAZABC4000_CODE = "P-azabc";
    //20.关注财税专家公众号
    public final static String POINT_RULE_GZCSZJGZH_CODE = "P-cszj";
    //21.首次上传头像
    public final static String POINT_RULE_SCSCTX_CODE = "P-sctx";
    //22.首次实名认证
    public final static String POINT_RULE_SCSMRZ_CODE = "P-smrz";


    //用户权益类型
    //1.用户财税经验证明印制
    public final static String PRIVILEGE_TYPE_CSJYZMYZ = "csjyzmyz";
    //2.用户帐号合并特权
    public final static String PRIVILEGE_TYPE_YHZHHBTQ = "yhzhhbtq";
    //3.线下课程培训
    public final static String PRIVILEGE_TYPE_XXKCPX = "xxkcpx";
    //4.线下会员活动
    public final static String PRIVILEGE_TYPE_XXHYHD = "xxhyhd";
    //5.个人帐号绑定企业户数
    public final static String PRIVILEGE_TYPE_GRZHBDQYS = "grzhbdqys";
    //6.发布求职信息
    public final static String PRIVILEGE_TYPE_FBQZXX = "fbqzxx";
    //7.用户积分转让
    public final static String PRIVILEGE_TYPE_YHJFZR = "yhjfzr";
    //8.商品/发票免邮券
    public final static String PRIVILEGE_TYPE_SPFPMYQ = "spfpmyq";


    //用户连续输错密码阈值
    public final static int USER_CONTINUE_PASSWORD_WRONG_MAX = 5;
    //用户连续输错密码超过阈值锁定时间，单位：毫秒
    public final static int LOCK_TIME = 10 * 60 * 1000;

    //消息类型，1：系统消息，2：帮帮消息
    public final static String BUSI_MSG_TYPE_SYSTEM = "1";
    public final static String BUSI_MSG_TYPE_BANGBANG = "2";
    //业务类型：帮帮
    public final static String BUSI_TYPE_BANGBANG = "BANGBANG-AWARD";
}
