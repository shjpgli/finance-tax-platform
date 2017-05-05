package com.abc12366.admin.service;

import com.abc12366.admin.model.User;
import com.abc12366.admin.model.bo.UserBO;

import java.util.List;

/**
 * @description：用户管理
 * @author：lizhongwei
 */
public interface UserService {
    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    User selectUserByLoginName(String username);

    /**
     * 根据用户id查询用户
     *
     * @param id
     * @return
     */
    User selectUserById(String id);

    /**
     * 用户列表
     *
     * @param user
     */
    List<UserBO> selectUser(User user);


    /**
     * 用户列表
     *
     */
    List<User> selectList();

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
     * @param userBO
     */
    int updateUser(UserBO userBO);

    /**
     * 删除用户
     *
     * @param id
     */
    int deleteUserById(String id);


    User selectOne(String id);

    UserBO login(UserBO userBO);
}
