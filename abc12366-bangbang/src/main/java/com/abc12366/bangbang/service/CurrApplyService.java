package com.abc12366.bangbang.service;


import com.abc12366.bangbang.model.curriculum.bo.CurriculumApplyBo;

import java.util.List;
import java.util.Map;

public interface CurrApplyService {

    List<CurriculumApplyBo> selectList(Map<String, Object> map);

    int selectApplyCnt(Map<String, Object> map);

    CurriculumApplyBo save(CurriculumApplyBo curriculumApplyBo);

    CurriculumApplyBo selectApply(String applyId);

    CurriculumApplyBo selectCurrApply(Map<String, Object> map);

    CurriculumApplyBo update(CurriculumApplyBo curriculumApplyBo);

    String updateStatus(String applyId, String status);

    String delete(String applyId);

}
