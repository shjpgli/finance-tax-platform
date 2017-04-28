package com.abc12366.gateway.mapper.db1;


import com.abc12366.gateway.model.Blacklist;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-06 2:07 PM
 * @since 1.0.0
 */
public interface BlacklistMapper {
    void insert(Blacklist blacklist);

    void update(Blacklist blacklist);

    void delete(String id);
}
