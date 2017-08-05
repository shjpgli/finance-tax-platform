package com.abc12366.message.service;

import com.abc12366.gateway.model.BodyStatus;
import com.abc12366.message.model.BusinessMessage;

import java.util.List;

/**
 * 用户消息服务
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-07-27 11:20 AM
 * @since 1.0.0
 */
public interface BusinessMsgService {

    List<BusinessMessage> selectList(BusinessMessage data, int page, int size);

    BusinessMessage insert(BusinessMessage data);

    BusinessMessage update(BusinessMessage data);

    BusinessMessage selectOne(String id);

    BodyStatus delete(BusinessMessage data);
}
