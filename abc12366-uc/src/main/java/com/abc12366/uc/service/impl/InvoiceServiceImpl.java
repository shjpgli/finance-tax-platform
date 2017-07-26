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
    private InvoiceDetailMapper invoiceDetailMapper;

    @Autowired
    private InvoiceBackMapper invoiceBackMapper;

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
        return invoiceRoMapper.selectById(id);
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

        String[] orderIds = invoiceBO.getOrderNos().split(",");
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
//        InvoiceDetail invoiceDetail = invoiceDetailRoMapper.selectInvoiceRepo("0");
//        if (invoiceDetail == null) {
//            LOGGER.info("{发票号码获取失败}", invoiceDetail);
//            throw new ServiceException(4124);
//        }else {
//            //将发票置为分配中，值为1
//            invoiceDetail.setStatus("1");
//            int dUpdate = invoiceDetailMapper.update(invoiceDetail);
//            if(dUpdate != 1){
//                LOGGER.info("{发票状态修改失败}", invoiceDetail);
//                throw new ServiceException(4178);
//            }
//        }
//        invoice.setInvoiceNo(invoiceDetail.getInvoiceNo());
//        invoice.setInvoiceCode(invoiceDetail.getInvoiceCode());
        Date date = new Date();
        invoice.setCreateTime(date);
        invoice.setLastUpdate(date);
        invoice.setUserOrderNo(DataUtils.getUserOrderString());
        invoiceMapper.insert(invoice);

        String id = invoice.getId();
        String[] orderNos = invoiceBO.getOrderNos().split(",");
        OrderInvoice orderInvoice = new OrderInvoice();

        for (String orderNo : orderNos) {
            orderInvoice.setId(Utils.uuid());
            orderInvoice.setInvoiceId(id);
            orderInvoice.setOrderNo(orderNo);
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
        invoiceBackBO.setId(Utils.uuid());
        Date date = new Date();
        invoiceBackBO.setCreateTime(date);
        invoiceBackBO.setLastUpdate(date);
        //状态1，待审批
        invoiceBackBO.setStatus("1");
        InvoiceBack invoiceBack = new InvoiceBack();
        BeanUtils.copyProperties(invoiceBackBO, invoiceBack);
        int insert = invoiceBackMapper.insert(invoiceBack);
        if (insert != 1){
            LOGGER.info("{新增失败}", invoiceBack);
            throw new ServiceException(4101);
        }
        return invoiceBackBO;
    }

    @Transactional("db1TxManager")
    @Override
    public InvoiceBackBO refundCheck(InvoiceBack invoiceBack) {
        Express express = expressRoMapper.selectByPrimaryKey(invoiceBack.getExpressId());
        if(express == null){
            LOGGER.info("{发票快递单信息查询错误}", invoiceBack);
            throw new ServiceException(4145);
        }
        Invoice invoice = invoiceRoMapper.selectByUserOrderNo(express.getUserOrderNo());
        if(invoice == null){
            LOGGER.info("{发票信息查询错误}", invoiceBack);
            throw new ServiceException(4146);
        }else{
            //修改发票状态
            int iUpdate =  invoiceMapper.update(invoice);
            if(iUpdate != 1){
                LOGGER.info("{发票信息修改错误}", invoice);
                throw new ServiceException(4147);
            }

        }
        //退票状态=4：已收货时，修改订单状态
        if(invoiceBack.getStatus() != null && "4".equals(invoiceBack.getStatus())){

            List<OrderInvoice> orderInvoiceList = orderInvoiceRoMapper.selectByInvoiceId(invoice.getId());
            Order order = null;
            for (OrderInvoice orderInvoice:orderInvoiceList){
                order = new Order();
                order.setOrderNo(orderInvoice.getOrderNo());
                order.setIsInvoice(false);
                order.setLastUpdate(new Date());
                int oUpdate = orderMapper.update(order);
                if(oUpdate != 1){
                    LOGGER.info("{订单信息修改错误}", order);
                    throw new ServiceException(4148);
                }
            }
        }
        invoiceBack.setLastUpdate(new Date());
        int bUpdate = invoiceBackMapper.update(invoiceBack);
        if (bUpdate != 1){
            LOGGER.info("{发票退订信息修改错误}", invoiceBack);
            throw new ServiceException(4149);
        }
        InvoiceBackBO bo = new InvoiceBackBO();
        BeanUtils.copyProperties(invoice,bo);
        return bo;
    }

    @Override
    public List<InvoiceBackBO> selectBOList(InvoiceBackBO invoiceBackBO) {
        return invoiceBackRoMapper.selectBOList(invoiceBackBO);
    }

    @Override
    public InvoiceBackBO selectBackOne(String id) {
        return invoiceBackRoMapper.selectBackOne(id);
    }

    @Override
    public InvoiceBO selectUserInvoice(Invoice invoice) {
        return invoiceRoMapper.selectUserInvoice(invoice);
    }
}
