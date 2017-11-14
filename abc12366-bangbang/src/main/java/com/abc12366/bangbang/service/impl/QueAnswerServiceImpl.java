package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.QuestionAnswerMapper;
import com.abc12366.bangbang.mapper.db1.QuestionMapper;
import com.abc12366.bangbang.mapper.db2.*;
import com.abc12366.bangbang.model.question.Question;
import com.abc12366.bangbang.model.question.QuestionAnswer;
import com.abc12366.bangbang.model.question.bo.QuestionAnswerBo;
import com.abc12366.bangbang.service.QueAnswerService;
import com.abc12366.bangbang.util.BangBangDtLogUtil;
import com.abc12366.bangbang.util.BangbangRestTemplateUtil;
import com.abc12366.gateway.component.SpringCtxHolder;
import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.UCConstant;
import com.abc12366.gateway.util.Utils;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by xieyanmao on 2017/9/14.
 */
@Service
public class QueAnswerServiceImpl implements QueAnswerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueAnswerServiceImpl.class);

    @Autowired
    private QuestionAnswerMapper answerMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionRoMapper questionRoMapper;

    @Autowired
    private QuestionAnswerRoMapper answerRoMapper;

    @Autowired
    private SensitiveWordsRoMapper sensitiveWordsRoMapper;

    @Autowired
    private QuestionDisableIpRoMapper questionDisableIpRoMapper;

    @Autowired
    private QuestionDisableUserRoMapper questionDisableUserRoMapper;

    @Autowired
    private BangBangDtLogUtil bangBangDtLogUtil;

    @Autowired
    private BangbangRestTemplateUtil bangbangRestTemplateUtil;

    @Override
    public List<QuestionAnswerBo> selectList(Map<String,Object> map) {
        List<QuestionAnswerBo> answerBoList;
        try {
            //查询问题回复列表
            answerBoList = answerRoMapper.selectList(map);
        } catch (Exception e) {
            LOGGER.error("查询问题回复列表信息异常：{}", e);
            throw new ServiceException(6110);
        }
        return answerBoList;
    }

    @Override
    public List<QuestionAnswerBo> selectListNew(Map<String,Object> map) {
        List<QuestionAnswerBo> answerBoList;
        try {
            //查询最新问题回复列表
            answerBoList = answerRoMapper.selectList(map);
        } catch (Exception e) {
            LOGGER.error("查询问题回复列表信息异常：{}", e);
            throw new ServiceException(6110);
        }
        return answerBoList;
    }

    @Override
    public List<QuestionAnswerBo> selectMyAnswerList(Map<String,Object> map) {
        List<QuestionAnswerBo> answerBoList;
        try {
            //查询我的回答列表
            answerBoList = answerRoMapper.selectMyAnswerList(map);
        } catch (Exception e) {
            LOGGER.error("查询问题回复列表信息异常：{}", e);
            throw new ServiceException(6110);
        }
        return answerBoList;
    }

    @Override
    public QuestionAnswerBo save(QuestionAnswerBo answerBo, HttpServletRequest request) {

        int ipcnt = questionDisableIpRoMapper.selectIpCnt(answerBo.getIp());

        if(ipcnt > 0){
            //该IP已被禁言
            throw new ServiceException(6372);
        }

        int usercnt = questionDisableUserRoMapper.selectUserCnt(answerBo.getUserId());

        if(usercnt > 0){
            //该用户已被禁言
            throw new ServiceException(6373);
        }

        JSONObject jsonStu = JSONObject.fromObject(answerBo);
        LOGGER.info("新增问题回复信息:{}", jsonStu.toString());

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("userId", answerBo.getUserId());
        dataMap.put("questionId", answerBo.getQuestionId());
        //查询是否已回复
        int answerCnt = answerRoMapper.selectMyAnswerCnt(dataMap);
        if(answerCnt >0){
            //已回复，请勿重复回复
            throw new ServiceException(6118);
        }
        try {
            String classifyCode = questionRoMapper.selectclassifyCode(answerBo.getQuestionId());
            Map<String, Object> dataMap1 = new HashMap<>();
            dataMap1.put("userId", answerBo.getUserId());
            dataMap1.put("classifyCode", classifyCode);
            String factionId = questionRoMapper.selectfactionId(dataMap1);
            if(factionId == null){
                factionId = "";
            }
            answerBo.setFactionId(factionId);
            answerBo.setCreateTime(new Date());
            answerBo.setLastUpdate(new Date());
            //保存问题回复信息
            String uuid = UUID.randomUUID().toString().replace("-", "");
            QuestionAnswer answer = new QuestionAnswer();
            answerBo.setId(uuid);
            answerBo.setCommentNum(0);
            answerBo.setLikeNum(0);
            answerBo.setTrampleNum(0);
            answerBo.setReportNum(0);
            answerBo.setStatus("0");//0正常，1待审查，2拉黑
            answerBo.setIsRead(0);

            //敏感词校验
            String answerTxt = answerBo.getAnswer();
            List<String> wordList = sensitiveWordsRoMapper.selectListWords();
            if(answerTxt != null && !"".equals(answerTxt)){
                for(String word : wordList){
                    boolean bl = answerTxt.contains(word);
                    if(bl){
                        answerBo.setStatus("1");
                        break;
                    }
                }
            }



            BeanUtils.copyProperties(answerBo, answer);

            int answerNum = answerRoMapper.selectAnswerCnt(answerBo.getQuestionId());
            Question question = new Question();
            question.setId(answerBo.getQuestionId());
            question.setAnswerNum(answerNum+1);
            questionMapper.updateByPrimaryKeySelective(question);
            answerBo.setAnswerNum(answerNum);

            answerMapper.insert(answer);


            //帮邦日志记录表
            //日志类型,问题或者秘籍ID,回复ID,来源ID,用户ID,被关注用户ID
            bangBangDtLogUtil.insertLog(2,1, answer.getQuestionId(), answer.getId(), answer.getId(),"", answer.getUserId(), "");

            String url = SpringCtxHolder.getProperty("abc12366.uc.url") + "/todo/task/do/award/{userId}/{taskCode}";
            String userId = Utils.getUserId();
            String sysTaskId = UCConstant.SYS_TASK_MRHDWT_CODE;
            bangbangRestTemplateUtil.send(url, HttpMethod.POST, request,userId,sysTaskId);


        } catch (Exception e) {
            LOGGER.error("新增问题回复信息异常：{}", e);
            throw new ServiceException(6112);
        }

        return answerBo;
    }

    @Override
    public QuestionAnswerBo selectAnswer(String id) {
        QuestionAnswerBo answerBo = new QuestionAnswerBo();
        try {
            LOGGER.info("查询单个问题回复信息:{}", id);
            //查询单个问题回复信息
            answerBo = answerRoMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            LOGGER.error("查询单个问题回复信息异常：{}", e);
            throw new ServiceException(6111);
        }
        return answerBo;
    }

    @Transactional("db1TxManager")
    @Override
    public QuestionAnswerBo update(QuestionAnswerBo answerBo) {

        int ipcnt = questionDisableIpRoMapper.selectIpCnt(answerBo.getIp());

        if(ipcnt > 0){
            //该IP已被禁言
            throw new ServiceException(6372);
        }

        int usercnt = questionDisableUserRoMapper.selectUserCnt(answerBo.getUserId());

        if(usercnt > 0){
            //该用户已被禁言
            throw new ServiceException(6373);
        }

        //更新问题回复信息
        QuestionAnswer answer = new QuestionAnswer();
        try {
            JSONObject jsonStu = JSONObject.fromObject(answerBo);
            LOGGER.info("更新问题回复信息:{}", jsonStu.toString());
            answerBo.setLastUpdate(new Date());
            answerBo.setStatus("0");

            //敏感词校验
            String answerTxt = answerBo.getAnswer();
            List<String> wordList = sensitiveWordsRoMapper.selectListWords();
            if(answerTxt != null && !"".equals(answerTxt)){
                for(String word : wordList){
                    boolean bl = answerTxt.contains(word);
                    if(bl){
                        answerBo.setStatus("1");
                        break;
                    }
                }
            }

            BeanUtils.copyProperties(answerBo, answer);
            answerMapper.updateByPrimaryKeySelective(answer);
        } catch (Exception e) {
            LOGGER.error("更新问题回复信息异常：{}", e);
            throw new ServiceException(6113);
        }
        return answerBo;
    }

    @Override
    public String updateStatus(String coursewareId,String status) {
        //更新问题回复信息
        try {

        } catch (Exception e) {
            LOGGER.error("更新问题回复信息异常：{}", e);
            throw new ServiceException(6113);
        }
        return "";
    }

    @Override
    public String delete(String id) {
        int num = 0;
        try {
            LOGGER.info("删除问题回复信息:{}", id);

            QuestionAnswerBo answer = answerRoMapper.selectByPrimaryKey(id);

            int answerNum = answerRoMapper.selectAnswerCnt(answer.getQuestionId());
            Question question = new Question();
            question.setId(answer.getQuestionId());
            question.setAnswerNum(answerNum-1);
            questionMapper.updateByPrimaryKeySelective(question);
            num = answerNum;

            answerMapper.deleteByPrimaryKey(id);



        } catch (Exception e) {
            LOGGER.error("删除问题回复异常：{}", e);
            throw new ServiceException(6114);
        }
        return num+"";
    }

    @Override
    public String updateIsRead(String id) {
        //更新问题回复信息
        try {
            answerMapper.updateIsRead(id);
        } catch (Exception e) {
            LOGGER.error("更新问题回复信息异常：{}", e);
            throw new ServiceException(6113);
        }
        return "";
    }

    @Override
    public String updateIsAccept(String questionId,String id) {
        int cnt = answerRoMapper.selectAcceptCnt(questionId);
        if(cnt >0){
            //该问题已有采纳的回复，请勿重复采纳
            throw new ServiceException(6190);
        }
        //设置为采纳
        try {
            answerMapper.updateIsAccept(id);
        } catch (Exception e) {
            LOGGER.error("更新问题回复信息异常：{}", e);
            throw new ServiceException(6113);
        }
        return "";
    }

}
