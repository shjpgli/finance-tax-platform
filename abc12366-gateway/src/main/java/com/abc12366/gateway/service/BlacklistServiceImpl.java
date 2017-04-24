package com.abc12366.gateway.service;

import com.abc12366.gateway.mapper.db2.BlacklistRoMapper;
import com.abc12366.gateway.model.Blacklist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-06 1:58 PM
 * @since 1.0.0
 */
@Service
public class BlacklistServiceImpl implements BlacklistService {

    @Autowired
    private BlacklistRoMapper blacklistRoMapper;

    @Override
    public boolean isBlacklist(String addr) {
        Blacklist blacklist = blacklistRoMapper.selectOne(addr);
        return blacklist != null;
    }
}
