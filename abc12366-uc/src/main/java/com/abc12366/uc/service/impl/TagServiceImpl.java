package com.abc12366.uc.service.impl;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.mapper.db1.SubjectTagMapper;
import com.abc12366.uc.mapper.db1.TagMapper;
import com.abc12366.uc.mapper.db2.TagRoMapper;
import com.abc12366.uc.model.Tag;
import com.abc12366.uc.model.bo.TagBO;
import com.abc12366.uc.model.bo.TagInsertBO;
import com.abc12366.uc.model.bo.TagSelectParamBO;
import com.abc12366.uc.model.bo.TagUpdateBO;
import com.abc12366.uc.service.SubjectTagService;
import com.abc12366.uc.service.TagService;
import com.abc12366.uc.web.SubjectTagController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-06-15
 * Time: 15:23
 */
@Service
public class TagServiceImpl implements TagService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectTagController.class);

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private TagRoMapper tagRoMapper;

    private SubjectTagService subjectTagService;


    @Override
    public List<TagBO> selectList(TagSelectParamBO tagSelectParamBO) {
        LOGGER.info("{}", tagSelectParamBO);
        return tagRoMapper.selectList(tagSelectParamBO);
    }

    @Override
    public TagBO insert(TagInsertBO tagInsertBO) {
        Tag tag = new Tag();
        BeanUtils.copyProperties(tagInsertBO, tag);
        Date date = new Date();
        tag.setId(Utils.uuid());
        tag.setCreateTime(date);
        tag.setLastUpdate(date);
        int result = tagMapper.insert(tag);
        if (result != 1) {
            LOGGER.warn("新增失败，参数：" + tag);
            throw new ServiceException(4101);
        }
        TagBO tagBO = new TagBO();
        BeanUtils.copyProperties(tag, tagBO);
        return tagBO;
    }

    @Override
    public TagBO selectOne(String id) {
        LOGGER.info("{}", id);
        return tagRoMapper.selectOne(id);
    }

    @Override
    public TagBO update(TagUpdateBO tagUpdateBO, String id) {
        LOGGER.info("{}:{}", tagUpdateBO, id);
        Tag tag = new Tag();
        BeanUtils.copyProperties(tagUpdateBO, tag);
        Date date = new Date();
        tag.setId(id);
        tag.setLastUpdate(date);
        int result = tagMapper.update(tag);
        if (result != 1) {
            LOGGER.warn("修改失败，参数：" + tag);
            throw new ServiceException(4102);
        }
        TagBO tagBO = new TagBO();
        BeanUtils.copyProperties(tag, tagBO);
        return tagBO;
    }

    @Transactional("db1TxManager")
    @Override
    public int delete(String id) {
        LOGGER.info("{}", id);
        int result = tagMapper.delete(id);
        if (result < 1) {
            LOGGER.warn("删除失败，参数：" + id);
            throw new ServiceException(4103);
        }
        subjectTagService.deleteByTagId(id);
        return 1;
    }
}
