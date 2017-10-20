package com.abc12366.bangbang.service;

import com.abc12366.bangbang.model.question.QuestionMedal;
import com.abc12366.bangbang.model.question.bo.QuestionMedalBo;

import java.util.List;

/**
 * @Author liuQi
 * @Date 2017/10/20 17:54
 */
public interface QuestionMedalService {

    List<QuestionMedalBo> selectList();

    QuestionMedalBo selectOne(String id);

    void add(QuestionMedal medal);

    void modify(QuestionMedal medal);

    void delete(String id);

}
