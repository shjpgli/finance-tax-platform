package com.abc12366.bangbang.service;

import com.abc12366.bangbang.model.KnowledgeBase;
import com.abc12366.bangbang.model.KnowledgeVoteLog;
import com.abc12366.bangbang.model.bo.KnowledgeBaseBO;
import com.abc12366.bangbang.model.bo.KnowledgeBaseHotParamBO;
import com.abc12366.bangbang.model.bo.KnowledgeBaseParamBO;

import java.util.List;
import java.util.Map;

/**
 * @Author liuqi
 * @Date 2017/8/2 19:53
 * 知识库 CRUD
 */
public interface KnowledgeBaseService {

    Map<String, List<KnowledgeBase>> hotMap(KnowledgeBaseHotParamBO paramBO);

    List<KnowledgeBase> selectList(KnowledgeBaseParamBO param);

    List<KnowledgeBase> interestedList(String id, int num);

    KnowledgeBase selectOne(String id);

    void add(KnowledgeBase knowledgeBase);

    void delete(List<String> ids);

    /*添加知识库数据*/
    KnowledgeBaseBO add(KnowledgeBaseBO knowledgeBaseBO);

    KnowledgeBaseBO modify(KnowledgeBaseBO knowledgeBaseBO);

    /*修改知识库数据 是否有用*/
    void useFull(KnowledgeVoteLog knowledgeVoteLog);

    /*修改知识库数据 浏览量*/
    void addPV(String id);

}
