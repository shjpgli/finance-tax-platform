package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.common.MapUtil;
import com.abc12366.bangbang.mapper.db1.*;
import com.abc12366.bangbang.mapper.db2.QuestionTipOffRoMapper;
import com.abc12366.bangbang.model.question.Question;
import com.abc12366.bangbang.model.question.QuestionAnswer;
import com.abc12366.bangbang.model.question.QuestionTipOff;
import com.abc12366.bangbang.model.question.bo.QuestionTipOffBo;
import com.abc12366.bangbang.model.question.bo.QuestionTipOffStatus;
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

    @Autowired
    private QuestionCommentMapper questionCommentMapper;

    @Autowired
    private CheatsMapper cheatsMapper;

    @Autowired
    private CheatsCommentMapper cheatsCommentMapper;


    @Override
    public List<QuestionTipOffBo> selectList() {
        return questionTipOffRoMapper.selectList();
    }

    @Override
    public QuestionTipOffBo selectOne(String id) {
        return questionTipOffRoMapper.selectOne(id);
    }


    @Transactional("db1TxManager")
    @Override
    public void changeStatus(QuestionTipOff questionTipOff) {
        String id = questionTipOff.getId();
        String status = questionTipOff.getStatus();

        questionTipOff.setUpdateAdmin(Utils.getAdminId());
        questionTipOffMapper.updateByPrimaryKeySelective(questionTipOff);
        /*如果举报内容 审核通过 加被举报数, 修改状态*/
        if(QuestionTipOffStatus.approved.name().equals(status)){
            QuestionTipOff record = questionTipOffRoMapper.selectByPrimaryKey(id);
            String sourceId = record.getSourceId();
            if("question".equals(record.getSourceType())){
                questionMapper.updateReportNum(sourceId);
            }else if("answer".equals(record.getSourceType())){
                questionAnswerMapper.updateReportNum(sourceId);
            }else if("comment".equals(record.getSourceType())){
                questionCommentMapper.updateReportNum(sourceId);
            }else if("cheats".equals(record.getSourceType())){
                cheatsMapper.updateReportNum(sourceId);
            }else if("cheats_comment".equals(record.getSourceType())){
                cheatsCommentMapper.updateReportNum(sourceId);
            }
        }
    }

    @Override
    public QuestionTipOffBo save(QuestionTipOffBo questionTipOffBo) {

        JSONObject jsonStu = JSONObject.fromObject(questionTipOffBo);
        String uuid = UUID.randomUUID().toString().replace("-", "");
        QuestionTipOff tipOff = new QuestionTipOff();
        questionTipOffBo.setCreateTime(new Date());
        questionTipOffBo.setId(uuid);
        questionTipOffBo.setStatus("auditing");

        Map map = MapUtil.kv("sourceId", questionTipOffBo.getSourceId(), "createUser", questionTipOffBo.getCreateUser());
        int cnt =  questionTipOffRoMapper.selectExist(map);
        if(cnt >0){
            throw new ServiceException(6370);
        }

        try {
            BeanUtils.copyProperties(questionTipOffBo, tipOff);
            questionTipOffMapper.insert(tipOff);

        } catch (Exception e) {
            throw new ServiceException(6371);
        }

        return questionTipOffBo;
    }

    @Override
    public QuestionTipOffBo savepb(QuestionTipOffBo questionTipOffBo) {

        JSONObject jsonStu = JSONObject.fromObject(questionTipOffBo);
        String uuid = UUID.randomUUID().toString().replace("-", "");
        QuestionTipOff tipOff = new QuestionTipOff();
        questionTipOffBo.setCreateTime(new Date());
        tipOff.setUpdateTime(new Date());
        questionTipOffBo.setUpdateAdmin(Utils.getAdminId());
        questionTipOffBo.setId(uuid);
        questionTipOffBo.setStatus("approved");//拉黑

        Map map = MapUtil.kv("sourceId", questionTipOffBo.getSourceId(), "createUser", questionTipOffBo.getCreateUser());
        int cnt =  questionTipOffRoMapper.selectExist(map);
        if(cnt >0){
            throw new ServiceException(6374);
        }

        try {
            BeanUtils.copyProperties(questionTipOffBo, tipOff);
            questionTipOffMapper.insert(tipOff);
            changeStatus(tipOff);

        } catch (Exception e) {
            throw new ServiceException(6375);
        }

        return questionTipOffBo;
    }
}
