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
    public final static String SYS_TASK_CHECK_ID = "a7668563-b70c-447e-9ddb-9dd63bf63282";
    //每日课程学习
    public final static String SYS_TASK_COURSE_LEARNING_ID = "76303795-aa1f-480b-94f4-51ac0deb3bb2";
    //每日分享
    public final static String SYS_TASK_SHARE_ID = "";
    //每日收藏
    public final static String SYS_TASK_COLLECT_ID = "";
    //每日评论
    public final static String SYS_TASK_COMMENT_ID = "";
    //每日浏览资讯
    public final static String SYS_TASK_BROSE_NEWS_ID = "";

    //首次消费
    public final static String SYS_TASK_FIRST_CONSUME_ID = "587748ea-05cd-4f60-9010-cb88786612a0";
    //首次手机认证
    public final static String SYS_TASK_FIRST_PHONE_VALIDATE_ID = "db7c37b6-00b8-4b29-9c31-dfd8f2b30322";
    //首次邮箱认证
    public final static String SYS_TASK_FIRST_MAIL_VALIDATE_ID = "";
    //首次实名认证
    public final static String SYS_TASK_FIRST_REALNAME_VALIDATE_ID = "";


    //登录tdps密码加密约定码
    public final static String TDPS_LOGIN_PWD_APPOINT_CODE = "abchngs";


    //经验值计算规则ID
    //1.网上申报
    public final static String EXP_RULE_WSSB_ID = "abchngs";


    //用户实名认证状态
    //未认证：0
    public final static String USER_REALNAME_UNVALIDATED = "0";
    //待认证：1
    public final static String USER_REALNAME_TO_VALIDATE = "1";
    //已认证：2
    public final static String USER_REALNAME_VALIDATED= "2";
    //认证失败：3
    public final static String USER_REALNAME_FAIL_VALIDATE= "3";
}
