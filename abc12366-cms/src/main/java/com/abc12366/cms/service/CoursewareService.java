package com.abc12366.cms.service;


import com.abc12366.cms.model.curriculum.bo.CurriculumBo;
import com.abc12366.cms.model.curriculum.bo.CurriculumCoursewareBo;
import com.abc12366.cms.model.curriculum.bo.CurriculumListBo;

import java.util.List;
import java.util.Map;

public interface CoursewareService {

    List<CurriculumCoursewareBo> selectList(Map<String, Object> map);

    CurriculumCoursewareBo save(CurriculumCoursewareBo curriculumCoursewareBo);

    CurriculumCoursewareBo selectCourseware(String coursewareId);

    CurriculumCoursewareBo update(CurriculumCoursewareBo curriculumCoursewareBo);

    String updateStatus(String coursewareId, String status);

    String delete(String coursewareId);

}
