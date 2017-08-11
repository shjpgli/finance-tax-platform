package com.abc12366.bangbang.service;

import com.abc12366.bangbang.model.KnowledgeTag;

import java.util.List;

/**
 * @Author liuqi
 * @Date 2017/8/4 15:59
 * 知识库标签
 */
public interface KnowledgeTagService {

    /* 知识库标签查询 */
    List<KnowledgeTag> selectList(String keywords);

    /* 知识库关联标签查询 */
    List<KnowledgeTag> selectRelatedTags(String knowledgeId);

    /* 批量新增标签 */
    List<KnowledgeTag> addBatch(List<KnowledgeTag> knowledgeTags);

    /* 新增标签 */
    KnowledgeTag add(KnowledgeTag knowledgeTag);

    /* 编辑标签 */
    KnowledgeTag modify(KnowledgeTag knowledgeTag);

    /* 启用，停用 */
    void modifyStatus(String id, Boolean status);

    /* 删除标签 */
    void delete(String id);

}
