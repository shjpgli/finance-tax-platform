package com.abc12366.uc.util;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-09-12
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


    //系统任务ID
    //用户登录任务
    public final static String SYS_TASK_LOGIN_ID = "55fe8293-a53b-46a2-8a8e-1269339d888f";
    //用户签到任务
    public final static String SYS_TASK_CHECK_ID = "8b125435-755a-4e9e-b1da-f92a17c736f6";
    //每日课程学习
    public final static String SYS_TASK_COURSE_LEARNING_ID = "76303795-aa1f-480b-94f4-51ac0deb3bb2";
    //每日课程分享
    public final static String SYS_TASK_COURSE_SHARE_ID = "de72f594-7d03-4473-a50c-dfe72279e5d2";
    //每日课程收藏
    public final static String SYS_TASK_COURSE_COLLECT_ID = "f2a0a28c-8c10-4d33-b1b2-9ab10aff3e3f";
    //每日课程评论
    public final static String SYS_TASK_COURSE_COMMENT_ID = "1df0299f-cd25-4c02-9cdf-2160bae56884";
    //每日问题分享
    public final static String SYS_TASK_ASK_SHARE_ID = "22575ee9-b531-4089-8fb8-a7c5a0b48e22";
    //每日问题收藏
    public final static String SYS_TASK_ASK_COLLECT_ID = "a02d6429-3567-4c69-9fc3-dd4514df4260";
    //每日问题评论
    public final static String SYS_TASK_ASK_COMMENT_ID = "d2e76477-9452-4285-a747-bd49788bdabc";
    //每日浏览资讯
    public final static String SYS_TASK_BROSE_NEWS_ID = "ebbe59c7-c597-4d96-ac19-b2a8554da53a";

    //首次消费
    public final static String SYS_TASK_FIRST_CONSUME_ID = "587748ea-05cd-4f60-9010-cb88786612a0";
    //首次手机认证
    public final static String SYS_TASK_FIRST_PHONE_VALIDATE_ID = "db7c37b6-00b8-4b29-9c31-dfd8f2b30322";
    //首次邮箱认证(暂不做)
    public final static String SYS_TASK_FIRST_MAIL_VALIDATE_ID = "ac5c676e-80a6-4d49-b809-a24d49b997bf";
    //首次实名认证
    public final static String SYS_TASK_FIRST_REALNAME_VALIDATE_ID = "99903d77-6197-44ef-86c6-df14aab9c269";
    //首次修改登录密码
    public final static String SYS_TASK_FIRST_UPDATE_PASSWROD_ID = "";
    //首次上传用户头像图片
    public final static String SYS_TASK_FIRST_UPLOAD_PICTURE_ID = "";

    //登录tdps密码加密约定码
    public final static String TDPS_LOGIN_PWD_APPOINT_CODE = "abchngs";


    //经验值计算规则ID
    //1.网上申报
    public final static String EXP_RULE_WSSB_ID = "";
    //1.登录（此条规则并不起作用，只是勇于展现）
    public final static String EXP_RULE_LOGIN_ID = "a9ac2dbd-61e6-411b-a59b-5f6f698b96f4";


    //用户实名认证状态
    //未认证：0
    public final static String USER_REALNAME_UNVALIDATED = "0";
    //待认证：1
    public final static String USER_REALNAME_TO_VALIDATE = "1";
    //已认证：2
    public final static String USER_REALNAME_VALIDATED= "2";
    //认证失败：3
    public final static String USER_REALNAME_FAIL_VALIDATE= "3";


    //积分计算规则ID
    //签到
    public final static String POINT_RULE_CHECK_ID = "d4aa8dca-d7e7-469f-93b4-764e35050a4f";
    //补签到
    public final static String POINT_RULE_RECHECK_ID = "003a6366-0bba-4a0b-8486-9b3c2805adeb";
    //积分兑换（该条规则不用于积分计算，仅用于展示）
    public final static String POINT_RULE_EXCHANGE_ID = "9e945332-2001-431a-bbc7-e8c38b66572f";
    //抽奖扣积分
    public final static String POINT_RULE_LOTTERY_ID = "31428853-2339-4523-91db-32a182f4fbcf";

}
