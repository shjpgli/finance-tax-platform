package com.abc12366.uc.service;

import com.abc12366.uc.model.bo.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-27 4:03 PM
 * @since 1.0.0
 */
public interface AuthService {

    /**
     * 新平台采用手机号码+登录密码+短信验证码注册，平台自动产生用户ID、用户名（字母UC+时间戳毫秒数）和用户昵称（财税+6位数字），同时自动绑定手机号码。
     * 用户ID作为平台内部字段永久有效且不可更改，平台自动产生的用户名可以允许修改一次且平台内唯一，用户名不能为中文，只能为字母+数字。
     *
     * @param registerBO RegisterBO
     * @param request HttpServletRequest
     * @return UserReturnBO
     * @see com.abc12366.uc.model.bo.UserReturnBO
     * @see com.abc12366.uc.model.bo.RegisterBO
     */
    UserReturnBO register(RegisterBO registerBO, HttpServletRequest request);

    /**
     * 用户手机号登陆
     *
     * @param loginBO LoginBO
     * @param accessToken 应用访问token
     * @return Map token,expires_in,用户信息
     * @throws Exception 加密异常
     */
    Map login(LoginBO loginBO, String accessToken) throws Exception;

    Map loginJs(LoginBO loginBO, String token) throws Exception;

    boolean isAuthentication(String userToken, HttpServletRequest request);

    boolean refreshToken(String token);

    Map loginByVerifyingCode(VerifyingCodeBO loginBO, String header) throws Exception;

    /**
     * 校验短信验证码是否正确
     *
     * @param loginVerifyingCodeBO VerifyingCodeBO
     * @param request HttpServletRequest
     * @return true，验证成功；false，验证失败
     */
    boolean verifyCode(VerifyingCodeBO loginVerifyingCodeBO, HttpServletRequest request);

    /**
     * 用户登出
     *
     * @param token 用户token
     */
    void logout(String token);

	Map loginByopenid(UserBO user, String header) throws Exception;

    void loginByVerifyFail(VerifyingCodeBO loginBO);
}
