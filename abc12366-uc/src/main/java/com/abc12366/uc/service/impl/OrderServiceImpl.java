package com.abc12366.uc.service.impl;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.uc.mapper.db1.OrderMapper;
import com.abc12366.uc.mapper.db2.GoodsRoMapper;
import com.abc12366.uc.mapper.db2.OrderRoMapper;
import com.abc12366.uc.mapper.db2.ProductRoMapper;
import com.abc12366.uc.model.Order;
import com.abc12366.uc.model.Product;
import com.abc12366.uc.model.bo.GoodsBO;
import com.abc12366.uc.model.bo.OrderBO;
import com.abc12366.uc.service.OrderService;
import com.abc12366.uc.util.DataUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRoMapper orderRoMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private GoodsRoMapper goodsRoMapper;

    @Autowired
    private ProductRoMapper productRoMapper;



    @Override
    public List<OrderBO> selectList(OrderBO orderBO) {
        return orderRoMapper.selectList(orderBO);
    }

    @Override
    public OrderBO selectOne(String id) {
        Order order = orderRoMapper.selectById(id);
        OrderBO orderBO = new OrderBO();
        BeanUtils.copyProperties(order, orderBO);
        return orderBO;
    }


    @Transactional("db1TxManager")
    @Override
    public OrderBO joinCart(OrderBO orderBO) {
        //判断产品数量
        isValidate(orderBO);
        Order order = new Order();
        BeanUtils.copyProperties(orderBO, order);
        order.setOrderNo(DataUtils.getDateToString());
        Date date = new Date();
        order.setCreateTime(date);
        order.setLastUpdate(date);
        order.setOrderStatus("0");
        int insert = orderMapper.insert(order);
        if (insert != 1) {
            LOGGER.info("{加入购物车失败}", orderBO);
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
       /* GoodsBO goods = goodsRoMapper.selectGoods(orderBO.getGoods());
        //goodsId、字典Id、会员等级，确定一条数据。
        Product product = new Product();
        product.setGoodsId(orderBO.getGoodsId());
        product.setDictId(orderBO.getDictId());
        product.setUvipLevel(orderBO.getUvipLevel());
        Product pro = productRoMapper.selectProduct(product);
        if(pro == null){
            LOGGER.info("{产品不存在}", orderBO);
            throw new ServiceException(4135);
        }
        if(pro.getStock() < orderBO.getNum()){
            LOGGER.info("{产品数量不足}", orderBO);
            throw new ServiceException(4136);
        }*/
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
            LOGGER.info("{修改购物车信息失败}", orderBO);
            throw new ServiceException(4102);
        } else {
            OrderBO temp = new OrderBO();
            BeanUtils.copyProperties(order, temp);
            return temp;
        }
    }

    @Override
    public List<OrderBO> selectOrderList(OrderBO order) {
        return orderRoMapper.selectOrderList(order);
    }

    @Transactional("db1TxManager")
    @Override
    public void deleteCart(OrderBO orderBO) {
        Order order = new Order();
        BeanUtils.copyProperties(orderBO,order);
        int del = orderMapper.deleteByIdAndUserId(order);
        if(del != 1){
            LOGGER.info("{删除购物车信息失败}", orderBO);
            throw new ServiceException(4103);
        }
    }

    @Transactional("db1TxManager")
    @Override
    public OrderBO submitOrder(OrderBO orderBO) {
        isValidate(orderBO);
        Order order = new Order();
        BeanUtils.copyProperties(orderBO, order);
        order.setOrderNo(DataUtils.getDateToString());
        Date date = new Date();
        order.setCreateTime(date);
        order.setLastUpdate(date);
        order.setOrderStatus("1");
        int insert = orderMapper.insert(order);
        if (insert != 1) {
            LOGGER.info("{提交产品订单失败}", orderBO);
            throw new ServiceException(4139);
        } else {
            OrderBO temp = new OrderBO();
            BeanUtils.copyProperties(order, temp);
            return temp;
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
            LOGGER.info("{订单信息不存在}", orderBO);
            throw new ServiceException(4134);
        }
        //订单状态是确认状态，不能删除
        if("1".equals(bo.getOrderStatus())){
            LOGGER.info("{订单状态是确认状态，不能删除}", orderBO);
            throw new ServiceException(4140);
        }
        int del = orderMapper.deleteByIdAndUserId(order);
        if(del != 1){
            LOGGER.info("{删除订单信息信息失败}", orderBO);
            throw new ServiceException(4103);
        }
    }

    @Override
    public OrderBO feedback(OrderBO orderBO) {
        Order order = new Order();
        order.setUserId(orderBO.getUserId());
        order.setOrderNo(orderBO.getOrderNo());
        //order.setFeedback(orderBO.getFeedback());
        int upd = orderMapper.update(order);
        if(upd != 1){
            LOGGER.info("{反馈信息失败}", orderBO);
            throw new ServiceException(4141);
        }
        return orderRoMapper.selectOrder(order);
    }
}
