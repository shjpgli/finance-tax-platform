package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.weixin.bo.template.Template;

import java.util.List;

public interface TemplateRoMapper {

    Template selectOne(String id);

    List<Template> selectList(Template template);


}
