package com.abc12366.bangbang.service;

import com.abc12366.bangbang.model.FollowUser;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-06
 * Time: 16:26
 */
public interface FollowService {
    FollowUser insert(String followedUserId, HttpServletRequest request);

    void delete(String followedUserId);

    List<FollowUser> selectHaveFollowedList(String userId);

    List<FollowUser> selectMyFollowerList(String userId);

    void selectFollowedCount(String followedUserId);
}
