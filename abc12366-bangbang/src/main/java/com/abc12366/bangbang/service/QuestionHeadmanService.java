package com.abc12366.bangbang.service;

import com.abc12366.bangbang.model.question.QuestionHeadman;
import com.abc12366.bangbang.model.question.bo.QuestionHeadmanBo;

import java.util.List;

/**
 * @Author liuQi
 * @Date 2017/9/22 17:32
 */
public interface QuestionHeadmanService {

    List<QuestionHeadman> selectList();

    void add(QuestionHeadmanBo record);

    void modify(QuestionHeadmanBo record);

    void delete(String id);

    /* 修改状态（apply:申请,success:通过,refuse:拒绝） */
    void changeStatus(String id, String status);

}
