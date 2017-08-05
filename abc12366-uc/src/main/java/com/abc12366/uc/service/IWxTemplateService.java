package com.abc12366.uc.service;

import com.abc12366.uc.model.weixin.bo.template.Template;

import java.util.List;

public interface IWxTemplateService {

    boolean synchroTemplate();

    public void delete(String id);

    Template selectOne(String id);

    List<Template> wxTemplateList(Template template, int page, int size);

}
