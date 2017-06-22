package com.abc12366.uc.service.impl;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.mapper.db1.SubjectTagMapper;
import com.abc12366.uc.mapper.db2.SubjectTagRoMapper;
import com.abc12366.uc.model.SubjectTag;
import com.abc12366.uc.model.bo.SubjectTagBO;
import com.abc12366.uc.model.bo.TagId;
import com.abc12366.uc.model.bo.TagList;
import com.abc12366.uc.service.SubjectTagService;
import com.abc12366.uc.web.SubjectTagController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-06-15
 * Time: 15:08
 */
@Service
public class SubjectTagServiceImpl implements SubjectTagService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectTagController.class);

    @Autowired
    private SubjectTagMapper subjectTagMapper;

    @Autowired
    private SubjectTagRoMapper subjectTagRoMapper;

    @Override
    public SubjectTagBO insert(String subject, String id, String tagId, HttpServletRequest request) {
        LOGGER.info("{}:{}:{}", subject, id, tagId);
        SubjectTag subjectTag = new SubjectTag();
        subjectTag.setId(Utils.uuid());
        subjectTag.setUserId((String) request.getAttribute(Constant.USER_ID));
        subjectTag.setSubjectId(id);
        subjectTag.setTagId(tagId);
        subjectTag.setType(subject);
        subjectTag.setCreateTime(new Date());
        int result = subjectTagMapper.insert(subjectTag);
        if (result != 1) {
            LOGGER.warn("新增失败，参数：" + subjectTag);
            throw new ServiceException(4101);
        }
        SubjectTagBO subjectTagBO = new SubjectTagBO();
        BeanUtils.copyProperties(subject, subjectTagBO);
        return subjectTagBO;
    }

    @Transactional("db1TxManager")
    @Override
    public List<SubjectTagBO> batchInsert(String subject, String id, TagList tagList, HttpServletRequest request) {
        LOGGER.info("{}:{}:{}", subject, id, tagList);
        List<SubjectTagBO> subjectTagBOList = new ArrayList<>();
        SubjectTag subjectTag = new SubjectTag();
        subjectTag.setId(Utils.uuid());
        subjectTag.setUserId((String) request.getAttribute(Constant.USER_ID));
        subjectTag.setSubjectId(id);
        subjectTag.setType(subject);
        subjectTag.setCreateTime(new Date());
        for (int i = 0; i < tagList.getTagIdList().size(); i++) {
            TagId tagId = tagList.getTagIdList().get(i);
            subjectTag.setTagId(tagId.getTagId());
            int result = subjectTagMapper.insert(subjectTag);
            if (result != 1) {
                LOGGER.warn("新增失败，参数：" + subjectTag);
                throw new ServiceException(4101);
            }
            SubjectTagBO subjectTagBO = new SubjectTagBO();
            BeanUtils.copyProperties(subjectTag, subjectTagBO);
            subjectTagBOList.add(subjectTagBO);
        }
        return subjectTagBOList;
    }

    @Override
    public List<SubjectTagBO> selectList(String subject, String id) {
        LOGGER.info("{}:{}", subject, id);
        Map map = new HashMap<>();
        if (subject != null && "".equals(subject)) {
            subject = null;
        }
        if (id != null && "".equals(id)) {
            id = null;
        }
        map.put("subject", subject);
        map.put("id", id);
        return subjectTagRoMapper.selectList(map);
    }

    @Override
    public int delete(String subject, String id, String tagId) {
        LOGGER.info("{}:{}:{}", subject, id, tagId);
        Map map = new HashMap<>();
        if (subject != null && "".equals(subject)) {
            subject = null;
        }
        if (id != null && "".equals(id)) {
            id = null;
        }
        if (tagId != null && "".equals(tagId)) {
            tagId = null;
        }
        map.put("subject", subject);
        map.put("id", id);
        map.put("tagId", tagId);
        int result = subjectTagMapper.delete(map);
        if (result < 1) {
            LOGGER.warn("删除失败，参数：" + id);
            throw new ServiceException(4103);
        }
        return result;
    }

    @Override
    public int deleteByTagId(String id) {
        LOGGER.info("{}:{}:{}", id);
        int result = subjectTagMapper.deleteByTagId(id);
        return result;
    }
}
