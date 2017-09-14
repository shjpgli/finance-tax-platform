package com.abc12366.bangbang.service;



import com.abc12366.bangbang.model.question.bo.QuestionBo;

import java.util.List;
import java.util.Map;

public interface QuestionService {

    List<QuestionBo> selectList(Map<String, Object> map);

    QuestionBo save(QuestionBo questionBo);

    QuestionBo selectQuestion(String id);

    QuestionBo update(QuestionBo questionBo);

    String updateStatus(String id, String status);

    String delete(String id);

}
