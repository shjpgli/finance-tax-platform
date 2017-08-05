package com.abc12366.uc.service;

import com.abc12366.uc.model.bo.MyTaskBO;
import com.abc12366.uc.model.bo.UserTaskBO;
import com.abc12366.uc.model.bo.UserTaskInsertBO;
import com.abc12366.uc.model.bo.UserTaskUpdateBO;

import java.util.Map;

/**
 * Admin: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-24
 * Time: 11:41
 */
public interface UserTaskService {
    UserTaskBO insert(UserTaskInsertBO userTaskInsertBO, String userId);

    UserTaskBO update(UserTaskUpdateBO userTaskUpdateBO, String userId, String id);

    boolean delete(Map<String, String> map);

    MyTaskBO selectMyTask(String userId);
}
