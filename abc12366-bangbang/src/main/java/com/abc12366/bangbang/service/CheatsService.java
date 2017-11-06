package com.abc12366.bangbang.service;



import com.abc12366.bangbang.model.bo.TopicRecommendParamBO;
import com.abc12366.bangbang.model.question.QuestionTag;
import com.abc12366.bangbang.model.question.bo.*;

import java.util.List;
import java.util.Map;

public interface CheatsService {

    List<CheatsBo> selectList(Map<String, Object> map);

    List<CheatsBo> selectListByBrowseNum(Map<String, Object> map);

    List<CheatsBo> selectListRecommend(Map<String, Object> map);

    List<CheatstjydBo> selectListRecommendTitle(Map<String, Object> map);

    List<CheatstjydBo> selectListByTag(Map<String, Object> map);

    List<CheatsBo> selectMyCheatsList(Map<String, Object> map);

    CheatsBo save(CheatsBo cheatsBo);

    CheatsBo selectCheats(String id);

    CheatsBo update(CheatsBo cheatsBo);

    String updateStatus(String id, String status);

    String delete(String id);

    List<CheatsTagBo> selectTagList();

    String updateBrowseNum(String id);

}
