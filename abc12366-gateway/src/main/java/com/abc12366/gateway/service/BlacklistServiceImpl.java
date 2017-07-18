package com.abc12366.gateway.service;

import com.abc12366.gateway.mapper.db1.BlacklistMapper;
import com.abc12366.gateway.mapper.db2.BlacklistRoMapper;
import com.abc12366.gateway.model.Blacklist;
import com.abc12366.gateway.model.bo.BlacklistBO;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    private BlacklistMapper blacklistMapper;

    @Override
    public boolean isBlacklist(String addr) {
        Blacklist blackList = new Blacklist();
        blackList.setStatus(true);
        blackList.setNow(new Date());
        List<Blacklist> blacklists = blacklistRoMapper.isBlacklist(blackList);
        return blacklists.size() > 0;
    }

    @Override
    public List<Blacklist> selectList() {
        return blacklistRoMapper.selectList();
    }

    @Override
    public Blacklist selectOne(String id) {
        Blacklist blacklist = new Blacklist();
        blacklist.setId(id);
        return blacklistRoMapper.selectOne(blacklist);
    }

    @Override
    public Blacklist insert(BlacklistBO bo) {
        Blacklist blacklist = new Blacklist();
        BeanUtils.copyProperties(bo, blacklist);

        Date now = new Date();
        blacklist.setCreateTime(now);
        blacklist.setCreateUser(null);

        blacklistMapper.insert(blacklist);
        return blacklist;
    }

    @Override
    public Blacklist update(BlacklistBO bo) {
        Blacklist blacklist = new Blacklist();
        blacklist.setId(bo.getId());
        blacklist = blacklistRoMapper.selectOne(blacklist);
        if (blacklist != null) {
            blacklist.setUserId(bo.getUserId());
            blacklist.setIp(bo.getIp());
            blacklist.setStartTime(bo.getStartTime());
            blacklist.setEndTime(bo.getEndTime());
            blacklist.setRemark(bo.getRemark());
            blacklist.setStatus(bo.getStatus());
            blacklistMapper.update(blacklist);
            return blacklist;
        }
        return null;
    }

    @Override
    public void delete(String id) {
        Blacklist blacklist = new Blacklist();
        blacklist.setId(id);
        blacklist = blacklistRoMapper.selectOne(blacklist);
        if (blacklist != null) {
            blacklistMapper.delete(id);
        }
    }
}
