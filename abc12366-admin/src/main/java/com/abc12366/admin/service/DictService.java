package com.abc12366.admin.service;

import com.abc12366.admin.model.Dict;
import com.abc12366.admin.model.bo.DictBO;
import com.abc12366.admin.model.bo.DictUpdateBO;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-24 3:09 PM
 * @since 1.0.0
 */
public interface DictService {
    List<Dict> selectList(Dict dict);

    DictBO selectOne(Dict dict);

    List<DictBO> selectFirstLevel();

    DictBO insert(DictBO dictBO);

    DictBO update(DictUpdateBO dictUpdateBO);

    int delete(String id);

    Dict selectById(String id);

    List<DictBO> selectDictList(Dict dict);
}
