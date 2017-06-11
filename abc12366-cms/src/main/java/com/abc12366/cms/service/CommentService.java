package com.abc12366.cms.service;


import com.abc12366.cms.model.bo.CommentListBo;
import com.abc12366.cms.model.bo.CommentSaveBo;
import com.abc12366.cms.model.bo.CommentTjListBo;

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

    CommentSaveBo save(CommentSaveBo commentBo);

    CommentSaveBo selectComment(String commentId);

    CommentSaveBo update(CommentSaveBo commentBo);

    String delete(String commentId);

    String deleteList(String[] commentIds);

    String spList(String[] commentIds);

    CommentTjListBo selectTj();

}
