package com.abc12366.bangbang.service;


import com.abc12366.bangbang.model.bo.CollectBO;
import com.abc12366.bangbang.model.bo.CollectListBO;
import com.abc12366.bangbang.model.bo.MyCollectListBO;
import com.abc12366.bangbang.model.curriculum.bo.CurriculumCollectBo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface CurrCollectService {

    CurriculumCollectBo insert(String curriculumId, HttpServletRequest request);

    void delete(String curriculumId, HttpServletRequest request);

    List<CurriculumCollectBo> selectList(String userId);

    String selectExist(String curriculumId,HttpServletRequest request);

}
