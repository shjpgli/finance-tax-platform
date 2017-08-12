package com.abc12366.cms.service;


import com.abc12366.cms.model.bo.ModelBo;
import com.abc12366.cms.model.bo.ModelListBo;
import com.abc12366.cms.model.curriculum.bo.CurriculumBo;
import com.abc12366.cms.model.curriculum.bo.CurriculumListBo;
import com.abc12366.cms.model.curriculum.bo.CurriculumSituationBo;

import java.util.List;
import java.util.Map;

public interface CurriculumService {

    List<CurriculumListBo> selectList(Map<String,Object> map);

    CurriculumSituationBo selectSituation(String curriculumId);

    CurriculumBo save(CurriculumBo curriculumBo);

    CurriculumBo selectCurriculum(String curriculumId);

    CurriculumBo update(CurriculumBo curriculumBo);

    String updateStatus(String curriculumId,String status);

    String delete(String curriculumId);

    String deleteList(String[] curriculumIds);

}
