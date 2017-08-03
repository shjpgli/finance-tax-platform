package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.KnowledgeBaseMapper;
import com.abc12366.bangbang.model.KnowledgeBase;
import com.abc12366.bangbang.model.bo.KnowledgeBaseParamBO;
import com.abc12366.bangbang.service.KnowledgeBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author liuqi
 * @Date 2017/8/2 19:57
 */
@Service
public class KnowledgeBaseServiceImpl implements KnowledgeBaseService{

    private static final Logger LOGGER = LoggerFactory.getLogger(KnowledgeBaseServiceImpl.class);

    @Autowired
    private KnowledgeBaseMapper knowledgeBaseMapper;

    @Override
    public List<KnowledgeBase> selectList(KnowledgeBaseParamBO param) {
        return knowledgeBaseMapper.selectList(param);
    }

    @Override
    public void add(KnowledgeBase knowledgeBase) {
        knowledgeBaseMapper.insert(knowledgeBase);
    }
}
