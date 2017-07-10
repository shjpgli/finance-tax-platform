package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.FollowMapper;
import com.abc12366.bangbang.model.FollowUser;
import com.abc12366.bangbang.service.FollowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-06
 * Time: 16:26
 */
@Service
public class FollowServiceImpl implements FollowService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FollowServiceImpl.class);

    @Autowired
    private FollowMapper followMapper;

    @Override
    public FollowUser insert(String followedUserId, HttpServletRequest request) {
        LOGGER.info("{}:{}", followedUserId, request);
        return null;
    }

    @Override
    public void delete(String followedUserId) {

    }

    @Override
    public List<FollowUser> selectHaveFollowedList(String userId) {
        return null;
    }

    @Override
    public List<FollowUser> selectMyFollowerList(String userId) {
        return null;
    }

    @Override
    public void selectFollowedCount(String followedUserId) {

    }
}
