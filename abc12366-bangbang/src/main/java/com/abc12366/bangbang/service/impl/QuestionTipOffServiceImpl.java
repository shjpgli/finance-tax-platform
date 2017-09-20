package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.QuestionAnswerMapper;
import com.abc12366.bangbang.mapper.db1.QuestionMapper;
import com.abc12366.bangbang.mapper.db1.QuestionTipOffMapper;
import com.abc12366.bangbang.model.question.Question;
import com.abc12366.bangbang.model.question.QuestionAnswer;
import com.abc12366.bangbang.model.question.QuestionTipOff;
import com.abc12366.bangbang.model.question.bo.QuestionTipOffBo;
import com.abc12366.bangbang.service.QuestionTipOffService;
import com.abc12366.gateway.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author liuQi
 * @Date 2017/9/19 14:17
 */
@Service
public class QuestionTipOffServiceImpl implements QuestionTipOffService{

    @Autowired
    private QuestionTipOffMapper questionTipOffMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionAnswerMapper questionAnswerMapper;

    @Override
    public List<QuestionTipOffBo> selectList() {
        return questionTipOffMapper.selectList();
    }


    @Transactional("db1TxManager")
    @Override
    public void changeStatus(String id ,String status) {
        QuestionTipOff req = new QuestionTipOff();
        req.setId(id);
        req.setUpdateAdmin(Utils.getAdminId());
        questionTipOffMapper.updateByPrimaryKeySelective(req);

        QuestionTipOff record = questionTipOffMapper.selectByPrimaryKey(id);
        if("question".equals(record.getSourceType())){
            Question question = new Question();
            question.setId(record.getSourceId());
            question.setStatus(status);
            questionMapper.updateByPrimaryKeySelective(question);
        }else{
            QuestionAnswer questionAnswer = new QuestionAnswer();
            questionAnswer.setId(record.getSourceId());
            questionAnswer.setStatus(status);
            questionAnswerMapper.updateByPrimaryKeySelective(questionAnswer);
        }

    }
}
