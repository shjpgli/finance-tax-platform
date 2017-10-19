package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.QuestionDisableUserMapper;
import com.abc12366.bangbang.mapper.db2.QuestionDisableUserRoMapper;
import com.abc12366.bangbang.model.question.QuestionDisableUser;
import com.abc12366.bangbang.model.question.bo.QuestionDisableUserBo;
import com.abc12366.bangbang.service.QuestionDisableUserService;
import com.abc12366.gateway.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author liuQi
 * @Date 2017/10/18 15:50
 */
@Service
public class QuestionDisableUserServiceImpl implements QuestionDisableUserService{

    @Autowired
    private QuestionDisableUserMapper questionDisableUserMapper;

    @Autowired
    private QuestionDisableUserRoMapper questionDisableUserRoMapper;

    @Override
    public List<QuestionDisableUserBo> selectList(Map map) {
        return questionDisableUserRoMapper.selectList(map);
    }

    @Transactional("db1TxManager")
    @Override
    public void disable(QuestionDisableUser record) {
        questionDisableUserMapper.deleteByPrimaryKey(record.getUserId());
        record.setUpdateAdmin(Utils.getAdminId());
        questionDisableUserMapper.insert(record);
    }

    @Override
    public void enable(String userId) {
        questionDisableUserMapper.deleteByPrimaryKey(userId);
    }
}
