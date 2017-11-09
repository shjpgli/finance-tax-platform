package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.common.MapUtil;
import com.abc12366.bangbang.mapper.db1.QuestionAttentionMapper;
import com.abc12366.bangbang.mapper.db2.QcUserRoMapper;
import com.abc12366.bangbang.mapper.db2.QuestionAttentionRoMapper;
import com.abc12366.bangbang.model.question.QuestionAttention;
import com.abc12366.bangbang.model.question.bo.QcBangUserBo;
import com.abc12366.bangbang.model.question.bo.QcTitleBo;
import com.abc12366.bangbang.model.question.bo.QcUserBo;
import com.abc12366.bangbang.model.question.bo.QuestionAttentionBo;
import com.abc12366.bangbang.service.QcUserService;
import com.abc12366.bangbang.service.QueAttentionService;
import com.abc12366.bangbang.util.BangBangDtLogUtil;
import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.UcUserCommon;
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
public class QcUserServiceImpl implements QcUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(QcUserServiceImpl.class);

    @Autowired
    private QcUserRoMapper qcUserRoMapper;

    @Override
    public List<QcBangUserBo> selectList() {
        //查询帮友
        return qcUserRoMapper.selectList();
    }

    @Override
    public List<QcTitleBo> selectQuestionList(String userId) {
        //查询帮友
        return qcUserRoMapper.selectQuestionList(userId);
    }


}
