package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.*;
import com.abc12366.bangbang.mapper.db2.QuestionSysBlockRoMapper;
import com.abc12366.bangbang.model.Message;
import com.abc12366.bangbang.model.question.*;
import com.abc12366.bangbang.model.question.bo.QuestionSysBlockBo;
import com.abc12366.bangbang.model.question.bo.QuestionSysBlockParamBo;
import com.abc12366.bangbang.service.MessageSendUtil;
import com.abc12366.bangbang.service.QuestionSysBlockService;
import com.abc12366.gateway.util.DateUtils;
import com.abc12366.gateway.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @Author liuQi
 * @Date 2017/9/19 16:07
 */
@Service
public class QuestionSysBlockServiceImpl implements QuestionSysBlockService {

    @Autowired
    private QuestionSysBlockMapper questionSysBlockMapper;

    @Autowired
    private QuestionSysBlockRoMapper questionSysBlockRoMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionAnswerMapper questionAnswerMapper;

    @Autowired
    private QuestionCommentMapper questionCommentMapper;

    @Autowired
    private CheatsMapper cheatsMapper;

    @Autowired
    private CheatsCommentMapper cheatsCommentMapper;

    @Autowired
    private MessageSendUtil messageSendUtil;

    @Override
    public List<QuestionSysBlockBo> selectList(QuestionSysBlockParamBo Param) {
        return questionSysBlockRoMapper.selectList(Param);
    }

    @Transactional("db1TxManager")
    @Override
    public void changeStatus(String id, String status, HttpServletRequest request) {
        QuestionSysBlock req = new QuestionSysBlock();
        req.setId(id);
        req.setUpdateAdmin(Utils.getAdminId());
        req.setStatus(status);
        questionSysBlockMapper.updateByPrimaryKeySelective(req);

        QuestionSysBlock record = questionSysBlockRoMapper.selectByPrimaryKey(id);
        if("question".equals(record.getSourceType())){
            Question question = new Question();
            question.setId(record.getSourceId());
            question.setStatus(status);
            question.setLastUpdate(new Date());
            questionMapper.updateByPrimaryKeySelective(question);
        }else if("answer".equals(record.getSourceType())){
            QuestionAnswer questionAnswer = new QuestionAnswer();
            questionAnswer.setId(record.getSourceId());
            questionAnswer.setStatus(status);
            questionAnswer.setLastUpdate(new Date());
            questionAnswerMapper.updateByPrimaryKeySelective(questionAnswer);
        }else if("comment".equals(record.getSourceType())){
            QuestionComment questionComment = new QuestionComment();
            questionComment.setId(record.getSourceId());
            questionComment.setStatus(status);
            questionComment.setLastUpdate(new Date());
            questionCommentMapper.updateByPrimaryKeySelective(questionComment);
        }else if("cheats".equals(record.getSourceType())){
            Cheats cheats = new Cheats();
            cheats.setId(record.getSourceId());
            cheats.setStatus(status);
            cheats.setLastUpdate(new Date());
            cheatsMapper.updateByPrimaryKeySelective(cheats);
        }else if("cheats".equals(record.getSourceType())){
            CheatsComment cheatsComment = new CheatsComment();
            cheatsComment.setId(record.getSourceId());
            cheatsComment.setStatus(status);
            cheatsComment.setLastUpdate(new Date());
            cheatsCommentMapper.updateByPrimaryKeySelective(cheatsComment);
        }

        if("2".equals(status)){
            Message message = new Message();
            message.setUserId(record.getUserId());
            message.setContent("您"+ DateUtils.dateToStr(record.getCreateTime())+"发布的内容涉及到敏感词汇，已被系统屏蔽！");
            message.setType("2");
            message.setBusinessId(record.getId());
            messageSendUtil.sendMessage(message, request);
        }
        if("0".equals(status)){
            Message message = new Message();
            message.setUserId(record.getUserId());
            message.setContent("您"+ DateUtils.dateToStr(record.getCreateTime())+"发布的内容涉及到敏感词汇，经管理员审核已取消屏蔽！");
            message.setType("2");
            message.setBusinessId(record.getId());
            messageSendUtil.sendMessage(message, request);
        }

    }
}
