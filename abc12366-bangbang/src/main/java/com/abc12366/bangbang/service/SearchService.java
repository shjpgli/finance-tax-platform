package com.abc12366.bangbang.service;

import com.abc12366.bangbang.model.question.CheatsSearchBo;
import com.abc12366.bangbang.model.question.QuestionSearchBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by stuy on 2017/11/9.
 */
public interface SearchService {

    List<QuestionSearchBo> queryQuestionSearch(String text);

    List<CheatsSearchBo> queryCheatsSearch(String text);

}
