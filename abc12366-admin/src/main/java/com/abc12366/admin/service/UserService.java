package com.abc12366.admin.service;

import com.abc12366.admin.model.User;
import com.abc12366.admin.vo.UserVO;

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
    List<UserVO> selectUser(User user);

    /**
     * 添加用户
     *
     * @param userVO
     */
    void addUser(UserVO userVO);

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
    UserVO selectUserVOById(String id);

    /**
     * 修改用户
     *
     * @param userVO
     */
    void updateUser(UserVO userVO);

    /**
     * 删除用户
     *
     * @param id
     */
    void deleteUserById(String id);

}
