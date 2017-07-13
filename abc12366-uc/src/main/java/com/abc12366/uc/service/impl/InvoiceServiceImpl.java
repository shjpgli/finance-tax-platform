package com.abc12366.uc.service.impl;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.mapper.db1.*;
import com.abc12366.uc.mapper.db2.*;
import com.abc12366.uc.model.*;
import com.abc12366.uc.model.bo.InvoiceBO;
import com.abc12366.uc.model.bo.InvoiceBackBO;
import com.abc12366.uc.model.bo.InvoiceExcel;
import com.abc12366.uc.service.InvoiceService;
import com.abc12366.uc.util.DataUtils;
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
 * @create 2017-05-15 10:17 AM
 * @since 1.0.0
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    @Autowired
    private InvoiceRoMapper invoiceRoMapper;

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderInvoiceMapper orderInvoiceMapper;

    @Autowired
    private OrderInvoiceRoMapper orderInvoiceRoMapper;

    @Autowired
    private InvoiceDetailRoMapper invoiceDetailRoMapper;

    @Autowired
    private InvoiceBackMapper invoiceBackPapper;

    @Autowired
    private InvoiceBackRoMapper invoiceBackRoMapper;


    @Autowired
    private ExpressMapper expressMapper;

    @Autowired
    private ExpressRoMapper expressRoMapper;


    @Autowired
    private UserAddressRoMapper userAddressRoMapper;

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
            orderInvoice.setOrderNo(orderId);
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
    @Transactional("db1TxManager")
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
        //获取发票编号，0，表示未使用
        InvoiceRepo invoiceRepo = invoiceDetailRoMapper.selectInvoiceRepo("0");
        if (invoiceRepo == null) {
            LOGGER.info("{发票号码获取失败}", invoiceRepo);
            throw new ServiceException(4124);
        }
        //invoice.setInvoiceNo(invoiceRepo.getInvoiceNo());
        invoice.setInvoiceCode(invoiceRepo.getInvoiceCode());
        Date date = new Date();
        invoice.setCreateTime(date);
        invoice.setLastUpdate(date);
        invoice.setUserOrderNo(DataUtils.getUserOrderString());
        invoiceMapper.insert(invoice);

        String id = invoice.getId();
        String[] orderIds = invoiceBO.getOrderIds().split(",");
        OrderInvoice orderInvoice = new OrderInvoice();

        for (String orderId : orderIds) {
            orderInvoice.setId(Utils.uuid());
            orderInvoice.setInvoiceId(id);
            orderInvoice.setOrderNo(orderId);
            orderInvoice.setCreateTime(date);
            orderInvoice.setLastUpdate(date);
            orderInvoiceMapper.insert(orderInvoice);
        }
        InvoiceBO temp = new InvoiceBO();
        BeanUtils.copyProperties(invoice, temp);
        return temp;
    }

    @Transactional("db1TxManager")
    @Override
    public List<InvoiceExcel> selectInvoiceExcelList(InvoiceBO invoice) {
        List<InvoiceBO> invoiceBOList = invoiceRoMapper.selectList(invoice);
        List<InvoiceExcel> excelList = new ArrayList<InvoiceExcel>();
        Express express = null;
        if (invoiceBOList != null) {
            InvoiceExcel excel = null;
            for (InvoiceBO bo : invoiceBOList) {
                excel = new InvoiceExcel();
                String userOrderNo = invoice.getUserOrderNo();
                List<Express> exList = expressRoMapper.selectbyUserOrderNo(userOrderNo);
                //获取联系人
                UserAddress userAddress = userAddressRoMapper.selectByPrimaryKey(invoice.getAddressId());
                if (userAddress == null) {
                    LOGGER.info("{地址信息获取失败}", express);
                    throw new ServiceException(4143);
                }
                StringBuffer address = new StringBuffer();
                address.append(userAddress.getProvince() + "-");
                address.append(userAddress.getCity() + "-");
                address.append(userAddress.getArea() + "-");
                address.append(userAddress.getDetail());
                //查询该快递单信息是否已经生成
                if (exList == null) {
                    //导出之前，生成订单表数据，及订单表与快递关系表数据
                    express = new Express();
                    express.setUserOrderNo(userOrderNo);
                    express.setUserId(invoice.getUserId());
                    express.setCreateTime(new Date());

                    int insertExpress = expressMapper.insert(express);
                    if (insertExpress != 1) {
                        LOGGER.info("{生成订单表数据失败}", express);
                        throw new ServiceException(4142);
                    }
                    excel.setUserOrderNo(userOrderNo);
                    excel.setReceivingCompany(invoice.getCompName());
                    excel.setAddress(address.toString());
                    excel.setPhone(invoice.getPhone());
                    excel.setCargoContent(invoice.getInvoiceNo());
                    excel.setCargoNum(1);
                } else {
                    StringBuffer content = new StringBuffer();
                    int count = 0;
                    for (Express ex : exList) {
                        if(count == 0){
                            content.append(ex.getExpressNo());
                        }else{
                            content.append(","+ex.getExpressNo());
                        }
                        count ++;
                    }
                    excel.setUserOrderNo(userOrderNo);
                    excel.setReceivingCompany(invoice.getCompName());
                    excel.setAddress(address.toString());
                    excel.setPhone(invoice.getPhone());
                    excel.setCargoContent(content.toString());
                    excel.setCargoNum(count);
                }
                excelList.add(excel);
            }
        }
        return excelList;
    }

    @Transactional("db1TxManager")
    @Override
    public InvoiceBackBO refund(InvoiceBackBO invoiceBackBO) {
        InvoiceBack invoiceBack = new InvoiceBack();
        BeanUtils.copyProperties(invoiceBackBO,invoiceBack);
        invoiceBack.setId(Utils.uuid());
        Date date = new Date();
        invoiceBack.setCreateTime(date);
        invoiceBack.setLastUpdate(date);
        int insert = invoiceBackPapper.insert(invoiceBack);
        if (insert != 1){
            LOGGER.info("{新增失败}", invoiceBack);
            throw new ServiceException(4101);
        }
        InvoiceBackBO bo = new InvoiceBackBO();
        BeanUtils.copyProperties(invoiceBack, bo);
        return bo;
    }

    @Transactional("db1TxManager")
    @Override
    public InvoiceBackBO refundCheck(InvoiceBackBO invoiceBackBO) {
        Express express = expressRoMapper.selectByPrimaryKey(invoiceBackBO.getExpressId());
        if(express == null){
            LOGGER.info("{发票快递单信息查询错误}", invoiceBackBO);
            throw new ServiceException(4145);
        }
        Invoice invoice = invoiceRoMapper.selectByUserOrderNo(express.getUserOrderNo());
        if(invoice == null){
            LOGGER.info("{发票信息查询错误}", invoiceBackBO);
            throw new ServiceException(4146);
        }else{
            //修改发票状态
            //TODO 需要确定状态值
            invoice.setStatus("0");
            int iUpdate =  invoiceMapper.update(invoice);
            if(iUpdate != 1){
                LOGGER.info("{发票信息修改错误}", invoice);
                throw new ServiceException(4147);
            }

        }
        List<OrderInvoice> orderInvoiceList = orderInvoiceRoMapper.selectByInvoiceId(invoice.getId());
        Order order = null;
        for (OrderInvoice orderInvoice:orderInvoiceList){
            order = new Order();
            order.setOrderNo(orderInvoice.getOrderNo());
            order.setOrderStatus("3");
            order.setLastUpdate(new Date());
            int oUpdate = orderMapper.update(order);
            if(oUpdate != 1){
                LOGGER.info("{订单信息修改错误}", order);
                throw new ServiceException(4148);
            }
        }
        InvoiceBack invoiceBack = new InvoiceBack();
        BeanUtils.copyProperties(invoiceBackBO,invoiceBack);
        invoiceBack.setLastUpdate(new Date());
        //TODO 需要确定状态值
        invoiceBack.setStatus("0");
        int bUpdate = invoiceBackPapper.update(invoiceBack);
        if (bUpdate != 1){
            LOGGER.info("{发票退订信息修改错误}", order);
            throw new ServiceException(4149);
        }
        InvoiceBackBO bo = new InvoiceBackBO();
        BeanUtils.copyProperties(invoice,bo);
        return bo;
    }

    public Invoice selectById(String id) {
        return invoiceRoMapper.selectById(id);
    }

}
