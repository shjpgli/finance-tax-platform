package com.abc12366.uc.service.impl;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.mapper.db1.OrderMapper;
import com.abc12366.uc.mapper.db2.OrderRoMapper;
import com.abc12366.uc.mapper.db2.ProductRoMapper;
import com.abc12366.uc.model.Order;
import com.abc12366.uc.model.Product;
import com.abc12366.uc.model.bo.OrderBO;
import com.abc12366.uc.service.OrderService;
import com.abc12366.uc.util.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @create 2017-05-15 10:17 AM
 * @since 1.0.0
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRoMapper orderRoMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductRoMapper productRoMapper;

    @Override
    public List<OrderBO> selectList(OrderBO order) {
        return orderRoMapper.selectList(order);
    }

    /**
     * 查询单个订单
     * @param id
     * @return
     */
    @Override
    public OrderBO selectOne(String id) {
        Order order = orderRoMapper.selectById(id);
        OrderBO orderBO = new OrderBO();
        BeanUtils.copyProperties(order, orderBO);
        return orderBO;
    }

    /**
     * 加入购物车
     * @param orderBO
     * @return
     */
    @Override
    public OrderBO joinCart(OrderBO orderBO) {
        //判断产品数量
        isValidate(orderBO);
        Order order = new Order();
        BeanUtils.copyProperties(orderBO, order);
        order.setId(Utils.uuid());
        order.setOrderId(StringUtils.getOrderIdString());
        Date date = new Date();
        order.setCreateTime(date);
        order.setLastUpdate(date);
        order.setStatus("1");
        int insert = orderMapper.insert(order);
        if (insert != 1) {
            throw new ServiceException(4101);
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
       /* Product product = orderBO.getProduct();
        if(product == null){
            throw new ServiceException(4134);
        }
        Product pd = productRoMapper.selectById(product.getId());
        if(pd == null){
            throw new ServiceException(4135);
        }
        if(pd.getTotal() < orderBO.getNum()){
            throw new ServiceException(4136);
        }
        boolean isValue = false;
        //判断积分和金额，必须有一个有值
        if(orderBO.getAmount() != 0){
            isValue = true;
        }
        if(orderBO.getPoints() != 0 && !"".equals(orderBO.getPoints())){
            isValue = true;
        }
        if(!isValue){
            throw new ServiceException(4133);
        }*/
    }

    /**
     * 修改购物车
     * @param orderBO
     * @return
     */
    @Override
    public OrderBO updateCart(OrderBO orderBO) {
        isValidate(orderBO);
        Order order = new Order();
        BeanUtils.copyProperties(orderBO, order);

        Date date = new Date();
        order.setLastUpdate(date);
        int update = orderMapper.update(order);
        if (update != 1) {
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

    /**
     * 根据订单ID和用户ID删除购物车
     * @param orderBO
     * @return
     */
    @Override
    public int deleteByIdAndUserId(OrderBO orderBO) {
        Order order = new Order();
        BeanUtils.copyProperties(orderBO,order);
        int del = orderMapper.deleteByIdAndUserId(order);
        if(del != 1){
            throw new ServiceException(4103);
        }
        return del;
    }

    /**
     *提交订单
     * @param orderBO
     * @return
     */
    @Override
    public OrderBO submitOrder(OrderBO orderBO) {
        Order order = new Order();
        BeanUtils.copyProperties(orderBO,order);
        Order temp = orderRoMapper.selectOrder(order);
        //判断支付方式
        if(temp != null){
            if(temp.getAmount() != 0){
                //跳转支付界面
            }else if(temp.getPoints() != 0){
                //去查询用户计分表，减掉积分
            }
        }
        return null;
    }

    @Override
    public List<OrderBO> selectCartList(OrderBO order) {
        return orderRoMapper.selectCartList(order);
    }

    public Order selectByPrimaryKey(@Param("id") String id) {
        return orderRoMapper.selectByPrimaryKey(id);
    }

    public Order selectById(String id) {
        return orderRoMapper.selectById(id);
    }

}
