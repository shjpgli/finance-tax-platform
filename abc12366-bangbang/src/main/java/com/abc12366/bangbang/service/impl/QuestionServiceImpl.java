package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.QuestionInviteMapper;
import com.abc12366.bangbang.mapper.db1.QuestionMapper;
import com.abc12366.bangbang.mapper.db1.QuestionTagMapper;
import com.abc12366.bangbang.mapper.db2.QuestionInviteRoMapper;
import com.abc12366.bangbang.mapper.db2.QuestionRoMapper;
import com.abc12366.bangbang.mapper.db2.QuestionTagRoMapper;
import com.abc12366.bangbang.model.bo.TopicRecommendParamBO;
import com.abc12366.bangbang.model.question.Question;
import com.abc12366.bangbang.model.question.QuestionInvite;
import com.abc12366.bangbang.model.question.QuestionTag;
import com.abc12366.bangbang.model.question.bo.*;
import com.abc12366.bangbang.service.QuestionService;
import com.abc12366.gateway.exception.ServiceException;
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
@Service
public class QuestionServiceImpl implements QuestionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionServiceImpl.class);

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionRoMapper questionRoMapper;

    @Autowired
    private QuestionTagMapper tagMapper;

    @Autowired
    private QuestionTagRoMapper tagRoMapper;

    @Autowired
    private QuestionInviteMapper inviteMapper;

    @Autowired
    private QuestionInviteRoMapper inviteRoMapper;

    @Override
    public List<QuestionBo> selectList(Map<String,Object> map) {
        List<QuestionBo> questionBoList;
        try {
            //查询最新问题列表
            questionBoList = questionRoMapper.selectList(map);
        } catch (Exception e) {
            LOGGER.error("查询问题列表信息异常：{}", e);
            throw new ServiceException(6100);
        }
        return questionBoList;
    }

    @Override
    public List<QuestionBo> selectListByBrowseNum(Map<String,Object> map) {
        List<QuestionBo> questionBoList;
        try {
            //查询热门问题
            questionBoList = questionRoMapper.selectListByBrowseNum(map);
        } catch (Exception e) {
            LOGGER.error("查询问题列表信息异常：{}", e);
            throw new ServiceException(6100);
        }
        return questionBoList;
    }

    @Override
    public List<QuestionBo> selectListRecommend(Map<String,Object> map) {
        List<QuestionBo> questionBoList;
        try {
            //推荐问题
            questionBoList = questionRoMapper.selectListRecommend(map);
        } catch (Exception e) {
            LOGGER.error("查询问题列表信息异常：{}", e);
            throw new ServiceException(6100);
        }
        return questionBoList;
    }

    @Override
    public List<QuestionBo> selectListByPoints(Map<String,Object> map) {
        List<QuestionBo> questionBoList;
        try {
            //高悬赏问题
            questionBoList = questionRoMapper.selectListByPoints(map);
        } catch (Exception e) {
            LOGGER.error("查询问题列表信息异常：{}", e);
            throw new ServiceException(6100);
        }
        return questionBoList;
    }

    @Override
    public List<QuestionBo> selectListWait(Map<String,Object> map) {
        List<QuestionBo> questionBoList;
        try {
            //查询等你回答的问题
            questionBoList = questionRoMapper.selectListWait(map);
        } catch (Exception e) {
            LOGGER.error("查询问题列表信息异常：{}", e);
            throw new ServiceException(6100);
        }
        return questionBoList;
    }

    @Override
    public List<QuestionBo> selectListAccept(Map<String,Object> map) {
        List<QuestionBo> questionBoList;
        try {
            //查询已解决的问题
            questionBoList = questionRoMapper.selectListAccept(map);
        } catch (Exception e) {
            LOGGER.error("查询问题列表信息异常：{}", e);
            throw new ServiceException(6100);
        }
        return questionBoList;
    }

    @Override
    public List<QuestionryBo> selectListry(Map<String,Object> map) {
        List<QuestionryBo> questionBoList;
        try {
            //查询帮友热议列表
            questionBoList = questionRoMapper.selectListry(map);
        } catch (Exception e) {
            LOGGER.error("查询帮友热议列表信息异常：{}", e);
            throw new ServiceException(6100);
        }
        return questionBoList;
    }

    @Transactional("db1TxManager")
    @Override
    public QuestionBo save(QuestionBo questionBo) {
        try {
            JSONObject jsonStu = JSONObject.fromObject(questionBo);
            LOGGER.info("新增问题信息:{}", jsonStu.toString());

            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("userId", questionBo.getUserId());
            dataMap.put("classifyId", questionBo.getClassifyCode());
            String factionId = questionRoMapper.selectfactionId(dataMap);
            if(factionId == null){
                factionId = "";
            }
            questionBo.setFactionId(factionId);

            questionBo.setCreateTime(new Date());
            questionBo.setLastUpdate(new Date());
            questionBo.setBrowseNum(0);
            questionBo.setCollectNum(0);
            questionBo.setReportNum(0);
            questionBo.setAnswerNum(0);
            //保存问题信息
            String uuid = UUID.randomUUID().toString().replace("-", "");
            Question question = new Question();
            questionBo.setId(uuid);
            BeanUtils.copyProperties(questionBo, question);


            List<QuestionTag> tagList = questionBo.getTagList();

            if(tagList != null){
                for(QuestionTag tag :tagList){
                    tag.setId(UUID.randomUUID().toString().replace("-", ""));
                    tag.setQuestionId(uuid);
                    tagMapper.insert(tag);
                }
            }

            List<QuestionInvite> inviteList = questionBo.getInviteList();

            if(inviteList != null){
                for(QuestionInvite invite :inviteList){
                    invite.setId(UUID.randomUUID().toString().replace("-", ""));
                    invite.setQuestionId(uuid);
                    inviteMapper.insert(invite);
                }
            }


            questionMapper.insert(question);
        } catch (Exception e) {
            LOGGER.error("新增问题信息异常：{}", e);
            throw new ServiceException(6102);
        }

        return questionBo;
    }

    @Override
    public QuestionBo selectQuestion(String id) {
        QuestionBo questionBo;
        try {
            LOGGER.info("查询单个问题信息:{}", id);
            //查询问题信息
            questionBo = questionRoMapper.selectQuestion(id);
            List<QuestionTag> tagList = tagRoMapper.selectList(id);
            List<QuestionInvite> inviteList = inviteRoMapper.selectList(id);
            questionBo.setTagList(tagList);
            questionBo.setInviteList(inviteList);
        } catch (Exception e) {
            LOGGER.error("查询单个问题信息异常：{}", e);
            throw new ServiceException(6101);
        }
        return questionBo;
    }

    @Override
    public List<QuestionTagBo> selectTagList() {
        List<QuestionTagBo> tagList;
        try {
            //查询热议标签列表
            tagList = tagRoMapper.selectTagList();
        } catch (Exception e) {
            LOGGER.error("查询热议标签列表信息异常：{}", e);
            throw new ServiceException(6105);
        }
        return tagList;
    }

    @Transactional("db1TxManager")
    @Override
    public QuestionBo update(QuestionBo questionBo) {
        //更新问题信息
        Question question = new Question();
        try {
            JSONObject jsonStu = JSONObject.fromObject(questionBo);
            LOGGER.info("更新问题信息:{}", jsonStu.toString());

            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("userId", questionBo.getUserId());
            dataMap.put("classifyId", questionBo.getClassifyCode());
            String factionId = questionRoMapper.selectfactionId(dataMap);
            if(factionId == null){
                factionId = "";
            }
            questionBo.setFactionId(factionId);

            questionBo.setLastUpdate(new Date());
            BeanUtils.copyProperties(questionBo, question);


            List<QuestionTag> tagList = questionBo.getTagList();

            tagMapper.deleteByPrimaryKey(questionBo.getId());

            if(tagList != null){
                for(QuestionTag tag :tagList){
                    tag.setId(UUID.randomUUID().toString().replace("-", ""));
                    tag.setQuestionId(questionBo.getId());
                    tagMapper.insert(tag);
                }
            }

            List<QuestionInvite> inviteList = questionBo.getInviteList();
            inviteMapper.deleteByPrimaryKey(questionBo.getId());

            if(inviteList != null){
                for(QuestionInvite invite :inviteList){
                    invite.setId(UUID.randomUUID().toString().replace("-", ""));
                    invite.setQuestionId(questionBo.getId());
                    inviteMapper.insert(invite);
                }
            }


            questionMapper.updateByPrimaryKeySelective(question);
        } catch (Exception e) {
            LOGGER.error("更新问题信息异常：{}", e);
            throw new ServiceException(6103);
        }
        return questionBo;
    }

    @Override
    public String updateStatus(String id,String status) {
        //更新课件信息
        try {

        } catch (Exception e) {
            LOGGER.error("更新课件信息异常：{}", e);
            throw new ServiceException(6103);
        }
        return "";
    }

    @Transactional("db1TxManager")
    @Override
    public String delete(String id) {
        try {
            LOGGER.info("删除问题信息:{}", id);
            tagMapper.deleteByPrimaryKey(id);
            questionMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            LOGGER.error("删除问题信息异常：{}", e);
            throw new ServiceException(6104);
        }
        return "";
    }

    @Override
    public String updateBrowseNum(String id) {
        questionMapper.updateBrowseNum(id);
        return "";
    }

    @Override
    public List<QuestionBo> selectList(TopicRecommendParamBO param) {
        return questionRoMapper.selectListTopicRecommend(param);
    }

    @Override
    public void recommend(String id, Boolean isRecommend) {
        try {
            LOGGER.info("话题推荐功能:{}", id);
            questionMapper.recommend(id, isRecommend);
        } catch (Exception e) {
            LOGGER.error("话题推荐功能异常：{}", e);
            throw new ServiceException(6106);
        }
    }

    @Override
    public List<QuestionjbBo> selectTipList(String userId) {
        //我的举报
        LOGGER.info("{}", userId);
        return questionRoMapper.selectTipList(userId);
    }

    @Override
    public List<QuestionBo> selectInviteList(String userId) {
        //邀我回答
        LOGGER.info("{}", userId);
        return questionRoMapper.selectInviteList(userId);
    }

    @Override
    public List<MyQuestionTjBo> selectMybangbang(String userId) {
        //我的帮帮
        LOGGER.info("{}", userId);
        return questionRoMapper.selectMybangbang(userId);
    }

    @Override
    public List<QuestionBo> selectMyQuestionList(Map<String,Object> map) {
        List<QuestionBo> questionBoList;
        try {
            //查询我的提问
            questionBoList = questionRoMapper.selectMyQuestionList(map);
        } catch (Exception e) {
            LOGGER.error("查询问题列表信息异常：{}", e);
            throw new ServiceException(6100);
        }
        return questionBoList;
    }

}
