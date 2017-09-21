package com.abc12366.bangbang.service;



import com.abc12366.bangbang.model.question.bo.*;

import java.util.List;
import java.util.Map;

public interface QueFactionService {

    List<QuestionFactionBo> selectList(Map<String, Object> map);

    List<QuestionFactionListBo> selectListTj(Map<String, Object> map);

    QuestionFactionBo save(QuestionFactionBo factionBo);

    QuestionFactionBo selectQuestionFaction(String factionId);

    QuestionFactionTjBo selectQuestionFactionTj(String factionId);

    List<QuestionAnswerBo> selectdtListByFactionId(String factionId);

    QuestionFactionBo update(QuestionFactionBo factionBo);

    String updateStatus(String factionId, String status);

    String delete(String factionId);

}
