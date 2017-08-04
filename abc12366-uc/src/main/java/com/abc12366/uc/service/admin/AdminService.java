package com.abc12366.uc.service.admin;
import com.abc12366.uc.model.admin.Admin;
import com.abc12366.uc.model.admin.AdminExtend;
import com.abc12366.uc.model.admin.bo.*;

import java.util.List;

/**
 * @description：用户管理
 * @author：lizhongwei
 */
public interface AdminService {
    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    Admin selectUserByLoginName(String username);

    /**
     * 根据用户id查询用户
     *
     * @param id
     * @return
     */
    Admin selectUserById(String id);

    /**
     * 用户列表
     *
     * @param admin
     */
    List<UserBO> selectUser(Admin admin);


    /**
     * 用户列表
     *
     * @param admin
     */
    List<UserBO> selectList(Admin admin);

    /**
     * 添加用户
     *
     * @param userBO
     */
    int register(UserBO userBO);

    /**
     * 修改密码
     *
     * @param userId
     * @param pwd
     */
    void updateUserPwdById(String userId, String pwd);

    /**
     * 根据用户id查询用户带部门
     *
     * @param id
     * @return
     */
    UserBO selectUserVOById(String id);

    /**
     * 修改用户
     *
     * @param userUpdateBO
     */
    UserUpdateBO updateUser(UserUpdateBO userUpdateBO);

    /**
     * 删除用户
     *
     * @param id
     */
    int deleteUserById(String id);

    UserBO selectOne(String id);

    UserBO login(UserBO userBO, String appId);


    AdminExtend selectUserExtendByUserId(String id);

    AdminExtend updateUserExtend(UserExtendBO userExtendBO);

    /**
     * 判断该tonken是否存在
     * @param userToken
     * @return
     */
    boolean isAuthentication(String userToken);

    UserBO addUser(UserBO userBO);

    /**
     * 登出
     * @param token
     */
    void logout(String token);

    /**
     * 修改用户密码
     * @param userPasswordBO
     * @return
     */
    int updateUserPwd(UserPasswordBO userPasswordBO);

    /**
     * 重置用户密码
     * @param id
     * @return
     */
    int resetUserPwd(String id);

    void disableAll();

    void enable(UserUpdateBO userUpdateBO);

    Boolean checkToken(String token);

    Boolean refreshToken(String token);

    LoginInfoBO selectLoginInfoByToken(String token);
}
