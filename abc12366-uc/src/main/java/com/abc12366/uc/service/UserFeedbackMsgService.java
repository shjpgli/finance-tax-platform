package com.abc12366.uc.service;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-11-10
 * Time: 15:11
 */
public interface UserFeedbackMsgService {
    /**
     *  用户修改密码成功前台回调soa发送消息提醒接口
     */
    void updatePasswordSuccessNotice();

    /**
     *  用户每日首次登录未实名认证发送消息提醒
     */
    void unrealname();

    /**
     *用户每日首次登录未实名认证发送消息提醒
     */
    void undotask();

    /**
     *  用户每日首次登录发送消息提醒用户去签到
     */
    void check();

    /**
     *  用户经验值等级提升每发送消息提醒用户
     */
    void expLevelUp();

    /**
     * 用户提交实名认证请求后审核通过或未通过发送消息提醒用户
     */
    void realNameValidate(String status);
}
