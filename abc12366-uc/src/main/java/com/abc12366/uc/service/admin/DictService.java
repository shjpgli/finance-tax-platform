package com.abc12366.uc.service.admin;

import com.abc12366.uc.model.Dict;
import com.abc12366.uc.model.bo.DictBO;
import com.abc12366.uc.model.bo.DictUpdateBO;

import java.util.List;

/**
 * 数据字典服务
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-24 3:09 PM
 * @since 1.0.0
 */
public interface DictService {

    /**
     * 字典列表查询
     *
     * @param dict {@linkplain com.abc12366.uc.model.Dict Dict}
     * @return List<Dict>
     */
    List<Dict> selectList(Dict dict);

    /**
     * 查看单个字典实体
     *
     * @param dict {@linkplain com.abc12366.uc.model.Dict Dict}
     * @return DictBO {@linkplain com.abc12366.uc.model.bo.DictBO DictBO}
     */
    DictBO selectOne(Dict dict);

    /**
     * 查找字典第一级列表
     *
     * @return List<DictBO> {@linkplain com.abc12366.uc.model.bo.DictBO DictBO}
     */
    List<DictBO> selectFirstLevel();

    DictBO insert(DictBO dictBO);

    DictBO update(DictUpdateBO dictUpdateBO);

    int delete(String id);

    Dict selectById(String id);

    List<DictBO> selectDictList(Dict dict);

    void batchDelete(Dict bo);

    List<Dict> selectListByDictName(Dict dict);

    List<Dict> selectList(String dictId);
}
