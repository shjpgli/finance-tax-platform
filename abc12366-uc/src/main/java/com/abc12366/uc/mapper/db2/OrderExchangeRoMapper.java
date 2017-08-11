package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.OrderExchange;
import com.abc12366.uc.model.bo.OrderExchangeExportBO;
import com.abc12366.uc.model.bo.SfExportBO;

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

    List<SfExportBO> export(OrderExchangeExportBO data);
}
