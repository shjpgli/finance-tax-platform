package com.abc12366.message.mapper.db2;

import com.abc12366.message.model.BusinessMessage;
import com.abc12366.message.model.bo.BusinessMessageAdmin;

import java.util.List;
import java.util.Map;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-07-28 5:05 PM
 * @since 1.0.0
 */
public interface BusinessMsgRoMapper {
    List<BusinessMessage> selectList(BusinessMessage data);

    BusinessMessage selectOne(String id);

    List<BusinessMessageAdmin> selectListByUsername(Map<String, Object> map);

    /**
     * 查询业务消息未读数量
     *
     * @param bm type,busiType
     * @return 消息未读总数
     */
    int unreadCount(BusinessMessage bm);
}
