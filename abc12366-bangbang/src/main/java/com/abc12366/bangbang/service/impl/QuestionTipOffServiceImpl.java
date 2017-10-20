package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.common.MapUtil;
import com.abc12366.bangbang.mapper.db1.QuestionAnswerMapper;
import com.abc12366.bangbang.mapper.db1.QuestionMapper;
import com.abc12366.bangbang.mapper.db1.QuestionTipOffMapper;
import com.abc12366.bangbang.mapper.db2.QuestionTipOffRoMapper;
import com.abc12366.bangbang.model.question.Question;
import com.abc12366.bangbang.model.question.QuestionAnswer;
import com.abc12366.bangbang.model.question.QuestionTipOff;
import com.abc12366.bangbang.model.question.bo.QuestionTipOffBo;
import com.abc12366.bangbang.service.QuestionTipOffService;
import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author liuQi
 * @Date 2017/9/19 14:17
 */
@Service
public class QuestionTipOffServiceImpl implements QuestionTipOffService{

    @Autowired
    private QuestionTipOffMapper questionTipOffMapper;

    @Autowired
    private QuestionTipOffRoMapper questionTipOffRoMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionAnswerMapper questionAnswerMapper;

    @Override
    public List<QuestionTipOffBo> selectList() {
        return questionTipOffRoMapper.selectList();
    }


    @Transactional("db1TxManager")
    @Override
    public void changeStatus(String id ,String status) {
        QuestionTipOff req = new QuestionTipOff();
        req.setId(id);
        req.setUpdateAdmin(Utils.getAdminId());
        questionTipOffMapper.updateByPrimaryKeySelective(req);

        QuestionTipOff record = questionTipOffRoMapper.selectByPrimaryKey(id);
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

    @Override
    public QuestionTipOffBo save(QuestionTipOffBo questionTipOffBo) {

        JSONObject jsonStu = JSONObject.fromObject(questionTipOffBo);
        String uuid = UUID.randomUUID().toString().replace("-", "");
        QuestionTipOff tipOff = new QuestionTipOff();
        questionTipOffBo.setCreateTime(new Date());
        questionTipOffBo.setId(uuid);

        Map map = MapUtil.kv("sourceId", questionTipOffBo.getSourceId(), "createUser", questionTipOffBo.getCreateUser());
        int cnt =  questionTipOffRoMapper.selectExist(map);
        if(cnt >0){
            throw new ServiceException(6370);
        }

        try {
            BeanUtils.copyProperties(questionTipOffBo, tipOff);
            questionTipOffMapper.insert(tipOff);

            int reportNum = questionTipOffRoMapper.selectTipoffCnt(questionTipOffBo.getSourceId());

            if("question".equals(tipOff.getSourceType())){
                Question question = new Question();
                question.setId(tipOff.getSourceId());
                question.setReportNum(reportNum);
                questionMapper.updateByPrimaryKeySelective(question);
            }else{
                QuestionAnswer questionAnswer = new QuestionAnswer();
                questionAnswer.setId(tipOff.getSourceId());
                questionAnswer.setReportNum(reportNum);
                questionAnswerMapper.updateByPrimaryKeySelective(questionAnswer);
            }

        } catch (Exception e) {
            throw new ServiceException(6371);
        }

        return questionTipOffBo;
    }
}
