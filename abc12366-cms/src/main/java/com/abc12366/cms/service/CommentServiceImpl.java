package com.abc12366.cms.service;

import com.abc12366.cms.mapper.db1.CommentExtMapper;
import com.abc12366.cms.mapper.db1.CommentMapper;
import com.abc12366.cms.mapper.db2.CommentExtRoMapper;
import com.abc12366.cms.mapper.db2.CommentRoMapper;
import com.abc12366.cms.model.Comment;
import com.abc12366.cms.model.CommentExt;
import com.abc12366.cms.model.bo.CommentListBo;
import com.abc12366.cms.model.bo.CommentBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017/5/2.
 */
@Service
public class CommentServiceImpl implements CommentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentRoMapper commentRoMapper;

    @Autowired
    private CommentExtMapper commentExtMapper;

    @Autowired
    private CommentExtRoMapper commentExtRoMapper;

    @Override
    public List<CommentListBo> selectList(Map<String, Object> map) {
        List<CommentListBo> comments = commentRoMapper.selectList(map);
        LOGGER.info("{}", comments);
        return comments;
    }

    @Override
    public String save(CommentBo commentBo) {
        Comment comment = commentBo.getComment();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        comment.setCommentId(uuid);
        CommentExt commentExt = commentBo.getCommentExt();
        commentExt.setCommentId(uuid);
        commentMapper.insert(comment);
        commentExtMapper.insert(commentExt);
        LOGGER.info("{}", "111");
        return "11111";
    }

    @Override
    public CommentBo selectComment(String commentId) {
        Comment comment = commentRoMapper.selectByPrimaryKey(commentId);
        CommentExt commentExt = commentExtRoMapper.selectByPrimaryKey(commentId);
        CommentBo commentBo = new CommentBo();
        commentBo.setComment(comment);
        commentBo.setCommentExt(commentExt);
        LOGGER.info("{}", "111");
        return commentBo;
    }

    @Override
    public String update(CommentBo commentBo) {
        Comment comment = commentBo.getComment();
        CommentExt commentExt = commentBo.getCommentExt();
        commentMapper.updateByPrimaryKey(comment);
        commentExtMapper.updateByPrimaryKey(commentExt);
        LOGGER.info("{}", "111");
        return "11111";
    }

    @Override
    public String delete(String commentId) {
        commentExtMapper.deleteByPrimaryKey(commentId);
        commentMapper.deleteByPrimaryKey(commentId);
        LOGGER.info("{}", "111");
        return "11111";
    }
}
