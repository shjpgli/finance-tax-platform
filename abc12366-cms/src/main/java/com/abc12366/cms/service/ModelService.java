package com.abc12366.cms.service;


import com.abc12366.cms.model.Channel;
import com.abc12366.cms.model.bo.ModelBo;
import com.abc12366.cms.vo.ChannelVO;

import java.util.List;

public interface ModelService {

    List<ModelBo> selectList();

    ModelBo save(ModelBo modelBo);

    ModelBo selectModel(String modelId);

    ModelBo update(ModelBo modelBo);

    String delete(String modelId);
}
