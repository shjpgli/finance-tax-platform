package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.QuestionMedalMapper;
import com.abc12366.bangbang.mapper.db2.QuestionMedalRoMapper;
import com.abc12366.bangbang.model.question.QuestionMedal;
import com.abc12366.bangbang.model.question.bo.QuestionMedalBo;
import com.abc12366.bangbang.service.QuestionMedalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author liuQi
 * @Date 2017/10/20 18:06
 */
@Service
public class QuestionMedalServiceImpl implements QuestionMedalService {

    @Autowired
    private QuestionMedalMapper questionMedalMapper;

    @Autowired
    private QuestionMedalRoMapper questionMedalRoMapper;

    @Override
    public List<QuestionMedalBo> selectList() {
        return questionMedalRoMapper.selectList(null);
    }

    @Override
    public QuestionMedalBo selectOne(String id) {
        return questionMedalRoMapper.selectByPrimaryKey(id);
    }

    @Override
    public void add(QuestionMedal medal) {
        questionMedalMapper.insert(medal);
    }

    @Override
    public void modify(QuestionMedal medal) {
        questionMedalMapper.updateByPrimaryKeySelective(medal);
    }

    @Override
    public void delete(String id) {
        questionMedalMapper.deleteByPrimaryKey(id);
    }
}
