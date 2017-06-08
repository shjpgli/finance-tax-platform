package com.abc12366.uc.service.impl;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.mapper.db1.GoodsMapper;
import com.abc12366.uc.mapper.db1.ProductMapper;
import com.abc12366.uc.mapper.db2.GoodsRoMapper;
import com.abc12366.uc.mapper.db2.OrderLogRoMapper;
import com.abc12366.uc.mapper.db2.ProductRoMapper;
import com.abc12366.uc.model.Goods;
import com.abc12366.uc.model.Order;
import com.abc12366.uc.model.OrderLog;
import com.abc12366.uc.model.Product;
import com.abc12366.uc.model.bo.GoodsBO;
import com.abc12366.uc.model.bo.GoodsCheckBO;
import com.abc12366.uc.model.bo.ProductBO;
import com.abc12366.uc.service.GoodsService;
import com.abc12366.uc.service.OrderLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @since 1.0.0
 */
@Service
public class OrderLogServiceImpl implements OrderLogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderLogServiceImpl.class);

    @Autowired
    private OrderLogRoMapper orderLogRoMapper;

    @Override
    public List<OrderLog> selectList(OrderLog orderLog) {
        return orderLogRoMapper.selectList(orderLog);
    }
}
