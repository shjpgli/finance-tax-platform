package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.common.UcUserCommon;
import com.abc12366.bangbang.mapper.db1.CurriculumLabelMapper;
import com.abc12366.bangbang.mapper.db1.KnowledgeTagMapper;
import com.abc12366.bangbang.mapper.db1.KnowledgeTagRelMapper;
import com.abc12366.bangbang.model.KnowledgeTag;
import com.abc12366.bangbang.service.KnowledgeTagService;
import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author liuQi
 * @Date 2017/8/4 16:08
 */
@Service
public class KnowledgeTagServiceImpl implements KnowledgeTagService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KnowledgeTagServiceImpl.class);

    @Autowired
    private KnowledgeTagMapper knowledgeTagMapper;

    @Autowired
    private KnowledgeTagRelMapper knowledgeTagRelMapper;

    @Autowired
    private CurriculumLabelMapper curriculumLabelMapper;

    @Override
    public List<String> selectHotTag(Integer num) {
        return knowledgeTagMapper.selectHotTag(num);
    }

    @Override
    public List<KnowledgeTag> selectList(String keywords, Boolean status, String tagType) {
        return knowledgeTagMapper.selectList(keywords, status, tagType);
    }

    @Override
    public List<KnowledgeTag> selectRelatedTags(String knowledgeId) {
        return knowledgeTagMapper.selectRelatedTags(knowledgeId);
    }

    @Override
    public KnowledgeTag selectByPk(String id) {
        return knowledgeTagMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<KnowledgeTag> addBatch(List<KnowledgeTag> knowledgeTags) {
        for (KnowledgeTag tag: knowledgeTags){
            KnowledgeTag tag1 = knowledgeTagMapper.selectByName(tag.getName());
            if(tag1 != null && tag1.getStatus()){
                throw new ServiceException(4520);
            }
            if(tag1 != null && !tag1.getStatus()){
                throw new ServiceException(4521);
            }
            tag.setId(Utils.uuid());
            tag.setCreateUser(UcUserCommon.getAdminId());
            tag.setUpdateUser(UcUserCommon.getAdminId());
        }
        knowledgeTagMapper.insertBatch(knowledgeTags);
        return knowledgeTags;
    }

    @Transactional("db1TxManager")
    @Override
    public List<KnowledgeTag> addBatchByOtherChannel(List<KnowledgeTag> knowledgeTags) {
        List<KnowledgeTag> updateList = new ArrayList<>();
        List<KnowledgeTag> insertList = new ArrayList<>();

        for (KnowledgeTag tag: knowledgeTags){
            KnowledgeTag tag1 = knowledgeTagMapper.selectByName(tag.getName());
            if(tag1 != null && tag1.getTagType().indexOf(tag.getTagType()) > -1 && tag1.getStatus()){
                throw new ServiceException(4520);
            }
            if(tag1 != null && tag1.getTagType().indexOf(tag.getTagType()) > -1 && !tag1.getStatus()){
                throw new ServiceException(4521);
            }
            tag.setUpdateUser(UcUserCommon.getAdminId());
            if(tag1 == null){
                tag.setId(Utils.uuid());
                tag.setCreateUser(UcUserCommon.getAdminId());
                insertList.add(tag);
            }else{
                tag.setId(tag1.getId());
                updateList.add(tag);
            }
        }
        if(!insertList.isEmpty()){
            knowledgeTagMapper.insertBatch(insertList);
        }
        if(!updateList.isEmpty()){
            knowledgeTagMapper.batchUpdateTypeByName(updateList);
        }
        return knowledgeTags;
    }

    @Override
    public KnowledgeTag add(KnowledgeTag knowledgeTag) {
        KnowledgeTag tag = knowledgeTagMapper.selectByName(knowledgeTag.getName());
        if(tag != null && tag.getStatus()){
            throw new ServiceException(4520);
        }
        if(tag != null && !tag.getStatus()){
            throw new ServiceException(4521);
        }
        try {
            knowledgeTag.setId(Utils.uuid());
            knowledgeTag.setCreateUser(UcUserCommon.getAdminId());
            knowledgeTag.setUpdateUser(UcUserCommon.getAdminId());
            knowledgeTagMapper.insert(knowledgeTag);
            return knowledgeTag;
        }catch (Exception e){
            LOGGER.error("KnowledgeTagServiceImpl.add()", e);
            throw new ServiceException(4514);
        }
    }

    @Override
    public KnowledgeTag modify(KnowledgeTag knowledgeTag) {
        KnowledgeTag tag = knowledgeTagMapper.selectByName(knowledgeTag.getName());

        try {
            knowledgeTag.setUpdateUser(UcUserCommon.getAdminId());
            knowledgeTag.setUpdateTime(new Date());
            knowledgeTagMapper.updateByPrimaryKeySelective(knowledgeTag);
            return knowledgeTag;
        }catch (Exception e){
            LOGGER.error("KnowledgeTagServiceImpl.modify()", e);
            throw new ServiceException(4516);
        }
    }

    @Override
    public void modifyStatus(String id, Boolean status) {
        try {
            KnowledgeTag knowledgeTag = new KnowledgeTag();
            knowledgeTag.setId(id);
            knowledgeTag.setStatus(status);
            knowledgeTag.setUpdateUser(UcUserCommon.getAdminId());
            knowledgeTag.setUpdateTime(new Date());
            knowledgeTagMapper.updateByPrimaryKeySelective(knowledgeTag);
        }catch (Exception e){
            LOGGER.error("KnowledgeTagServiceImpl.modifyStatus()", e);
            throw new ServiceException(4517);
        }
    }

    @Transactional("db1TxManager")
    @Override
    public void delete(String id) {
        try {
            curriculumLabelMapper.deleteByLabelId(id);
            knowledgeTagRelMapper.deleteByTagId(id);
            knowledgeTagMapper.deleteByPrimaryKey(id);
        }catch (Exception e){
            LOGGER.error("KnowledgeTagServiceImpl.delete()", e);
            throw new ServiceException(4515);
        }
    }

    @Transactional("db1TxManager")
    @Override
    public void delete(List<String> ids) {
        try {
            curriculumLabelMapper.deleteByLabelIds(ids);
            knowledgeTagRelMapper.deleteByTagIds(ids);
            knowledgeTagMapper.deleteByPrimaryKeys(ids);
        }catch (Exception e){
            LOGGER.error("KnowledgeTagServiceImpl.delete(List<String> ids)", e);
            throw new ServiceException(4515);
        }
    }
}
