package com.abc12366.cms.service;


import com.abc12366.cms.model.curriculum.bo.CurriculumClassifyBo;

import java.util.List;
import java.util.Map;

public interface CurrClassifyService {

    List<CurriculumClassifyBo> selectList(Map<String, Object> map);

    CurriculumClassifyBo save(CurriculumClassifyBo classifyBo);

    CurriculumClassifyBo selectClassify(String classifyId);

    CurriculumClassifyBo update(CurriculumClassifyBo classifyBo);

    String updateStatus(String classifyId, String status);

    String delete(String classifyId);

}
