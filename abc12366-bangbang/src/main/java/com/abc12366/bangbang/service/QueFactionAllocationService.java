package com.abc12366.bangbang.service;



import com.abc12366.bangbang.model.question.bo.QuestionFactionAllocationBo;

import java.util.List;
import java.util.Map;

public interface QueFactionAllocationService {

    List<QuestionFactionAllocationBo> selectList(Map<String, Object> map);

    QuestionFactionAllocationBo save(QuestionFactionAllocationBo questionFactionAllocationBo);

    QuestionFactionAllocationBo selectFactionAllocation(String id);

    QuestionFactionAllocationBo update(QuestionFactionAllocationBo questionFactionAllocationBo);

    String delete(String id);

}
