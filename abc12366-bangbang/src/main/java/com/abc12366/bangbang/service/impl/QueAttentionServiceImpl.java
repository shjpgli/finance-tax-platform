package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.common.MapUtil;
import com.abc12366.bangbang.mapper.db1.QuestionAttentionMapper;
import com.abc12366.bangbang.mapper.db2.QuestionAttentionRoMapper;
import com.abc12366.bangbang.model.question.QuestionAttention;
import com.abc12366.bangbang.model.question.bo.QuestionAttentionBo;
import com.abc12366.bangbang.service.QueAttentionService;
import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.UcUserCommon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by xieyanmao on 2017/9/15.
 */
@Service
public class QueAttentionServiceImpl implements QueAttentionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueAttentionServiceImpl.class);

    @Autowired
    private QuestionAttentionMapper attentionMapper;

    @Autowired
    private QuestionAttentionRoMapper attentionRoMapper;

    @Override
    public String insert(String id, HttpServletRequest request) {
        LOGGER.info("{}:{}", id, request);
        String userId = UcUserCommon.getUserId(request);


        QuestionAttention attention = new QuestionAttention();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        attention.setUserId(userId);
        attention.setAttentionId(uuid);
        attention.setAttentionUserId(id);

        Map map = MapUtil.kv("attentionUserId", id, "userId", userId);
        int cnt =  attentionRoMapper.selectExist(map);

        if(cnt >0){
            throw new ServiceException(6117);
        }



        int result = attentionMapper.insert(attention);

        int attentionCnt = attentionRoMapper.selectAttentionCnt(id);

        return attentionCnt+"";
    }

    @Override
    public String delete(String id, HttpServletRequest request) {
        LOGGER.info("{}:{}", id, request);
        String userId = UcUserCommon.getUserId(request);
        Map map = MapUtil.kv("attentionUserId", id, "userId", userId);
        attentionMapper.delete(map);
        int attentionCnt = attentionRoMapper.selectAttentionCnt(id);

        return attentionCnt+"";
    }

    @Override
    public List<QuestionAttentionBo> selectAttentionUserList(String userId) {
        //查询我关注的用户
        LOGGER.info("{}", userId);
        return attentionRoMapper.selectAttentionUserList(userId);
    }

    @Override
    public List<QuestionAttentionBo> selectUserList(String attentionUserId) {
        //查询关注我的用户
        LOGGER.info("{}", attentionUserId);
        return attentionRoMapper.selectUserList(attentionUserId);
    }

    @Override
    public String selectExist(String id, HttpServletRequest request) {
        LOGGER.info("{}", id);
        String userId = UcUserCommon.getUserId(request);
        Map map = MapUtil.kv("attentionUserId", id, "userId", userId);
        String cnt = attentionRoMapper.selectExist(map)+"";
        return cnt;
    }

}
