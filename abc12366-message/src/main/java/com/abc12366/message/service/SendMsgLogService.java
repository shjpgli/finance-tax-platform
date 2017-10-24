package com.abc12366.message.service;

import com.abc12366.message.model.MessageSendLog;

import java.util.List;
import java.util.Map;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-10-24
 * Time: 11:18
 */
public interface SendMsgLogService {
    MessageSendLog insert(MessageSendLog sendLog);

    List<MessageSendLog> selectList(Map<String, Object> map);
}
