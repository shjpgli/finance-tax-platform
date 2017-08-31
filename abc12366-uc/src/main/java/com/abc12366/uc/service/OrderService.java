package com.abc12366.uc.service;

import com.abc12366.uc.model.Order;
import com.abc12366.uc.model.OrderBack;
import com.abc12366.uc.model.bo.*;

import java.util.List;

/**
 * Created by MY on 2017-05-15.
 */
public interface OrderService {

    /**
     * 查询订单列表信息
     */
    List<OrderBO> selectList(OrderBO orderBO, int pageNum, int pageSize);

    /**
     * 查询订单信息
     */
    OrderBO selectByOrderNo(String orderNo);

    /**
     * 加入购物车
     */
    OrderBO joinCart(OrderBO orderBO);

    /**
     * 修改购物车
     */
    OrderBO updateCart(OrderBO orderBO);

    /**
     * 查询列表
     */
    List<OrderBO> selectOrderList(OrderBO order, int pageNum, int pageSize);

    /**
     * 删除购物车
     */
    void deleteCart(OrderBO orderBO);

    /**
     * 用户直接提交订单
     */
    OrderBO submitOrder(OrderBO orderBO);

    /**
     * 修改用户等级服务
     */
    VipLogBO updateVipLevel(String orderNo);

    /**
     * 查询购物车列表
     */
    List<OrderBO> selectCartList(OrderBO order);

    /**
     * 购物车中提交订单
     */
    void submitCart(Order order);

    /**
     * 删除订单
     */
    void deleteOrder(OrderBO orderBO);

    /**
     * 订单反馈
     */
    OrderBO feedback(OrderBO orderBO);

    /**
     * 取消订单
     */
    OrderBO cancelOrder(OrderCancelBO orderCancelBO);

    /**
     * 前台用户，查询所有订单
     */
    List<OrderBO> selectUserAllOrderList(OrderBO order, int pageNum, int pageSize);

    /**
     * 申请退单
     */
    OrderBack applyBackOrder(OrderBack orderBack);

    /**
     * 提交退单申请
     */
    OrderBack submitBackOrder(OrderBack orderBack);

    /**
     * 管理员同意退单
     */
    OrderBack backCheckOrder(OrderBack orderBack);

    /**
     * 查询退单列表
     */
    List<OrderBackBO> selectOrderBackList(OrderBackBO orderBackBO);

    /**
     * 修改支付状态
     */
    OrderBO paymentOrder(OrderPayBO orderPayBO, String goodsType);

    /**
     * 查询导出订单
     */
    List<OrderListBO> selectExprotOrder(Order order);

    /**
     * 查询导入订单
     */
    void selectImprotOrder(List<OrderBO> orderBOList);

    /**
     * 订单发货
     */
    void sendOrder(OrderOperationBO orderOperationBO);

    /**
     * 订单作废
     */
    void invalidOrder(OrderOperationBO orderOperationBO);

    /**
     * 自动确认收货
     */
    void automaticReceipt();

    /**
     * 自动取消订单
     */
    void automaticCancel();

    OrderBO selectOrderByGoodsIdAndUserId(Order order);

    /**
     * 用户修改订单
     */
    OrderUpdateBO updateOrder(OrderUpdateBO orderUpdateBO);

}
