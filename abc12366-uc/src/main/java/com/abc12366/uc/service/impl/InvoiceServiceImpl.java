package com.abc12366.uc.service.impl;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.mapper.db1.InvoiceMapper;
import com.abc12366.uc.mapper.db1.OrderInvoiceMapper;
import com.abc12366.uc.mapper.db2.InvoiceRoMapper;
import com.abc12366.uc.mapper.db2.OrderInvoiceRoMapper;
import com.abc12366.uc.model.Invoice;
import com.abc12366.uc.model.OrderInvoice;
import com.abc12366.uc.model.bo.InvoiceBO;
import com.abc12366.uc.service.InvoiceService;
import com.abc12366.uc.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @create 2017-05-15 10:17 AM
 * @since 1.0.0
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRoMapper invoiceRoMapper;

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Autowired
    private OrderInvoiceMapper orderInvoiceMapper;

    @Autowired
    private OrderInvoiceRoMapper orderInvoiceRoMapper;

    @Override
    public List<InvoiceBO> selectList(InvoiceBO invoice) {
        return invoiceRoMapper.selectList(invoice);
    }

    /**
     * 查询单个订单
     *
     * @param id
     * @return
     */
    @Override
    public InvoiceBO selectOne(String id) {
        Invoice invoice = invoiceRoMapper.selectById(id);
        InvoiceBO invoiceBO = new InvoiceBO();
        BeanUtils.copyProperties(invoice, invoiceBO);
        return invoiceBO;
    }


    /**
     * 修改购物车
     *
     * @param invoiceBO
     * @return
     */
    @Transactional("db1TxManager")
    @Override
    public InvoiceBO updateInvoice(InvoiceBO invoiceBO) {
        Invoice invoice = new Invoice();
        BeanUtils.copyProperties(invoiceBO, invoice);
        Date date = new Date();
        invoice.setLastUpdate(date);
        invoiceMapper.update(invoice);

        String id = invoice.getId();

        List<OrderInvoice> orderInvoiceList = orderInvoiceRoMapper.selectByInvoiceId(id);
        if (orderInvoiceList != null && (!orderInvoiceList.isEmpty())) {
            for (OrderInvoice orderInvoice : orderInvoiceList) {
                orderInvoiceMapper.delete(orderInvoice.getId());
            }
        }

        String[] orderIds = invoiceBO.getOrderIds().split(",");
        OrderInvoice orderInvoice = new OrderInvoice();
        for (String orderId : orderIds) {
            orderInvoice.setId(Utils.uuid());
            orderInvoice.setInvoiceId(id);
            orderInvoice.setOrderId(orderId);
            orderInvoice.setLastUpdate(date);
            orderInvoiceMapper.insert(orderInvoice);
        }
        InvoiceBO temp = new InvoiceBO();
        BeanUtils.copyProperties(invoice, temp);
        return temp;
    }

    /**
     * 根据订单ID和用户ID删除购物车
     *
     * @param invoiceBO
     * @return
     */
    @Override
    public int deleteByIdAndUserId(InvoiceBO invoiceBO) {
        Invoice invoice = new Invoice();
        BeanUtils.copyProperties(invoiceBO, invoice);
        int del = invoiceMapper.deleteByIdAndUserId(invoice);
        if (del != 1) {
            throw new ServiceException(4103);
        }
        return del;
    }

    /**
     * 提交订单
     *
     * @param invoiceBO
     * @return
     */
    @Transactional("db1TxManager")
    @Override
    public InvoiceBO addInvoice(InvoiceBO invoiceBO) {
        Invoice invoice = new Invoice();
        BeanUtils.copyProperties(invoiceBO, invoice);
        invoice.setId(Utils.uuid());
        //TODO 获取发票编号
        invoice.setInvoiceId(StringUtils.getInvoiceIdString());
        invoice.setOrderNum(StringUtils.getUserOrderString());
        Date date = new Date();
        invoice.setCreateTime(date);
        invoice.setLastUpdate(date);
        invoiceMapper.insert(invoice);

        String id = invoice.getId();
        String[] orderIds = invoiceBO.getOrderIds().split(",");
        OrderInvoice orderInvoice = new OrderInvoice();

        for (String orderId : orderIds) {
            orderInvoice.setId(Utils.uuid());
            orderInvoice.setInvoiceId(id);
            orderInvoice.setOrderId(orderId);
            orderInvoice.setCreateTime(date);
            orderInvoice.setLastUpdate(date);
            orderInvoiceMapper.insert(orderInvoice);
        }
        InvoiceBO temp = new InvoiceBO();
        BeanUtils.copyProperties(invoice, temp);
        return temp;
    }

    public Invoice selectById(String id) {
        return invoiceRoMapper.selectById(id);
    }

}
