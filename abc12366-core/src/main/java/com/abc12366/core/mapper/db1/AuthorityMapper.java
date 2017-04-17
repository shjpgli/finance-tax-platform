package com.abc12366.core.mapper.db1;

import com.abc12366.core.model.Authority;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-28 9:21 AM
 * @since 1.0.0
 */
public interface AuthorityMapper {

    int insert(Authority authority);

    List<String> selectByUserId(String id);
}
