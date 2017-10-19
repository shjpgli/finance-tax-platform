package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.common.MapUtil;
import com.abc12366.bangbang.common.UcUserCommon;
import com.abc12366.bangbang.mapper.db1.QuestionCollectMapper;
import com.abc12366.bangbang.mapper.db2.QuestionCollectRoMapper;
import com.abc12366.bangbang.model.question.QuestionCollect;
import com.abc12366.bangbang.model.question.bo.QuestionBo;
import com.abc12366.bangbang.model.question.bo.QuestionCollectBo;
import com.abc12366.bangbang.service.QueCollectService;
import com.abc12366.gateway.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by xieyanmao on 2017/9/15.
 */
@Service
public class QueCollectServiceImpl implements QueCollectService {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueCollectServiceImpl.class);

    @Autowired
    private QuestionCollectMapper collectMapper;

    @Autowired
    private QuestionCollectRoMapper collectRoMapper;

    @Override
    public String insert(String id, HttpServletRequest request) {
        LOGGER.info("{}:{}", id, request);
        String userId = UcUserCommon.getUserId(request);


        QuestionCollect collect = new QuestionCollect();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        collect.setUserId(userId);
        collect.setCollectId(uuid);
        collect.setQuestionId(id);
        collect.setCollectTime(new Date());

        Map map = MapUtil.kv("questionId", id, "userId", userId);
        int cnt =  collectRoMapper.selectExist(map);

        if(cnt >0){
            throw new ServiceException(6116);
        }

        int result = collectMapper.insert(collect);

        int collectCnt = collectRoMapper.selectCollectCnt(id);

        return collectCnt+"";
    }

    @Override
    public String delete(String id, HttpServletRequest request) {
        LOGGER.info("{}:{}", id, request);
        String userId = UcUserCommon.getUserId(request);
        Map map = MapUtil.kv("questionId", id, "userId", userId);
        collectMapper.delete(map);
        int collectCnt = collectRoMapper.selectCollectCnt(id);

        return collectCnt+"";
    }

    @Override
    public List<QuestionBo> selectList(String userId) {
        LOGGER.info("{}", userId);
        return collectRoMapper.selectList(userId);
    }

    @Override
    public String selectExist(String id, HttpServletRequest request) {
        LOGGER.info("{}", id);
        String userId = UcUserCommon.getUserId(request);
        Map map = MapUtil.kv("questionId", id, "userId", userId);
        String cnt = collectRoMapper.selectExist(map)+"";
        return cnt;
    }

}
