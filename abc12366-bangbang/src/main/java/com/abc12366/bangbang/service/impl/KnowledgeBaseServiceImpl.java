package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.common.UcUserCommon;
import com.abc12366.bangbang.mapper.db1.KnowledgeBaseMapper;
import com.abc12366.bangbang.mapper.db1.KnowledgeRelMapper;
import com.abc12366.bangbang.mapper.db1.KnowledgeTagRelMapper;
import com.abc12366.bangbang.model.KnowledgeBase;
import com.abc12366.bangbang.model.KnowledgeRel;
import com.abc12366.bangbang.model.KnowledgeTagRel;
import com.abc12366.bangbang.model.bo.KnowledgeBaseBO;
import com.abc12366.bangbang.model.bo.KnowledgeBaseParamBO;
import com.abc12366.bangbang.service.KnowledgeBaseService;
import com.abc12366.common.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author liuqi
 * @Date 2017/8/2 19:57
 */
@Service
public class KnowledgeBaseServiceImpl implements KnowledgeBaseService{

    private static final Logger LOGGER = LoggerFactory.getLogger(KnowledgeBaseServiceImpl.class);

    @Autowired
    private KnowledgeBaseMapper knowledgeBaseMapper;

    @Autowired
    private KnowledgeTagRelMapper knowledgeTagRelMapper;

    @Autowired
    private KnowledgeRelMapper knowledgeRelMapper;

    @Override
    public List<KnowledgeBase> selectList(KnowledgeBaseParamBO param) {
        return knowledgeBaseMapper.selectList(param);
    }

    @Override
    public void add(KnowledgeBase knowledgeBase) {
        knowledgeBaseMapper.insert(knowledgeBase);
    }

    @Transactional("db1TxManager")
    @Override
    public void delete(List<String> ids) {
        //删除关联标签
        knowledgeTagRelMapper.deleteByKnowledgeIds(ids);
        //删除关联问题
        knowledgeRelMapper.deleteByKnowledgeIds(ids);
        knowledgeRelMapper.deleteByRelKnowledgeIds(ids);
        //删除知识库
        knowledgeBaseMapper.deleteByPrimaryKeys(ids);
    }

    @Transactional("db1TxManager")
    @Override
    public KnowledgeBaseBO add(KnowledgeBaseBO knowledgeBaseBO) {
        KnowledgeBase knowledgeBase = knowledgeBaseBO.getKnowledgeBase();
        knowledgeBase.setId(Utils.uuid());
        knowledgeBase.setCreateUser(UcUserCommon.getUserId());
        knowledgeBase.setUpdateUser(UcUserCommon.getUserId());
        knowledgeBaseMapper.insert(knowledgeBase);
        //添加关联标签
        addTagRel(knowledgeBaseBO);
        //添加关联的问题
        addKnowledgeRel(knowledgeBaseBO);

        return knowledgeBaseBO;
    }

    @Transactional("db1TxManager")
    @Override
    public KnowledgeBaseBO modify(KnowledgeBaseBO knowledgeBaseBO) {

        KnowledgeBase knowledgeBase = knowledgeBaseBO.getKnowledgeBase();
        String knowledgeBaseId = knowledgeBase.getId();
        knowledgeBaseMapper.updateByPrimaryKey(knowledgeBase);

        //管理关联 标签
        knowledgeTagRelMapper.deleteByKnowledgeId(knowledgeBaseId);
        addTagRel(knowledgeBaseBO);
        //管理关联 的知识库
        knowledgeRelMapper.deleteByKnowledgeId(knowledgeBaseId);
        addKnowledgeRel(knowledgeBaseBO);

        return knowledgeBaseBO;
    }

    private void addTagRel(KnowledgeBaseBO knowledgeBaseBO){
        List<String> tagIds = knowledgeBaseBO.getTagIds();
        if(tagIds!=null && !tagIds.isEmpty()){
            KnowledgeBase knowledgeBase = knowledgeBaseBO.getKnowledgeBase();
            List<KnowledgeTagRel> list = new ArrayList<>();
            for (String tagId :tagIds){
                KnowledgeTagRel rel = new KnowledgeTagRel();
                rel.setId(Utils.uuid());
                rel.setKnowledgeId(knowledgeBase.getId());
                rel.setTagId(tagId);
                list.add(rel);
            }
            knowledgeTagRelMapper.insertBatch(list);
        }
    }

    private void addKnowledgeRel(KnowledgeBaseBO knowledgeBaseBO){
        List<String> relKnowledgeIds = knowledgeBaseBO.getRefKnowledgeId();
        if(relKnowledgeIds!= null && !relKnowledgeIds.isEmpty()){
            KnowledgeBase knowledgeBase = knowledgeBaseBO.getKnowledgeBase();
            List<KnowledgeRel> list = new ArrayList<>();
            for (String relKnowledgeId :relKnowledgeIds){
                KnowledgeRel rel = new KnowledgeRel();
                rel.setId(Utils.uuid());
                rel.setKnowledgeId(knowledgeBase.getId());
                rel.setRelKnowledgeId(relKnowledgeId);
                list.add(rel);
            }
            knowledgeRelMapper.insertBatch(list);
        }
    }


}
