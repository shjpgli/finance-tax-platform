package com.abc12366.bangbang.service;

import com.abc12366.bangbang.model.bo.SystemRecordBO;
import com.abc12366.bangbang.model.bo.SystemRecordInsertBO;


import java.util.List;
import java.util.Map;

/**
 * Admin: lingsuzhi <554600654@qq.com.com> Date: 2017-08-16
 */
public interface SystemRecordService {
    List<SystemRecordBO> selectList(Map map);

    SystemRecordBO selectOne(String id);

    SystemRecordBO insert(SystemRecordInsertBO SystemRecordInsertBO);
 
}
