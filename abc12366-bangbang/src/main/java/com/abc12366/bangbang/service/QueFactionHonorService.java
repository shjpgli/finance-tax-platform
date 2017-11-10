package com.abc12366.bangbang.service;



import com.abc12366.bangbang.model.question.bo.QuestionFactionInformBo;
import com.abc12366.bangbang.model.question.bo.QuestionFactionPhBo;

import java.util.List;
import java.util.Map;

public interface QueFactionHonorService {

    List<QuestionFactionPhBo> selectList(String honorTime);

    List<QuestionFactionPhBo> selectljList();

}
