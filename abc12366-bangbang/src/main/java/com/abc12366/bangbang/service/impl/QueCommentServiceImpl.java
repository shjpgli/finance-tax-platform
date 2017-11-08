package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.QuestionAnswerMapper;
import com.abc12366.bangbang.mapper.db1.QuestionCommentMapper;
import com.abc12366.bangbang.mapper.db1.QuestionMapper;
import com.abc12366.bangbang.mapper.db2.*;
import com.abc12366.bangbang.model.question.Question;
import com.abc12366.bangbang.model.question.QuestionAnswer;
import com.abc12366.bangbang.model.question.QuestionComment;
import com.abc12366.bangbang.model.question.bo.QuestionCommentBo;
import com.abc12366.bangbang.service.QueCommentService;
import com.abc12366.bangbang.util.BangBangDtLogUtil;
import com.abc12366.gateway.exception.ServiceException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by xieyanmao on 2017/9/14.
 */
@Service
public class QueCommentServiceImpl implements QueCommentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueCommentServiceImpl.class);

    @Autowired
    private QuestionCommentMapper commentMapper;

    @Autowired
    private QuestionAnswerMapper answerMapper;

    @Autowired
    private QuestionCommentRoMapper commentRoMapper;

    @Autowired
    private QuestionAnswerRoMapper answerRoMapper;

    @Autowired
    private SensitiveWordsRoMapper sensitiveWordsRoMapper;

    @Autowired
    private QuestionDisableIpRoMapper questionDisableIpRoMapper;

    @Autowired
    private QuestionDisableUserRoMapper questionDisableUserRoMapper;

    @Autowired
    private BangBangDtLogUtil bangBangDtLogUtil;

    @Override
    public List<QuestionCommentBo> selectList(Map<String,Object> map) {
        List<QuestionCommentBo> commentBoList;
        try {
            //查询问题评论列表
            commentBoList = commentRoMapper.selectList(map);
        } catch (Exception e) {
            LOGGER.error("查询问题评论列表信息异常：{}", e);
            throw new ServiceException(6160);
        }
        return commentBoList;
    }

    @Override
    public List<QuestionCommentBo> selectMyCommentList(Map<String,Object> map) {
        List<QuestionCommentBo> commentBoList;
        try {
            //查询我的评论列表
            commentBoList = commentRoMapper.selectMyCommentList(map);
        } catch (Exception e) {
            LOGGER.error("查询我的评论列表信息异常：{}", e);
            throw new ServiceException(6160);
        }
        return commentBoList;
    }

    @Override
    public QuestionCommentBo save(QuestionCommentBo commentBo) {

        int ipcnt = questionDisableIpRoMapper.selectIpCnt(commentBo.getIp());

        if(ipcnt > 0){
            //该IP已被禁言
            throw new ServiceException(6372);
        }

        int usercnt = questionDisableUserRoMapper.selectUserCnt(commentBo.getUserId());

        if(usercnt > 0){
            //该用户已被禁言
            throw new ServiceException(6373);
        }

        try {
            JSONObject jsonStu = JSONObject.fromObject(commentBo);
            LOGGER.info("新增问题评论信息:{}", jsonStu.toString());

            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("userId", commentBo.getUserId());
            dataMap.put("questionId", commentBo.getQuestionId());
            String factionId = answerRoMapper.selectfactionId(dataMap);
            if(factionId == null){
                factionId = "";
            }
            commentBo.setFactionId(factionId);
            commentBo.setCreateTime(new Date());
            commentBo.setLastUpdate(new Date());
            commentBo.setLikeNum(0);
            commentBo.setTrampleNum(0);
            commentBo.setReportNum(0);
            commentBo.setStatus("0");

            //敏感词校验
            String commentTxt = commentBo.getCommentTxt();
            List<String> wordList = sensitiveWordsRoMapper.selectListWords();
            if(commentTxt != null && !"".equals(commentTxt)){
                for(String word : wordList){
                    boolean bl = commentTxt.contains(word);
                    if(bl){
                        commentBo.setStatus("1");
                        break;
                    }
                }
            }

            //保存问题评论信息
            String uuid = UUID.randomUUID().toString().replace("-", "");
            QuestionComment comment = new QuestionComment();
            commentBo.setId(uuid);
            BeanUtils.copyProperties(commentBo, comment);

            int commentNum = commentRoMapper.selectCommentCnt(commentBo.getAnswerId());
            QuestionAnswer answer = new QuestionAnswer();
            answer.setId(commentBo.getAnswerId());
            answer.setCommentNum(commentNum+1);
            answerMapper.updateByPrimaryKeySelective(answer);
            commentBo.setCommentNum(commentNum);

            commentMapper.insert(comment);

            //帮邦日志记录表
            //日志类型,问题或者秘籍ID,回复ID,来源ID,用户ID,被关注用户ID
            bangBangDtLogUtil.insertLog(3, comment.getQuestionId(), comment.getAnswerId(), comment.getId(), comment.getUserId(), "");



        } catch (Exception e) {
            LOGGER.error("新增问题评论信息异常：{}", e);
            throw new ServiceException(6162);
        }

        return commentBo;
    }

    @Override
    public QuestionCommentBo selectComment(String id) {
        QuestionCommentBo commentBo = new QuestionCommentBo();
        try {
            LOGGER.info("查询单个问题评论信息:{}", id);
            //查询单个问题回复信息
            commentBo = commentRoMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            LOGGER.error("查询单个问题评论信息异常：{}", e);
            throw new ServiceException(6161);
        }
        return commentBo;
    }

    @Transactional("db1TxManager")
    @Override
    public QuestionCommentBo update(QuestionCommentBo commentBo) {

        int ipcnt = questionDisableIpRoMapper.selectIpCnt(commentBo.getIp());

        if(ipcnt > 0){
            //该IP已被禁言
            throw new ServiceException(6372);
        }

        int usercnt = questionDisableUserRoMapper.selectUserCnt(commentBo.getUserId());

        if(usercnt > 0){
            //该用户已被禁言
            throw new ServiceException(6373);
        }

        //更新问题评论信息
        QuestionComment comment = new QuestionComment();
        try {
            JSONObject jsonStu = JSONObject.fromObject(commentBo);
            LOGGER.info("更新问题评论信息:{}", jsonStu.toString());
            commentBo.setLastUpdate(new Date());
            commentBo.setStatus("0");

            //敏感词校验
            String commentTxt = commentBo.getCommentTxt();
            List<String> wordList = sensitiveWordsRoMapper.selectListWords();
            if(commentTxt != null && !"".equals(commentTxt)){
                for(String word : wordList){
                    boolean bl = commentTxt.contains(word);
                    if(bl){
                        commentBo.setStatus("1");
                        break;
                    }
                }
            }

            BeanUtils.copyProperties(commentBo, comment);
            commentMapper.updateByPrimaryKeySelective(comment);
        } catch (Exception e) {
            LOGGER.error("更新问题评论信息异常：{}", e);
            throw new ServiceException(6163);
        }
        return commentBo;
    }

    @Override
    public String updateStatus(String id,String status) {
        //更新问题评论信息
        try {

        } catch (Exception e) {
            LOGGER.error("更新问题评论信息异常：{}", e);
            throw new ServiceException(6163);
        }
        return "";
    }

    @Override
    public String delete(String id) {
        int num = 0;
        try {
            LOGGER.info("删除问题回复信息:{}", id);

            QuestionCommentBo commentBo = commentRoMapper.selectByPrimaryKey(id);

            int commentNum = commentRoMapper.selectCommentCnt(commentBo.getAnswerId());
            QuestionAnswer answer = new QuestionAnswer();
            answer.setId(commentBo.getAnswerId());
            answer.setCommentNum(commentNum-1);
            answerMapper.updateByPrimaryKeySelective(answer);
            num = commentNum-1;

            commentMapper.deleteByPrimaryKey(id);



        } catch (Exception e) {
            LOGGER.error("删除问题评论异常：{}", e);
            throw new ServiceException(6164);
        }
        return num+"";
    }

}
