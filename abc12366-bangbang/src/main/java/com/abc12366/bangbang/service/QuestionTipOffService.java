package com.abc12366.bangbang.service;

import com.abc12366.bangbang.model.question.QuestionTipOff;
import com.abc12366.bangbang.model.question.bo.QuestionTipOffBo;

import java.util.List;

/**
 * @Author liuQi
 * @Date 2017/9/19 11:48
 * 帮邦举报内容
 */
public interface QuestionTipOffService {

    /* 列表查询 */
    List<QuestionTipOffBo> selectList();

    /* 审核 */
    void changeStatus(QuestionTipOff questionTipOff);

    QuestionTipOffBo save(QuestionTipOffBo questionTipOffBo);

}
