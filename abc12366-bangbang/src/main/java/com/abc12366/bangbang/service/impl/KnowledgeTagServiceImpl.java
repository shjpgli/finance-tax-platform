package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.common.UcUserCommon;
import com.abc12366.bangbang.mapper.db1.KnowledgeTagMapper;
import com.abc12366.bangbang.model.KnowledgeTag;
import com.abc12366.bangbang.service.KnowledgeTagService;
import com.abc12366.gateway.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author liuqi
 * @Date 2017/8/4 16:08
 */
@Service
public class KnowledgeTagServiceImpl implements KnowledgeTagService {

    @Autowired
    private KnowledgeTagMapper knowledgeTagMapper;

    @Override
    public List<KnowledgeTag> selectList(String keywords) {
        return knowledgeTagMapper.selectList(keywords);
    }

    @Override
    public KnowledgeTag add(KnowledgeTag knowledgeTag) {
        knowledgeTag.setId(Utils.uuid());
        knowledgeTag.setCreateUser(UcUserCommon.getUserId());
        knowledgeTag.setUpdateUser(UcUserCommon.getUserId());
        knowledgeTagMapper.insert(knowledgeTag);
        return knowledgeTag;
    }

    @Override
    public KnowledgeTag modify(KnowledgeTag knowledgeTag) {
        knowledgeTagMapper.updateByPrimaryKey(knowledgeTag);
        return knowledgeTag;
    }

    @Override
    public void modifyStatus(String id, Boolean status) {
        KnowledgeTag knowledgeTag = new KnowledgeTag();
        knowledgeTag.setId(id);
        knowledgeTag.setStatus(status);
        knowledgeTagMapper.updateByPrimaryKeySelective(knowledgeTag);
    }

    @Override
    public void delete(String id) {
        knowledgeTagMapper.deleteByPrimaryKey(id);
    }
}
