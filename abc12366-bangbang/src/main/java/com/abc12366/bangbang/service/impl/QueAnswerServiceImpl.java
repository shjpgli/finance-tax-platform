package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.QuestionAnswerMapper;
import com.abc12366.bangbang.mapper.db2.QuestionAnswerRoMapper;
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
    private QuestionAnswerRoMapper answerRoMapper;

    @Override
    public List<QuestionAnswerBo> selectList(Map<String,Object> map) {
        List<QuestionAnswerBo> answerBoList;
        try {
            //查询问题回复列表
            answerBoList = answerRoMapper.selectList(map);
        } catch (Exception e) {
            LOGGER.error("查询问题回复列表信息异常：{}", e);
            throw new ServiceException(4610);
        }
        return answerBoList;
    }

    @Transactional("db1TxManager")
    @Override
    public QuestionAnswerBo save(QuestionAnswerBo answerBo) {
        try {
            JSONObject jsonStu = JSONObject.fromObject(answerBo);
            LOGGER.info("新增问题回复信息:{}", jsonStu.toString());
            answerBo.setCreateTime(new Date());
            answerBo.setLastUpdate(new Date());
            //保存问题回复信息
            String uuid = UUID.randomUUID().toString().replace("-", "");
            QuestionAnswer answer = new QuestionAnswer();
            answerBo.setId(uuid);
            BeanUtils.copyProperties(answerBo, answer);
            answerMapper.insert(answer);
        } catch (Exception e) {
            LOGGER.error("新增问题回复信息异常：{}", e);
            throw new ServiceException(4612);
        }

        return answerBo;
    }

    @Override
    public QuestionAnswerBo selectAnswer(String id) {
        QuestionAnswerBo answerBo = new QuestionAnswerBo();
        try {
            LOGGER.info("查询单个问题回复信息:{}", id);
            //查询单个问题回复信息
            QuestionAnswer answer = answerRoMapper.selectByPrimaryKey(id);
            BeanUtils.copyProperties(answer, answerBo);
        } catch (Exception e) {
            LOGGER.error("查询单个问题回复信息异常：{}", e);
            throw new ServiceException(4611);
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
            throw new ServiceException(4613);
        }
        return answerBo;
    }

    @Override
    public String updateStatus(String coursewareId,String status) {
        //更新问题回复信息
        try {

        } catch (Exception e) {
            LOGGER.error("更新问题回复信息异常：{}", e);
            throw new ServiceException(4613);
        }
        return "";
    }

    @Transactional("db1TxManager")
    @Override
    public String delete(String id) {
        try {
            LOGGER.info("删除问题回复信息:{}", id);
            answerMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            LOGGER.error("删除问题回复异常：{}", e);
            throw new ServiceException(4614);
        }
        return "";
    }

}
