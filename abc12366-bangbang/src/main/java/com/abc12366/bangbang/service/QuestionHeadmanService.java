package com.abc12366.bangbang.service;

import com.abc12366.bangbang.model.question.bo.QuestionHeadmanBo;

/**
 * @Author liuQi
 * @Date 2017/9/22 17:32
 */
public interface QuestionHeadmanService {



    void add(QuestionHeadmanBo record);

    void modify(QuestionHeadmanBo record);

    void delete(String id);


}
