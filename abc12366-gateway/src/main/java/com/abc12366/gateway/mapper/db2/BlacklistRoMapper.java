package com.abc12366.gateway.mapper.db2;


import com.abc12366.gateway.model.Blacklist;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-06 2:07 PM
 * @since 1.0.0
 */
public interface BlacklistRoMapper {
    Blacklist selectOne(String addr);
}
