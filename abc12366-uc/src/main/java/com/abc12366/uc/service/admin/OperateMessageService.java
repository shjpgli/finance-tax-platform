package com.abc12366.uc.service.admin;

import com.abc12366.uc.model.User;
import com.abc12366.uc.model.UserExtend;
import com.abc12366.uc.model.admin.bo.OperateMessageBO;

import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-12-18
 * Time: 11:03
 */
public interface OperateMessageService {
    OperateMessageBO insert(OperateMessageBO operateMessageBO);

    List<OperateMessageBO> selectList(String status,String name,String createTime,int page, int size);

    OperateMessageBO update(OperateMessageBO operateMessageBO);

    void send(String userId);

    void sendYyxx(OperateMessageBO o, User user);

    boolean tagIdContains(List<String> tagIdList, String tagIds);

    void sendPart(OperateMessageBO o, User user, UserExtend userExtend, List<String> tagIdList);

    OperateMessageBO selectOne(String id);

    void delete(String id);

    OperateMessageBO reuse(String id);
}
