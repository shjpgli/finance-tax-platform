package com.abc12366.cszj.mapper.db1;

import com.abc12366.cszj.model.weixin.bo.template.Template;

public interface TemplateMapper {

	void insert(Template template);

	void delete(String id);

	void deleteAll();

}
