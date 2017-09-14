package com.abc12366.bangbang.service;



import com.abc12366.bangbang.model.question.bo.QuestionAnswerBo;

import java.util.List;
import java.util.Map;

public interface QueAnswerService {

    List<QuestionAnswerBo> selectList(Map<String, Object> map);

    QuestionAnswerBo save(QuestionAnswerBo questionAnswerBo);

    QuestionAnswerBo selectAnswer(String id);

    QuestionAnswerBo update(QuestionAnswerBo questionAnswerBo);

    String updateStatus(String answerId, String status);

    String delete(String answerId);

}
