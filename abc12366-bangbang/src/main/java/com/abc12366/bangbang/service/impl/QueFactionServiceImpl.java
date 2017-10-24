package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.common.UcUserCommon;
import com.abc12366.bangbang.mapper.db1.*;
import com.abc12366.bangbang.mapper.db2.QuestionFactionClassifyRoMapper;
import com.abc12366.bangbang.mapper.db2.QuestionFactionMemberRoMapper;
import com.abc12366.bangbang.mapper.db2.QuestionFactionRoMapper;
import com.abc12366.bangbang.mapper.db2.QuestionFactionTagRoMapper;
import com.abc12366.bangbang.model.question.*;
import com.abc12366.bangbang.model.question.bo.QuestionAnswerBo;
import com.abc12366.bangbang.model.question.bo.QuestionFactionBo;
import com.abc12366.bangbang.model.question.bo.QuestionFactionListBo;
import com.abc12366.bangbang.model.question.bo.QuestionFactionTjBo;
import com.abc12366.bangbang.service.QueFactionService;
import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.model.bo.UCUserBO;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by xieyanmao on 2017/9/14.
 */
@Service("factionService")
public class QueFactionServiceImpl implements QueFactionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueFactionServiceImpl.class);

    @Autowired
    private QuestionFactionMapper questionFactionMapper;

    @Autowired
    private QuestionFactionRoMapper questionFactionRoMapper;

    @Autowired
    private QuestionFactionMemberMapper memberFactionMapper;

    @Autowired
    private QuestionFactionMemberRoMapper memberFactionRoMapper;

    @Autowired
    private QuestionFactionTagMapper tagMapper;

    @Autowired
    private QuestionFactionTagRoMapper tagRoMapper;

    @Autowired
    private QuestionFactionClassifyMapper classifyMapper;

    @Autowired
    private QuestionFactionClassifyRoMapper classifyRoMapper;

    @Autowired
    private QuestionFactionHonorMapper factionHonorMapper;

    @Autowired
    private QuestionMemberHonorMapper memberHonorMapper;

    @Override
    public List<QuestionFactionBo> selectList(Map<String,Object> map) {
        List<QuestionFactionBo> questionFactionBoList;
        try {
            //查询我管理的邦派列表
            questionFactionBoList = questionFactionRoMapper.selectList(map);
        } catch (Exception e) {
            LOGGER.error("查询邦派列表信息异常：{}", e);
            throw new ServiceException(6120);
        }
        return questionFactionBoList;
    }

    @Override
    public List<QuestionFactionListBo> selectListTj(Map<String,Object> map) {
        List<QuestionFactionListBo> QuestionFactionListBoList;
        try {
            //查询我加入的邦派列表
            QuestionFactionListBoList = questionFactionRoMapper.selectListTj(map);

            for(QuestionFactionListBo questionFactionBo : QuestionFactionListBoList){
                int honor = 2*questionFactionBo.getAnswerNum() + 1*questionFactionBo.getDiscussNum() + 7*questionFactionBo.getAdoptNum();

                questionFactionBo.setHonor(honor+"");
            }

        } catch (Exception e) {
            LOGGER.error("查询邦派列表信息异常：{}", e);
            throw new ServiceException(6120);
        }
        return QuestionFactionListBoList;
    }

    @Override
    public List<QuestionFactionListBo> selectListExcellent(Map<String,Object> map) {
        List<QuestionFactionListBo> QuestionFactionListBoList;
        try {
            //查询优秀邦派列表
            QuestionFactionListBoList = questionFactionRoMapper.selectListExcellent(map);

            for(QuestionFactionListBo questionFactionBo : QuestionFactionListBoList){
                int honor = 2*questionFactionBo.getAnswerNum() + 1*questionFactionBo.getDiscussNum() + 7*questionFactionBo.getAdoptNum();

                questionFactionBo.setHonor(honor+"");
            }

        } catch (Exception e) {
            LOGGER.error("查询邦派列表信息异常：{}", e);
            throw new ServiceException(6120);
        }
        return QuestionFactionListBoList;
    }

    @Override
    public List<QuestionFactionListBo> selectListPotential(Map<String,Object> map) {
        List<QuestionFactionListBo> QuestionFactionListBoList;
        try {
            //查询潜力邦派列表
            QuestionFactionListBoList = questionFactionRoMapper.selectListPotential(map);

            for(QuestionFactionListBo questionFactionBo : QuestionFactionListBoList){
                int honor = 2*questionFactionBo.getAnswerNum() + 1*questionFactionBo.getDiscussNum() + 7*questionFactionBo.getAdoptNum();

                questionFactionBo.setHonor(honor+"");
            }

        } catch (Exception e) {
            LOGGER.error("查询邦派列表信息异常：{}", e);
            throw new ServiceException(6120);
        }
        return QuestionFactionListBoList;
    }

    @Transactional("db1TxManager")
    @Override
    public QuestionFactionBo save(QuestionFactionBo questionFactionBo) {

        JSONObject jsonStu = JSONObject.fromObject(questionFactionBo);
        LOGGER.info("新增邦派信息:{}", jsonStu.toString());
        questionFactionBo.setCreateTime(new Date());
        questionFactionBo.setUpdateTime(new Date());
        //保存邦派信息
        String uuid = UUID.randomUUID().toString().replace("-", "");
        QuestionFaction questionFaction = new QuestionFaction();
        questionFactionBo.setFactionId(uuid);
        BeanUtils.copyProperties(questionFactionBo, questionFaction);

        int factionCnt = questionFactionRoMapper.selectFactionCnt(questionFactionBo.getUserId());

        UCUserBO userBo = UcUserCommon.getUserInfo();
        String vipLevel = "";
        String userLevel = "";
        if(userBo != null){
            vipLevel = userBo.getVipLevel();
            userLevel = userBo.getLevel();
        }

        if(factionCnt>4){
            //普通用户只能创建2个帮派，VIP 会员可以创建最多4个帮派
            throw new ServiceException(6126);
        }

        List<QuestionFactionTag> tagList = questionFactionBo.getTagList();

        if(tagList != null){
            for(QuestionFactionTag tag :tagList){
                tag.setId(UUID.randomUUID().toString().replace("-", ""));
                tag.setFactionId(uuid);
                tagMapper.insert(tag);
            }
        }

        List<QuestionFactionClassify> classifyList = questionFactionBo.getClassifyList();

        if(classifyList != null){
            for(QuestionFactionClassify classify :classifyList){
                classify.setId(UUID.randomUUID().toString().replace("-", ""));
                classify.setFactionId(uuid);
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("userId", questionFactionBo.getUserId());//
                dataMap.put("factionId", classify.getFactionId());//
                int cnt = classifyRoMapper.selectClassifyCnt(dataMap);
                if(cnt > 0){
                    //该用户创建的帮派所选话题分类不允许重叠
                    throw new ServiceException(6125);
                }

                classifyMapper.insert(classify);
            }
        }

        try {
            QuestionFactionMember member = new QuestionFactionMember();
            member.setFactionId(questionFaction.getFactionId());
            member.setUserId(questionFaction.getUserId());
            member.setDuty("1");
            member.setCreateTime(new Date());
            member.setMemberId(UUID.randomUUID().toString().replace("-", ""));
            member.setStatus(1);
            memberFactionMapper.insert(member);

            questionFactionMapper.insert(questionFaction);
        } catch (Exception e) {
            LOGGER.error("新增邦派信息异常：{}", e);
            throw new ServiceException(6122);
        }

        return questionFactionBo;
    }

    @Override
    public QuestionFactionBo selectQuestionFaction(String factionId) {
        QuestionFactionBo questionFactionBo = new QuestionFactionBo();
        try {
            LOGGER.info("查询单个邦派信息:{}", factionId);
            //查询邦派信息
            QuestionFaction questionFaction = questionFactionRoMapper.selectByPrimaryKey(factionId);
            List<QuestionFactionTag> tagList = tagRoMapper.selectList(factionId);
            List<QuestionFactionClassify> classifyList = classifyRoMapper.selectList(factionId);
            BeanUtils.copyProperties(questionFaction, questionFactionBo);
            questionFactionBo.setTagList(tagList);
            questionFactionBo.setClassifyList(classifyList);
        } catch (Exception e) {
            LOGGER.error("查询单个邦派信息异常：{}", e);
            throw new ServiceException(6121);
        }
        return questionFactionBo;
    }

    @Override
    public QuestionFactionTjBo selectQuestionFactionTj(String factionId) {
        QuestionFactionTjBo questionFactionBo = new QuestionFactionTjBo();
        try {
            //我管理的邦派信息
            LOGGER.info("查询单个邦派信息:{}", factionId);
            //查询邦派信息
            questionFactionBo = questionFactionRoMapper.selectFactionTj(factionId);

            int honor = 2*questionFactionBo.getAnswerNum() + 1*questionFactionBo.getDiscussNum() + 7*questionFactionBo.getAdoptNum();

            questionFactionBo.setHonor(honor+"");

            List<QuestionFactionTag> tagList = tagRoMapper.selectList(factionId);
            List<QuestionFactionClassify> classifyList = classifyRoMapper.selectList(factionId);
            questionFactionBo.setTagList(tagList);
            questionFactionBo.setClassifyList(classifyList);
        } catch (Exception e) {
            LOGGER.error("查询单个邦派信息异常：{}", e);
            throw new ServiceException(6121);
        }
        return questionFactionBo;
    }

    @Override
    public List<QuestionAnswerBo> selectdtListByFactionId(String factionId) {
        List<QuestionAnswerBo> questionAnswerBoList;
        try {
            //查询邦派动态列表
            questionAnswerBoList = questionFactionRoMapper.selectdtListByFactionId(factionId);
        } catch (Exception e) {
            LOGGER.error("查询邦派动态信息异常：{}", e);
            throw new ServiceException(6121);
        }
        return questionAnswerBoList;
    }

    @Transactional("db1TxManager")
    @Override
    public QuestionFactionBo update(QuestionFactionBo questionFactionBo) {
        //更新邦派信息
        QuestionFaction questionFaction = new QuestionFaction();
        try {
            JSONObject jsonStu = JSONObject.fromObject(questionFactionBo);
            LOGGER.info("更新邦派信息:{}", jsonStu.toString());
            questionFactionBo.setUpdateTime(new Date());
            BeanUtils.copyProperties(questionFactionBo, questionFaction);



            tagMapper.deleteByPrimaryKey(questionFactionBo.getFactionId());

            List<QuestionFactionTag> tagList = questionFactionBo.getTagList();

            if(tagList != null){
                for(QuestionFactionTag tag :tagList){
                    tag.setId(UUID.randomUUID().toString().replace("-", ""));
                    tag.setFactionId(questionFactionBo.getFactionId());
                    tagMapper.insert(tag);
                }
            }

//            classifyMapper.deleteByPrimaryKey(questionFactionBo.getFactionId());
//
//            List<QuestionFactionClassify> classifyList = questionFactionBo.getClassifyList();
//
//            if(classifyList != null){
//                for(QuestionFactionClassify classify :classifyList){
//                    classify.setId(UUID.randomUUID().toString().replace("-", ""));
//                    classify.setFactionId(questionFactionBo.getFactionId());
//                    classifyMapper.insert(classify);
//                }
//            }


            questionFactionMapper.updateByPrimaryKeySelective(questionFaction);
        } catch (Exception e) {
            LOGGER.error("更新邦派信息异常：{}", e);
            throw new ServiceException(6123);
        }
        return questionFactionBo;
    }

    @Override
    public String updateStatus(String factionId,String status) {
        //更新邦派信息
        try {

        } catch (Exception e) {
            LOGGER.error("更新邦派信息异常：{}", e);
            throw new ServiceException(6123);
        }
        return "";
    }

    @Transactional("db1TxManager")
    @Override
    public String delete(String factionId) {
        try {
            LOGGER.info("删除邦派信息:{}", factionId);
            tagMapper.deleteByPrimaryKey(factionId);
            classifyMapper.deleteByPrimaryKey(factionId);
            questionFactionMapper.deleteByPrimaryKey(factionId);
        } catch (Exception e) {
            LOGGER.error("删除邦派信息异常：{}", e);
            throw new ServiceException(6124);
        }
        return "";
    }

    @Transactional("db1TxManager")
    @Override
    public void autoCalculateFactionHonor() {
        List<QuestionFactionHonor> questionFactionHonorList = questionFactionRoMapper.selectFactionHonorList();
        for (QuestionFactionHonor factionHonor : questionFactionHonorList){
            int splendidNum = questionFactionRoMapper.selectSplendidNumByFactionId(factionHonor.getFactionId());
            factionHonor.setSplendidNum(splendidNum);
            factionHonor.setId(UUID.randomUUID().toString().replace("-", ""));
            factionHonor.setCreateTime(new Date());
            factionHonor.setUpdateTime(new Date());

            List<QuestionMemberHonor> questionMemberHonorList = memberFactionRoMapper.selectMemberHonorList(factionHonor.getFactionId());
            for (QuestionMemberHonor memberHonor : questionMemberHonorList){
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("factionId", memberHonor.getFactionId());
                dataMap.put("userId", memberHonor.getUserId());
                int splendidNum1 = memberFactionRoMapper.selectSplendidNum(dataMap);
                memberHonor.setSplendidNum(splendidNum1);
                memberHonor.setId(UUID.randomUUID().toString().replace("-", ""));
                memberHonor.setCreateTime(new Date());
                memberHonor.setUpdateTime(new Date());
                memberHonorMapper.insert(memberHonor);
            }

            factionHonorMapper.insert(factionHonor);
        }
    }

}
