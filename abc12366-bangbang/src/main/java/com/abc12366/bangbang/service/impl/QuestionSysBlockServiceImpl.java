package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.QuestionAnswerMapper;
import com.abc12366.bangbang.mapper.db1.QuestionMapper;
import com.abc12366.bangbang.mapper.db1.QuestionSysBlockMapper;
import com.abc12366.bangbang.mapper.db2.QuestionSysBlockRoMapper;
import com.abc12366.bangbang.model.question.Question;
import com.abc12366.bangbang.model.question.QuestionAnswer;
import com.abc12366.bangbang.model.question.QuestionSysBlock;
import com.abc12366.bangbang.model.question.bo.QuestionSysBlockBo;
import com.abc12366.bangbang.service.QuestionSysBlockService;
import com.abc12366.gateway.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public List<QuestionSysBlockBo> selectList() {
        return questionSysBlockRoMapper.selectList();
    }

    @Transactional("db1TxManager")
    @Override
    public void changeStatus(String id, String status) {
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
        }else{
            QuestionAnswer questionAnswer = new QuestionAnswer();
            questionAnswer.setId(record.getSourceId());
            questionAnswer.setStatus(status);
            questionAnswer.setLastUpdate(new Date());
            questionAnswerMapper.updateByPrimaryKeySelective(questionAnswer);
        }
    }
}
