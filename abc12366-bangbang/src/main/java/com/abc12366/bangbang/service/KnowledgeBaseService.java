package com.abc12366.bangbang.service;

import com.abc12366.bangbang.model.KnowledgeBase;
import com.abc12366.bangbang.model.bo.KnowledgeBaseParamBO;

import java.util.List;

/**
 * @Author liuqi
 * @Date 2017/8/2 19:53
 * 知识库 CRUD
 */
public interface KnowledgeBaseService {

    List<KnowledgeBase> selectList(KnowledgeBaseParamBO param);

    void add(KnowledgeBase knowledgeBase);

}
