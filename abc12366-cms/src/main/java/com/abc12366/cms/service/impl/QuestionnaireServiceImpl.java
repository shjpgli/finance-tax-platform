package com.abc12366.cms.service.impl;

import com.abc12366.cms.mapper.db1.OptionMapper;
import com.abc12366.cms.mapper.db1.QuestionnaireMapper;
import com.abc12366.cms.mapper.db1.QuestionnaireParamMapper;
import com.abc12366.cms.mapper.db2.OptionRoMapper;
import com.abc12366.cms.mapper.db2.QuestionnaireParamRoMapper;
import com.abc12366.cms.mapper.db2.QuestionnaireRoMapper;
import com.abc12366.cms.model.questionnaire.Option;
import com.abc12366.cms.model.questionnaire.Questionnaire;
import com.abc12366.cms.model.questionnaire.QuestionnaireParam;
import com.abc12366.cms.model.questionnaire.bo.QuestionnaireBO;
import com.abc12366.cms.service.QuestionnaireService;
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
public class QuestionnaireServiceImpl implements QuestionnaireService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionnaireServiceImpl.class);
    @Autowired
    private QuestionnaireRoMapper questionnaireRoMapper;

    @Autowired
    private QuestionnaireMapper questionnaireMapper;

    @Autowired
    private QuestionnaireParamRoMapper questionnaireParamRoRoMapper;

    @Autowired
    private QuestionnaireParamMapper questionnaireParamMapper;



    @Override
    public List<QuestionnaireBO> selectList(QuestionnaireBO questionnaireBO) {
        Questionnaire questionnaire = new Questionnaire();
        BeanUtils.copyProperties(questionnaireBO,questionnaire);

        return questionnaireRoMapper.selectList(questionnaire);
    }

    @Override
    public QuestionnaireBO selectOne(String id) {
        return questionnaireRoMapper.selectOne(id);
    }


    @Transactional("db1TxManager")
    @Override
    public QuestionnaireBO insert(QuestionnaireBO questionnaireBO) {
        Questionnaire questionnaire = new Questionnaire();
        BeanUtils.copyProperties(questionnaireBO,questionnaire);
        String questionnaireId = Utils.uuid();
        questionnaire.setId(questionnaireId);
        Date date = new Date();
        questionnaire.setCreateTime(date);
        questionnaire.setUpdateTime(date);
        int insert = questionnaireMapper.insert(questionnaire);
        if(insert != 1){
            LOGGER.info("{新增问卷失败}", questionnaire);
            throw new ServiceException(4393);
        }
        QuestionnaireParam param = questionnaireBO.getQuestionnaireParam();
        if(param != null){
            param.setQuestionId(questionnaireId);
            int pInsert = questionnaireParamMapper.insert(param);
            if(pInsert != 1){
                LOGGER.info("{新增问卷设置失败}", questionnaire);
                throw new ServiceException(4401);
            }
        }
        QuestionnaireBO bo = new QuestionnaireBO();
        BeanUtils.copyProperties(questionnaire,bo);
        bo.setQuestionnaireParam(param);
        return bo;
    }

    @Transactional("db1TxManager")
    @Override
    public QuestionnaireBO update(QuestionnaireBO questionnaireBO) {
        Questionnaire questionnaire = new Questionnaire();
        BeanUtils.copyProperties(questionnaireBO,questionnaire);
        questionnaire.setUpdateTime(new Date());
        int update = questionnaireMapper.update(questionnaire);
        if(update != 1){
            LOGGER.info("{修改问卷失败}", questionnaire);
            throw new ServiceException(4392);
        }
        QuestionnaireParam param = questionnaireBO.getQuestionnaireParam();
        if(param != null){
            param.setQuestionId(questionnaireBO.getId());
            int pUpdate = questionnaireParamMapper.update(param);
            if(pUpdate != 1){
                LOGGER.info("{修改问卷设置失败}", questionnaire);
                throw new ServiceException(4402);
            }
        }
        QuestionnaireBO bo = new QuestionnaireBO();
        BeanUtils.copyProperties(questionnaire,bo);
        bo.setQuestionnaireParam(param);
        return bo;
    }

    @Transactional("db1TxManager")
    @Override
    public void delete(QuestionnaireBO questionnaireBO) {
        Questionnaire questionnaire = new Questionnaire();
        BeanUtils.copyProperties(questionnaireBO,questionnaire);
        int del = questionnaireMapper.delete(questionnaire);
        if (del != 1){
            LOGGER.info("{删除问卷失败}", questionnaire);
            throw new ServiceException(4391);
        }
        int pDel = questionnaireParamMapper.deleteByPrimaryKey(questionnaireBO.getId());
        if(pDel != 1){
            LOGGER.info("{删除问卷设置失败}", questionnaire);
            throw new ServiceException(4403);
        }
    }

    @Override
    public void updateStatus(String id, Boolean status) {
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setId(id);
        questionnaire.setStatus(status);
        int update = questionnaireMapper.update(questionnaire);
        if (update != 1){
            LOGGER.info("{修改问卷状态失败}", questionnaire);
            throw new ServiceException(4404);
        }

    }

    @Override
    public void updateAccessRate(String id) {
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setId(id);
        int update = questionnaireMapper.updateAccessRate(questionnaire);
        if (update != 1){
            LOGGER.info("{修改问卷状态失败}", questionnaire);
            throw new ServiceException(4404);
        }
    }

    @Override
    public void updateRecoveryRate(String id) {
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setId(id);
        int update = questionnaireMapper.updateRecoveryRate(questionnaire);
        if (update != 1){
            LOGGER.info("{修改问卷状态失败}", questionnaire);
            throw new ServiceException(4404);
        }
    }


}
