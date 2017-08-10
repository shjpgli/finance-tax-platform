package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.common.UcUserCommon;
import com.abc12366.bangbang.mapper.db1.KnowledgeBaseMapper;
import com.abc12366.bangbang.mapper.db1.KnowledgeRelMapper;
import com.abc12366.bangbang.mapper.db1.KnowledgeTagRelMapper;
import com.abc12366.bangbang.mapper.db1.KnowledgeVoteLogMapper;
import com.abc12366.bangbang.model.KnowledgeBase;
import com.abc12366.bangbang.model.KnowledgeRel;
import com.abc12366.bangbang.model.KnowledgeTagRel;
import com.abc12366.bangbang.model.KnowledgeVoteLog;
import com.abc12366.bangbang.model.bo.KnowledgeBaseBO;
import com.abc12366.bangbang.model.bo.KnowledgeBaseHotParamBO;
import com.abc12366.bangbang.model.bo.KnowledgeBaseParamBO;
import com.abc12366.bangbang.model.bo.KnowledgeVoteLogBO;
import com.abc12366.bangbang.service.KnowledgeBaseService;
import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author liuqi
 * @Date 2017/8/2 19:57
 */
@Service
public class KnowledgeBaseServiceImpl implements KnowledgeBaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KnowledgeBaseServiceImpl.class);

    @Autowired
    private KnowledgeBaseMapper knowledgeBaseMapper;

    @Autowired
    private KnowledgeTagRelMapper knowledgeTagRelMapper;

    @Autowired
    private KnowledgeRelMapper knowledgeRelMapper;

    @Autowired
    private KnowledgeVoteLogMapper knowledgeVoteLogMapper;

    @Override
    public Map<String, List<KnowledgeBase>> hotMap(KnowledgeBaseHotParamBO paramBO) {
        List<KnowledgeBase> list = knowledgeBaseMapper.hotList(paramBO);
        if(!list.isEmpty()){
            return category(list);
        }
        return Collections.EMPTY_MAP;
    }

    @Override
    public List<KnowledgeBase> selectList(KnowledgeBaseParamBO param) {
        return knowledgeBaseMapper.selectList(param);
    }

    @Override
    public List<KnowledgeBase> interestedList(String id, int num) {
        return knowledgeBaseMapper.interestedList(id, num);
    }

    @Override
    public List<KnowledgeBase> relatedList(String id, int num) {
        return knowledgeBaseMapper.relatedList(id, num);
    }

    @Override
    public KnowledgeBase selectOne(String id) {
        return knowledgeBaseMapper.selectByPrimaryKey(id);
    }

    @Override
    public void add(KnowledgeBase knowledgeBase) {
        try {
            knowledgeBaseMapper.insert(knowledgeBase);
        }catch (Exception e){
            LOGGER.error("KnowledgeBaseServiceImpl.add():" + e);
            throw new ServiceException(4501);
        }
    }

    @Transactional("db1TxManager")
    @Override
    public void delete(List<String> ids) {
        try {
            //删除关联标签
            knowledgeTagRelMapper.deleteByKnowledgeIds(ids);
            //删除关联问题
            knowledgeRelMapper.deleteByKnowledgeIds(ids);
            knowledgeRelMapper.deleteByRelKnowledgeIds(ids);
            //根据知识库ids删除知识库投票数据
            knowledgeVoteLogMapper.deleteByKnowledgeIds(ids);
            //删除知识库
            knowledgeBaseMapper.deleteByPrimaryKeys(ids);
        }catch (Exception e){
            LOGGER.error("KnowledgeBaseServiceImpl.delete():" + e);
            throw new ServiceException(4503);
        }
    }

    @Override
    public void modifyStatus(Map<String,Object> map) {
        knowledgeBaseMapper.updateStatusByPKs(map);
    }

    @Transactional("db1TxManager")
    @Override
    public KnowledgeBaseBO add(KnowledgeBaseBO knowledgeBaseBO) {
        try {
            KnowledgeBase knowledgeBase = knowledgeBaseBO.getKnowledgeBase();
            knowledgeBase.setId(Utils.uuid());
            knowledgeBase.setCreateUser(UcUserCommon.getAdminId());
            knowledgeBase.setUpdateUser(UcUserCommon.getAdminId());
            Long zero = new Long(0);
            knowledgeBase.setPv(zero);
            knowledgeBase.setShareNum(zero);
            knowledgeBase.setUsefulVotes(zero);
            knowledgeBase.setUselessVotes(zero);
            knowledgeBaseMapper.insert(knowledgeBase);
            //添加关联标签
            addTagRel(knowledgeBaseBO);
            //添加关联的问题
            addKnowledgeRel(knowledgeBaseBO);

            return knowledgeBaseBO;
        }catch (Exception e){
            LOGGER.error("KnowledgeBaseServiceImpl.add(KnowledgeBaseBO knowledgeBaseBO):" + e);
            throw new ServiceException(4501);
        }
    }

    @Transactional("db1TxManager")
    @Override
    public KnowledgeBaseBO modify(KnowledgeBaseBO knowledgeBaseBO) {
        try {
            KnowledgeBase knowledgeBase = knowledgeBaseBO.getKnowledgeBase();
            knowledgeBase.setUpdateTime(new Date());
            knowledgeBase.setUpdateUser(UcUserCommon.getAdminId());
            String knowledgeBaseId = knowledgeBase.getId();
            knowledgeBaseMapper.updateByPrimaryKey(knowledgeBase);

            //管理关联 标签
            knowledgeTagRelMapper.deleteByKnowledgeId(knowledgeBaseId);
            addTagRel(knowledgeBaseBO);
            //管理关联 的知识库
            knowledgeRelMapper.deleteByKnowledgeId(knowledgeBaseId);
            addKnowledgeRel(knowledgeBaseBO);

            return knowledgeBaseBO;
        }catch (Exception e){
            LOGGER.error("KnowledgeBaseServiceImpl.modify():" + e);
            throw new ServiceException(4502);
        }
    }

    @Transactional("db1TxManager")
    @Override
    public void addVote(KnowledgeVoteLog knowledgeVoteLog) {
        try {
            Boolean isUseFull = knowledgeVoteLog.getIsUseFull();
            String knowledgeId = knowledgeVoteLog.getKnowledgeId();
            if(isUseFull == Boolean.TRUE){
                knowledgeBaseMapper.addUsefulVoteByPK(knowledgeId);
            }else{
                knowledgeBaseMapper.addUselessVoteByPK(knowledgeId);
            }
            knowledgeVoteLog.setId(Utils.uuid());
            knowledgeVoteLogMapper.insert(knowledgeVoteLog);
        }catch (Exception e){
            LOGGER.error("KnowledgeBaseServiceImpl.addVote():" + e);
            throw new ServiceException(4504);
        }
    }

    @Override
    public void addPV(String id) {
        try {
            knowledgeBaseMapper.addPVByPK(id);
        }catch (Exception e){
            LOGGER.error("KnowledgeBaseServiceImpl.addPV():" + e);
            throw new ServiceException(4503);
        }
    }

    @Override
    public void deleteVoteLogs(List<String> ids) {
        try {
            knowledgeVoteLogMapper.deleteByPrimaryKeys(ids);
        }catch (Exception e){
            LOGGER.error("KnowledgeBaseServiceImpl.deleteVoteLogs():" + e);
            throw new ServiceException(4505);
        }
    }

    @Override
    public List<KnowledgeVoteLogBO> selectVoteList(KnowledgeBaseParamBO param) {
        return knowledgeVoteLogMapper.selectList(param);
    }

    @Override
    public KnowledgeVoteLog selectByUserVotedKnowledge(String userId, String knowledgeId) {
        return knowledgeVoteLogMapper.selectByUserVotedKnowledge(userId, knowledgeId);
    }


    private void addTagRel(KnowledgeBaseBO knowledgeBaseBO) {
        List<String> tagIds = knowledgeBaseBO.getTagIds();
        if (tagIds != null && !tagIds.isEmpty()) {
            KnowledgeBase knowledgeBase = knowledgeBaseBO.getKnowledgeBase();
            List<KnowledgeTagRel> list = new ArrayList<>();
            for (String tagId : tagIds) {
                KnowledgeTagRel rel = new KnowledgeTagRel();
                rel.setId(Utils.uuid());
                rel.setKnowledgeId(knowledgeBase.getId());
                rel.setTagId(tagId);
                list.add(rel);
            }
            knowledgeTagRelMapper.insertBatch(list);
        }
    }

    private void addKnowledgeRel(KnowledgeBaseBO knowledgeBaseBO) {
        List<String> relKnowledgeIds = knowledgeBaseBO.getRefKnowledgeId();
        if (relKnowledgeIds != null && !relKnowledgeIds.isEmpty()) {
            KnowledgeBase knowledgeBase = knowledgeBaseBO.getKnowledgeBase();
            List<KnowledgeRel> list = new ArrayList<>();
            for (String relKnowledgeId : relKnowledgeIds) {
                KnowledgeRel rel = new KnowledgeRel();
                rel.setId(Utils.uuid());
                rel.setKnowledgeId(knowledgeBase.getId());
                rel.setRelKnowledgeId(relKnowledgeId);
                list.add(rel);
            }
            knowledgeRelMapper.insertBatch(list);
        }
    }

    private Map<String, List<KnowledgeBase>> category(List<KnowledgeBase> list){
        Map<String, List<KnowledgeBase>> map = new HashMap<>();
        if(list!= null && !list.isEmpty()){
            for (KnowledgeBase knowledge : list){
                String categoryCode = knowledge.getCategoryCode();
                if(map.containsKey(categoryCode)){
                    List<KnowledgeBase> knowledgeList = map.get(categoryCode);
                    knowledgeList.add(knowledge);
                }else{
                    List<KnowledgeBase> knowledgeList = new ArrayList<>();
                    knowledgeList.add(knowledge);
                    map.put(categoryCode,knowledgeList);
                }
            }
        }
        return map;
    }

}
