package com.abc12366.message.mapper.db1;

import com.abc12366.message.model.BusinessMessage;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-07-28 5:04 PM
 * @since 1.0.0
 */
public interface BusinessMsgMapper {
    void insert(BusinessMessage data);

    void update(BusinessMessage data);

    void batchInsert(List<BusinessMessage> dataList);
}
