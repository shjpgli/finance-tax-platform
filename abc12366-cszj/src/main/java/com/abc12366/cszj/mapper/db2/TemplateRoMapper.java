package com.abc12366.cszj.mapper.db2;

import java.util.List;

import com.abc12366.cszj.model.weixin.bo.template.Template;


public interface TemplateRoMapper {

	Template selectOne(String id);

	List<Template> selectList(Template template);

	

}
