package com.abc12366.uc.service;


import com.abc12366.uc.model.bo.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-22 10:15 AM
 * @since 1.0.0
 */
public interface UserService {

    List<UserBO> selectList(Map<String, Object> map);

    Map selectOne(String userId);

    UserBO update(UserUpdateBO userUpdateBO);

    UserBO selectByUsernameOrPhone(String usernameOrPhone);

    //AdminBO register(RegisterBO registerBO);

    UserBO delete(String userId);

    UserBO authAndRefreshToken(String token);

    Boolean updatePassword(PasswordUpdateBO passwordUpdateBO, HttpServletRequest request);

    void enableOrDisable(String id, String status);
    //String login(LoginBO loginBO, String token) throws Exception;

    void updateUserVipInfo(String userId, String vipLevel);

    UserBO selectByopenid(String openid);

    /**
     * 自动会员失效
     */
    void automaticUserCancel();

    /**
     * 用户更换手机号码
     */
    UserBO updatePhone(UserPhoneBO bo);

    /**
     * 用户未登录状态下获取用户简单信息：用户编号，用户昵称，用户头像，擅长领域
     *
     * @param userId
     * @return
     */
    UserSimpleInfoBO selectSimple(String userId);

    /**
     * 用户登录状态下发送短信接口
     *
     * @param sendCodeBO
     */
    void loginedSendCode(LoginedSendCodeBO sendCodeBO);

    /**
     * 用户已登录状态下通过用户ID和验证码校验验证码
     * @param verifyCodeBO
     */
    void loginedVerifyCode(LoginedVerifyCodeBO verifyCodeBO);

    /**
     * 用户绑定手机号码
     *
     * @param bindPhoneBO
     */
    void bindPhone(BindPhoneBO bindPhoneBO);

    /**
     * 用户手机+验证码登录专用接口
     * @param sendCodeBO
     */
    void phoneLoginSendCode(SendPhoneCodeParam sendCodeBO);

    /**
     * 旧手机号码有效性校验
     * @param oldPhone
     */
    void verifyOldPhone(oldPhoneBO oldPhone);

    /**
     * 用户是否实名认证
     * @return
     */
    IsRealNameBO isRealName();

    /**
     * 根据用户ID获取用户信息和用户扩展信息（供后台管理系统使用，敏感信息不做模糊化处理）
     * @param id
     * @return
     */
    Map selectOneForAdmin(String id);
}
