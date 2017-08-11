package com.abc12366.uc.service;

import com.abc12366.uc.model.OrderExchange;
import com.abc12366.uc.model.bo.OrderExchangeExportBO;
import com.abc12366.uc.model.bo.SfExportBO;
import com.abc12366.uc.model.bo.SfImportBO;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-08-10 10:27 AM
 * @since 1.0.0
 */
public interface OrderExchangeService {
    OrderExchange insert(OrderExchange data);

    List<OrderExchange> selectList(OrderExchange oe, int pageNum, int pageSize);

    OrderExchange disagree(OrderExchange data);

    OrderExchange selectOne(String id);

    OrderExchange agree(OrderExchange data);

    OrderExchange confirm(OrderExchange data);

    OrderExchange update(OrderExchange data);

    List<SfExportBO> export(OrderExchangeExportBO data);

    void importJson(List<SfImportBO> dataList);
}
