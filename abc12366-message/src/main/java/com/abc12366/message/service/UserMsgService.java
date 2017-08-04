package com.abc12366.message.service;

import com.abc12366.gateway.model.BodyStatus;
import com.abc12366.message.model.UserMessage;

import java.util.List;

/**
 * 用户消息服务
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-07-27 11:20 AM
 * @since 1.0.0
 */
public interface UserMsgService {

    List<UserMessage> selectList(UserMessage data, int page, int size);

    UserMessage insert(UserMessage data);

    UserMessage update(UserMessage data);

    UserMessage selectOne(String id);

    BodyStatus delete(UserMessage data);
}
