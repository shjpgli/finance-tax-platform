package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.Order;
import com.abc12366.uc.model.bo.OrderBO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * OrderMapper数据库操作接口类
 **/

public interface OrderRoMapper {


    /**
     * 查询（根据主键ID查询）
     **/
    Order selectByPrimaryKey(@Param("id") String id);

    List<OrderBO> selectList(OrderBO order);

    OrderBO selectById(String orderNo);

    List<OrderBO> selectOrderList(OrderBO order);

    OrderBO selectOrder(Order order);

    List<OrderBO> selectCartList(OrderBO order);

    List<OrderBO> selectWaitList(OrderBO order);

    List<OrderBO> selectExprotOrder(Order order);

    List<Order> selectReceiptOrderByDate(@Param("date")Date date);

    List<Order> selectCancelOrderByDate(@Param("date")Date date);
}