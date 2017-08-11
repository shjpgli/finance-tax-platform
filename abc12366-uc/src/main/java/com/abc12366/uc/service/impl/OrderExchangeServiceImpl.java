package com.abc12366.uc.service.impl;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.mapper.db1.OrderExchangeMapper;
import com.abc12366.uc.mapper.db1.OrderLogMapper;
import com.abc12366.uc.mapper.db2.DictRoMapper;
import com.abc12366.uc.mapper.db2.OrderExchangeRoMapper;
import com.abc12366.uc.model.Dict;
import com.abc12366.uc.model.OrderExchange;
import com.abc12366.uc.model.OrderLog;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.service.OrderExchangeService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-08-10 10:27 AM
 * @since 1.0.0
 */
@Service
public class OrderExchangeServiceImpl implements OrderExchangeService {

    @Autowired
    private OrderExchangeMapper orderExchangeMapper;

    @Autowired
    private OrderExchangeRoMapper orderExchangeRoMapper;

    @Autowired
    private OrderLogMapper orderLogMapper;

    @Autowired
    private DictRoMapper dictRoMapper;

    @Transactional("db1TxManager")
    @Override
    public OrderExchange insert(ExchangeApplicationBO ra) {
        // todo 1.实物商品才能换货
        OrderExchange data = new OrderExchange();
        BeanUtils.copyProperties(ra, data);
        if (data != null) {
            List<OrderExchange> dataList = selectUndoneList(data.getOrderNo());
            if (dataList.size() == 0){
                data.setId(Utils.uuid());
                Timestamp now = new Timestamp(new Date().getTime());
                data.setCreateTime(now);
                data.setLastUpdate(now);
                data.setStatus("1");
                orderExchangeMapper.insert(data);

                // 插入订单日志
                insertLog(data.getOrderNo(), "1", Utils.getUserId(), data.getUserRemark());
//                insertLog(data.getOrderNo(), "1", Utils.getAdminId(), data.getUserRemark());
            } else {
                throw new ServiceException(4950);
            }
        }
        return data;
    }

    @Override
    public OrderExchange update(ExchangeApplicationBO data) {
        OrderExchange oe = selectOne(data.getId());
        if (oe != null) {
            List<OrderExchange> dataList = selectUndoneList(data.getOrderNo());
            if (dataList.size() == 0){
                oe.setReason(data.getReason());
                oe.setUserRemark(data.getUserRemark());
                oe.setType(data.getType());
                oe.setLastUpdate(new Timestamp(new Date().getTime()));
                oe.setStatus("1");
                orderExchangeMapper.update(oe);

                // 插入订单日志
                insertLog(data.getOrderNo(), "1", Utils.getUserId(), data.getUserRemark());
//                insertLog(oe.getOrderNo(), "1", Utils.getAdminId(), oe.getUserRemark());
            } else {
                throw new ServiceException(4950);
            }
        }
        return oe;
    }

    @Override
    public List<SfExportBO> export(OrderExchangeExportBO data) {
        return orderExchangeRoMapper.export(data);
    }

    @Override
    public void importJson(List<SfImportBO> dataList) {
        if (dataList.size() > 0) {
            for (SfImportBO data : dataList) {
                OrderExchange oe = new OrderExchange.Builder()
                        .orderNo(data.getOrderNo())
                        .toExpressNo(data.getExpressNo())
                        .status("3")
                        .build();
                orderExchangeMapper.update(oe);
                // 插入订单日志
                insertLog(oe.getOrderNo(), "3", Utils.getAdminId(), oe.getAdminRemark());
            }
        }
    }

    @Override
    public List<OrderExchange> selectList(OrderExchange oe, int page, int size) {
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        return orderExchangeRoMapper.selectList(oe);
    }

    @Transactional("db1TxManager")
    @Override
    public OrderExchange disagree(ExchangeAdminBO data) {
        OrderExchange oe = selectOne(data.getId());

        if (oe != null) {
            Timestamp now = new Timestamp(new Date().getTime());
            oe.setAdminRemark(data.getAdminRemark());
            oe.setLastUpdate(now);
            oe.setStatus("5");
            orderExchangeMapper.update(oe);

            // 插入订单日志
            insertLog(oe.getOrderNo(), "5", Utils.getAdminId(), oe.getAdminRemark());
        }
        return oe;
    }

    @Override
    public OrderExchange selectOne(String id) {
        return orderExchangeRoMapper.selectOne(id);
    }

    @Transactional("db1TxManager")
    @Override
    public OrderExchange agree(ExchangeAdminBO data) {
        OrderExchange oe = selectOne(data.getId());

        if (oe != null) {
            Timestamp now = new Timestamp(new Date().getTime());
            oe.setAdminRemark(data.getAdminRemark());
            oe.setLastUpdate(now);
            oe.setStatus("2");
            orderExchangeMapper.update(oe);

            // 插入订单日志
            insertLog(oe.getOrderNo(), "2", Utils.getAdminId(), oe.getAdminRemark());
        }
        return oe;
    }

    @Override
    public OrderExchange confirm(ExchangeConfirmBO data) {
        OrderExchange oe = selectOne(data.getId());

        if (oe != null) {
            Timestamp now = new Timestamp(new Date().getTime());
            oe.setExpressNo(data.getExpressNo());
            oe.setExpressComp(data.getExpressNo());
            oe.setAdminRemark(data.getAdminRemark());
            oe.setLastUpdate(now);
            oe.setStatus("6");
            orderExchangeMapper.update(oe);

            // 插入订单日志
            insertLog(oe.getOrderNo(), "6", Utils.getAdminId(), oe.getAdminRemark());
        }
        return oe;
    }

    private List<OrderExchange> selectUndoneList(String orderNo) {
        return orderExchangeRoMapper.selectUndoneList(orderNo);
    }

    private String selectFieldValue(String value) {
        Dict dict = new Dict();
        dict.setDictId("exchange_status");
        dict.setFieldValue(value);
        dict = dictRoMapper.selectOne(dict);
        return dict != null ? dict.getFieldKey() : "";
    }

    private void insertLog(String orderNo, String status, String userId, String remark) {
        // 插入订单日志
        OrderLog ol = new OrderLog.Builder()
                .id(Utils.uuid())
                .orderNo(orderNo)
                .action(selectFieldValue(status))
                .createTime(new Timestamp(new Date().getTime()))
                .createUser(userId)
                .remark(remark)
                .build();
        orderLogMapper.insert(ol);
    }
}
