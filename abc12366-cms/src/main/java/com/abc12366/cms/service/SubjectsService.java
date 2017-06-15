package com.abc12366.cms.service;


import com.abc12366.cms.model.bo.CommentListBo;
import com.abc12366.cms.model.bo.CommentSaveBo;
import com.abc12366.cms.model.bo.CommentTjListBo;
import com.abc12366.cms.model.questionnaire.Subjects;
import com.abc12366.cms.model.questionnaire.bo.SubjectsBO;

import java.util.List;
import java.util.Map;

/**
 * 题目管理接口类
 * @author lizhongwei
 * @create 2017-6-14
 * @since 1.0.0
 */
public interface SubjectsService {

    List<SubjectsBO> selectList(SubjectsBO subjects);

    SubjectsBO selectOne(String id);

    SubjectsBO insert(SubjectsBO subjectsBO);

    SubjectsBO update(SubjectsBO subjectsBO);

    void delete(SubjectsBO subjectsBO);
}
