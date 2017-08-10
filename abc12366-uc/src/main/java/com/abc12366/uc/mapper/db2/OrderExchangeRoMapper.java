package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.OrderExchange;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-08-10 11:02 AM
 * @since 1.0.0
 */
public interface OrderExchangeRoMapper {
    List<OrderExchange> selectList(OrderExchange oe);

    List<OrderExchange> selectUndoneList(String orderNo);

    OrderExchange selectOne(String id);
}
