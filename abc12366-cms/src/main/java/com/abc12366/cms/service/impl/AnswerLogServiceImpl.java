package com.abc12366.cms.service.impl;

import com.abc12366.cms.mapper.db1.AnswerLogMapper;
import com.abc12366.cms.mapper.db1.AnswerMapper;
import com.abc12366.cms.mapper.db2.AnswerLogRoMapper;
import com.abc12366.cms.mapper.db2.AnswerRoMapper;
import com.abc12366.cms.model.questionnaire.Answer;
import com.abc12366.cms.model.questionnaire.AnswerLog;
import com.abc12366.cms.model.questionnaire.Option;
import com.abc12366.cms.model.questionnaire.bo.AnswerLogBO;
import com.abc12366.cms.service.AnswerLogService;
import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author lizhongwei
 * @create 2017-06-16 4:21 PM
 * @since 1.0.0
 */
@Service
public class AnswerLogServiceImpl implements AnswerLogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnswerLogServiceImpl.class);
    @Autowired
    private AnswerLogRoMapper answerLogRoMapper;

    @Autowired
    private AnswerLogMapper answerLogMapper;

    @Autowired
    private AnswerRoMapper answerRoMapper;

    @Autowired
    private AnswerMapper answerMapper;

    @Override
    public List<AnswerLogBO> selectList(AnswerLogBO answerLogBO) {
        AnswerLog answerLog = new AnswerLog();
        BeanUtils.copyProperties(answerLogBO,answerLog);

        return answerLogRoMapper.selectList(answerLog);
    }

    @Override
    public AnswerLogBO selectOne(String id) {
        return answerLogRoMapper.selectOne(id);
    }


    @Transactional("db1TxManager")
    @Override
    public AnswerLogBO insert(AnswerLogBO answerLogBO) {
        AnswerLog answerLog = new AnswerLog();
        BeanUtils.copyProperties(answerLogBO,answerLog);
        String answerLogId = Utils.uuid();
        answerLog.setId(answerLogId);
        Date date = new Date();
        answerLog.setStartTime(date);
        answerLog.setEndTime(date);
        int insert = answerLogMapper.insert(answerLog);
        if(insert != 1){
            LOGGER.info("{新增答题记录失败}", answerLog);
            throw new ServiceException(4407);
        }
        AnswerLogBO bo = new AnswerLogBO();
        BeanUtils.copyProperties(answerLog,bo);
        return bo;
    }

    @Transactional("db1TxManager")
    @Override
    public AnswerLogBO update(AnswerLogBO answerLogBO) {
        AnswerLog answerLog = new AnswerLog();
        BeanUtils.copyProperties(answerLogBO,answerLog);
        answerLog.setEndTime(new Date());
        int update = answerLogMapper.update(answerLog);
        if(update != 1){
            LOGGER.info("{修改答题记录失败}", answerLog);
            throw new ServiceException(4408);
        }
        Answer answer = answerLogBO.getAnswer();
        answer.setAnswerLogId(answerLogBO.getId());
        //查询是否存在该题回答
        Answer temp = answerRoMapper.selectByLogId(answer);
        if(temp != null){
            int upd = answerMapper.update(answer);
            if(upd != 1){
                LOGGER.info("{修改答题失败}", answerLog);
                throw new ServiceException(4411);
            }
        }else{
            int ins = answerMapper.insert(answer);
            if(ins != 1){
                LOGGER.info("{新增答题失败}", answerLog);
                throw new ServiceException(4410);
            }
        }
        AnswerLogBO bo = new AnswerLogBO();
        BeanUtils.copyProperties(answerLog,bo);
        bo.setAnswer(answer);
        return bo;
    }

    @Transactional("db1TxManager")
    @Override
    public void delete(AnswerLogBO answerLogBO) {
        AnswerLog answerLog = new AnswerLog();
        BeanUtils.copyProperties(answerLogBO,answerLog);
        int del = answerLogMapper.delete(answerLog);
        if (del != 1){
            LOGGER.info("{删除答题记录失败}", answerLog);
            throw new ServiceException(4409);
        }
        Answer answer = new Answer();
        answer.setAnswerLogId(answerLog.getId());
        List<Answer> answerList = answerRoMapper.selectList(answer);
    }

    @Override
    public AnswerLogBO batch(AnswerLogBO answerLogBO) {
        AnswerLog answerLog = new AnswerLog();
        BeanUtils.copyProperties(answerLogBO,answerLog);
        answerLog.setEndTime(new Date());
        int update = answerLogMapper.update(answerLog);
        if(update != 1){
            LOGGER.info("{修改答题记录失败}", answerLog);
            throw new ServiceException(4408);
        }
        List<Answer> answerList = answerLogBO.getAnswerList();
        for (Answer answer : answerList){
            answer.setAnswerLogId(answerLogBO.getId());
            //查询是否存在该题回答
            Answer temp = answerRoMapper.selectByLogId(answer);
            if(temp != null){
                int upd = answerMapper.update(answer);
                if(upd != 1){
                    LOGGER.info("{修改答题失败}", answerLog);
                    throw new ServiceException(4411);
                }
            }else{
                int ins = answerMapper.insert(answer);
                if(ins != 1){
                    LOGGER.info("{新增答题失败}", answerLog);
                    throw new ServiceException(4410);
                }
            }
        }

        AnswerLogBO bo = new AnswerLogBO();
        BeanUtils.copyProperties(answerLog,bo);
        bo.setAnswerList(answerList);
        return bo;
    }

    @Override
    public AnswerLogBO answerAvg(AnswerLogBO answerLogBO) {
        AnswerLog answerLog = new AnswerLog();
        BeanUtils.copyProperties(answerLogBO,answerLog);
        return answerLogRoMapper.selectAvgTime(answerLog);
    }
}
