package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.QuestionHeadmanClassifyRelMapper;
import com.abc12366.bangbang.mapper.db1.QuestionHeadmanMapper;
import com.abc12366.bangbang.model.question.QuestionHeadman;
import com.abc12366.bangbang.model.question.QuestionHeadmanClassifyRel;
import com.abc12366.bangbang.model.question.bo.QuestionHeadmanBo;
import com.abc12366.bangbang.service.QuestionHeadmanService;
import com.abc12366.gateway.util.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author liuQi
 * @Date 2017/9/22 18:05
 */
@Service
public class QuestionHeadmanServiceImpl implements QuestionHeadmanService {

    @Autowired
    private QuestionHeadmanMapper questionHeadmanMapper;

    @Autowired
    private QuestionHeadmanClassifyRelMapper questionHeadmanClassifyRelMapper;

    @Override
    public List<QuestionHeadman> selectList() {
        return questionHeadmanMapper.selectList();
    }

    @Transactional("db1TxManager")
    @Override
    public void add(QuestionHeadmanBo headmanBo) {
        QuestionHeadman headman = new QuestionHeadman();
        BeanUtils.copyProperties(headmanBo, headman);
        headman.setId(Utils.uuid());
        headman.setUserId(Utils.getUserId());
        questionHeadmanMapper.insert(headman);
        List<QuestionHeadmanClassifyRel> relList = new ArrayList<>();
        for (String classifyId:headmanBo.getClassifyIds()){
            QuestionHeadmanClassifyRel rel = new QuestionHeadmanClassifyRel();
            rel.setId(Utils.uuid());
            rel.setHeadmanId(headman.getId());
            rel.setClassifyId(classifyId);
        }
        questionHeadmanClassifyRelMapper.insertBatch(relList);
    }

    @Transactional("db1TxManager")
    @Override
    public void modify(QuestionHeadmanBo headmanBo) {
        QuestionHeadman headman = new QuestionHeadman();
        BeanUtils.copyProperties(headmanBo, headman);
        questionHeadmanMapper.updateByPrimaryKeySelective(headman);
        questionHeadmanClassifyRelMapper.deleteByHeadmanId(headman.getId());
        List<QuestionHeadmanClassifyRel> relList = new ArrayList<>();
        for (String classifyId:headmanBo.getClassifyIds()){
            QuestionHeadmanClassifyRel rel = new QuestionHeadmanClassifyRel();
            rel.setId(Utils.uuid());
            rel.setHeadmanId(headman.getId());
            rel.setClassifyId(classifyId);
        }
        questionHeadmanClassifyRelMapper.insertBatch(relList);
    }

    @Transactional("db1TxManager")
    @Override
    public void delete(String id) {
        questionHeadmanMapper.deleteByPrimaryKey(id);
        questionHeadmanClassifyRelMapper.deleteByHeadmanId(id);
    }

    @Override
    public void changeStatus(String id, String status) {
        QuestionHeadman headman = new QuestionHeadman();
        headman.setId(id);
        headman.setStatus(status);
        questionHeadmanMapper.updateByPrimaryKeySelective(headman);
    }
}
