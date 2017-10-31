package com.abc12366.bangbang.service;



import com.abc12366.bangbang.model.question.bo.QuestionCommentBo;

import java.util.List;
import java.util.Map;

public interface QueCommentService {

    List<QuestionCommentBo> selectList(Map<String, Object> map);

    QuestionCommentBo save(QuestionCommentBo questionCommentBo);

    QuestionCommentBo selectComment(String id);

    QuestionCommentBo update(QuestionCommentBo questionCommentBo);

    String updateStatus(String id, String status);

    String delete(String id);

    List<QuestionCommentBo> selectMyCommentList(Map<String, Object> map);

}
