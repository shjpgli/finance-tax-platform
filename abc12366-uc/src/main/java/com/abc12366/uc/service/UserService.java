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
     * 用户绑定手机号码
     *
     * @param bindPhoneBO
     */
    void bindPhone(BindPhoneBO bindPhoneBO);
}
