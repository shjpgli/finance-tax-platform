package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.QuestionAnswerMapper;
import com.abc12366.bangbang.mapper.db1.QuestionMapper;
import com.abc12366.bangbang.mapper.db2.QuestionAnswerRoMapper;
import com.abc12366.bangbang.model.question.Question;
import com.abc12366.bangbang.model.question.QuestionAnswer;
import com.abc12366.bangbang.model.question.bo.QuestionAnswerBo;
import com.abc12366.bangbang.service.QueAnswerService;
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
public class QueAnswerServiceImpl implements QueAnswerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueAnswerServiceImpl.class);

    @Autowired
    private QuestionAnswerMapper answerMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionAnswerRoMapper answerRoMapper;

    @Override
    public List<QuestionAnswerBo> selectList(Map<String,Object> map) {
        List<QuestionAnswerBo> answerBoList;
        try {
            //查询问题回复列表
            answerBoList = answerRoMapper.selectList(map);
        } catch (Exception e) {
            LOGGER.error("查询问题回复列表信息异常：{}", e);
            throw new ServiceException(6110);
        }
        return answerBoList;
    }

    @Override
    public List<QuestionAnswerBo> selectListByParentId(Map<String,Object> map) {
        List<QuestionAnswerBo> answerBoList;
        try {
            //查询问题回复列表
            answerBoList = answerRoMapper.selectListByParentId(map);
        } catch (Exception e) {
            LOGGER.error("查询问题回复列表信息异常：{}", e);
            throw new ServiceException(6110);
        }
        return answerBoList;
    }

    @Override
    public List<QuestionAnswerBo> selectListNew(Map<String,Object> map) {
        List<QuestionAnswerBo> answerBoList;
        try {
            //查询最新问题回复列表
            answerBoList = answerRoMapper.selectList(map);
        } catch (Exception e) {
            LOGGER.error("查询问题回复列表信息异常：{}", e);
            throw new ServiceException(6110);
        }
        return answerBoList;
    }

    @Override
    public List<QuestionAnswerBo> selectMyAnswerList(Map<String,Object> map) {
        List<QuestionAnswerBo> answerBoList;
        try {
            //查询我的回答列表
            answerBoList = answerRoMapper.selectMyAnswerList(map);
        } catch (Exception e) {
            LOGGER.error("查询问题回复列表信息异常：{}", e);
            throw new ServiceException(6110);
        }
        return answerBoList;
    }

    @Override
    public List<QuestionAnswerBo> selectMyCommentList(Map<String,Object> map) {
        List<QuestionAnswerBo> answerBoList;
        try {
            //查询我的评论列表
            answerBoList = answerRoMapper.selectMyCommentList(map);
        } catch (Exception e) {
            LOGGER.error("查询问题回复列表信息异常：{}", e);
            throw new ServiceException(6110);
        }
        return answerBoList;
    }

    @Override
    public QuestionAnswerBo save(QuestionAnswerBo answerBo) {
        try {
            JSONObject jsonStu = JSONObject.fromObject(answerBo);
            LOGGER.info("新增问题回复信息:{}", jsonStu.toString());
            if(answerBo.getParentId() == null){
                answerBo.setParentId("");
            }

            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("userId", answerBo.getUserId());
            dataMap.put("questionId", answerBo.getQuestionId());
            String factionId = answerRoMapper.selectfactionId(dataMap);
            if(factionId == null){
                factionId = "";
            }
            answerBo.setFactionId(factionId);
            answerBo.setCreateTime(new Date());
            answerBo.setLastUpdate(new Date());
            //保存问题回复信息
            String uuid = UUID.randomUUID().toString().replace("-", "");
            QuestionAnswer answer = new QuestionAnswer();
            answerBo.setId(uuid);
            BeanUtils.copyProperties(answerBo, answer);
            answerMapper.insert(answer);

            if("".equals(answerBo.getParentId())){
                int answerNum = answerRoMapper.selectAnswerCnt(answerBo.getQuestionId());
                Question question = new Question();
                question.setId(answerBo.getQuestionId());
                question.setAnswerNum(answerNum);
                questionMapper.updateByPrimaryKeySelective(question);
                answerBo.setAnswerNum(answerNum);
            }else{
                int commentNum = answerRoMapper.selectCommentCnt(answerBo.getParentId());
                QuestionAnswer answer1 = new QuestionAnswer();
                answer1.setId(answerBo.getParentId());
                answer1.setCommentNum(commentNum);
                answerMapper.updateByPrimaryKeySelective(answer1);
                answerBo.setCommentNum(commentNum);
            }


        } catch (Exception e) {
            LOGGER.error("新增问题回复信息异常：{}", e);
            throw new ServiceException(6112);
        }

        return answerBo;
    }

    @Override
    public QuestionAnswerBo selectAnswer(String id) {
        QuestionAnswerBo answerBo = new QuestionAnswerBo();
        try {
            LOGGER.info("查询单个问题回复信息:{}", id);
            //查询单个问题回复信息
            answerBo = answerRoMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            LOGGER.error("查询单个问题回复信息异常：{}", e);
            throw new ServiceException(6111);
        }
        return answerBo;
    }

    @Transactional("db1TxManager")
    @Override
    public QuestionAnswerBo update(QuestionAnswerBo answerBo) {
        //更新问题回复信息
        QuestionAnswer answer = new QuestionAnswer();
        try {
            JSONObject jsonStu = JSONObject.fromObject(answerBo);
            LOGGER.info("更新问题回复信息:{}", jsonStu.toString());
            answerBo.setLastUpdate(new Date());
            BeanUtils.copyProperties(answerBo, answer);
            answerMapper.updateByPrimaryKeySelective(answer);
        } catch (Exception e) {
            LOGGER.error("更新问题回复信息异常：{}", e);
            throw new ServiceException(6113);
        }
        return answerBo;
    }

    @Override
    public String updateStatus(String coursewareId,String status) {
        //更新问题回复信息
        try {

        } catch (Exception e) {
            LOGGER.error("更新问题回复信息异常：{}", e);
            throw new ServiceException(6113);
        }
        return "";
    }

    @Override
    public String delete(String id) {
        int num = 0;
        try {
            LOGGER.info("删除问题回复信息:{}", id);

            QuestionAnswerBo answer = answerRoMapper.selectByPrimaryKey(id);

            answerMapper.deleteByPrimaryKey(id);

            if("".equals(answer.getParentId())){
                int answerNum = answerRoMapper.selectAnswerCnt(answer.getQuestionId());
                Question question = new Question();
                question.setId(answer.getQuestionId());
                question.setAnswerNum(answerNum);
                questionMapper.updateByPrimaryKeySelective(question);
                num = answerNum;
            }else{
                int commentNum = answerRoMapper.selectCommentCnt(answer.getParentId());
                QuestionAnswer answer1 = new QuestionAnswer();
                answer1.setId(answer.getParentId());
                answer1.setCommentNum(commentNum);
                answerMapper.updateByPrimaryKeySelective(answer1);
                num = commentNum;
            }

        } catch (Exception e) {
            LOGGER.error("删除问题回复异常：{}", e);
            throw new ServiceException(6114);
        }
        return num+"";
    }

}
