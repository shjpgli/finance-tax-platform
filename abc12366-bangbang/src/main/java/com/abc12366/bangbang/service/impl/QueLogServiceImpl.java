package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.common.MapUtil;
import com.abc12366.bangbang.mapper.db1.QuestionAnswerMapper;
import com.abc12366.bangbang.mapper.db1.QuestionCommentMapper;
import com.abc12366.bangbang.mapper.db1.QuestionLikeMapper;
import com.abc12366.bangbang.mapper.db1.QuestionLogMapper;
import com.abc12366.bangbang.mapper.db2.QuestionAnswerRoMapper;
import com.abc12366.bangbang.mapper.db2.QuestionCommentRoMapper;
import com.abc12366.bangbang.mapper.db2.QuestionLikeRoMapper;
import com.abc12366.bangbang.model.question.QuestionAnswer;
import com.abc12366.bangbang.model.question.QuestionComment;
import com.abc12366.bangbang.model.question.QuestionLike;
import com.abc12366.bangbang.model.question.QuestionLog;
import com.abc12366.bangbang.model.question.bo.QuestionAnswerBo;
import com.abc12366.bangbang.model.question.bo.QuestionBo;
import com.abc12366.bangbang.model.question.bo.QuestionCommentBo;
import com.abc12366.bangbang.service.QueLikeService;
import com.abc12366.bangbang.service.QueLogService;
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
public class QueLogServiceImpl implements QueLogService {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueLogServiceImpl.class);

    @Autowired
    private QuestionLogMapper questionLogMapper;

    @Override
    public QuestionLog insert(QuestionLog questionLog) {

        questionLogMapper.insert(questionLog);
        return questionLog;
    }


}
