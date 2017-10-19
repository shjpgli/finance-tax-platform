package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.common.MapUtil;
import com.abc12366.bangbang.common.UcUserCommon;
import com.abc12366.bangbang.mapper.db1.QuestionAnswerMapper;
import com.abc12366.bangbang.mapper.db1.QuestionLikeMapper;
import com.abc12366.bangbang.mapper.db2.QuestionLikeRoMapper;
import com.abc12366.bangbang.model.question.QuestionAnswer;
import com.abc12366.bangbang.model.question.QuestionLike;
import com.abc12366.bangbang.model.question.bo.QuestionBo;
import com.abc12366.bangbang.model.question.bo.QuestionLikeBo;
import com.abc12366.bangbang.service.QueLikeService;
import com.abc12366.gateway.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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
    private QuestionLikeRoMapper likeRoMapper;

    @Transactional("db1TxManager")
    @Override
    public String insert(String id, HttpServletRequest request) {
        LOGGER.info("{}:{}", id, request);
        String userId = UcUserCommon.getUserId(request);

        QuestionLike like = new QuestionLike();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        like.setUserId(userId);
        like.setLikeId(uuid);
        like.setId(id);

        Map map = MapUtil.kv("id", id, "userId", userId);
        int cnt =  likeRoMapper.selectExist(map);

        if(cnt >0){
            throw new ServiceException(6115);
        }

        int likeCnt = likeRoMapper.selectLikeCnt(id) +1;

        int result = likeMapper.insert(like);



        QuestionAnswer answer = new QuestionAnswer();
        answer.setLikeNum(likeCnt);
        answer.setId(id);
        answerMapper.updateByPrimaryKeySelective(answer);

        return likeCnt+"";
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
