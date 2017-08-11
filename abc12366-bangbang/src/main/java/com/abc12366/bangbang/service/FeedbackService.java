package com.abc12366.bangbang.service;

import com.abc12366.bangbang.model.Feedback;
import com.abc12366.bangbang.model.bo.FeedbackParamBO;

import java.util.List;

/**
 * @Author liuqi
 * @Date 2017/8/7 14:28
 */
public interface FeedbackService {

    Feedback add(Feedback feedBack);

    List<Feedback> selectList(FeedbackParamBO param);

    void delete(List<String> ids);

    void delete(String id);
}
