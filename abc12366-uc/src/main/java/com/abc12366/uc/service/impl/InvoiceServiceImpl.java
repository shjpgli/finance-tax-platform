package com.abc12366.uc.service.impl;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.mapper.db1.*;
import com.abc12366.uc.mapper.db2.*;
import com.abc12366.uc.model.*;
import com.abc12366.uc.model.bo.InvoiceBO;
import com.abc12366.uc.model.bo.InvoiceBackBO;
import com.abc12366.uc.model.bo.InvoiceExcel;
import com.abc12366.uc.model.invoice.InvoiceDetail;
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

    @Autowired
    private InvoiceLogRoMapper invoiceLogRoMapper;

    @Autowired
    private InvoiceLogMapper invoiceLogMapper;

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

        String[] orderNos = invoiceBO.getOrderNos().split(",");
        OrderInvoice orderInvoice = new OrderInvoice();
        for (String orderId : orderNos) {
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
     * 索要发票
     *
     * @param invoiceBO
     * @return
     */
    @Transactional("db1TxManager")
    @Override
    public InvoiceBO addInvoice(InvoiceBO invoiceBO) {

        String invoiceId = Utils.uuid();
        invoiceBO.setId(invoiceId);
        //电子发票直接把发票信息插入
        if (invoiceBO.getProperty() != null && "2".equals(invoiceBO.getProperty())) {
            //获取发票编号，0，表示未使用
            InvoiceDetail invoiceDetail = invoiceDetailRoMapper.selectInvoiceRepo("0");
            if (invoiceDetail == null) {
                LOGGER.info("发票号码获取失败}", invoiceDetail);
                throw new ServiceException(4124);
            } else {
                //将发票置为已使用值为2
                invoiceDetail.setStatus("2");
                int dUpdate = invoiceDetailMapper.update(invoiceDetail);
                if (dUpdate != 1) {
                    LOGGER.info("发票状态修改失败}", invoiceDetail);
                    throw new ServiceException(4178);
                }
            }
            invoiceBO.setInvoiceNo(invoiceDetail.getInvoiceNo());
            invoiceBO.setInvoiceCode(invoiceDetail.getInvoiceCode());
        }
        Date date = new Date();
        invoiceBO.setCreateTime(date);
        invoiceBO.setLastUpdate(date);
        invoiceBO.setUserOrderNo(DataUtils.getUserOrderString());
        Invoice invoice = new Invoice();
        BeanUtils.copyProperties(invoiceBO, invoice);
        int ins = invoiceMapper.insert(invoice);

        if (ins != 1) {
            LOGGER.info("新增失败：{}", invoice);
            throw new ServiceException(4101);
        }
        String id = invoice.getId();
        String[] orderNos = invoiceBO.getOrderNos().split(",");
        OrderInvoice orderInvoice = new OrderInvoice();

        for (String orderNo : orderNos) {
            orderInvoice.setId(Utils.uuid());
            orderInvoice.setInvoiceId(id);
            orderInvoice.setOrderNo(orderNo);
            orderInvoice.setCreateTime(date);
            orderInvoice.setLastUpdate(date);
            int oInsert = orderInvoiceMapper.insert(orderInvoice);
            if (oInsert != 1) {
                LOGGER.info("新增失败：{}", invoice);
                throw new ServiceException(4101);
            }
            //修改订单是否已开发票状态
            Order order = new Order();
            order.setIsInvoice(true);
            order.setUserId(invoiceBO.getUserId());
            order.setOrderNo(orderNo);
            int oUpd = orderMapper.update(order);
            if (oUpd != 1) {
                LOGGER.info("修改失败：{}", order);
                throw new ServiceException(4102);
            }
        }
        insertInvoiceLog(invoiceId, invoiceBO.getUserId(), "索要发票");

        return invoiceBO;
    }

    private void insertInvoiceLog(String invoiceId, String userId, String opertion) {
        //加入发票操作日志
        InvoiceLog invoiceLog = new InvoiceLog();
        invoiceLog.setId(Utils.uuid());
        invoiceLog.setCreateTime(new Date());
        invoiceLog.setAction(opertion);
        invoiceLog.setInvoiceId(invoiceId);
        invoiceLog.setCreateUser(userId);
        int logInsert = invoiceLogMapper.insert(invoiceLog);
        if (logInsert != 1) {
            LOGGER.info("新增发票操作日志失败：{}", invoiceLog);
            throw new ServiceException(4185);
        }
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
                    LOGGER.info("地址信息获取失败：{}", express);
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
                        LOGGER.info("生成订单表数据失败：{}", express);
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
                        if (count == 0) {
                            content.append(ex.getExpressNo());
                        } else {
                            content.append("," + ex.getExpressNo());
                        }
                        count++;
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
        if (insert != 1) {
            LOGGER.info("新增失败：{}", invoiceBack);
            throw new ServiceException(4101);
        }

        insertInvoiceLog(invoiceBackBO.getInvoiceId(), invoiceBackBO.getUserId(), "发票退票");
        return invoiceBackBO;
    }

    @Transactional("db1TxManager")
    @Override
    public InvoiceBackBO refundCheck(InvoiceBack invoiceBack) {
        Express express = expressRoMapper.selectByPrimaryKey(invoiceBack.getExpressId());
        if (express == null) {
            LOGGER.info("发票快递单信息查询错误：{}", invoiceBack);
            throw new ServiceException(4145);
        }
        Invoice invoice = invoiceRoMapper.selectByUserOrderNo(express.getUserOrderNo());
        if (invoice == null) {
            LOGGER.info("发票信息查询错误：{}", invoiceBack);
            throw new ServiceException(4146);
        } else {
            //修改发票状态
            int iUpdate = invoiceMapper.update(invoice);
            if (iUpdate != 1) {
                LOGGER.info("发票信息修改错误：{}", invoice);
                throw new ServiceException(4147);
            }

        }
        //退票状态=4：已收货时，修改订单状态
        if (invoiceBack.getStatus() != null && "4".equals(invoiceBack.getStatus())) {

            List<OrderInvoice> orderInvoiceList = orderInvoiceRoMapper.selectByInvoiceId(invoice.getId());
            Order order = null;
            for (OrderInvoice orderInvoice : orderInvoiceList) {
                order = new Order();
                order.setOrderNo(orderInvoice.getOrderNo());
                order.setIsInvoice(false);
                order.setLastUpdate(new Date());
                int oUpdate = orderMapper.update(order);
                if (oUpdate != 1) {
                    LOGGER.info("订单信息修改错误：{}", order);
                    throw new ServiceException(4148);
                }
            }
            //删除订单和发票对应关系
            orderInvoiceMapper.deleteByInvoiceId(invoice.getId());

        }
        invoiceBack.setLastUpdate(new Date());
        int bUpdate = invoiceBackMapper.update(invoiceBack);
        if (bUpdate != 1) {
            LOGGER.info("发票退订信息修改错误：{}", invoiceBack);
            throw new ServiceException(4149);
        }
        InvoiceBackBO bo = new InvoiceBackBO();
        BeanUtils.copyProperties(invoice, bo);
        insertInvoiceLog(invoiceBack.getInvoiceId(), invoiceBack.getUserId(), "发票审核");
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

    @Transactional("db1TxManager")
    @Override
    public InvoiceBO billing(InvoiceBO invoiceBO) {
        if ("2".equals(invoiceBO.getStatus())) {
            InvoiceDetail tail = invoiceBO.getInvoiceDetail();
            InvoiceDetail detail = invoiceDetailRoMapper.selectByPrimaryKey(tail.getId());
            if (detail == null) {
                LOGGER.info("发票详情信息不能为空：{}", detail);
                throw new ServiceException(4186);
            }
            detail.setStatus("2");
            int dUpdate = invoiceDetailMapper.update(detail);
            if (dUpdate != 1) {
                LOGGER.info("发票详情信息修改失败：{}", detail);
                throw new ServiceException(4187);
            }
        } else if ("3".equals(invoiceBO.getStatus())) {
            List<OrderInvoice> orderInvoiceList = orderInvoiceRoMapper.selectByInvoiceId(invoiceBO.getId());
            Order order = null;
            //修改订单是否已开发票状态
            for (OrderInvoice orderInvoice : orderInvoiceList) {
                order = new Order();
                order.setOrderNo(orderInvoice.getOrderNo());
                order.setIsInvoice(false);
                order.setLastUpdate(new Date());
                int oUpdate = orderMapper.update(order);
                if (oUpdate != 1) {
                    LOGGER.info("订单信息修改错误：{}", order);
                    throw new ServiceException(4148);
                }
            }
            //删除订单和发票对应关系
            orderInvoiceMapper.deleteByInvoiceId(invoiceBO.getId());
        }
        invoiceBO.setLastUpdate(new Date());
        Invoice invoice = new Invoice();
        BeanUtils.copyProperties(invoiceBO, invoice);
        int update = invoiceMapper.update(invoice);
        if (update != 1) {
            LOGGER.info("修改失败：{}", invoice);
            throw new ServiceException(4102);
        }
        //加入发票日志
        insertInvoiceLog(invoiceBO.getId(), invoiceBO.getInvoiceLog().getCreateUser(), invoiceBO.getInvoiceDetail().getRemark());
        return invoiceBO;
    }
}
