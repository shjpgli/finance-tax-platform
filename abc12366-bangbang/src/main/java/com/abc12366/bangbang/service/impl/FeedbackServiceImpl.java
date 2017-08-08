package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.FeedbackMapper;
import com.abc12366.bangbang.model.Feedback;
import com.abc12366.bangbang.model.bo.FeedbackParamBO;
import com.abc12366.bangbang.service.FeedbackService;
import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author liuqi
 * @Date 2017/8/7 13:36
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    public Feedback add(Feedback feedBack) {
        try {
            feedBack.setId(Utils.uuid());
            feedbackMapper.insert(feedBack);
            return feedBack;
        }catch (Exception e){
            LOGGER.error("FeedbackServiceImpl.add()", e);
            throw new ServiceException(4501);
        }
    }

    @Override
    public List<Feedback> selectList(FeedbackParamBO param) {
        return feedbackMapper.selectList(param);
    }

    @Override
    public int delete(List<String> ids) {
        try {
            return feedbackMapper.deleteByPrimaryKeys(ids);
        }catch (Exception e){
            LOGGER.error("FeedbackServiceImpl.delete()", e);
            throw new ServiceException(4519);
        }
    }


}
