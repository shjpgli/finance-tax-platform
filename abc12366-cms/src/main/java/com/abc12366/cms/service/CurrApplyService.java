package com.abc12366.cms.service;


import com.abc12366.cms.model.curriculum.bo.CurriculumApplyBo;

import java.util.List;
import java.util.Map;

public interface CurrApplyService {

    List<CurriculumApplyBo> selectList(Map<String, Object> map);

    CurriculumApplyBo save(CurriculumApplyBo curriculumApplyBo);

    CurriculumApplyBo selectApply(String applyId);

    CurriculumApplyBo update(CurriculumApplyBo curriculumApplyBo);

    String updateStatus(String applyId, String status);

    String delete(String applyId);

}
