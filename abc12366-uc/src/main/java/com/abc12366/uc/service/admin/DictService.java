package com.abc12366.uc.service.admin;

import com.abc12366.uc.model.Dict;
import com.abc12366.uc.model.bo.DictBO;
import com.abc12366.uc.model.bo.DictUpdateBO;

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

    void batchDelete(Dict bo);

    List<Dict> selectListByDictName(Dict dict);
}
