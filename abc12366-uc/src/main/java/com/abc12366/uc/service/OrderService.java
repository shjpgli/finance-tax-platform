package com.abc12366.uc.service;

import com.abc12366.uc.model.Order;
import com.abc12366.uc.model.bo.OrderBO;

import java.util.List;

/**
 * Created by MY on 2017-05-15.
 */
public interface OrderService {

    List<OrderBO> selectList(OrderBO product);

    OrderBO selectOne(String id);

    OrderBO joinCart(OrderBO productBO);

    OrderBO updateCart(OrderBO productBO);

    List<OrderBO> selectOrderList(OrderBO order);

    int deleteByIdAndUserId(OrderBO orderBO);

    OrderBO submitOrder(OrderBO orderBO);

    List<OrderBO> selectCartList(OrderBO order);
}
