package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.QuestionFactionLevelMapper;
import com.abc12366.bangbang.mapper.db2.QuestionFactionLevelRoMapper;
import com.abc12366.bangbang.model.question.QuestionFactionLevel;
import com.abc12366.bangbang.service.QuestionFactionLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author liuQi
 * @Date 2017/9/22 14:49
 */
@Service
public class QuestionFactionLevelServiceImpl implements QuestionFactionLevelService {

    @Autowired
    private QuestionFactionLevelMapper questionFactionLevelMapper;

    @Autowired
    private QuestionFactionLevelRoMapper questionFactionLevelRoMapper;

    @Override
    public List<QuestionFactionLevel> selectList() {
        return questionFactionLevelRoMapper.selectList();
    }

    @Override
    public void add(QuestionFactionLevel record) {
        questionFactionLevelMapper.insert(record);
    }

    @Override
    public void modify(QuestionFactionLevel record) {
        questionFactionLevelMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public void delete(String id) {
        questionFactionLevelMapper.deleteByPrimaryKey(id);
    }
}
