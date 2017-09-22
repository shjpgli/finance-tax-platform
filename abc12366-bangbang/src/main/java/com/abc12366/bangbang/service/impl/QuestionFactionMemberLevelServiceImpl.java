package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.QuestionFactionMemberLevelMapper;
import com.abc12366.bangbang.model.question.QuestionFactionMemberLevel;
import com.abc12366.bangbang.service.QuestionFactionMemberLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author liuQi
 * @Date 2017/9/22 14:45
 */
@Service
public class QuestionFactionMemberLevelServiceImpl implements QuestionFactionMemberLevelService {

    @Autowired
    private QuestionFactionMemberLevelMapper questionFactionMemberLevelMapper;


    @Override
    public List<QuestionFactionMemberLevel> selectList() {
        return questionFactionMemberLevelMapper.selectList();
    }

    @Override
    public void add(QuestionFactionMemberLevel record) {
        questionFactionMemberLevelMapper.insert(record);
    }

    @Override
    public void modify(QuestionFactionMemberLevel record) {
        questionFactionMemberLevelMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public void delete(String id) {
        questionFactionMemberLevelMapper.deleteByPrimaryKey(id);
    }
}
