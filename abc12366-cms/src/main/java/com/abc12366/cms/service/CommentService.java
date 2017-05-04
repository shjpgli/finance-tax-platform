package com.abc12366.cms.service;


import com.abc12366.cms.model.bo.CommentListBo;
import com.abc12366.cms.model.bo.CommentBo;

import java.util.List;
import java.util.Map;

/**
 * 内容管理模块
 *
 * @author xieyanmao
 * @create 2017-5-2
 * @since 1.0.0
 */
public interface CommentService {
    List<CommentListBo> selectList(Map<String, Object> map);

    String save(CommentBo commentBo);

    CommentBo selectComment(String commentId);

    String update(CommentBo commentBo);

    String delete(String commentId);

}
