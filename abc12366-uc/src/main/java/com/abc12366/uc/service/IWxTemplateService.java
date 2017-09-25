package com.abc12366.uc.service;

import com.abc12366.uc.model.weixin.bo.template.Template;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface IWxTemplateService {

    boolean synchroTemplate();

    public void delete(String id);

    Template selectOne(String id);

    List<Template> wxTemplateList(Template template, int page, int size);

	@SuppressWarnings("rawtypes")
	ResponseEntity templateSend(String temp_id, Map<String, String> dataList);

	ResponseEntity templateSend(String templatemsg);

}
