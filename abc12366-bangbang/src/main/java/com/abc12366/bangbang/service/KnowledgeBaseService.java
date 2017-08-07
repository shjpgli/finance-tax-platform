package com.abc12366.bangbang.service;

import com.abc12366.bangbang.model.KnowledgeBase;
import com.abc12366.bangbang.model.KnowledgeVoteLog;
import com.abc12366.bangbang.model.bo.KnowledgeBaseBO;
import com.abc12366.bangbang.model.bo.KnowledgeBaseHotParamBO;
import com.abc12366.bangbang.model.bo.KnowledgeBaseParamBO;
import com.abc12366.bangbang.model.bo.KnowledgeVoteLogBO;

import java.util.List;
import java.util.Map;

/**
 * @Author liuqi
 * @Date 2017/8/2 19:53
 * 知识库 CRUD
 */
public interface KnowledgeBaseService {

    /* 帮助中心首页 热点问题，词条 */
    Map<String, List<KnowledgeBase>> hotMap(KnowledgeBaseHotParamBO paramBO);

    /* 知识库列表查询数据*/
    List<KnowledgeBase> selectList(KnowledgeBaseParamBO param);

    /* 根据知识ID 查询关联感兴趣的知识列表 */
    List<KnowledgeBase> interestedList(String id, int num);

    /* 知识库单个查询*/
    KnowledgeBase selectOne(String id);

    /* 新增知识库数据 */
    void add(KnowledgeBase knowledgeBase);

    /* 批量删除知识库 */
    void delete(List<String> ids);

    /*添加知识库数据*/
    KnowledgeBaseBO add(KnowledgeBaseBO knowledgeBaseBO);

    /*修改知识库数据*/
    KnowledgeBaseBO modify(KnowledgeBaseBO knowledgeBaseBO);

    /*修改知识库数据 浏览量*/
    void addPV(String id);

    /*新增知识库投票数据*/
    void addVote(KnowledgeVoteLog knowledgeVoteLog);

    /*删除知识库投票数据*/
    void deleteVoteLogs(List<String> ids);

    /*知识库投票列表查询*/
    List<KnowledgeVoteLogBO> selectVoteList(KnowledgeBaseParamBO param);

}
