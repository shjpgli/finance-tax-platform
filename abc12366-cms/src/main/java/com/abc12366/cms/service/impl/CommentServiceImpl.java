package com.abc12366.cms.service.impl;

import com.abc12366.cms.mapper.db1.CommentExtMapper;
import com.abc12366.cms.mapper.db1.CommentMapper;
import com.abc12366.cms.mapper.db2.CommentExtRoMapper;
import com.abc12366.cms.mapper.db2.CommentRoMapper;
import com.abc12366.cms.model.Comment;
import com.abc12366.cms.model.CommentExt;
import com.abc12366.cms.model.bo.*;
import com.abc12366.cms.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
        //查询评论列表
        List<CommentListBo> comments = commentRoMapper.selectList(map);
        LOGGER.info("{}", comments);
        return comments;
    }

    @Override
    public CommentTjListBo selectTj() {
        CommentTjListBo CommentTjListBo = new CommentTjListBo();

        CommentTjListBo.setDays(commentRoMapper.selectday().intValue());
        CommentTjListBo.setWeeks(commentRoMapper.selectweek().intValue());
        CommentTjListBo.setMonths(commentRoMapper.selectmonth().intValue());
        CommentTjListBo.setYears(commentRoMapper.selectyear().intValue());
        CommentTjListBo.setCnts(commentRoMapper.selectall().intValue());

        //查询评论统计
        List<CommentTjBo> tjday = commentRoMapper.selectByday();
        List<CommentTjBo> tjday0 = commentRoMapper.selectByday0();
        List<CommentTjBo> tjday1 = commentRoMapper.selectByday1();
        CommentTjListBo.setTjday(tjday);
        CommentTjListBo.setTjday0(tjday0);
        CommentTjListBo.setTjday1(tjday1);

        List<CommentTjBo> tjmonth = commentRoMapper.selectBymonth();
        List<CommentTjBo> tjmonth0 = commentRoMapper.selectBymonth0();
        List<CommentTjBo> tjmonth1 = commentRoMapper.selectBymonth1();
        CommentTjListBo.setTjmonth(tjmonth);
        CommentTjListBo.setTjmonth0(tjmonth0);
        CommentTjListBo.setTjmonth1(tjmonth1);

        List<CommentTjBo> tjyear = commentRoMapper.selectByyear();
        List<CommentTjBo> tjyear0 = commentRoMapper.selectByyear0();
        List<CommentTjBo> tjyear1 = commentRoMapper.selectByyear1();
        CommentTjListBo.setTjyear(tjyear);
        CommentTjListBo.setTjyear0(tjyear0);
        CommentTjListBo.setTjyear1(tjyear1);

        LOGGER.info("{}", CommentTjListBo);
        return CommentTjListBo;
    }

    @Transactional("db1TxManager")
    @Override
    public CommentSaveBo save(CommentSaveBo commentSaveBo) {
        //评论信息
        String uuid = UUID.randomUUID().toString().replace("-", "");
        CommentBo commentBo = commentSaveBo.getComment();
        Comment comment = new Comment();
        commentBo.setCommentId(uuid);
        commentBo.setCreateTime(new Date());
        try {
            BeanUtils.copyProperties(commentBo, comment);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        //评论扩展信息
        CommentExtBo commentExtBo = commentSaveBo.getCommentExt();
        CommentExt commentExt = new CommentExt();
        commentExtBo.setCommentId(uuid);
        try {
            BeanUtils.copyProperties(commentExtBo, commentExt);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        commentMapper.insert(comment);
        commentExtMapper.insert(commentExt);
        LOGGER.info("{}", commentSaveBo);
        return commentSaveBo;
    }

    @Override
    public CommentSaveBo selectComment(String commentId) {
        //评论信息
        Comment comment = commentRoMapper.selectByPrimaryKey(commentId);
        //评论扩展信息
        CommentExt commentExt = commentExtRoMapper.selectByPrimaryKey(commentId);
        CommentSaveBo commentSaveBo = new CommentSaveBo();
        CommentBo commentBo = new CommentBo();
        try {
            BeanUtils.copyProperties(comment, commentBo);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        CommentExtBo commentExtBo = new CommentExtBo();
        try {
            BeanUtils.copyProperties(commentExt, commentExtBo);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        commentSaveBo.setComment(commentBo);
        commentSaveBo.setCommentExt(commentExtBo);
        LOGGER.info("{}", commentSaveBo);
        return commentSaveBo;
    }

    @Transactional("db1TxManager")
    @Override
    public CommentSaveBo update(CommentSaveBo commentSaveBo) {
        //评论信息
        CommentBo commentBo = commentSaveBo.getComment();
        commentBo.setReplyTime(new Date());
        Comment comment = new Comment();
        try {
            BeanUtils.copyProperties(commentBo, comment);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        //评论扩展信息
        CommentExtBo commentExtBo = commentSaveBo.getCommentExt();
        CommentExt commentExt = new CommentExt();
        try {
            BeanUtils.copyProperties(commentExtBo, commentExt);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        commentMapper.updateByPrimaryKeySelective(comment);
        commentExtMapper.updateByPrimaryKeySelective(commentExt);
        LOGGER.info("{}", commentSaveBo);
        return commentSaveBo;
    }

    @Transactional("db1TxManager")
    @Override
    public String delete(String commentId) {
        //删除评论信息
        commentExtMapper.deleteByPrimaryKey(commentId);
        //删除评论扩展信息
        int r = commentMapper.deleteByPrimaryKey(commentId);
        LOGGER.info("{}", r);
        return "";
    }

    @Transactional("db1TxManager")
    @Override
     public String deleteList(String[] commentIds) {
        //删除评论信息
        commentExtMapper.deleteList(commentIds);
        //删除评论扩展信息
        int r = commentMapper.deleteList(commentIds);
        LOGGER.info("{}", r);
        return "";
    }

    @Transactional("db1TxManager")
    @Override
    public String spList(String[] commentIds) {
        //批量审批评论信息
        int r = commentMapper.updateIsCheckedList(commentIds);
        LOGGER.info("{}", r);
        return "";
    }
}
