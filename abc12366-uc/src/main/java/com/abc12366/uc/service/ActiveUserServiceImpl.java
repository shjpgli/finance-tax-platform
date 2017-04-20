package com.abc12366.uc.service;

import com.abc12366.uc.mapper.db1.ActiveUserMapper;
import com.abc12366.uc.model.bo.ActiveUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-23 10:24 AM
 * @since 1.0.0
 */
@Service
public class ActiveUserServiceImpl implements ActiveUserService {

    @Autowired
    private ActiveUserMapper activeUserMapper;

    @Override
    public List<ActiveUser> selectList() {
        return activeUserMapper.selectList();
    }

    @Override
    public ActiveUser selectOne(String id) {
        return activeUserMapper.selectOne(id);
    }

    @Override
    public int delete(String id) {
        ActiveUser user = activeUserMapper.selectOne(id);
        return user != null ? activeUserMapper.delete(id) : 0;
    }

    @Override
    public ActiveUser insert(ActiveUser user) {
        activeUserMapper.insert(user);
        return user;
    }
}
