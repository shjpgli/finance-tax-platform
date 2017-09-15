package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.QuestionExpertMapper;
import com.abc12366.bangbang.model.question.QuestionExpert;
import com.abc12366.bangbang.service.QuestionExpertService;
import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author liuQi
 * @Date 2017/9/15 10:37
 */
public class QuestionExpertServiceImpl implements QuestionExpertService{

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionExpertServiceImpl.class);

    @Autowired
    private QuestionExpertMapper questionExpertMapper;

    @Override
    public List<QuestionExpert> selectList() {
        return questionExpertMapper.selectList();
    }

    @Override
    public QuestionExpert selectOne(String id) {
        return questionExpertMapper.selectByPrimaryKey(id);
    }

    @Override
    public void add(QuestionExpert questionExpert) {
        try{
            questionExpert.setId(Utils.uuid());
            questionExpert.setCreateUser(Utils.getAdminId());
            questionExpertMapper.insert(questionExpert);
        }catch (Exception e){
            LOGGER.error("QuestionExpertServiceImpl.add():" + e);
            throw new ServiceException(4503);
        }
    }

    @Override
    public void modify(QuestionExpert questionExpert) {
        try {
            questionExpertMapper.updateByPrimaryKeySelective(questionExpert);
        }catch (Exception e){
            LOGGER.error("QuestionExpertServiceImpl.modify():" + e);
            throw new ServiceException(4503);
        }
    }

    @Override
    public void delete(String id) {
        try {
            questionExpertMapper.deleteByPrimaryKey(id);
        }catch (Exception e){
            LOGGER.error("QuestionExpertServiceImpl.delete():" + e);
            throw new ServiceException(4503);
        }
    }
}
