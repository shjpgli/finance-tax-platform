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
    List<AdminBO> selectUser(Admin admin);


    /**
     * 用户列表
     *
     * @param admin
     */
    List<AdminBO> selectList(AdminBO admin);

    /**
     * 添加用户
     *
     * @param adminBO
     */
    int register(AdminBO adminBO);

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
    AdminBO selectUserVOById(String id);

    /**
     * 修改用户
     *
     * @param adminUpdateBO
     */
    AdminUpdateBO updateUser(AdminUpdateBO adminUpdateBO);

    /**
     * 删除用户
     *
     * @param id
     */
    int deleteUserById(String id);

    AdminBO selectOne(String id);

    AdminBO login(AdminBO adminBO, String appId);


    AdminExtend selectUserExtendByUserId(String id);

    AdminExtend updateUserExtend(AdminExtendBO adminExtendBO);

    /**
     * 判断该tonken是否存在
     *
     * @param userToken
     * @return
     */
    boolean isAuthentication(String userToken);

    AdminBO addUser(AdminBO adminBO);

    /**
     * 登出
     *
     * @param token
     */
    void logout(String token);

    /**
     * 修改用户密码
     *
     * @param userPasswordBO
     * @return
     */
    int updateUserPwd(UserPasswordBO userPasswordBO);

    /**
     * 重置用户密码
     *
     * @param id
     * @return
     */
    int resetUserPwd(String id);

    void disableAll();

    void enable(AdminUpdateBO adminUpdateBO);

    Boolean checkToken(String token);

    Boolean refreshToken(String token);

    LoginInfoBO selectLoginInfoByToken(String token);
}
