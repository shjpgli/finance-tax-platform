package com.abc12366.cms.service;


import com.abc12366.cms.model.curriculum.bo.CurriculumStudyBo;

import java.util.List;
import java.util.Map;

public interface CurrStudyService {

    List<CurriculumStudyBo> selectList(Map<String, Object> map);

    CurriculumStudyBo save(CurriculumStudyBo curriculumStudyBo);

    CurriculumStudyBo selectStudy(String studyId);

    CurriculumStudyBo update(CurriculumStudyBo curriculumStudyBo);

    String updateStatus(String studyId, String status);

    String delete(String studyId);

}
