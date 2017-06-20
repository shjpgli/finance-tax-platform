package com.abc12366.bangbang.service;


import com.abc12366.bangbang.model.SensitiveWords;
import com.abc12366.bangbang.model.bo.WikiAccesslogBO;

import java.util.List;

/**
 * Created by MY on 2017-05-20.
 */
public interface SensitiveWordsService {

    /**
     * 查询敏感词列表信息
     * @param sensitiveWords
     * @return
     */
    List<SensitiveWords> selectList(SensitiveWords sensitiveWords);

    /**
     * 查询敏感词信息
     * @param id
     * @return
     */
    SensitiveWords selectOne(String id);


    /**
     * 修改购物车
     * @param sensitiveWords
     * @return
     */
    SensitiveWords update(SensitiveWords sensitiveWords);


    /**
     * 新增敏感词
     * @param sensitiveWords
     * @return
     */
    SensitiveWords add(SensitiveWords sensitiveWords);


    /**
     * 删除敏感词
     * @param sensitiveWords
     */
    void delete(SensitiveWords sensitiveWords);

}
