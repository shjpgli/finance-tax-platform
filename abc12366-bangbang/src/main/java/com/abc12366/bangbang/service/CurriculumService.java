package com.abc12366.bangbang.service;


import com.abc12366.bangbang.model.curriculum.bo.*;

import java.util.List;
import java.util.Map;

public interface CurriculumService {

    List<CurriculumListBo> selectList(Map<String, Object> map);

    List<CurriculumListsyBo> selectListNew(Map<String, Object> map);

    List<CurriculumListsyBo> selectListWatch(Map<String, Object> map);

    List<CurriculumListsyBo> selectRecommend(Map<String, Object> map);

    List<CurriculumLabelBo> selectLabelList();

    CurriculumSituationBo selectSituation(String curriculumId);

    CurriculumBo save(CurriculumBo curriculumBo);

    CurriculumBo selectCurriculum(String curriculumId);

    CurriculumsyBo selectCurriculumsy(String curriculumId);

    CurriculumBo update(CurriculumBo curriculumBo);

    String updateStatus(String curriculumId, String status);

    String delete(String curriculumId);

    String deleteList(String[] curriculumIds);

}
