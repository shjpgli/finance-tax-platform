package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.QuestionClassifyMapper;
import com.abc12366.bangbang.mapper.db2.QuestionClassifyRoMapper;
import com.abc12366.bangbang.mapper.db2.QuestionRoMapper;
import com.abc12366.bangbang.model.question.QuestionClassify;
import com.abc12366.bangbang.model.question.bo.QuestionClassifyBo;
import com.abc12366.bangbang.service.QueClassifyService;
import com.abc12366.gateway.exception.ServiceException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Created by xieyanmao on 2017/9/14.
 */
@Service
public class QueClassifyServiceImpl implements QueClassifyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueClassifyServiceImpl.class);

    @Autowired
    private QuestionClassifyMapper classifyMapper;

    @Autowired
    private QuestionClassifyRoMapper classifyRoMapper;

    @Autowired
    private QuestionRoMapper QuestionRoMapper;

    @Override
    public List<QuestionClassifyBo> selectList(Map<String,Object> map) {
        List<QuestionClassifyBo> classifyBoList;
        try {
            //查询课程分类列表
            classifyBoList = classifyRoMapper.selectList(map);

        } catch (Exception e) {
            LOGGER.error("查询课程分类信息异常：{}", e);
            throw new ServiceException(4300);
        }
        return classifyBoList;
    }

    @Override
    public QuestionClassifyBo save(QuestionClassifyBo classifyBo) {
        try {
            JSONObject jsonStu = JSONObject.fromObject(classifyBo);
            LOGGER.info("新增课程分类信息:{}", jsonStu.toString());
            //保存课程分类信息
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String code = "";

            String classifyCode = "";

            String parentCode = classifyBo.getParentCode();

            for(int i=0;i<20;i++){
                code = this.genCodes(6);
                classifyCode = parentCode + code;
                int cnt = classifyRoMapper.selectClassifyCnt(classifyCode);
                if(cnt ==0){
                    break;
                }
            }

            QuestionClassify classify = new QuestionClassify();
            classifyBo.setClassifyId(uuid);
            BeanUtils.copyProperties(classifyBo, classify);
            classifyMapper.insert(classify);
        } catch (Exception e) {
            LOGGER.error("新增课程分类信息异常：{}", e);
            throw new ServiceException(4302);
        }

        return classifyBo;
    }

    @Override
    public QuestionClassifyBo selectClassify(String classifyCode) {
        QuestionClassifyBo classifyBo = new QuestionClassifyBo();
        try {
            LOGGER.info("查询单个课程分类信息:{}", classifyCode);
            //查询课程分类信息
            QuestionClassify classify = classifyRoMapper.selectByPrimaryKey(classifyCode);
            BeanUtils.copyProperties(classify, classifyBo);
        } catch (Exception e) {
            LOGGER.error("查询单个课程分类信息异常：{}", e);
            throw new ServiceException(4301);
        }
        return classifyBo;
    }

    @Override
    public QuestionClassifyBo update(QuestionClassifyBo classifyBo) {
        //更新课程分类信息
        QuestionClassify classify = new QuestionClassify();
        try {
            JSONObject jsonStu = JSONObject.fromObject(classifyBo);
            LOGGER.info("更新课程分类信息:{}", jsonStu.toString());
            BeanUtils.copyProperties(classifyBo, classify);
            classifyMapper.updateByPrimaryKeySelective(classify);
        } catch (Exception e) {
            LOGGER.error("更新课程分类信息异常：{}", e);
            throw new ServiceException(4303);
        }
        return classifyBo;
    }

    @Override
    public String updateStatus(String classifyId,String status) {
        //更新课程分类信息
        try {
//            LOGGER.info("更新课程状态信息:{}", ClassifyId+","+status);
//            Question Question = new Question();
//            Question.setQuestionId(QuestionId);
//            Question.setStatus(Integer.parseInt(status));
//            Question.setUpdateTime(new Date());
//            //1为发布
//            if("1".equals(status)){
//                Question.setIssueTime(new Date());
//            }else{
//                Question.setIssueTime(null);
//            }
//            ClassifyMapper.updateStatus(Question);
        } catch (Exception e) {
            LOGGER.error("更新课程分类信息异常：{}", e);
            throw new ServiceException(4303);
        }
        return "";
    }

    @Transactional("db1TxManager")
    @Override
    public String delete(String classifyCode) {
        LOGGER.info("删除课程分类信息:{}", classifyCode);
//        int cnt = QuestionRoMapper.selectCurrCntByClassify(classifyCode);
//        if(cnt > 0){
//            throw new ServiceException(4305);
//        }
        try {
            classifyMapper.deleteByPrimaryKey(classifyCode);
        } catch (Exception e) {
            LOGGER.error("删除课程分类异常：{}", e);
            throw new ServiceException(4304);
        }
        return "";
    }

    public String genCodes(int length){

        String val = "";
        Random random = new Random();
        for(int i = 0; i < length; i++)
        {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

            if("char".equalsIgnoreCase(charOrNum)) // 字符串
            {
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母
                val += (char) (choice + random.nextInt(26));
            }
            else if("num".equalsIgnoreCase(charOrNum)) // 数字
            {
                val += String.valueOf(random.nextInt(10));
            }
        }
        val=val.toLowerCase();

        return val;
    }

}
