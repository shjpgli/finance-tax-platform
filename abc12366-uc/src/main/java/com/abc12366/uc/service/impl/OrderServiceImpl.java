package com.abc12366.uc.service.impl;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.mapper.db1.*;
import com.abc12366.uc.mapper.db2.*;
import com.abc12366.uc.model.*;
import com.abc12366.uc.model.bo.OrderBO;
import com.abc12366.uc.model.bo.OrderBackBO;
import com.abc12366.uc.model.bo.OrderProductBO;
import com.abc12366.uc.model.bo.ProductBO;
import com.abc12366.uc.service.OrderService;
import com.abc12366.uc.util.DataUtils;
import com.github.pagehelper.PageHelper;
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
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRoMapper orderRoMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderLogMapper orderLogMapper;

    @Autowired
    private OrderProductMapper orderProductMapper;

    @Autowired
    private OrderProductRoMapper orderProductRoMapper;

    @Autowired
    private OrderBackMapper orderBackMap;

    @Autowired
    private OrderBackRoMapper orderBackRoMap;

    @Autowired
    private InvoiceRoMapper invoiceRoMapper;

    @Autowired
    private GoodsRoMapper goodsRoMapper;

    @Autowired
    private ProductRoMapper productRoMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductRepoMapper productRepoMapper;


    @Override
    public List<OrderBO> selectList(OrderBO orderBO, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        return orderRoMapper.selectList(orderBO);
    }

    @Override
    public OrderBO selectByOrderNo(String orderNo) {
        OrderBO orderBO = orderRoMapper.selectById(orderNo);
        return orderBO;
    }


    @Transactional("db1TxManager")
    @Override
    public OrderBO joinCart(OrderBO orderBO) {
        //判断产品数量
        //isValidate(orderBO);
        Order order = new Order();
        BeanUtils.copyProperties(orderBO, order);
        order.setOrderNo(DataUtils.getDateToString());
        Date date = new Date();
        order.setCreateTime(date);
        order.setLastUpdate(date);
        order.setOrderStatus("0");
        int insert = orderMapper.insert(order);
        if (insert != 1) {
            LOGGER.info("加入购物车失败：{}", orderBO);
            throw new ServiceException(4138);
        } else {
            OrderBO temp = new OrderBO();
            BeanUtils.copyProperties(order, temp);
            return temp;
        }
    }

    /**
     * 验证订单数据信息
     * @param orderBO
     */
    private void isValidate(OrderBO orderBO) {
        //TODO 
//        List<OrderProductBO> orderProductBOs = orderBO.getOrderProductBOList();
//        if (orderProductBOs == null){
//            LOGGER.info("产品信息错误：{}", orderBO);
//            throw new ServiceException(4166);
//        }else{
//            for (OrderProductBO orderProductBO : orderProductBOs){
//                //查询产品库存信息
//
//            }
//        }
    }

    @Transactional("db1TxManager")
    @Override
    public OrderBO updateCart(OrderBO orderBO) {
        isValidate(orderBO);
        Order order = new Order();
        BeanUtils.copyProperties(orderBO, order);

        Date date = new Date();
        order.setLastUpdate(date);
        int update = orderMapper.update(order);
        if (update != 1) {
            LOGGER.info("修改购物车信息失败：{}", orderBO);
            throw new ServiceException(4102);
        } else {
            OrderBO temp = new OrderBO();
            BeanUtils.copyProperties(order, temp);
            return temp;
        }
    }

    @Override
    public List<OrderBO> selectOrderList(OrderBO order, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<OrderBO> orderBOList = new ArrayList<>();
        if (order != null && !"".equals(order.getOrderStatus())){
            String status[] = order.getOrderStatus().split(",");
            for (String st : status){
                order.setOrderStatus(st);
                List<OrderBO> oList = orderRoMapper.selectOrderList(order);
                orderBOList.addAll(oList);
            }
        }
        return orderBOList;
    }

    @Transactional("db1TxManager")
    @Override
    public void deleteCart(OrderBO orderBO) {
        Order order = new Order();
        BeanUtils.copyProperties(orderBO,order);
        int del = orderMapper.deleteByIdAndUserId(order);
        if(del != 1){
            LOGGER.info("删除购物车信息失败：{}", orderBO);
            throw new ServiceException(4103);
        }
    }

    @Transactional("db1TxManager")
    @Override
    public OrderBO submitOrder(OrderBO orderBO) {
        //isValidate(orderBO);
        String orderId = Utils.uuid();
        orderBO.setOrderNo(DataUtils.getDateToString());
        Date date = new Date();
        orderBO.setCreateTime(date);
        orderBO.setLastUpdate(date);
        orderBO.setOrderStatus("1");
        orderBO.setIsInvoice(false);
        Order order = new Order();
        BeanUtils.copyProperties(orderBO, order);
        int insert = orderMapper.insert(order);
        if (insert != 1) {
            LOGGER.info("提交产品订单失败：{}", orderBO);
            throw new ServiceException(4139);
        }
        //加入订单与产品关系信息
        List<OrderProductBO> orderProductBOs = orderBO.getOrderProductBOList();
        if (orderProductBOs == null){
            LOGGER.info("产品信息错误：{}", orderBO);
            throw new ServiceException(4166);
        }else{
            for (OrderProductBO orderProductBO : orderProductBOs){
                orderProductBO.setOrderNo(orderId);
                OrderProduct orderProduct = new OrderProduct();
                BeanUtils.copyProperties(orderProductBO,orderProduct);
                int opInsert = orderProductMapper.insert(orderProduct);
                if (opInsert != 1){
                    LOGGER.info("提交订单与产品关系信息失败：{}", orderProduct);
                    throw new ServiceException(4167);
                }
                //减去Product库存数量
                ProductBO productBO = orderProductBO.getProductBO();
                int stock = productBO.getStock() - orderProduct.getNum();
                productBO.setStock(stock);
                Product product = new Product();
                BeanUtils.copyProperties(productBO,product);
                productMapper.update(product);
                //库存表数据处理
                ProductRepo repo = new ProductRepo();
                repo.setId(Utils.uuid());
                repo.setGoodsId(productBO.getGoodsId());
                repo.setProductId(productBO.getId());
                repo.setOutcome(orderProduct.getNum());
                repo.setStock(stock);
                repo.setCreateTime(date);
                repo.setLastUpdate(date);
                productRepoMapper.insert(repo);
            }
        }
        insertOrderLog(orderBO.getUserId(), orderBO.getOrderNo(), date,"用户新增订单");

        OrderBO temp = new OrderBO();
        BeanUtils.copyProperties(order, temp);
        return temp;

    }

    private void insertOrderLog(String userId, String orderId, Date date,String action) {
        //加入订单日志信息
        OrderLog orderLog = new OrderLog();
        orderLog.setId(Utils.uuid());
        orderLog.setAction(action);
        orderLog.setOrderNo(orderId);
        orderLog.setCreateTime(date);
        orderLog.setCreateUser(userId);
        int logInsert = orderLogMapper.insert(orderLog);
        if(logInsert != 1){
            LOGGER.info("订单日志新增失败：{}", orderLog);
            throw new ServiceException(4901);
        }
    }

    @Override
    public List<OrderBO> selectCartList(OrderBO order) {
        return orderRoMapper.selectCartList(order);
    }

    @Override
    public void submitCart(Order order) {
        OrderBO temp = orderRoMapper.selectOrder(order);
        //验证产品信息
        isValidate(temp);
        //将购物车状态修改为新订单状态
        order.setOrderStatus("1");
        orderMapper.update(order);
    }

    @Transactional("db1TxManager")
    @Override
    public void deleteOrder(OrderBO orderBO) {
        Order order = new Order();
        BeanUtils.copyProperties(orderBO,order);
        //查询订单状态
        OrderBO bo = orderRoMapper.selectOrder(order);
        if(bo == null){
            LOGGER.info("订单信息不存在：{}", orderBO);
            throw new ServiceException(4134);
        }
        //订单状态是确认状态，不能删除
        if(!"4".equals(bo.getOrderStatus()) || !"2".equals(bo.getOrderStatus())){
            LOGGER.info("订单只有在未付款或作废订单可以删除：{}", orderBO);
            throw new ServiceException(4140);
        }
        //order.setOrderStatus("9");
        int del = orderMapper.deleteByIdAndUserId(order);
        if(del != 1){
            LOGGER.info("删除失败：{}", orderBO);
            throw new ServiceException(4103);
        }
        //订单删除成功之后，删除订单与产品对应关系

        List<OrderProductBO> orderProductBOs = orderBO.getOrderProductBOList();
        if (orderProductBOs == null){
            LOGGER.info("产品信息错误：{}", orderBO);
            throw new ServiceException(4166);
        }else{
            for (OrderProductBO orderProductBO : orderProductBOs){
                int opDel = orderProductMapper.delete(orderProductBO.getOrderNo());
                if (opDel != 1){
                    LOGGER.info("删除订单与产品关系信息失败：{}", orderBO);
                    throw new ServiceException(4168);
                }
            }
        }
        insertOrderLog(orderBO.getUserId(), orderBO.getOrderNo(), new Date(),"用户删除订单");
    }

    @Override
    public OrderBO feedback(OrderBO orderBO) {
        Order order = new Order();
        order.setUserId(orderBO.getUserId());
        order.setOrderNo(orderBO.getOrderNo());
        //order.setFeedback(orderBO.getFeedback());
        int upd = orderMapper.update(order);
        if(upd != 1){
            LOGGER.info("反馈信息失败：{}", orderBO);
            throw new ServiceException(4141);
        }
        return orderRoMapper.selectOrder(order);
    }

    @Override
    public OrderBO cancelOrder(Order order) {
        OrderBO bo = orderRoMapper.selectById(order.getOrderNo());
        if(bo == null){
            LOGGER.info("订单信息不存在：{}", order);
            throw new ServiceException(4134);
        }
        if(!bo.getOrderStatus().equals("2")){
            LOGGER.info("只有待支付可以取消订单：{}", order);
            throw new ServiceException(4189);
        }
        order.setOrderStatus("4");
        int update = orderMapper.update(order);
        if (update != 1){
            LOGGER.info("修改失败：{}", order);
            throw new ServiceException(4102);
        }
        insertOrderLog(bo.getUserId(), bo.getOrderNo(), new Date(),"用户删除订单");
        return bo;
    }

    @Override
    public List<OrderBO> selectUserAllOrderList(OrderBO order, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<OrderBO> oList = orderRoMapper.selectOrderList(order);
        return oList;
    }

    @Override
    public OrderBack applyBackOrder(OrderBack orderBack) {
        OrderBO bo = orderRoMapper.selectById(orderBack.getOrderNo());
        if(bo == null){
            LOGGER.info("订单信息不存在：{}", orderBack);
            throw new ServiceException(4134);
        }
        if(bo.getIsInvoice()){
            LOGGER.info("该订单已开发票，请先发起发票退单：{}", bo);
            throw new ServiceException(4188);
        }
        orderBack.setId(Utils.uuid());
        Date date = new Date();
        orderBack.setCreateTime(date);
        orderBack.setLastUpdate(date);
        orderBackMap.insert(orderBack);
        insertOrderLog(orderBack.getUserId(), orderBack.getOrderNo(), new Date(), "用户申请退单");
        return orderBack;
    }

    @Override
    public OrderBack submitBackOrder(OrderBack orderBack) {
        orderBack.setLastUpdate(new Date());
        int upd = orderBackMap.update(orderBack);
        if(upd != 1){
            LOGGER.info("修改失败：{}", orderBack);
            throw new ServiceException(4102);
        }
        insertOrderLog(orderBack.getUserId(), orderBack.getOrderNo(), new Date(), "用户填写快递号");
        return orderBack;
    }

    @Override
    public OrderBack backCheckOrder(OrderBack orderBack) {
        Date date = new Date();
        orderBack.setLastUpdate(date);
        int upd = orderBackMap.update(orderBack);
        if(upd != 1){
            LOGGER.info("修改失败：{}", orderBack);
            throw new ServiceException(4102);
        }
        //0：允许，1：不允许，
        if("0".equals(orderBack.getStatus())){
            //修改订单状态，6：退款
            Order order = new Order();
            String orderNo = orderBack.getOrderNo();
            order.setOrderNo(orderNo);
            order.setOrderStatus("6");
            orderMapper.update(order);
            insertOrderLog(orderBack.getUserId(), orderNo, new Date(), "管理员允许退单");

            //获取订单和产品关系信息
            OrderProductBO pBO = new OrderProductBO();
            pBO.setOrderNo(orderBack.getOrderNo());
            List<OrderProductBO> orderProductBOs = orderProductRoMapper.selectByOrderNo(pBO);
            for (OrderProductBO orderProductBO : orderProductBOs){
                orderProductBO.setOrderNo(orderNo);
                OrderProduct orderProduct = new OrderProduct();
                BeanUtils.copyProperties(orderProductBO,orderProduct);
                int opInsert = orderProductMapper.insert(orderProduct);
                if (opInsert != 1){
                    LOGGER.info("提交订单与产品关系信息失败：{}", orderProduct);
                    throw new ServiceException(4167);
                }
                //增加Product库存数量
                ProductBO productBO = orderProductBO.getProductBO();
                int stock = productBO.getStock() + orderProduct.getNum();
                productBO.setStock(stock);
                Product product = new Product();
                BeanUtils.copyProperties(productBO,product);
                productMapper.update(product);
                //库存表数据处理
                ProductRepo repo = new ProductRepo();
                repo.setId(Utils.uuid());
                repo.setGoodsId(productBO.getGoodsId());
                repo.setProductId(productBO.getId());
                repo.setIncome(orderProduct.getNum());
                repo.setStock(stock);
                repo.setCreateTime(date);
                repo.setLastUpdate(date);
                productRepoMapper.insert(repo);
            }

        }else if("1".equals(orderBack.getStatus())){
            //TODO 需要确定状态
            Order order = new Order();
            order.setOrderNo(orderBack.getOrderNo());
            order.setOrderStatus("6");
            orderMapper.update(order);
            insertOrderLog(orderBack.getUserId(), orderBack.getOrderNo(), new Date(), "管理员不允许退单");
        }
        return orderBack;
    }

    @Override
    public List<OrderBackBO> selectOrderBackList(OrderBackBO orderBackBO) {
        return orderBackRoMap.selectOrderBackList(orderBackBO);
    }

}
