package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.QuestionAcceptedMapper;
import com.abc12366.bangbang.mapper.db2.QuestionAcceptedRoMapper;
import com.abc12366.bangbang.model.question.QuestionAccepted;
import com.abc12366.bangbang.model.question.bo.QuestionAcceptedBO;
import com.abc12366.bangbang.service.QuestionAcceptedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xieyanmao on 2017/9/14.
 */
@Service
public class QuestionAcceptedServiceImpl implements QuestionAcceptedService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionAcceptedServiceImpl.class);

    @Autowired
    private QuestionAcceptedRoMapper acceptedRoMapper;

    @Autowired
    private QuestionAcceptedMapper acceptedMapper;

    @Override
    public QuestionAccepted add(QuestionAccepted questionAccepted) {
//        acceptedMapper.insert(questionAccepted);
        return null;
    }

    @Override
    public List<QuestionAccepted> selectList(QuestionAcceptedBO questionAcceptedBO) {
        return acceptedRoMapper.selectList(questionAcceptedBO);
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public List<QuestionAcceptedBO> selectStatisList(QuestionAcceptedBO param) {
        return acceptedRoMapper.selectStatisList(param);
    }
}
