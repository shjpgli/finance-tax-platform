package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.common.UcUserCommon;
import com.abc12366.bangbang.mapper.db1.KnowledgeTagMapper;
import com.abc12366.bangbang.model.KnowledgeTag;
import com.abc12366.bangbang.service.KnowledgeTagService;
import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author liuqi
 * @Date 2017/8/4 16:08
 */
@Service
public class KnowledgeTagServiceImpl implements KnowledgeTagService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KnowledgeTagServiceImpl.class);

    @Autowired
    private KnowledgeTagMapper knowledgeTagMapper;

    @Override
    public List<KnowledgeTag> selectList(String keywords) {
        return knowledgeTagMapper.selectList(keywords);
    }

    @Override
    public KnowledgeTag add(KnowledgeTag knowledgeTag) {
        try {
            knowledgeTag.setId(Utils.uuid());
            knowledgeTag.setCreateUser(UcUserCommon.getUserId());
            knowledgeTag.setUpdateUser(UcUserCommon.getUserId());
            knowledgeTagMapper.insert(knowledgeTag);
            return knowledgeTag;
        }catch (Exception e){
            LOGGER.error("KnowledgeTagServiceImpl.add()", e);
            throw new ServiceException(4514);
        }
    }

    @Override
    public KnowledgeTag modify(KnowledgeTag knowledgeTag) {
        try {
            knowledgeTagMapper.updateByPrimaryKey(knowledgeTag);
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
            knowledgeTagMapper.updateByPrimaryKeySelective(knowledgeTag);
        }catch (Exception e){
            LOGGER.error("KnowledgeTagServiceImpl.modifyStatus()", e);
            throw new ServiceException(4517);
        }
    }

    @Override
    public void delete(String id) {
        try {
            knowledgeTagMapper.deleteByPrimaryKey(id);
        }catch (Exception e){
            LOGGER.error("KnowledgeTagServiceImpl.delete()", e);
            throw new ServiceException(4515);
        }
    }
}
