package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.common.UcUserCommon;
import com.abc12366.bangbang.mapper.db1.*;
import com.abc12366.bangbang.mapper.db2.KnowledgeAttachmentRoMapper;
import com.abc12366.bangbang.model.KnowledgeAttachment;
import com.abc12366.bangbang.model.KnowledgeBase;
import com.abc12366.bangbang.model.KnowledgeRel;
import com.abc12366.bangbang.model.KnowledgeTagRel;
import com.abc12366.bangbang.model.bo.KnowledgeBaseBO;
import com.abc12366.bangbang.model.bo.KnowledgeBaseHotParamBO;
import com.abc12366.bangbang.model.bo.KnowledgeBaseParamBO;
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

    @Autowired
    private KnowledgeAttachmentRoMapper knowledgeAttachmentRoMapper;

    @Autowired
    private KnowledgeAttachmentMapper knowledgeAttachmentMapper;

    @Override
    public Map<String, List<KnowledgeBase>> hotMap(KnowledgeBaseHotParamBO paramBO) {
        List<KnowledgeBase> list = knowledgeBaseMapper.hotList(paramBO);
        if(!list.isEmpty()){
            return category(list);
        }
        return Collections.EMPTY_MAP;
    }

    @Override
    public List<KnowledgeBase> hotUnClassifyMap(KnowledgeBaseHotParamBO paramBO) {
        List<KnowledgeBase> list = knowledgeBaseMapper.hotUnClassifyList(paramBO);
        return list;
    }

    @Override
    public List<KnowledgeBase> selectUCList(KnowledgeBaseParamBO param) {
        return knowledgeBaseMapper.selectUCList(param);
    }

    @Override
    public List<KnowledgeBase> selectUCListByTag(KnowledgeBaseParamBO param) {
        return knowledgeBaseMapper.selectUCListBytag(param);
    }

    @Override
    public List<KnowledgeBase> selectList(KnowledgeBaseParamBO param) {
        List<KnowledgeBase> list = knowledgeBaseMapper.selectList(param);
        if(!list.isEmpty()){
            Date now = new Date();
            List<String> ids = new ArrayList<>();
            for (KnowledgeBase know: list){
                if(Boolean.TRUE == know.getStatus()){
                    if(know.getActiveTime() != null && know.getActiveTime().before(now)){
                        know.setStatus(Boolean.FALSE);//修改知识状态
                        ids.add(know.getId());
                    }
                }
            }
            if(!ids.isEmpty()){
                knowledgeBaseMapper.updateStatusByPKs(new HashMap<String, Object>(){{
                        put("ids",ids);
                        put("status",Boolean.FALSE);
                }});
            }
        }
        return list;
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
        KnowledgeBase knowledgeBase = knowledgeBaseMapper.selectByPrimaryKey(id);
        if(knowledgeBase != null){
            List<KnowledgeAttachment> list =knowledgeAttachmentRoMapper.selectListByKnowledgeId(id);
            knowledgeBase.setAttachmentList(list);
        }
        return knowledgeBase;
    }

    @Override
    public void add(KnowledgeBase knowledgeBase) {
        try {
            knowledgeBaseMapper.insert(knowledgeBase);
            List<KnowledgeAttachment> attachmentList = knowledgeBase.getAttachmentList();
            if(attachmentList != null && !attachmentList.isEmpty()){
                for (KnowledgeAttachment attachment: attachmentList){
                    attachment.setId(Utils.uuid());
                }
                knowledgeAttachmentMapper.insertBatch(attachmentList);
            }
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
            //删除知识库附件
            knowledgeAttachmentMapper.deleteByKnowledgeIds(ids);
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

            List<KnowledgeAttachment> attachmentList = knowledgeBase.getAttachmentList();
            if(attachmentList != null && !attachmentList.isEmpty()){
                for (KnowledgeAttachment attachment: attachmentList){
                    attachment.setId(Utils.uuid());
                    attachment.setKnowledgeId(knowledgeBase.getId());
                }
                knowledgeAttachmentMapper.insertBatch(attachmentList);
            }
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
            String knowledgeId = knowledgeBase.getId();
            knowledgeBaseMapper.updateByPrimaryKeySelective(knowledgeBase);

            //管理关联 标签
            knowledgeTagRelMapper.deleteByKnowledgeId(knowledgeId);
            addTagRel(knowledgeBaseBO);
            //管理关联 的知识库
            knowledgeRelMapper.deleteByKnowledgeId(knowledgeId);
            addKnowledgeRel(knowledgeBaseBO);

            knowledgeAttachmentMapper.deleteByKnowledgeId(knowledgeId);
            List<KnowledgeAttachment> attachmentList = knowledgeBase.getAttachmentList();
            if(attachmentList != null && !attachmentList.isEmpty()){
                for (KnowledgeAttachment attachment: attachmentList){
                    attachment.setId(Utils.uuid());
                    attachment.setKnowledgeId(knowledgeId);
                }
                knowledgeAttachmentMapper.insertBatch(attachmentList);
            }

            return knowledgeBaseBO;
        }catch (Exception e){
            LOGGER.error("KnowledgeBaseServiceImpl.modify():" + e);
            throw new ServiceException(4502);
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
        for (String key : map.keySet()) {
            sort(map.get(key));
        }
        return map;
    }

    private void sort(List<KnowledgeBase> list){
        Collections.sort(list, new Comparator<KnowledgeBase>() {
            public int compare(KnowledgeBase n1, KnowledgeBase n2) {
                long pv1 = n1.getPv();
                long pv2 = n2.getPv();
                if (pv1 < pv2) {
                    return 1;
                } else if (pv1 == pv2) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
    }

	@Override
	public List<KnowledgeBase> wxhotUnClassifyMap(KnowledgeBaseHotParamBO param) {
		return knowledgeBaseMapper.wxhotUnClassifyMap(param);
	}


}
