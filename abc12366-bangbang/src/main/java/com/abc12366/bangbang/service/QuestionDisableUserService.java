package com.abc12366.bangbang.service;

import com.abc12366.bangbang.model.question.QuestionDisableUser;
import com.abc12366.bangbang.model.question.bo.QuestionDisableUserBo;

import java.util.List;
import java.util.Map;

/**
 * @Author liuQi
 * @Date 2017/10/18 15:44
 * 禁言用户管理
 */
public interface QuestionDisableUserService {

    List<QuestionDisableUserBo> selectList(Map map);

    /*禁言*/
    void disable(QuestionDisableUser questionDisableUser);

    /*撤销禁言*/
    void enable(String userId);

}
