package com.abc12366.cms.service;


import com.abc12366.cms.vo.ModelItemVO;

import java.util.List;

public interface ModelItemService {

    List<ModelItemVO> selectList();

    int update(ModelItemVO modelItemVO);

    ModelItemVO selectOneById(String modelItemId);

    List<ModelItemVO> selectListByParam(ModelItemVO modelItemVO);

    int delete(ModelItemVO modelItemVO);

    int deleteById(String modelItemId);
}
