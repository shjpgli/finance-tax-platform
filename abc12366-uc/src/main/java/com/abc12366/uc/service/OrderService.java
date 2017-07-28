package com.abc12366.uc.service;

import com.abc12366.uc.model.Order;
import com.abc12366.uc.model.bo.OrderBO;

import java.util.List;

/**
 * Created by MY on 2017-05-15.
 */
public interface OrderService {

    /**
     * 查询订单列表信息
     * @param orderBO
     * @param pageNum
     *@param pageSize @return
     */
    List<OrderBO> selectList(OrderBO orderBO, int pageNum, int pageSize);

    /**
     * 查询订单信息
     * @return
     */
    OrderBO selectByOrderNo(String orderNo);

    /**
     * 加入购物车
     * @param orderBO
     * @return
     */
    OrderBO joinCart(OrderBO orderBO);

    /**
     * 修改购物车
     * @param orderBO
     * @return
     */
    OrderBO updateCart(OrderBO orderBO);

    /**
     * 查询列表
     * @param order
     * @param pageNum
     *@param pageSize @return
     */
    List<OrderBO> selectOrderList(OrderBO order, int pageNum, int pageSize);

    /**
     * 删除购物车
     * @param orderBO
     */
    void deleteCart(OrderBO orderBO);

    /**
     * 用户直接提交订单
     * @param orderBO
     * @return
     */
    OrderBO submitOrder(OrderBO orderBO);

    /**
     * 查询购物车列表
     * @param order
     * @return
     */
    List<OrderBO> selectCartList(OrderBO order);

    /**
     * 购物车中提交订单
     * @param order
     */
    void submitCart(Order order);

    /**
     * 删除订单
     * @param orderBO
     */
    void deleteOrder(OrderBO orderBO);

    OrderBO feedback(OrderBO orderBO);

    OrderBO cancelOrder(Order order);

    List<OrderBO> selectUserAllOrderList(OrderBO order, int pageNum, int pageSize);
}
