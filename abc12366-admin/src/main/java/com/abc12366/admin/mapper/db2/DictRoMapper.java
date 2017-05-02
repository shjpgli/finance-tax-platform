package com.abc12366.admin.mapper.db2;

import com.abc12366.admin.model.Dict;
import com.abc12366.admin.model.bo.DictBO;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-24 3:12 PM
 * @since 1.0.0
 */
public interface DictRoMapper {
    List<Dict> selectList();

    List<Dict> selectFirstLevel();

    Dict selectOne(String id);

    Dict selectByDictId(String dictId);

    Dict selectByDictName(String dictName);
}
