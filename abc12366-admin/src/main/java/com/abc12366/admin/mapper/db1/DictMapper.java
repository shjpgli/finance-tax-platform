package com.abc12366.admin.mapper.db1;

import com.abc12366.admin.model.Dict;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-24 3:11 PM
 * @since 1.0.0
 */
public interface DictMapper {

    int insert(Dict dict);

    int update(Dict dict);

    int delete(String id);
}
