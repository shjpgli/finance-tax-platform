package com.abc12366.message.mapper.db2;

import com.abc12366.message.model.UserMessage;
import com.abc12366.message.model.bo.UserMessageForBangbang;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-07-27 2:57 PM
 * @since 1.0.0
 */
public interface UserMsgRoMapper {
    List<UserMessage> selectList(UserMessage data);

    UserMessage selectOne(String id);

    List<UserMessageForBangbang> UserMessageForBangbang(String userId);

    /**
     * 查询用户消息未读总数
     *
     * @param data 用户消息对象，userId,type
     * @return 未读消息数
     */
    int unreadCount(UserMessage data);
}
