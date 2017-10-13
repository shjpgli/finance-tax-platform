package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.common.MapUtil;
import com.abc12366.bangbang.common.UcUserCommon;
import com.abc12366.bangbang.mapper.db1.QuestionLikeMapper;
import com.abc12366.bangbang.mapper.db2.QuestionCountRoMapper;
import com.abc12366.bangbang.mapper.db2.QuestionLikeRoMapper;
import com.abc12366.bangbang.model.question.QuestionLike;
import com.abc12366.bangbang.model.question.bo.QuestionCountBo;
import com.abc12366.bangbang.model.question.bo.QuestionLikeBo;
import com.abc12366.bangbang.service.QueCountService;
import com.abc12366.bangbang.service.QueLikeService;
import com.abc12366.gateway.exception.ServiceException;
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
public class QueCountServiceImpl implements QueCountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueCountServiceImpl.class);

    @Autowired
    private QuestionCountRoMapper countMapper;


    @Override
    public List<QuestionCountBo> selectLike() {
        return countMapper.selectLike();
    }

    @Override
    public List<QuestionCountBo> selectAttention() {
        return countMapper.selectAttention();
    }

    @Override
    public List<QuestionCountBo> selectAccept() {
        return countMapper.selectAccept();
    }
}
