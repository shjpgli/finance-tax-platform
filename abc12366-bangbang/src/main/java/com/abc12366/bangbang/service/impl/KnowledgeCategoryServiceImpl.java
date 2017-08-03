package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.KnowledgeBaseMapper;
import com.abc12366.bangbang.mapper.db1.KnowledgeCategoryMapper;
import com.abc12366.bangbang.model.KnowledgeCategory;
import com.abc12366.bangbang.service.KnowledgeCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author liuqi
 * @Date 2017/8/2 21:01
 */
@Service
public class KnowledgeCategoryServiceImpl implements KnowledgeCategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KnowledgeBaseServiceImpl.class);

    @Autowired
    private KnowledgeCategoryMapper knowledgeCategoryMapper;


    @Override
    public List<KnowledgeCategory> listAll() {
        return knowledgeCategoryMapper.selectAll();
    }
}
