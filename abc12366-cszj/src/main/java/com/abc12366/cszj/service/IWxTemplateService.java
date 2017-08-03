package com.abc12366.cszj.service;

import java.util.List;

import com.abc12366.cszj.model.weixin.bo.template.Template;

public interface IWxTemplateService {

	boolean synchroTemplate();
	
	public void delete(String id);

	Template selectOne(String id);

	List<Template> wxTemplateList(Template template, int page, int size);

}
