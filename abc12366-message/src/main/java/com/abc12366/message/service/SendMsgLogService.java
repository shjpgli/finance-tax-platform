package com.abc12366.message.service;

import com.abc12366.message.model.MessageSendLog;

import java.util.List;
import java.util.Map;

/**
 * 短信日志
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-10-24
 * Time: 11:18
 */
public interface SendMsgLogService {
    /**
     * 新增短信日志
     * @param sendLog
     * @return
     */
    MessageSendLog insert(MessageSendLog sendLog);

    /**
     * 查询短信日志列表
     * @param map
     * @return
     */
    List<MessageSendLog> selectList(Map<String, Object> map);
}
