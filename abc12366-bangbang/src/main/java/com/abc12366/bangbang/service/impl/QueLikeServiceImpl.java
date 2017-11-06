package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.common.MapUtil;
import com.abc12366.bangbang.mapper.db1.QuestionAnswerMapper;
import com.abc12366.bangbang.mapper.db1.QuestionCommentMapper;
import com.abc12366.bangbang.mapper.db1.QuestionLikeMapper;
import com.abc12366.bangbang.mapper.db2.QuestionAnswerRoMapper;
import com.abc12366.bangbang.mapper.db2.QuestionCommentRoMapper;
import com.abc12366.bangbang.mapper.db2.QuestionLikeRoMapper;
import com.abc12366.bangbang.model.Answer;
import com.abc12366.bangbang.model.question.QuestionAnswer;
import com.abc12366.bangbang.model.question.QuestionComment;
import com.abc12366.bangbang.model.question.QuestionLike;
import com.abc12366.bangbang.model.question.bo.QuestionAnswerBo;
import com.abc12366.bangbang.model.question.bo.QuestionBo;
import com.abc12366.bangbang.model.question.bo.QuestionCommentBo;
import com.abc12366.bangbang.model.question.bo.QuestionLikeBo;
import com.abc12366.bangbang.service.QueLikeService;
import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.UcUserCommon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by xieyanmao on 2017/9/15.
 */
@Service
public class QueLikeServiceImpl implements QueLikeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueLikeServiceImpl.class);

    @Autowired
    private QuestionLikeMapper likeMapper;

    @Autowired
    private QuestionAnswerMapper answerMapper;

    @Autowired
    private QuestionAnswerRoMapper answerRoMapper;

    @Autowired
    private QuestionCommentMapper commentMapper;

    @Autowired
    private QuestionCommentRoMapper commentRoMapper;

    @Autowired
    private QuestionLikeRoMapper likeRoMapper;

    @Override
    public String insert(String id, HttpServletRequest request) {
        LOGGER.info("{}:{}", id, request);
//        String userId = UcUserCommon.getUserId(request);
        String userId = "11";

                QuestionAnswerBo answer = answerRoMapper.selectByPrimaryKey(id);
        String questionId = "";
        int likeTarget = 1;//点赞来源1为回答，2为评论
        if(answer != null){
            questionId = answer.getQuestionId();
        }
        QuestionCommentBo comment = commentRoMapper.selectByPrimaryKey(id);
        if(comment != null && "".equals(questionId)){
            likeTarget = 2;
            questionId = comment.getQuestionId();
        }

        QuestionLike like = new QuestionLike();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        like.setUserId(userId);
        like.setLikeId(uuid);
        like.setLikeTime(new Date());
        like.setLikeType(1);
        like.setLikeTarget(likeTarget);
        like.setQuestionId(questionId);
        like.setId(id);

        Map map = MapUtil.kv("id", id, "userId", userId);
        int cnt =  likeRoMapper.selectExist(map);

        if(cnt >0){
            throw new ServiceException(6115);
        }

        int likeCnt = likeRoMapper.selectLikeCnt(id)+1;

        if(likeTarget == 1){
            QuestionAnswer answer1 = new QuestionAnswer();
            answer1.setLikeNum(likeCnt);
            answer1.setId(id);
            answerMapper.updateByPrimaryKeySelective(answer1);
        }else{
            QuestionComment comment1 = new QuestionComment();
            comment1.setLikeNum(likeCnt);
            comment1.setId(id);
            commentMapper.updateByPrimaryKeySelective(comment1);
        }


        int result = likeMapper.insert(like);



        return likeCnt+"";
    }

    @Override
    public String inserttrample(String id, HttpServletRequest request) {
        LOGGER.info("{}:{}", id, request);
        String userId = UcUserCommon.getUserId(request);
        QuestionAnswerBo answer = answerRoMapper.selectByPrimaryKey(id);
        String questionId = "";
        int likeTarget = 1;//点赞来源1为回答，2为评论
        if(answer != null){
            questionId = answer.getQuestionId();
        }
        QuestionCommentBo comment = commentRoMapper.selectByPrimaryKey(id);
        if(comment != null && "".equals(questionId)){
            likeTarget = 2;
            questionId = comment.getQuestionId();
        }
        QuestionLike like = new QuestionLike();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        like.setUserId(userId);
        like.setLikeId(uuid);
        like.setLikeType(2);
        like.setLikeTime(new Date());
        like.setLikeTarget(likeTarget);
        like.setQuestionId(questionId);
        like.setId(id);

        Map map = MapUtil.kv("id", id, "userId", userId);
        int cnt =  likeRoMapper.selectExist(map);

        if(cnt >0){
            throw new ServiceException(6115);
        }

        int trampleNum = likeRoMapper.selectLikeCnt(id)+1;

        if(likeTarget == 1){
            QuestionAnswer answer1 = new QuestionAnswer();
            answer1.setTrampleNum(trampleNum);
            answer1.setId(id);
            answerMapper.updateByPrimaryKeySelective(answer1);
        }else{
            QuestionComment comment1 = new QuestionComment();
            comment1.setReportNum(trampleNum);
            comment1.setId(id);
            commentMapper.updateByPrimaryKeySelective(comment1);
        }

        int result = likeMapper.insert(like);



        return trampleNum+"";
    }

    @Override
    public String delete(String id, HttpServletRequest request) {
        LOGGER.info("{}:{}", id, request);
        String userId = UcUserCommon.getUserId(request);
        Map map = MapUtil.kv("id", id, "userId", userId);
        likeMapper.delete(map);
        int likeCnt = likeRoMapper.selectLikeCnt(id);
        QuestionAnswer answer = new QuestionAnswer();
        answer.setLikeNum(likeCnt);
        answer.setId(id);
        answerMapper.updateByPrimaryKeySelective(answer);

        return likeCnt+"";
    }

    @Override
    public List<QuestionBo> selectList(String userId) {
        LOGGER.info("{}", userId);
        return likeRoMapper.selectList(userId);
    }

    @Override
    public String selectExist(String id, HttpServletRequest request) {
        LOGGER.info("{}", id);
        String userId = UcUserCommon.getUserId(request);
        Map map = MapUtil.kv("id", id, "userId", userId);
        String cnt = likeRoMapper.selectExist(map)+"";
        return cnt;
    }

}
