package com.abc12366.cms.service;

import com.abc12366.cms.mapper.db1.ContentTypeMapper;
import com.abc12366.cms.mapper.db2.ContentTypeRoMapper;
import com.abc12366.cms.model.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 内容类型管理模块
 *
 * @author xieyanmao
 * @create 2017-5-3
 * @since 1.0.0
 */
@Service
public class ContentTypeServiceImpl implements ContentTypeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContentTypeServiceImpl.class);

    @Autowired
    private ContentTypeMapper contentTypeMapper;

    @Autowired
    private ContentTypeRoMapper contentTypeRoMapper;


    @Override
    public List<ContentType> selectList() {
        List<ContentType> contentTypeList = contentTypeRoMapper.selectList();
        return contentTypeList;
    }

    @Override
    public String save(ContentType contentType) {

        contentTypeMapper.insert(contentType);

        return "22222";
    }

    @Override
    public ContentType selectContentType(String typeId) {
        ContentType contentType = contentTypeRoMapper.selectByPrimaryKey(typeId);
        return contentType;
    }

    @Override
    public String update(ContentType contentType) {
        contentTypeMapper.updateByPrimaryKey(contentType);
        return "333333";
    }

    @Override
    public String delete(String typeId) {
        contentTypeMapper.deleteByPrimaryKey(typeId);
        return null;
    }
}
