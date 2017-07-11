package com.abc12366.admin.mapper.db2;

import com.abc12366.admin.model.Dict;
import com.abc12366.admin.model.bo.DictBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-24 3:12 PM
 * @since 1.0.0
 */
public interface DictRoMapper {
    List<Dict> selectList(Dict dict);

    List<Dict> selectFirstLevel();

    Dict selectOne(Dict dict);

    Dict selectByDictId(String dictId);

    Dict selectById(String id);

    Dict selectByDictName(String dictName);

    List<DictBO> selectDictList(Dict dict);

    List<Dict> selectListByDictName(Dict dict);

    Dict selectByDict(DictBO dictBO);
}
