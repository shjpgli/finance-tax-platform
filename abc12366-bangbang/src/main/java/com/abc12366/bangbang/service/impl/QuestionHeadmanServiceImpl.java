package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.QuestionHeadmanMapper;
import com.abc12366.bangbang.mapper.db2.QuestionHeadmanRoMapper;
import com.abc12366.bangbang.model.question.QuestionHeadman;
import com.abc12366.bangbang.model.question.bo.QuestionHeadmanBo;
import com.abc12366.bangbang.service.QuestionHeadmanService;
import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
    private QuestionHeadmanRoMapper questionHeadmanRoMapper;

    @Override
    public List<QuestionHeadman> selectList() {
        return questionHeadmanRoMapper.selectList();
    }

    @Override
    public QuestionHeadmanBo selectByPrimaryKey(String id) {
        return questionHeadmanRoMapper.selectByPrimaryKey(id);
    }

    @Transactional("db1TxManager")
    @Override
    public void add(QuestionHeadmanBo headmanBo) {
        QuestionHeadman headman = new QuestionHeadman();
        BeanUtils.copyProperties(headmanBo, headman);
        headman.setId(Utils.uuid());
        headman.setUserId(Utils.getUserId());
        headman.setStatus("apply");
        headman.setCreateTime(new Date());
        int cnt = questionHeadmanRoMapper.selectExist(headman.getUserId());
        if(cnt >0){
            //已申请过掌门人，请勿重复申请
            throw new ServiceException(6192);
        }

        questionHeadmanMapper.insert(headman);
    }

    @Transactional("db1TxManager")
    @Override
    public void modify(QuestionHeadmanBo headmanBo) {
        QuestionHeadman headman = new QuestionHeadman();
        BeanUtils.copyProperties(headmanBo, headman);
        questionHeadmanMapper.updateByPrimaryKeySelective(headman);
    }

    @Transactional("db1TxManager")
    @Override
    public void delete(String id) {
        questionHeadmanMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void changeStatus(String id, String status) {
        QuestionHeadman headman = new QuestionHeadman();
        headman.setId(id);
        headman.setStatus(status);
        questionHeadmanMapper.updateByPrimaryKeySelective(headman);
    }
}
