package com.abc12366.bangbang.service;

import com.abc12366.bangbang.model.question.bo.QuestionSysBlockBo;

import java.util.List;

/**
 * @Author liuQi
 * @Date 2017/9/19 16:03
 */
public interface QuestionSysBlockService {

    /* 列表查询 */
    List<QuestionSysBlockBo> selectList();

    /* 审核 */
    void changeStatus(String id ,String status);


}
