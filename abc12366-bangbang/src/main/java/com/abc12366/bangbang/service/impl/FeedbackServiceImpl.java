package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.FeedbackMapper;
import com.abc12366.bangbang.model.Feedback;
import com.abc12366.bangbang.model.bo.FeedbackParamBO;
import com.abc12366.bangbang.service.FeedbackService;
import com.abc12366.gateway.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author liuqi
 * @Date 2017/8/7 13:36
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    public Feedback add(Feedback feedBack) {
        feedBack.setId(Utils.uuid());
        feedbackMapper.insert(feedBack);
        return feedBack;
    }

    @Override
    public List<Feedback> selectList(FeedbackParamBO param) {
        return feedbackMapper.selectList(param);
    }

    @Override
    public int delete(List<String> ids) {
        return feedbackMapper.deleteByPrimaryKeys(ids);
    }


}
