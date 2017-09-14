package com.abc12366.bangbang.service;

import com.abc12366.bangbang.model.question.bo.QuestionClassifyBo;

import java.util.List;
import java.util.Map;

public interface QueClassifyService {

    List<QuestionClassifyBo> selectList(Map<String, Object> map);

    QuestionClassifyBo save(QuestionClassifyBo classifyBo);

    QuestionClassifyBo selectClassify(String classifyId);

    QuestionClassifyBo update(QuestionClassifyBo classifyBo);

    String updateStatus(String classifyId, String status);

    String delete(String classifyId);

}
