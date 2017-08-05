package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.OrderProduct;
import com.abc12366.uc.model.bo.OrderProductBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * OrderProductMapper数据库操作接口类
 **/

public interface OrderProductRoMapper {


    /**
     * 查询（根据主键ID查询）
     **/
    OrderProduct selectByPrimaryKey(@Param("id") String id);


    OrderProduct selectByProductId(@Param("productId") String productId);

    List<OrderProductBO> selectByOrderNo(OrderProductBO pBO);
}