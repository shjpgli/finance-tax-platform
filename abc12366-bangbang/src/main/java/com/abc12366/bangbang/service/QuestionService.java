package com.abc12366.bangbang.service;



import com.abc12366.bangbang.model.bo.TopicRecommendParamBO;
import com.abc12366.bangbang.model.question.bo.QuestionBo;
import com.abc12366.bangbang.model.question.bo.QuestionTagBo;

import java.util.List;
import java.util.Map;

public interface QuestionService {

    List<QuestionBo> selectList(Map<String, Object> map);

    List<QuestionBo> selectListByBrowseNum(Map<String, Object> map);

    List<QuestionBo> selectListWait(Map<String, Object> map);

    List<QuestionBo> selectListAccept(Map<String, Object> map);

    List<QuestionBo> selectListry(Map<String, Object> map);

    QuestionBo save(QuestionBo questionBo);

    QuestionBo selectQuestion(String id);

    QuestionBo update(QuestionBo questionBo);

    String updateStatus(String id, String status);

    String delete(String id);

    List<QuestionTagBo> selectTagList();

    String updateBrowseNum(String id);

    List<QuestionBo> selectList(TopicRecommendParamBO param);

}
