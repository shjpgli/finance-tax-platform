package com.abc12366.uc.service;


import com.abc12366.uc.model.User;
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

    /**
     * 后台查询用户列表
     *
     * @param map  用户信息
     * @param page 当前页
     * @param size 每页大小
     * @return 用户列表List
     */
    List<UserListBO> selectList(Map<String, Object> map, int page, int size);

    User selectUser(String userId);

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
     *
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
     *
     * @param sendCodeBO
     */
    void phoneLoginSendCode(SendPhoneCodeParam sendCodeBO);

    /**
     * 旧手机号码有效性校验
     *
     * @param oldPhone
     */
    void verifyOldPhone(oldPhoneBO oldPhone);

    /**
     * 用户是否实名认证
     *
     * @return
     */
    IsRealNameBO isRealName();

    /**
     * 根据用户ID获取用户信息和用户扩展信息（供后台管理系统使用，敏感信息不做模糊化处理）
     *
     * @param id
     * @return
     */
    Map selectOneForAdmin(String id);

    /**
     * 获取正常用户总数
     *
     * @return
     */
    int getAllNomalCont();

    /**
     * 分页获取正常用户信息
     *
     * @param map
     * @return
     */
    List<UserBO> getNomalList(Map<String, Object> map);

    /**
     * 微信绑定关系修改
     *
     * @param userUpdateDTO
     * @return
     */
    int changeWxBdxx(UserUpdateBO userUpdateDTO);

    /**
     * 通过绑定的国税纳税人识别号查询用户信息
     *
     * @param nsrsbh
     * @return
     */
    List<User> findByHngsNsrsbh(String nsrsbh);

    /**
     * 根据手机号码查询用户
     *
     * @param phone 手机号码
     * @return UserBO
     */
    UserBO selectOneByPhone(String phone);

    /**
     * 根据用户ID查询用户基本表信息
     *
     * @param user 用户条件
     * @return 用户基本表信息
     */

    User selectUserById(User user);
    /**
     * 统计用户，统计维度为【月份】
     * @param map
     * @return
     */
    List<UserStatisBO> statisUserByMonth(Map<String, Object> map);

    /**
     * 统计用户，列表查询
     * @param map
     * @return
     */
    List<UserSimpleInfoBO> statisUserList(Map<String, Object> map);

    /**
     * 用户流失率统计
     * @param map
     * @return
     */
    UserLossRateBO statisUserLossRate(Map<String, Object> map);
}
