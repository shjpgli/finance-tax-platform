package com.abc12366.uc.service.impl;

import com.abc12366.gateway.component.SpringCtxHolder;
import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.mapper.db1.*;
import com.abc12366.uc.mapper.db2.*;
import com.abc12366.uc.model.*;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.model.dzfp.DzfpGetReq;
import com.abc12366.uc.model.dzfp.Einvocie;
import com.abc12366.uc.model.dzfp.InvoiceXm;
import com.abc12366.uc.model.invoice.InvoiceDetail;
import com.abc12366.uc.service.InvoiceService;
import com.abc12366.uc.util.*;
import com.abc12366.uc.webservice.DzfpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @create 2017-05-15 10:17 AM
 * @since 1.0.0
 */
@Service("invoiceService")
public class InvoiceServiceImpl implements InvoiceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    @Autowired
    private InvoiceRoMapper invoiceRoMapper;

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderRoMapper orderRoMapper;


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
    private ExpressRoMapper expressRoMapper;

    @Autowired
    private InvoiceLogMapper invoiceLogMapper;

    @Autowired
    private OrderExchangeRoMapper orderExchangeRoMapper;

    @Autowired
    private ExpressCompRoMapper expressCompRoMapper;

    @Autowired
    private MessageSendUtil messageSendUtil;

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
     * 修改发票申请
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

        String[] orderNos = invoiceBO.getOrderNos();
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
     * 根据订单ID和用户ID删除发票订单
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

        String invoiceId = DataUtils.getInvoiceOrderString();
        invoiceBO.setId(invoiceId);
        Date date = new Date();
        invoiceBO.setCreateTime(date);
        invoiceBO.setLastUpdate(date);

        String[] orderNos = invoiceBO.getOrderNos();
        OrderInvoice orderInvoice = new OrderInvoice();
        Double amount = 0.0;
        for (String orderNo : orderNos) {
            orderInvoice.setId(Utils.uuid());
            orderInvoice.setInvoiceId(invoiceId);
            orderInvoice.setOrderNo(orderNo);
            orderInvoice.setCreateTime(date);
            orderInvoice.setLastUpdate(date);
            OrderInvoice oInvoice = orderInvoiceRoMapper.selectByOrderNo(orderNo);
            if(oInvoice != null){
                throw new ServiceException(4199,"已开发票");
            }
            OrderExchange orderExchange = orderExchangeRoMapper.selectByOrderNo(orderNo);
            if(orderExchange != null){
                LOGGER.info("订单在退换货中，不能开发票：{}", orderExchange);
                throw new ServiceException(4918);
            }

            int oInsert = orderInvoiceMapper.insert(orderInvoice);
            if (oInsert != 1) {
                LOGGER.info("新增失败：{}", orderInvoice);
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
            OrderBO orderBO = orderRoMapper.selectById(orderNo);
            if(orderBO != null){
                amount = amount + orderBO.getTotalPrice();
            }
        }
        invoiceBO.setAmount(amount);
        Invoice invoice = new Invoice();
        BeanUtils.copyProperties(invoiceBO, invoice);
        int ins = invoiceMapper.insert(invoice);

        if (ins != 1) {
            LOGGER.info("新增失败：{}", invoice);
            throw new ServiceException(4101);
        }
        insertInvoiceLog(invoiceId, invoiceBO.getUserId(), "索要发票");

        return invoiceBO;
    }

    @Transactional("db1TxManager")
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
    public List<InvoiceExcel> selectInvoicePrintExcelList(InvoiceBO invoice) {
        invoice.setStatus("2");
        List<InvoiceBO> invoiceBOList = invoiceRoMapper.selectList(invoice);
        List<InvoiceExcel> excelList = new ArrayList<InvoiceExcel>();
        Express express = null;
        if (invoiceBOList != null) {
            InvoiceExcel excel = null;
            for (InvoiceBO bo : invoiceBOList) {
                excel = new InvoiceExcel();
                //导出之前，生成订单表数据，及订单表与快递关系表数据
//                express = new Express();
//                express.setId(Utils.uuid());
//                express.setInvoiceOrderNo(bo.getId());
//                express.setUserId(bo.getUserId());
//                express.setCreateTime(new Date());
                //Express exp = expressRoMapper.selectbyInvoiceOrderNo(invoice.getId());
//                expressMapper.deleteByInvoiceOrderNo(bo.getId());
//                int insertExpress = expressMapper.insert(express);
//                if (insertExpress != 1) {
//                    LOGGER.info("生成订单表数据失败：{}", express);
//                    throw new ServiceException(4142);
//                }
                excel.setInvoiceOrderNo(bo.getId());
                excel.setNsrsbh(bo.getNsrsbh());
                if("1".equals(bo.getName())){
                    excel.setNsrmc("个人");
                }else{
                    excel.setNsrmc(bo.getNsrmc());
                }

                excel.setCreateTime(DateUtils.dateToString(bo.getCreateTime()));
                excel.setContent(bo.getContentDetail());
                excel.setNum(1);
                excel.setAmount(bo.getAmount());
                excel.setAmountBig(bo.getAmount());
                excel.setAmountSmall(bo.getAmount());
                excel.setDrawer(UserUtil.getAdminInfo().getNickname());
                List<OrderBO> orderBOList = bo.getOrderBOList();
                StringBuffer remark = new StringBuffer();
                for(OrderBO orderBO : orderBOList){
                    remark.append("订单号："+orderBO.getOrderNo());
                    remark.append("  ");
                    remark.append("支付方式：");
                    if("WEIXIN".equals(orderBO.getPayMethod())){
                        remark.append("微信");
                    }else if("ALIPAY".equals(orderBO.getPayMethod())){
                        remark.append("支付宝");
                    }else if("POINTS".equals(orderBO.getPayMethod())){
                        remark.append("积分");
                    }

                }
                excel.setRemark(remark.toString());
                excelList.add(excel);
            }
        }
        return excelList;
    }

    //拼接地址信息
    private UserAddressBO setUserAddress(InvoiceBO bo, StringBuffer address) {
        UserAddressBO userAddress = bo.getUserAddressBO();
        address.append(userAddress.getProvinceName() + "-");
        address.append(userAddress.getCityName() + "-");
        address.append(userAddress.getAreaName() + "-");
        address.append(userAddress.getDetail());
        return userAddress;
    }

    @Override
    public List<InvoiceExpressExcel> selectInvoiceExpressExcelList(InvoiceBO invoice) {
        //发票申请状态，1：待审批，2：已审批，3：已拒绝，4：已发货，5：已退票，6：已收货，7：待发货
        invoice.setStatus("7");
        List<InvoiceBO> invoiceBOList = invoiceRoMapper.selectList(invoice);
        List<InvoiceBO> invoiceDatas = invoiceRoMapper.selectList(invoice);
        List<InvoiceExpressExcel> excelList = new ArrayList<InvoiceExpressExcel>();
        Express express = null;
        if (invoiceBOList != null) {
            InvoiceExpressExcel excel = null;
            for (InvoiceBO bo : invoiceBOList) {
                excel = new InvoiceExpressExcel();
                //List<Express> exList = expressRoMapper.selectbyInvoiceOrderNo(invoice.getId());
                excel.setInvoiceOrderNo(bo.getId());
                //收货地址
                StringBuffer address = new StringBuffer();
                String phone = null;
                if(bo.getUserAddressBO() != null){
                    UserAddressBO userAddress = setUserAddress(bo, address);
                    phone = userAddress.getPhone();
                    excel.setPhone(phone);
                    excel.setAddress(address.toString());
                    excel.setLinkman(userAddress.getName());
                    excel.setPhone(userAddress.getPhone());
                }else {
                    LOGGER.info("收货人地址信息异常：{}", bo);
                    throw new ServiceException(4909);
                }
                boolean isAlike = false;
                StringBuffer invoiceNos = new StringBuffer();
                int num = 0;
                if(address != null && !"".equals(address) && phone != null && !"".equals(phone)) {
                    for (InvoiceBO data : invoiceDatas) {
                        StringBuffer addressBuffer;
                        String phoneTemp = null;
                        if (data.getUserAddressBO() != null) {
                            addressBuffer = new StringBuffer();
                            UserAddressBO userAddressBO = setUserAddress(data, addressBuffer);
                            phoneTemp = userAddressBO.getPhone();
                        }else {
                            LOGGER.info("收货人地址信息异常：{}", bo);
                            throw new ServiceException(4909);
                        }
                        //有地址和电话相同的
                        if (address.toString().equals(addressBuffer.toString()) && phone.equals(phoneTemp)) {
                            isAlike = true;
                            //寄托货物信息合并
                            invoiceNos.append(data.getInvoiceNo());
                            invoiceNos.append(";");
                            num++;
                        }
                    }
                    //没有地址和电话相同的
                    if (!isAlike) {
                        //寄托货物
                        invoiceNos.append(bo.getInvoiceNo());
                        invoiceNos.append(";");
                    }
                    excel.setCargoContent(invoiceNos.toString());
                    //寄托数量
                    excel.setCargoNum(num);
                }
                excel.setReceivingCompany(bo.getNsrmc());
                excelList.add(excel);
            }
        }
        return excelList;
    }

    @Transactional("db1TxManager")
    @Override
    public void insertInvoiceExpressExcelList(List<InvoiceExpressExcel> expressExcelList, String expressCompId) {
        for (InvoiceExpressExcel expressExcel:expressExcelList){
            Invoice invoice = new Invoice();
            invoice.setStatus("4");
            invoice.setId(expressExcel.getInvoiceOrderNo());
            if(expressExcel.getWaybillNum() != null && CharUtil.isChinese(expressExcel.getWaybillNum())){
                LOGGER.warn("运单号不能存在中文：{}",expressExcel);
                throw new ServiceException(4102,expressExcel.getWaybillNum() + "运单号不能存在中文");
            }
            invoice.setWaybillNum(expressExcel.getWaybillNum());
            invoice.setExpressCompId(expressCompId);
            Invoice ce = new Invoice();
            ce.setId(expressExcel.getInvoiceOrderNo());
            ce.setStatus("7");
            //查询发票信息表状态
            Invoice invoiceTemp = invoiceRoMapper.selectByInvoiceOrderNo(ce);
            if(invoiceTemp == null){
                LOGGER.info("发票不存在或发票已被使用：{}", expressExcel);
                throw new ServiceException(4913,"只有在已开票状态，该张发票才能被导入"+expressExcel.getInvoiceOrderNo());
            }

            int update = invoiceMapper.update(invoice);
            if(update != 1){
                LOGGER.info("修改失败：{}", invoice);
                throw new ServiceException(4102);
            }

            //发送消息
            ExpressComp expressComp = expressCompRoMapper.selectByPrimaryKey(expressCompId);
            if(expressComp == null){
                LOGGER.warn("物流公司查询失败：{}", invoiceTemp.getExpressCompId());
                throw new ServiceException(4102,"物流公司查询失败");
            }
            Message message = new Message();
            message.setBusinessId(invoiceTemp.getId());
            message.setType(MessageConstant.ZZFPDD);
            message.setContent(MessageConstant.IMPORT_COURIER_INFO+expressComp.getCompName()+"+"+invoiceTemp.getWaybillNum() +MessageConstant.SUFFIX);
            message.setUserId(invoiceTemp.getUserId());
            messageSendUtil.sendMessage(message);

        }
    }

    @Transactional("db1TxManager")
    @Override
    public void insertInvoicePrintExcelList(List<InvoiceExcel> invoiceList) {
        for(InvoiceExcel invoiceExcel:invoiceList){
            InvoiceDetail temp = new InvoiceDetail();
            temp.setInvoiceNo(invoiceExcel.getInvoiceNo());
            temp.setInvoiceCode(invoiceExcel.getInvoiceCode());
            InvoiceDetail invoiceDetail = invoiceDetailRoMapper.selectByInvoiceNoAndCode(temp);
            if(invoiceDetail == null){
                LOGGER.info("发票号码或发票代码不存在：{}", invoiceDetail);
                throw new ServiceException(4913,"发票号码或发票代码不存在");
            }
            if(!"0".equals(invoiceDetail.getStatus())){
                throw new ServiceException(4913,"发票号码："+invoiceExcel.getInvoiceNo()+"未出库或已使用");
            }

            Invoice ce = new Invoice();
            ce.setId(invoiceExcel.getInvoiceOrderNo());
            ce.setStatus("2");
            //查询发票信息表状态
            InvoiceBO invoiceTemp = invoiceRoMapper.selectInvoice(ce);
            if(invoiceTemp == null){
                LOGGER.info("只有在已审批状态，该张发票才能被导入：{}", invoiceDetail);
                throw new ServiceException(4913,"只有在已审批状态，该张发票才能被导入"+invoiceExcel.getInvoiceOrderNo());
            }

            Invoice invoice = new Invoice();
            invoice.setStatus("7");
            invoice.setId(invoiceExcel.getInvoiceOrderNo());
            invoice.setInvoiceNo(invoiceDetail.getInvoiceNo());
            invoice.setInvoiceCode(invoiceDetail.getInvoiceCode());
            int update = invoiceMapper.update(invoice);
            if(update != 1){
                LOGGER.info("修改失败：{}", invoice);
                throw new ServiceException(4102);
            }
            //修改发票详情表
            List<OrderBO> orderBOList = invoiceTemp.getOrderBOList();
            StringBuffer remark = new StringBuffer();
            for(OrderBO orderBO : orderBOList){
                remark.append(orderBO.getOrderNo());
                remark.append("  ");
            }
            invoiceDetail.setStatus("2");
            invoiceDetail.setRemark(remark.toString());
            int dUpdate = invoiceDetailMapper.update(invoiceDetail);
            if(dUpdate != 1){
                throw new ServiceException(4102,"修改发票详情失败");
            }
        }
    }

    @Override
    public void confirmInvoice(Invoice invoice) {
        Invoice data = invoiceRoMapper.selectByIdAndUserId(invoice);
        if(data != null && !"4".equals(data.getStatus())){
            LOGGER.info("发票订单只有在已经发货的情况下，才能确认收货：{}", invoice);
            throw new ServiceException(4102,"发票订单只有在已经发货的情况下，才能确认收货");
        }
        int update = invoiceMapper.update(invoice);
        if(update != 1){
            LOGGER.info("确认收货失败：{}", invoice);
            throw new ServiceException(4102,"确认收货失败");
        }
    }

    @Override
    public void automaticReceiptInvoice() {
        Date date = DataUtils.getAddDate(UCConstant.ORDER_RECEIPT_DAYS);
        //查询15天之前未确认的订单
        List<Invoice> orderList = invoiceRoMapper.selectReceiptInvoiceByDate(date);
        for(Invoice invoice:orderList){
            invoice.setStatus("5");
            invoiceMapper.update(invoice);
            insertInvoiceLog(invoice.getId(), invoice.getUserId(), "系统自动确认收货");

            //发送消息
            /*Message message = new Message();
            message.setBusinessId(invoice.getOrderNo());
            message.setType(MessageConstant.SPDD);
            message.setContent(MessageConstant.AUTOMATIC_CONFIRMATION_RECEIPT+"<a href=\""+MessageConstant.ABCUC_URL+"/orderDetail/"+invoice.getOrderNo()+"\">"+invoice.getOrderNo()+"</a>");
            message.setUserId(invoice.getUserId());
            messageSendUtil.sendMessage(message);*/
        }
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
        Invoice temp = new Invoice();
        temp.setId(express.getInvoiceOrderNo());
        Invoice invoice = invoiceRoMapper.selectByInvoiceOrderNo(temp);
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
            //修改订单和发票对应关系
            //orderInvoiceMapper.deleteByInvoiceId(invoice.getId());
            orderInvoiceMapper.updateByInvoiceId(invoice.getId());

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
    public void billing(InvoiceCheckBO invoiceCheckBO, HttpServletRequest request) {
        Invoice invoice = new Invoice();
        BeanUtils.copyProperties(invoiceCheckBO, invoice);
        String invoiceNo = null;
        String invoiceCode = null;
        //是否通过
        InvoiceBO invoiceBO = invoiceRoMapper.selectById(invoiceCheckBO.getId());
        if (invoiceCheckBO.getIsBilling()) {
            //1：纸质发票，2：电子发票；电子发票直接把发票信息插入
            if (invoiceBO.getProperty() != null && "2".equals(invoiceBO.getProperty())) {
                List<OrderBO> orderBOList = invoiceBO.getOrderBOList();
                List<InvoiceXm> invoiceXmList=new ArrayList<InvoiceXm>();
                InvoiceXm invoiceXm = new InvoiceXm();
                //发票信息填充
                DzfpGetReq dzfpGetReq=new DzfpGetReq();
                dzfpGetReq.setZsfs("0"); //
                dzfpGetReq.setKplx("0"); //开票0，退票1
                dzfpGetReq.setKpr(UserUtil.getAdminInfo().getNickname());
                invoiceXm.setXmmc(invoiceBO.getContentDetail());
                //商品编码
                invoiceXm.setSpbm("1010105000000000000");
                //价格
                invoiceXm.setTotalAmt(invoiceBO.getAmount());
                //数量
                invoiceXm.setXmsl(Double.valueOf(1));
                invoiceXm.setFphxz("0");
                invoiceXm.setYhzcbs("0");
                invoiceXmList.add(invoiceXm);
                if("1".equals(invoiceBO.getName())){
                    dzfpGetReq.setGmf_mc("个人");
                    //dzfpGetReq.setGmf_nsrsbh("110109500321655");
                }else if("2".equals(invoiceBO.getName())){
                    dzfpGetReq.setGmf_mc(invoiceBO.getNsrmc());
                    dzfpGetReq.setGmf_nsrsbh(invoiceBO.getNsrsbh());
                }
                dzfpGetReq.setInvoiceXms(invoiceXmList);
                Einvocie einvocie=null;
                try {
                    einvocie = (Einvocie) DzfpClient.doSender("DFXJ1001", dzfpGetReq.tosendXml(), Einvocie.class);
                } catch (Exception e) {
                    LOGGER.error("电子发票webservice调用异常,原因：",e);
                    throw new ServiceException(4908);
                }
                if(!"0000".equals(einvocie.getReturnCode())){
                    LOGGER.error("发票开票异常：{}", einvocie);
                    throw new ServiceException(4908,einvocie.getReturnMessage());
                }
                invoiceBO.setInvoiceNo(einvocie.getFP_HM());
                invoiceBO.setInvoiceCode(einvocie.getFP_DM());

                InvoiceDetail tail = new InvoiceDetail();
                tail.setInvoiceCode(einvocie.getFP_DM());
                tail.setInvoiceNo(einvocie.getFP_HM());
                tail.setSpUrl(einvocie.getSP_URL());
                tail.setPdfUrl(einvocie.getPDF_URL());
                //根据发票号码和发票代码查找发票详细信息表
                InvoiceDetail detail = invoiceDetailRoMapper.selectByInvoiceNoAndCode(tail);
                if (detail == null) {
                    LOGGER.info("发票仓库未存在该发票，请先入库：{}", detail);
                    throw new ServiceException(4186,"发票仓库未存在该发票，请先入库");
                }
                invoiceNo = detail.getInvoiceNo();
                invoiceCode = detail.getInvoiceCode();
                int dUpdate = invoiceDetailMapper.update(detail);
                if (dUpdate != 1) {
                    LOGGER.info("发票详情信息修改失败：{}", detail);
                    throw new ServiceException(4187);
                }
                invoice.setStatus("5");

                //发送消息
                Message message = new Message();
                message.setBusinessId(invoiceBO.getId());
                message.setType(MessageConstant.ZZFPDD);
                message.setContent(MessageConstant.ELECTRON_INVOICE_CHECK_ADOPT+"<a href=\""+ SpringCtxHolder.getProperty("abc12366.api.url.uc")+"/userinfo/invoice/"+invoiceBO.getId()+"\">"+invoiceBO.getId()+"</a>"+MessageConstant.SUFFIX);
                message.setUserId(invoiceBO.getUserId());
                messageSendUtil.sendMessage(message, request);

            }else {
                if(invoiceCheckBO.getDetailId() != null && !"".equals(invoiceCheckBO.getDetailId())){
                    InvoiceDetail invoiceDetail = invoiceDetailRoMapper.selectByPrimaryKey(invoiceCheckBO.getDetailId());
                    if(invoiceDetail == null){
                        LOGGER.info("发票详情信息不能为空：{}", invoiceDetail);
                        throw new ServiceException(4186);
                    }
                    invoiceDetail.setStatus("2");
                    int dUpdate = invoiceDetailMapper.update(invoiceDetail);
                    if (dUpdate != 1) {
                        LOGGER.info("发票详情信息修改失败：{}", invoiceDetail);
                        throw new ServiceException(4187);
                    }
                    invoiceNo = invoiceDetail.getInvoiceNo();
                    invoiceCode = invoiceDetail.getInvoiceCode();
                }
                invoice.setStatus("2");
                //发送消息
                Message message = new Message();
                message.setBusinessId(invoiceBO.getId());
                message.setType(MessageConstant.ZZFPDD);
                message.setContent(MessageConstant.INVOICE_CHECK_ADOPT);
                message.setUserId(invoiceBO.getUserId());
                messageSendUtil.sendMessage(message, request);
            }
        } else {
            //发票审核不通过
            checkInvoiceRefuse(invoiceCheckBO, request, invoice, invoiceBO);

        }
        invoice.setRemark(invoiceCheckBO.getRemark());
        invoice.setLastUpdate(new Date());
        invoice.setInvoiceNo(invoiceNo);
        invoice.setInvoiceCode(invoiceCode);
        invoice.setType(invoiceCheckBO.getType());
        int update = invoiceMapper.update(invoice);
        if (update != 1) {
            LOGGER.info("修改失败：{}", invoice);
            throw new ServiceException(4102);
        }
        //加入发票日志
        insertInvoiceLog(invoiceCheckBO.getId(), UserUtil.getAdminId(), invoiceCheckBO.getRemark());
    }

    /**
     * 纸质发票审核不通过
     */
    private void checkInvoiceRefuse(InvoiceCheckBO invoiceCheckBO, HttpServletRequest request,Invoice invoice, InvoiceBO invoiceBO) {
        List<OrderInvoice> orderInvoiceList = orderInvoiceRoMapper.selectByInvoiceId(invoiceCheckBO.getId());
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
        invoice.setStatus("3");
        //修改订单和发票对应关系
        orderInvoiceMapper.updateByInvoiceId(invoiceCheckBO.getId());
        //1：纸质发票，2：电子发票；
        if (invoiceBO.getProperty() != null && "2".equals(invoiceBO.getProperty())) {
            //发送消息
            Message message = new Message();
            message.setBusinessId(invoiceBO.getId());
            message.setType(MessageConstant.ZZFPDD);
            message.setContent(MessageConstant.INVOICE_CHECK_REFUSE+"<a href=\""+SpringCtxHolder.getProperty("abc12366.api.url.uc")+"/userinfo/invoice/"+invoiceBO.getId()+"\">"+invoiceBO.getId()+"</a>"+MessageConstant.SUFFIX);
            message.setUserId(invoiceBO.getUserId());
            messageSendUtil.sendMessage(message, request);
        }else{
            //发送消息
            Message message = new Message();
            message.setBusinessId(invoiceBO.getId());
            message.setType(MessageConstant.ZZFPDD);
            message.setContent(MessageConstant.ELECTRON_INVOICE_CHECK_REFUSE+"<a href=\""+SpringCtxHolder.getProperty("abc12366.api.url.uc")+"/userinfo/invoice/"+invoiceBO.getId()+"\">"+invoiceBO.getId()+"</a>"+MessageConstant.SUFFIX);
            message.setUserId(invoiceBO.getUserId());
            messageSendUtil.sendMessage(message, request);
        }
    }

}
