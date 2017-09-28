package com.abc12366.bangbang.service;


import com.abc12366.bangbang.model.curriculum.bo.CurriculumClassifyBo;
import com.abc12366.bangbang.model.curriculum.bo.CurriculumClassifyTagBo;
import com.abc12366.bangbang.model.curriculum.bo.CurriculumClassifysBo;

import java.util.List;
import java.util.Map;

public interface CurrClassifyService {

    List<CurriculumClassifyBo> selectList(Map<String, Object> map);

    List<CurriculumClassifysBo> selectClassifyListsy();

    CurriculumClassifyBo save(CurriculumClassifyBo classifyBo);

    CurriculumClassifyBo selectClassify(String classifyId);

    List<CurriculumClassifyTagBo> selectClassifyTagList(String classifyId);

    CurriculumClassifyBo update(CurriculumClassifyBo classifyBo);

    CurriculumClassifyBo updateClassify(CurriculumClassifyBo classifyBo);

    String updateStatus(String classifyId, String status);

    String delete(String classifyId);

}
