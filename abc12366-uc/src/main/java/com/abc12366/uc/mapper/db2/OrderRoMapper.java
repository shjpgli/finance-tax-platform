package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.bo.OrderStatBO;
import com.abc12366.uc.model.order.Order;
import com.abc12366.uc.model.order.bo.OrderBO;
import com.abc12366.uc.model.order.bo.OrderTradeBO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * OrderMapper数据库操作接口类
 **/

public interface OrderRoMapper {


    /**
     * 查询（根据主键ID查询）
     **/
    Order selectByPrimaryKey(@Param("orderNo") String orderNo);

    Order selectOne(Order order);

    List<OrderBO> selectList(OrderBO order);

    OrderBO selectById(String orderNo);

    List<OrderBO> selectOrderList(OrderBO order);

    OrderBO selectOrder(Order order);

    List<OrderBO> selectCartList(OrderBO order);

    List<OrderBO> selectWaitList(OrderBO order);

    List<OrderBO> selectExprotOrder(Order order);

    List<Order> selectReceiptOrderByDate(@Param("date")Date date);

    List<Order> selectCancelOrderByDate(@Param("date")Date date);

    List<OrderBO> selectByOrderNos(@Param("orderNos") String[] orderNos);

    OrderBO selectOrderByGoodsIdAndUserId(Map<String, Object> order);

    List<OrderBO> selectUserAllOrderList(OrderBO order);

    List<OrderBO> selectCurriculumOrderList(OrderBO orderBO);

    /**
     * 状态为[付款成功]订单数量
     *
     * @return Integer
     */
    Integer selectTodoListCount();

    /**
     * 统计3-付款中，4-付款成功，6-订单完成，7-订单结束，9-已退款，订单总数的数量
     *
     * @return OrderStatBO
     */
    OrderStatBO orderStat();

    OrderBO selectByOrderNoAdmin(String orderNo);

    /**
     * 根据交易流水号查询订单信息
     * @param tradeNo
     * @return
     */
    List<OrderBO> selectByTradeNo(String tradeNo);

    /**
     * 根据交易流水号查询订单合并信息
     * @param tradeNo
     * @return
     */
    OrderTradeBO selectOrderTrade(String tradeNo);
}