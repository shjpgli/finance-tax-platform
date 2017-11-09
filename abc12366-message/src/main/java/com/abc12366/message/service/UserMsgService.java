package com.abc12366.message.service;

import com.abc12366.gateway.model.BodyStatus;
import com.abc12366.message.model.UserBatchMessage;
import com.abc12366.message.model.UserMessage;
import com.abc12366.message.model.bo.UserMessageForBangbang;

import java.util.List;
import java.util.Map;

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

    // 批量发送用户消息
    List<UserMessage> insert(UserBatchMessage data);

    //获取用户私信列表：帮帮用
    List<UserMessageForBangbang> selectListForBangbang(String userId, int page, int size);

    /**
     * 根据用户名查询发给这个用户的消息
     * @param map
     * @param page
     * @param size
     * @return
     */
    List<UserMessageForBangbang> selectListToUser(Map<String, String> map, int page, int size);

    /**
     * 根据用户名查询这个用户发出去的消息
     * @param map
     * @param page
     * @param size
     * @return
     */
    List<UserMessageForBangbang> selectListByUser(Map<String, String> map, int page, int size);
}
