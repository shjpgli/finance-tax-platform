package com.abc12366.gateway.service;

import com.abc12366.gateway.mapper.db2.BlacklistRoMapper;
import com.abc12366.gateway.model.Blacklist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
        Blacklist blackList = new Blacklist.Builder()
                .ip(addr)
                .status(true)
                .now(new Date())
                .build();
        List<Blacklist> blacklists = blacklistRoMapper.isBlacklist(blackList);
        return blacklists.size() > 0;
    }
}
