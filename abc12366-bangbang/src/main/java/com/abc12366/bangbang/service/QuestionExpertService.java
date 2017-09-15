package com.abc12366.bangbang.service;

import com.abc12366.bangbang.model.question.QuestionExpert;

import java.util.List;

/**
 * @Author liuQi
 * @Date 2017/9/14 18:39
 */
public interface QuestionExpertService {

    /* 查询用户列表 */
    List<QuestionExpert> selectList();

    /* 单个查询 */
    QuestionExpert selectOne(String id);

    /* 新增专家 */
    void add(QuestionExpert questionExpert);

    /* 修改专家 */
    void modify(QuestionExpert questionExpert);

    /* 删去专家 */
    void delete(String id);

}
