package com.abc12366.gateway.service;

import com.abc12366.gateway.model.Blacklist;
import com.abc12366.gateway.model.bo.BlacklistBO;

import java.util.List;

/**
 * 黑名单服务
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-06 1:46 PM
 * @since 1.0.0
 */
public interface BlacklistService {

    /**
     * 是否为黑名单
     *
     * @param addr IP地址
     * @return true: 是名单，false:不是黑名单
     */
    boolean isBlacklist(String addr);

    List<Blacklist> selectList();

    Blacklist selectOne(String id);

    Blacklist insert(BlacklistBO bo);

    Blacklist update(BlacklistBO bo);

    void delete(String id);
}
