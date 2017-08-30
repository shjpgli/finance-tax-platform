package com.abc12366.uc.service.impl;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.mapper.db1.*;
import com.abc12366.uc.mapper.db2.*;
import com.abc12366.uc.model.*;
import com.abc12366.uc.model.bo.*;
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
@Service("orderService")
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

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoMapper userRoMapper;

    @Autowired
    private PointsLogMapper pointsLogMapper;

    @Autowired
    private UvipPriceRoMapper uvipPriceRoMapper;


    @Autowired
    private OrderProductspecRoMapper orderProductspecRoMapper;

    @Autowired
    private OrderProductspecMapper orderProductspecMapper;

    @Autowired
    private CityRoMapper cityRoMapper;

    @Autowired
    private AreaRoMapper areaRoMapper;

    @Autowired
    private ProvinceRoMapper provinceRoMapper;


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

    @Transactional("db1TxManager")
    @Override
    public OrderBO updateCart(OrderBO orderBO) {
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
        if (order != null && !"".equals(order.getOrderStatus())) {
            String status[] = order.getOrderStatus().split(",");
            for (String st : status) {
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
        BeanUtils.copyProperties(orderBO, order);
        int del = orderMapper.deleteByIdAndUserId(order);
        if (del != 1) {
            LOGGER.info("删除购物车信息失败：{}", orderBO);
            throw new ServiceException(4103);
        }
    }

    @Transactional("db1TxManager")
    @Override
    public OrderBO submitOrder(OrderBO orderBO) {
        String orderNo = DataUtils.getDateToString();
        orderBO.setOrderNo(orderNo);
        Date date = new Date();
        orderBO.setCreateTime(date);
        orderBO.setLastUpdate(date);
        Order order = new Order();
        //加入订单与产品关系信息
        List<OrderProductBO> orderProductBOs = orderBO.getOrderProductBOList();
        if (orderProductBOs == null) {
            LOGGER.info("产品信息错误：{}", orderBO);
            throw new ServiceException(4166);
        } else {
            for (OrderProductBO orderProductBO : orderProductBOs) {
                //查询产品库存信息
                ProductBO prBO = productRoMapper.selectBOById(orderProductBO.getProductId());
                if (prBO != null && prBO.getStock() < orderProductBO.getNum()) {
                    LOGGER.info("商品库存不足：{}", prBO);
                    throw new ServiceException(4903);
                }
                GoodsBO goodsBO = goodsRoMapper.selectGoods(orderBO.getGoodsId());
                //1：实物，2：虚拟物品，3：服务，4：会员服务，5：会员充值，6：学堂服务

                String goodsType = goodsBO.getGoodsType();
                if ("RMB".equals(orderBO.getTradeMethod())) {
                    if ("5".equals(goodsType)) {
                        //会员充值
                        operationMoneyRechargeOrder(orderBO, date, order, orderProductBO, prBO, goodsBO,"2");
                    }else{
                        operationMoneyServiceOrder(orderBO, date, order, orderProductBO, prBO, goodsBO, "2");
                    }
                } else if ("POINTS".equals(orderBO.getTradeMethod())) {
                    //订单状态，1：新订单，2：待支付，3：支付中，4：待发货，5：待收货，6：已完成，7：已取消
                    if ("1".equals(goodsType) || "2".equals(goodsType)) {
                        operationPointsOrder(orderBO, date, order, orderProductBO, prBO, goodsBO,"4");
                    } else if ("3".equals(goodsType) || "4".equals(goodsType)) {
                        operationPointsOrder(orderBO, date, order, orderProductBO, prBO, goodsBO,"6");
                        //TODO 还差开通服务接口调用
                    }else if ("6".equals(goodsType)) {
                        operationPointsOrder(orderBO, date, order, orderProductBO, prBO, goodsBO,"6");
                        //TODO 还差开通服务接口调用
                    }
                }
            }
        }
        insertOrderLog(orderBO.getUserId(), orderBO.getOrderNo(), date, "用户新增订单");

        OrderBO temp = new OrderBO();
        BeanUtils.copyProperties(order, temp);
        return temp;

    }

    /**
     * 处理人民币购买服务
     */
    private void operationMoneyServiceOrder(OrderBO orderBO, Date date, Order order, OrderProductBO orderProductBO, ProductBO prBO, GoodsBO goodsBO,String orderStatus) {
        // 查询用户信息
        User user = userRoMapper.selectOne(orderBO.getUserId());
        orderProductBO.setOrderNo(orderBO.getOrderNo());

        //减去Product库存数量
        int num = orderProductBO.getNum();
        int stock = prBO.getStock() - num;
        if (stock < 0) {
            LOGGER.info("库存不足,请联系管理员：{}", stock);
            throw new ServiceException(4905);
        }
        prBO.setStock(stock);
        Product product = new Product();
        BeanUtils.copyProperties(prBO, product);
        productMapper.update(product);
        //库存表数据处理
        ProductRepo repo = new ProductRepo();
        repo.setId(Utils.uuid());
        repo.setGoodsId(prBO.getGoodsId());
        repo.setProductId(prBO.getId());
        repo.setOutcome(num);
        repo.setStock(stock);
        repo.setCreateTime(date);
        repo.setLastUpdate(date);
        productRepoMapper.insert(repo);

        //商品价格
        double totalPrice;
        int giftPoints; //赠送积分
//        int consumePoints;
        //根据用户等级查找该等级下赠送的积分，没有则设置为goods中设置的赠送积分
        UvipPrice uvip = new UvipPrice();
        uvip.setProductId(prBO.getId());
        uvip.setVipLevel(user.getVipLevel());
        UvipPrice uvipPrice = uvipPriceRoMapper.selectByLevel(uvip);
        //查找对应的金额
        if(uvipPrice != null && uvipPrice.getTradePrice() != null && !"".equals(uvipPrice.getTradePrice())){
            totalPrice = uvipPrice.getTradePrice();
        }else{
            totalPrice = prBO.getSellingPrice();
        }
        //查找对应的积分
        if(uvipPrice != null && uvipPrice.getGiftPoints() != null && !"".equals(uvipPrice.getGiftPoints())){
            giftPoints = uvipPrice.getGiftPoints();
        }else{
            giftPoints = goodsBO.getGiftPoints();
        }
        double discount = 0;
        //查找对应的折扣
        if(uvipPrice != null && uvipPrice.getDiscount() != null){
            discount = uvipPrice.getDiscount();
        }
        //加入订单信息
        orderBO.setOrderStatus(orderStatus);
        orderBO.setIsInvoice(false);
        orderBO.setNowVipLevel(user.getVipLevel());
        orderBO.setUsername(user.getUsername());
        orderBO.setGiftPoints(giftPoints);
        orderBO.setTotalPrice(totalPrice);
        orderBO.setIsShipping(goodsBO.getIsShipping());
        orderBO.setIsFreeShipping(goodsBO.getIsFreeShipping());

        BeanUtils.copyProperties(orderBO, order);
        int insert = orderMapper.insert(order);
        if (insert != 1) {
            LOGGER.info("提交产品订单失败：{}", orderBO);
            throw new ServiceException(4139);
        }
        orderProductBO.setSellingPrice(totalPrice);
        orderProductBO.setUnitPrice(prBO.getMarketPrice());
        orderProductBO.setNum(num);
        orderProductBO.setDiscount(discount);
        orderProductBO.setDealPrice(totalPrice);
        orderProductBO.setName(goodsBO.getName());
        orderProductBO.setCategoryId(goodsBO.getCategoryId());
        orderProductBO.setCategory(goodsBO.getCategoryName());
        orderProductBO.setCreateTime(date);
        orderProductBO.setLastUpdate(date);
        orderProductBO.setGoodsId(goodsBO.getId());
        orderProductBO.setIsExchange(goodsBO.getIsExchange());
        orderProductBO.setIsReturn(goodsBO.getIsReturn());
        orderProductBO.setImageUrl(goodsBO.getImageUrl());
        orderProductBO.setGoodsType(goodsBO.getGoodsType());
        OrderProduct orderProduct = new OrderProduct();
        BeanUtils.copyProperties(orderProductBO, orderProduct);

        int opInsert = orderProductMapper.insert(orderProduct);
        if (opInsert != 1) {
            LOGGER.info("提交订单与产品关系信息失败：{}", orderProduct);
            throw new ServiceException(4167);
        }
    }

    /**
     * 处理人民币充值积分订单
     */
    private void operationMoneyRechargeOrder(OrderBO orderBO, Date date, Order order, OrderProductBO orderProductBO, ProductBO prBO, GoodsBO goodsBO,String orderStatus) {
        // 查询用户信息
        User user = userRoMapper.selectOne(orderBO.getUserId());
        orderProductBO.setOrderNo(orderBO.getOrderNo());
        //减去Product库存数量
        int num = orderProductBO.getNum();
        int stock = prBO.getStock() - num;
        if(stock < 0){
            LOGGER.info("库存不足,请联系管理员：{}", stock);
            throw new ServiceException(4905);
        }
        prBO.setStock(stock);
        Product product = new Product();
        BeanUtils.copyProperties(prBO, product);
        productMapper.update(product);
        //库存表数据处理
        ProductRepo repo = new ProductRepo();
        repo.setId(Utils.uuid());
        repo.setGoodsId(prBO.getGoodsId());
        repo.setProductId(prBO.getId());
        repo.setOutcome(num);
        repo.setStock(stock);
        repo.setCreateTime(date);
        repo.setLastUpdate(date);
        repo.setRemark(orderBO.getOrderNo());
        repo.setOptionUser(orderBO.getUserId());
        repo.setRemark(orderBO.getOrderNo());
        repo.setOptionUser(orderBO.getUserId());
        productRepoMapper.insert(repo);

        //商品价格
        double totalPrice = orderBO.getTotalPrice();
        int giftPoints = (int) (totalPrice+(totalPrice * 0.1)) * 1000;

        //加入订单信息
        orderBO.setOrderStatus(orderStatus);
        orderBO.setIsInvoice(false);
        orderBO.setNowVipLevel(user.getVipLevel());
        orderBO.setUsername(user.getUsername());
        orderBO.setGiftPoints(giftPoints);
        //orderBO.setTotalPrice(totalPrice);
        orderBO.setIsShipping(goodsBO.getIsShipping());
        orderBO.setIsFreeShipping(goodsBO.getIsFreeShipping());
        BeanUtils.copyProperties(orderBO, order);
        int insert = orderMapper.insert(order);
        if (insert != 1) {
            LOGGER.info("提交产品订单失败：{}", orderBO);
            throw new ServiceException(4139);
        }
        orderProductBO.setSellingPrice(totalPrice);
        orderProductBO.setUnitPrice(prBO.getMarketPrice());
        orderProductBO.setNum(num);
        orderProductBO.setDealPrice(totalPrice);
        orderProductBO.setName(goodsBO.getName());
        orderProductBO.setCategoryId(goodsBO.getCategoryId());
        orderProductBO.setCategory(goodsBO.getCategoryName());
        orderProductBO.setCreateTime(date);
        orderProductBO.setLastUpdate(date);
        orderProductBO.setGoodsId(goodsBO.getId());
        orderProductBO.setIsExchange(goodsBO.getIsExchange());
        orderProductBO.setIsReturn(goodsBO.getIsReturn());
        orderProductBO.setImageUrl(goodsBO.getImageUrl());
        orderProductBO.setGoodsType(goodsBO.getGoodsType());
        OrderProduct orderProduct = new OrderProduct();
        BeanUtils.copyProperties(orderProductBO, orderProduct);

        int opInsert = orderProductMapper.insert(orderProduct);
        if (opInsert != 1) {
            LOGGER.info("提交订单与产品关系信息失败：{}", orderProduct);
            throw new ServiceException(4167);
        }
    }

    /**
     * 处理积分订单
     */
    private void operationPointsOrder(OrderBO orderBO, Date date, Order order, OrderProductBO orderProductBO, ProductBO prBO, GoodsBO goodsBO,String orderStatus) {
        // 查询用户积分
        User user = userRoMapper.selectOne(orderBO.getUserId());
        orderProductBO.setOrderNo(orderBO.getOrderNo());
        //减去Product库存数量
        int num = orderProductBO.getNum();
        int stock = prBO.getStock() - num;
        if(stock < 0){
            LOGGER.info("库存不足,请联系管理员：{}", stock);
            throw new ServiceException(4905);
        }
        prBO.setStock(stock);
        Product product = new Product();
        BeanUtils.copyProperties(prBO, product);
        productMapper.update(product);
        //库存表数据处理
        ProductRepo repo = new ProductRepo();
        repo.setId(Utils.uuid());
        repo.setGoodsId(prBO.getGoodsId());
        repo.setProductId(prBO.getId());
        repo.setOutcome(num);
        repo.setStock(stock);
        repo.setCreateTime(date);
        repo.setLastUpdate(date);
        repo.setRemark(orderBO.getOrderNo());
        repo.setOptionUser(orderBO.getUserId());
        productRepoMapper.insert(repo);

        //商品价格
        double totalPrice;
        int giftPoints; //赠送积分
        //根据用户等级查找该等级下赠送的积分，没有则设置为goods中设置的赠送积分
        UvipPrice uvip = new UvipPrice();
        uvip.setProductId(prBO.getId());
        uvip.setVipLevel(user.getVipLevel());
        UvipPrice uvipPrice = uvipPriceRoMapper.selectByLevel(uvip);
        //查找对应的金额
        if(uvipPrice != null && uvipPrice.getTradePrice() != null && !"".equals(uvipPrice.getTradePrice())){
            totalPrice = uvipPrice.getTradePrice();
        }else{
            totalPrice = prBO.getSellingPrice();
        }
        //判断是正常积分兑换，还是RMB转积分兑换
        if(orderBO.getTotalPrice() != null && !"".equals(orderBO.getTotalPrice())){
            totalPrice = orderBO.getTotalPrice();
        }
        //查找对应的积分
        if(uvipPrice != null && uvipPrice.getGiftPoints() != null && !"".equals(uvipPrice.getGiftPoints())){
            giftPoints = uvipPrice.getGiftPoints();
        }else{
            giftPoints = goodsBO.getGiftPoints();
        }

        int userPoints = user.getPoints();
        //比较用户积分是否足够
        if (user != null && userPoints < totalPrice) {
            LOGGER.info("用户积分不足，请充值：{}", user);
            throw new ServiceException(4904);
        }

        //可用积分=上一次的可用积分+|-本次收入|支出
        int usablePoints = (int) (userPoints + (giftPoints * num) - (totalPrice * num));
        //uc_user的points字段和uc_point_log的usablePoints字段都要更新
        user.setPoints(usablePoints);
        int userUpdateResult = userMapper.update(user);
        if (userUpdateResult != 1) {
            LOGGER.warn("新增失败,更新用户表积分失败,参数为：userId=" + orderBO.getUserId());
            throw new ServiceException(4101);
        }

        PointsLog pointsLog = new PointsLog();
        pointsLog.setUserId(orderBO.getUserId());
        pointsLog.setId(Utils.uuid());
        pointsLog.setIncome(giftPoints);
        pointsLog.setOutgo((int) totalPrice);
        pointsLog.setCreateTime(new Date());
        pointsLog.setUsablePoints(usablePoints);
        pointsLog.setRemark("积分兑换");
        int result = pointsLogMapper.insert(pointsLog);
        if (result != 1) {
            LOGGER.warn("新增失败，参数：{}", pointsLog.toString());
            throw new ServiceException(4101);
        }
        //加入订单信息,
        orderBO.setOrderStatus(orderStatus);
        orderBO.setIsInvoice(false);
        orderBO.setNowVipLevel(user.getVipLevel());
        orderBO.setUsername(user.getUsername());
        orderBO.setGiftPoints(giftPoints);
        orderBO.setTotalPrice(totalPrice);
        orderBO.setIsShipping(goodsBO.getIsShipping());
        orderBO.setIsFreeShipping(goodsBO.getIsFreeShipping());

        BeanUtils.copyProperties(orderBO, order);
        int insert = orderMapper.insert(order);
        if (insert != 1) {
            LOGGER.info("提交产品订单失败：{}", orderBO);
            throw new ServiceException(4139);
        }

        orderProductBO.setUnitPrice(prBO.getMarketPrice());
        orderProductBO.setNum(num);
        orderProductBO.setName(goodsBO.getName());
        orderProductBO.setCategoryId(goodsBO.getCategoryId());
        orderProductBO.setCategory(goodsBO.getCategoryName());
        orderProductBO.setCreateTime(date);
        orderProductBO.setLastUpdate(date);
        orderProductBO.setGoodsId(goodsBO.getId());
        orderProductBO.setIsExchange(goodsBO.getIsExchange());
        orderProductBO.setIsReturn(goodsBO.getIsReturn());
        orderProductBO.setImageUrl(goodsBO.getImageUrl());
        orderProductBO.setGoodsType(goodsBO.getGoodsType());
        OrderProduct orderProduct = new OrderProduct();
        BeanUtils.copyProperties(orderProductBO, orderProduct);

        int opInsert = orderProductMapper.insert(orderProduct);
        if (opInsert != 1) {
            LOGGER.info("提交订单与产品关系信息失败：{}", orderProduct);
            throw new ServiceException(4167);
        }
    }


    private void insertOrderLog(String userId, String orderId, Date date, String action) {
        //加入订单日志信息
        OrderLog orderLog = new OrderLog();
        orderLog.setId(Utils.uuid());
        orderLog.setAction(action);
        orderLog.setOrderNo(orderId);
        orderLog.setCreateTime(date);
        orderLog.setCreateUser(userId);
        int logInsert = orderLogMapper.insert(orderLog);
        if (logInsert != 1) {
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
        //将购物车状态修改为新订单状态
        order.setOrderStatus("1");
        orderMapper.update(order);
    }

    @Transactional("db1TxManager")
    @Override
    public void deleteOrder(OrderBO orderBO) {
        Order order = new Order();
        BeanUtils.copyProperties(orderBO, order);
        //查询订单状态
        OrderBO bo = orderRoMapper.selectOrder(order);
        if (bo == null) {
            LOGGER.info("订单信息不存在：{}", orderBO);
            throw new ServiceException(4134);
        }
        //订单状态，2：待付款，3：付款中，4：付款成功，5：已发货，6：已完成，7：已结束，8：付款失败，9：已退单
        if ("7".equals(bo.getOrderStatus()) || "2".equals(bo.getOrderStatus())) {
            //order.setOrderStatus("9");
            int del = orderMapper.deleteByIdAndUserId(order);
            if (del != 1) {
                LOGGER.info("删除失败：{}", orderBO);
                throw new ServiceException(4103);
            }
            //订单删除成功之后，删除订单与产品对应关系

            List<OrderProductBO> orderProductBOs = bo.getOrderProductBOList();
            if (orderProductBOs == null) {
                LOGGER.info("产品信息错误：{}", orderBO);
                throw new ServiceException(4166);
            } else {
                for (OrderProductBO orderProductBO : orderProductBOs) {
                    int opDel = orderProductMapper.delete(orderProductBO.getOrderNo());
                    if (opDel != 1) {
                        LOGGER.info("删除订单与产品关系信息失败：{}", orderBO);
                        throw new ServiceException(4168);
                    }
                    orderProductspecMapper.deleteByOrderNo(orderProductBO.getOrderNo());
                }
            }
            insertOrderLog(orderBO.getUserId(), orderBO.getOrderNo(), new Date(), "用户删除订单");
        }else{
            LOGGER.info("订单只有在未付款或已结束可以删除：{}", orderBO);
            throw new ServiceException(4140);
        }
    }

    @Override
    public OrderBO feedback(OrderBO orderBO) {
        Order order = new Order();
        order.setUserId(orderBO.getUserId());
        order.setOrderNo(orderBO.getOrderNo());
        //order.setFeedback(orderBO.getFeedback());
        int upd = orderMapper.update(order);
        if (upd != 1) {
            LOGGER.info("反馈信息失败：{}", orderBO);
            throw new ServiceException(4141);
        }
        return orderRoMapper.selectOrder(order);
    }

    @Override
    public OrderBO cancelOrder(OrderCancelBO orderCancelBO) {
        OrderBO bo = orderRoMapper.selectById(orderCancelBO.getOrderNo());
        if (bo == null) {
            LOGGER.info("订单信息不存在：{}", orderCancelBO);
            throw new ServiceException(4134);
        }
        if (!bo.getOrderStatus().equals("2")) {
            LOGGER.info("只有待支付可以取消订单：{}", orderCancelBO);
            throw new ServiceException(4189);
        }
        orderCancelBO.setOrderStatus("7");
        Order order = new Order();
        BeanUtils.copyProperties(orderCancelBO,order);
        int update = orderMapper.update(order);
        if (update != 1) {
            LOGGER.info("修改失败：{}", order);
            throw new ServiceException(4102);
        }
        insertOrderLog(bo.getUserId(), bo.getOrderNo(), new Date(), "用户取消订单");
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
        if (bo == null) {
            LOGGER.info("订单信息不存在：{}", orderBack);
            throw new ServiceException(4134);
        }
        if (bo.getIsInvoice()) {
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
        orderBack.setId(Utils.uuid());
        orderBack.setLastUpdate(new Date());
        int upd = orderBackMap.insert(orderBack);
        if (upd != 1) {
            LOGGER.info("新增失败：{}", orderBack);
            throw new ServiceException(4101);
        }

        //修改订单状态
        Order order = new Order();
        order.setOrderNo(orderBack.getOrderNo());
        order.setOrderStatus("7");
        int oUpdate = orderMapper.update(order);
        if(oUpdate != 1){
            LOGGER.info("修改失败：{}", order);
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
        if (upd != 1) {
            LOGGER.info("修改失败：{}", orderBack);
            throw new ServiceException(4102);
        }
        //0：允许，1：不允许，
        if ("0".equals(orderBack.getStatus())) {
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
            for (OrderProductBO orderProductBO : orderProductBOs) {
                /*orderProductBO.setOrderNo(orderNo);
                OrderProduct orderProduct = new OrderProduct();
                BeanUtils.copyProperties(orderProductBO, orderProduct);
                int opInsert = orderProductMapper.insert(orderProduct);
                if (opInsert != 1) {
                    LOGGER.info("提交订单与产品关系信息失败：{}", orderProduct);
                    throw new ServiceException(4167);
                }*/
                //增加Product库存数量
                ProductBO productBO = new ProductBO();
                int stock = productBO.getStock() + orderProductBO.getNum();
                productBO.setStock(stock);
                Product product = new Product();
                BeanUtils.copyProperties(productBO, product);
                productMapper.update(product);
                //库存表数据处理
                ProductRepo repo = new ProductRepo();
                repo.setId(Utils.uuid());
                repo.setGoodsId(productBO.getGoodsId());
                repo.setProductId(productBO.getId());
                repo.setIncome(orderProductBO.getNum());
                repo.setStock(stock);
                repo.setCreateTime(date);
                repo.setLastUpdate(date);
                repo.setRemark(orderBack.getOrderNo());
                repo.setOptionUser(orderBack.getUserId());
                productRepoMapper.insert(repo);
            }

        } else if ("1".equals(orderBack.getStatus())) {
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

    @Override
    public OrderBO paymentOrder(OrderPayBO orderPayBO, String goodsType) {
        String orderNo = orderPayBO.getOrderNo();
        OrderBO orderBO = orderRoMapper.selectById(orderNo);
        if(orderBO != null){
            OrderProductBO pBO = new OrderProductBO();
            pBO.setOrderNo(orderNo);
            List<OrderProductBO> orderProductBOs = orderProductRoMapper.selectByOrderNo(pBO);

            for(OrderProductBO orderProductBO:orderProductBOs){
                String goodsId = orderProductBO.getGoodsId();
                GoodsBO goodsBO = goodsRoMapper.selectGoods(goodsId);
                goodsType = goodsBO.getGoodsType();
                //订单状态，2：待付款，3：付款中，4：付款成功，5：已发货，6：已完成，7：已结束，8：付款失败，9：已退单
                //支付状态，1：支付中，2：支付成功，3：支付失败，
                int isPay = orderPayBO.getIsPay();
                Order order = new Order();
                order.setOrderNo(orderNo);
                order.setPayMethod(orderPayBO.getPayMethod());
                order.setUserId(orderPayBO.getUserId());
                if(isPay == 1){
                    order.setOrderStatus("3");
                    int update = orderMapper.update(order);
                    if(update != 1){
                        LOGGER.warn("修改失败，参数：{}", order);
                        throw new ServiceException(4102);
                    }
                }else if(isPay == 2){
                    //查询商品类型，商品类型，1.虚拟，2.实物，3.服务，4.会员服务，5.会员充值，6.学堂服务
                    if(goodsType.equals("1") || goodsType.equals("2")){
                        order.setOrderStatus("4");
                        orderMapper.update(order);
                        insertPoints(orderBO);
                    }else if(goodsType.equals("3") || goodsType.equals("4")){
                        order.setOrderStatus("6");
                        orderMapper.update(order);
                        insertPoints(orderBO);
                        //TODO 开通服务
                    }else if(goodsType.equals("5") || goodsType.equals("6")){
                        order.setOrderStatus("6");
                        orderMapper.update(order);
                        insertPoints(orderBO);

                    }
                }else if(isPay == 3){
                    order.setOrderStatus("2");
                    orderMapper.update(order);
                }

            }

        }
        return orderBO;
    }

    /**
     * 插入可获得的积分
     * @param orderBO
     */
    private void insertPoints(OrderBO orderBO) {
        //加入积分
        //可用积分=上一次的可用积分+|-本次收入|支出
        User user = userRoMapper.selectOne(orderBO.getUserId());
        int userPoints = user.getPoints();
        int giftPoints = orderBO.getGiftPoints();
        int usablePoints = userPoints + giftPoints;
        //uc_user的points字段和uc_point_log的usablePoints字段都要更新
        user.setPoints(usablePoints);
        int userUpdateResult = userMapper.update(user);
        if (userUpdateResult != 1) {
            LOGGER.warn("新增失败,更新用户表积分失败,参数为：userId=" + orderBO.getUserId());
            throw new ServiceException(4101);
        }
        PointsLog pointsLog = new PointsLog();
        pointsLog.setUserId(orderBO.getUserId());
        pointsLog.setId(Utils.uuid());
        pointsLog.setIncome(giftPoints);
        pointsLog.setCreateTime(new Date());
        pointsLog.setUsablePoints(usablePoints);
        int result = pointsLogMapper.insert(pointsLog);
        if(result != 1){
            LOGGER.warn("新增失败，参数：{}", pointsLog.toString());
            throw new ServiceException(4101);
        }
    }

    @Override
    public List<OrderListBO> selectExprotOrder(Order order) {
        List<OrderBO> orderBOList = orderRoMapper.selectExprotOrder(order);
        List<OrderListBO> orderListBOList = new ArrayList<>();
        for(OrderBO bo:orderBOList){
            OrderListBO orderListBO = new OrderListBO();
            orderListBO.setOrderNo(bo.getOrderNo());
            //收货地址
            if(bo.getUserAddressBO() != null){
                UserAddressBO userAddress = bo.getUserAddressBO();
                StringBuffer address = new StringBuffer();
                address.append(userAddress.getProvinceName() + "-");
                address.append(userAddress.getCityName() + "-");
                address.append(userAddress.getAreaName() + "-");
                address.append(userAddress.getDetail());
                orderListBO.setAddress(address.toString());
                orderListBO.setPhone(userAddress.getPhone());
                orderListBO.setFullName(userAddress.getName());
            }
            //支付方式
            orderListBO.setPayMethod(bo.getPayMethod());
            //寄托货物
            StringBuffer goodsName = new StringBuffer();
            if(bo.getOrderProductBOList() != null){
                List<OrderProductBO> orderProductBOList = bo.getOrderProductBOList();
                for(OrderProductBO orderProductBO:orderProductBOList){
                    goodsName.append(orderProductBO.getName());
                    //List<OrderProductspecBO> orderProductspecBOs = orderProductBO.getOrderProductspecBOList();
                    /*if(orderProductspecBOs != null){
                        for (OrderProductspecBO specBO:orderProductspecBOs){
                            goodsName.append("  "+specBO.getFieldKey());
                        }
                    }*/
                    goodsName.append(orderProductBO.getName()+"  "+orderProductBO.getSpecInfo());
                }
                orderListBO.setGoodsName(goodsName.toString());
            }
            //寄托数量
            orderListBO.setNum(1);
            orderListBOList.add(orderListBO);
        }
        return orderListBOList;
    }

    @Override
    public void selectImprotOrder(List<OrderBO> orderBOList) {
        for(OrderBO bo:orderBOList){
            Order order = new Order();
            BeanUtils.copyProperties(bo,order);
            int upd = orderMapper.update(order);
            if(upd != 1){
                LOGGER.warn("修改失败，参数：{}", order);
                throw new ServiceException(4102);
            }
            insertOrderLog(order.getUserId(), order.getOrderNo(), new Date(), "管理员已发货");
        }
    }

    @Override
    public void sendOrder(OrderOperationBO orderOperationBO) {
        //订单状态，2：待支付，3：支付中，4：待发货，5：待收货，6：已完成，7：已取消
        orderOperationBO.setOrderStatus("5");
        Order order = new Order();
        BeanUtils.copyProperties(orderOperationBO,order);
        int upd = orderMapper.update(order);
        if(upd != 1){
            LOGGER.warn("修改失败，参数：{}", order);
            throw new ServiceException(4102);
        }
    }

    @Override
    public void invalidOrder(OrderOperationBO orderOperationBO) {
        orderOperationBO.setOrderStatus("6");
        Order order = new Order();
        BeanUtils.copyProperties(orderOperationBO,order);
        int upd = orderMapper.update(order);
        if(upd != 1){
            LOGGER.warn("修改失败，参数：{}", order);
            throw new ServiceException(4102);
        }
    }

    @Override
    public void automaticReceipt() {
        Date date = DataUtils.getAddDate(15);
        //查询15天之前未确认的订单
        List<Order> orderList = orderRoMapper.selectReceiptOrderByDate(date);
        for(Order order:orderList){
            order.setOrderStatus("6");
            orderMapper.update(order);
            insertOrderLog("",order.getOrderNo(),date,"系统自动确认收货");
        }
    }

    @Override
    public void automaticCancel() {
        Date date = DataUtils.getAddTime(3);
        //查询两个小时未支付的订单，自动取消
        List<Order> orderList = orderRoMapper.selectCancelOrderByDate(date);
        for(Order order:orderList){
            order.setOrderStatus("7");
            orderMapper.update(order);
            insertOrderLog("",order.getOrderNo(),date,"系统自动取消订单");
        }
    }

    @Override
    public OrderBO selectOrderByGoodsIdAndUserId(Order order) {
        return orderRoMapper.selectOrderByGoodsIdAndUserId(order);
    }

    @Override
    public OrderUpdateBO updateOrder(OrderUpdateBO orderUpdateBO) {
        Order order = new Order();
        order.setOrderNo(orderUpdateBO.getOrderNo());
        order.setUserId(orderUpdateBO.getUserId());
        order.setRecommendUser(orderUpdateBO.getRecommendUser());
        int update = orderMapper.update(order);
        if(update != 1){
            LOGGER.warn("修改失败，参数：{}", order);
            throw new ServiceException(4102);
        }
        return orderUpdateBO;
    }

}
