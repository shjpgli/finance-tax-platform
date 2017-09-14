package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.QuestionMapper;
import com.abc12366.bangbang.mapper.db1.QuestionTagMapper;
import com.abc12366.bangbang.mapper.db2.QuestionRoMapper;
import com.abc12366.bangbang.mapper.db2.QuestionTagRoMapper;
import com.abc12366.bangbang.model.question.Question;
import com.abc12366.bangbang.model.question.QuestionTag;
import com.abc12366.bangbang.model.question.bo.QuestionBo;
import com.abc12366.bangbang.service.QuestionService;
import com.abc12366.gateway.exception.ServiceException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by xieyanmao on 2017/9/14.
 */
@Service
public class QuestionServiceImpl implements QuestionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionServiceImpl.class);

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionRoMapper questionRoMapper;

    @Autowired
    private QuestionTagMapper tagMapper;

    @Autowired
    private QuestionTagRoMapper tagRoMapper;

    @Override
    public List<QuestionBo> selectList(Map<String,Object> map) {
        List<QuestionBo> questionBoList;
        try {
            //查询问题列表
            questionBoList = questionRoMapper.selectList(map);
        } catch (Exception e) {
            LOGGER.error("查询问题列表信息异常：{}", e);
            throw new ServiceException(6100);
        }
        return questionBoList;
    }

    @Transactional("db1TxManager")
    @Override
    public QuestionBo save(QuestionBo questionBo) {
        try {
            JSONObject jsonStu = JSONObject.fromObject(questionBo);
            LOGGER.info("新增问题信息:{}", jsonStu.toString());
            questionBo.setCreateTime(new Date());
            questionBo.setLastUpdate(new Date());
            //保存问题信息
            String uuid = UUID.randomUUID().toString().replace("-", "");
            Question question = new Question();
            questionBo.setId(uuid);
            BeanUtils.copyProperties(questionBo, question);


            List<QuestionTag> tagList = questionBo.getTagList();

            if(tagList != null){
                for(QuestionTag tag :tagList){
                    tag.setId(UUID.randomUUID().toString().replace("-", ""));
                    tag.setQuestionId(uuid);
                    tagMapper.insert(tag);
                }
            }


            questionMapper.insert(question);
        } catch (Exception e) {
            LOGGER.error("新增问题信息异常：{}", e);
            throw new ServiceException(6102);
        }

        return questionBo;
    }

    @Override
    public QuestionBo selectQuestion(String id) {
        QuestionBo questionBo = new QuestionBo();
        try {
            LOGGER.info("查询单个问题信息:{}", id);
            //查询问题信息
            Question question = questionRoMapper.selectByPrimaryKey(id);
            List<QuestionTag> tagList = tagRoMapper.selectList(id);
            BeanUtils.copyProperties(question, questionBo);
            questionBo.setTagList(tagList);
        } catch (Exception e) {
            LOGGER.error("查询单个问题信息异常：{}", e);
            throw new ServiceException(6101);
        }
        return questionBo;
    }

    @Transactional("db1TxManager")
    @Override
    public QuestionBo update(QuestionBo questionBo) {
        //更新问题信息
        Question question = new Question();
        try {
            JSONObject jsonStu = JSONObject.fromObject(questionBo);
            LOGGER.info("更新问题信息:{}", jsonStu.toString());
            questionBo.setLastUpdate(new Date());
            BeanUtils.copyProperties(questionBo, question);


            List<QuestionTag> tagList = questionBo.getTagList();

            tagMapper.deleteByPrimaryKey(questionBo.getId());

            if(tagList != null){
                for(QuestionTag tag :tagList){
                    tag.setId(UUID.randomUUID().toString().replace("-", ""));
                    tag.setQuestionId(questionBo.getId());
                    tagMapper.insert(tag);
                }
            }


            questionMapper.updateByPrimaryKeySelective(question);
        } catch (Exception e) {
            LOGGER.error("更新问题信息异常：{}", e);
            throw new ServiceException(6103);
        }
        return questionBo;
    }

    @Override
    public String updateStatus(String id,String status) {
        //更新课件信息
        try {

        } catch (Exception e) {
            LOGGER.error("更新课件信息异常：{}", e);
            throw new ServiceException(6103);
        }
        return "";
    }

    @Transactional("db1TxManager")
    @Override
    public String delete(String id) {
        try {
            LOGGER.info("删除问题信息:{}", id);
            tagMapper.deleteByPrimaryKey(id);
            questionMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            LOGGER.error("删除问题信息异常：{}", e);
            throw new ServiceException(6104);
        }
        return "";
    }

}
