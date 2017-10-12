package com.abc12366.uc.service.impl;

import com.abc12366.gateway.component.SpringCtxHolder;
import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.mapper.db1.*;
import com.abc12366.uc.mapper.db2.*;
import com.abc12366.uc.model.*;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.service.*;
import com.abc12366.uc.util.*;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
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
    private UcRestTemplateUtil ucRestTemplateUtil;

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
    private GoodsRoMapper goodsRoMapper;

    @Autowired
    private ProductRoMapper productRoMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductRepoMapper productRepoMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoMapper userRoMapper;

    @Autowired
    private PointsLogService pointsLogService;

    @Autowired
    private UvipPriceRoMapper uvipPriceRoMapper;

    @Autowired
    private OrderProductspecMapper orderProductspecMapper;

    @Autowired
    private VipLogService vipLogService;

    @Autowired
    private DictRoMapper dictRoMapper;

    @Autowired
    private TradeLogMapper tradeLogMapper;

    @Autowired
    private UserAddressRoMapper userAddressRoMapper;

    @Autowired
    private ExpressCompRoMapper expressCompRoMapper;

    @Autowired
    private MessageSendUtil messageSendUtil;

    @Autowired
    private TodoTaskService todoTaskService;




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
        List<OrderBO> orderBOList = orderRoMapper.selectOrderList(order);
//        if (order != null && !"".equals(order.getOrderStatus())) {
//            String status[] = order.getOrderStatus().split(",");
//            for (String st : status) {
//                order.setOrderStatus(st);
//                List<OrderBO> oList = orderRoMapper.selectOrderList(order);
//                orderBOList.addAll(oList);
//            }
//        }
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
                StringBuffer specInfo = new StringBuffer();
                if(orderProductBO.getProductBO() != null){
                    List<DictBO> dictList  = orderProductBO.getProductBO().getDictList();
                    if(dictList != null && dictList.size() > 0){
                        for(DictBO dictBO:dictList){
                            specInfo.append(dictBO.getDictName()+"  ");
                            specInfo.append(dictBO.getFieldKey());
                        }
                    }
                }

                if(prBO == null){
                    throw new ServiceException(4903,"商品信息错误");
                }
                if(prBO.getStock() == null){
                    throw new ServiceException(4903,"商品库存信息异常");
                }
                if (prBO.getStock() < orderProductBO.getNum()) {
                    LOGGER.info("商品库存不足：{}", prBO);
                    throw new ServiceException(4903);
                }
                GoodsBO goodsBO = goodsRoMapper.selectGoods(orderBO.getGoodsId());
                //1：实物，2：虚拟物品，3：服务，4：会员服务，5：会员充值，6：学堂服务

                String goodsType = goodsBO.getGoodsType();
                if ("RMB".equals(orderBO.getTradeMethod())) {
                    if ("5".equals(goodsType)) {
                        //会员充值
                        operationMoneyRechargeOrder(orderBO, date, order, orderProductBO, prBO, goodsBO,"2",specInfo.toString());
                        insertOrderLog(orderBO.getUserId(), orderBO.getOrderNo(), "2", "用户新增订单","0");
                    }else{
                        operationMoneyServiceOrder(orderBO, date, order, orderProductBO, prBO, goodsBO, "2",specInfo.toString());
                        insertOrderLog(orderBO.getUserId(), orderBO.getOrderNo(), "2", "用户新增订单","0");
                    }
                } else if ("POINTS".equals(orderBO.getTradeMethod())) {
                    //订单状态，1：新订单，2：待支付，3：支付中，4：待发货，5：待收货，6：已完成，7：已取消
//                    if ("1".equals(goodsType) || "2".equals(goodsType)) {
                        operationPointsOrder(orderBO, date, order, orderProductBO, prBO, goodsBO,"2",specInfo.toString());
                        insertOrderLog(orderBO.getUserId(), orderBO.getOrderNo(), "2", "用户新增订单","0");
//                    } else if ("3".equals(goodsType) || "4".equals(goodsType)) {
//                        operationPointsOrder(orderBO, date, order, orderProductBO, prBO, goodsBO,"6",specInfo.toString());
//                        userService.updateUserVipInfo(orderBO.getUserId(), goodsBO.getMemberLevel());
//                        insertOrderLog(orderBO.getUserId(), orderBO.getOrderNo(), "6", "用户新增订单，支付成功","0");
//                    }else if ("6".equals(goodsType)) {
//                        operationPointsOrder(orderBO, date, order, orderProductBO, prBO, goodsBO,"6",specInfo.toString());
//                        insertOrderLog(orderBO.getUserId(), orderBO.getOrderNo(), "6", "用户新增订单，支付成功","0");
//                    }
                }
            }
        }

        OrderBO temp = new OrderBO();
        BeanUtils.copyProperties(order, temp);
        return temp;

    }

    /**
     * 更新会员等级
     *
     * @param orderNo 订单号
     * @return 会员日志信息
     */
    @Transactional("db1TxManager")
    @Override
    public VipLogBO updateVipLevel(String orderNo) {
        OrderProductBO bo = new OrderProductBO();
        bo.setOrderNo(orderNo);
        List<OrderProductBO> orderProductList = orderProductRoMapper.selectByOrderNo(bo);
        if (orderProductList != null && orderProductList.size() > 0) {
            for (OrderProductBO orderProductBO : orderProductList) {
                if ("4".equals(orderProductBO.getGoodsType())) {
                    OrderBO orderBO = selectByOrderNo(orderNo);
                    GoodsBO goodsBO = goodsRoMapper.selectGoods(orderProductBO.getGoodsId());
                    userService.updateUserVipInfo(orderBO.getUserId(), goodsBO.getMemberLevel());

                    VipLogBO vipLogBO = new VipLogBO();
                    vipLogBO.setLevelId(goodsBO.getMemberLevel());
                    vipLogBO.setSource(orderNo);
                    vipLogBO.setUserId(orderBO.getUserId());
                    return vipLogBO;
                }
            }
        }
        return null;
    }

    /**
     * 处理人民币购买服务
     */
    @Transactional("db1TxManager")
    private void operationMoneyServiceOrder(OrderBO orderBO, Date date, Order order, OrderProductBO orderProductBO, ProductBO prBO, GoodsBO goodsBO,String orderStatus,String specInfo) {
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
        /*prBO.setStock(stock);
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
        productRepoMapper.insert(repo);*/

        //交易价格
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
        orderProductBO.setSellingPrice(prBO.getSellingPrice());
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
        orderProductBO.setSpecInfo(specInfo);
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
    @Transactional("db1TxManager")
    private void operationMoneyRechargeOrder(OrderBO orderBO, Date date, Order order, OrderProductBO orderProductBO, ProductBO prBO, GoodsBO goodsBO,String orderStatus,String specInfo) {
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
//        prBO.setStock(stock);
//        Product product = new Product();
//        BeanUtils.copyProperties(prBO, product);
//        productMapper.update(product);
//        //库存表数据处理
//        ProductRepo repo = new ProductRepo();
//        repo.setId(Utils.uuid());
//        repo.setGoodsId(prBO.getGoodsId());
//        repo.setProductId(prBO.getId());
//        repo.setOutcome(num);
//        repo.setStock(stock);
//        repo.setCreateTime(date);
//        repo.setLastUpdate(date);
//        repo.setRemark(orderBO.getOrderNo());
//        repo.setOptionUser(orderBO.getUserId());
//        repo.setRemark(orderBO.getOrderNo());
//        repo.setOptionUser(orderBO.getUserId());
//        productRepoMapper.insert(repo);

        //商品价格
        double totalPrice = orderBO.getTotalPrice();
        int giftPoints = (int) totalPrice * 1100;

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
        orderProductBO.setSpecInfo(specInfo);
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
    private void operationPointsOrder(OrderBO orderBO, Date date, Order order, OrderProductBO orderProductBO, ProductBO prBO, GoodsBO goodsBO,String orderStatus,String specInfo) {
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

        /*// 积分日志
        PointsLogBO pointsLog = new PointsLogBO();
        pointsLog.setUserId(orderBO.getUserId());
        pointsLog.setRuleId(orderBO.getOrderNo());
        pointsLog.setIncome(giftPoints * num);
        pointsLog.setOutgo((int) totalPrice * num);
        pointsLog.setLogType("POINTS_RECHARGE");
        pointsLog.setRemark("积分兑换");
        pointsLogService.insert(pointsLog);

        //加入交易日志
        TradeLog tradeLog=new TradeLog();
        tradeLog.setId(Utils.uuid());
        tradeLog.setOrderNo(orderBO.getOrderNo());
        tradeLog.setAliTrandeNo(orderBO.getOrderNo());
        tradeLog.setTradeStatus("1");
        tradeLog.setTradeType("2");
        tradeLog.setAmount(Double.parseDouble("-"+totalPrice));
        Timestamp now = new Timestamp(new Date().getTime());
        tradeLog.setTradeTime(now);
        tradeLog.setCreateTime(now);
        tradeLog.setLastUpdate(now);
        //tradeLog.setPayMethod("ALIPAY");
        tradeLogMapper.insertTradeLog(tradeLog);*/

        //加入订单信息,
        orderBO.setOrderStatus(orderStatus);
        orderBO.setIsInvoice(false);
        orderBO.setNowVipLevel(user.getVipLevel());
        orderBO.setUsername(user.getUsername());
        orderBO.setGiftPoints(giftPoints);
        orderBO.setTotalPrice(totalPrice * num);
        orderBO.setIsShipping(goodsBO.getIsShipping());
        orderBO.setIsFreeShipping(goodsBO.getIsFreeShipping());

        BeanUtils.copyProperties(orderBO, order);
        int insert = orderMapper.insert(order);
        if (insert != 1) {
            LOGGER.info("提交产品订单失败：{}", orderBO);
            throw new ServiceException(4139);
        }

        orderProductBO.setDealPrice(totalPrice * num);
        orderProductBO.setSellingPrice(prBO.getSellingPrice());
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
        orderProductBO.setSpecInfo(specInfo);
        OrderProduct orderProduct = new OrderProduct();
        BeanUtils.copyProperties(orderProductBO, orderProduct);

        int opInsert = orderProductMapper.insert(orderProduct);
        if (opInsert != 1) {
            LOGGER.info("提交订单与产品关系信息失败：{}", orderProduct);
            throw new ServiceException(4167);
        }
    }


    /**
     * 加入订单日志
     */
    private void insertOrderLog(String userId, String orderNo, String status, String remark,String logType) {
        //加入订单日志信息
        OrderLog orderLog = new OrderLog();
        orderLog.setId(Utils.uuid());
        orderLog.setAction(selectFieldValue("orderStatus", status));
        orderLog.setOrderNo(orderNo);
        orderLog.setCreateTime(new Date());
        orderLog.setCreateUser(userId);
        orderLog.setRemark(remark);
        orderLog.setLogType(logType);
        int logInsert = orderLogMapper.insert(orderLog);
        if (logInsert != 1) {
            LOGGER.info("订单日志新增失败：{}", orderLog);
            throw new ServiceException(4901);
        }
    }

    private String selectFieldValue(String fieldValue, String value) {
        Dict dict = new Dict();
        dict.setDictId(fieldValue);
        dict.setFieldValue(value);
        dict = dictRoMapper.selectOne(dict);
        return dict != null ? dict.getFieldKey() : "";
    }

    @Override
    public List<OrderBO> selectCartList(OrderBO order) {
        return orderRoMapper.selectCartList(order);
    }

    @Transactional("db1TxManager")
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
            insertOrderLog(orderBO.getUserId(), orderBO.getOrderNo(), "7", "用户删除订单","0");
        }else{
            LOGGER.info("订单只有在未付款或已结束可以删除：{}", orderBO);
            throw new ServiceException(4140);
        }
    }

    @Transactional("db1TxManager")
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

    @Transactional("db1TxManager")
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
        BeanUtils.copyProperties(orderCancelBO, order);
        int update = orderMapper.update(order);
        if (update != 1) {
            LOGGER.info("修改失败：{}", order);
            throw new ServiceException(4102);
        }
        insertOrderLog(bo.getUserId(), bo.getOrderNo(), "7", "用户取消订单","0");
        return bo;
    }

    @Override
    public List<OrderBO> selectUserAllOrderList(OrderBO order, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<OrderBO> oList = orderRoMapper.selectUserAllOrderList(order);
        return oList;
    }

    @Transactional("db1TxManager")
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
        insertOrderLog(orderBack.getUserId(), orderBack.getOrderNo(), "1", "用户提交退单","1");
        return orderBack;
    }

    @Transactional("db1TxManager")
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
        insertOrderLog(orderBack.getUserId(), orderBack.getOrderNo(), "7", "用户填写快递号","1");
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
            insertOrderLog(orderBack.getUserId(), orderNo, "6", "管理员允许退单","1");

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
            insertOrderLog(orderBack.getUserId(), orderBack.getOrderNo(), "6", "管理员不允许退单","1");
        }
        return orderBack;
    }

    @Override
    public List<OrderBackBO> selectOrderBackList(OrderBackBO orderBackBO) {
        return orderBackRoMap.selectOrderBackList(orderBackBO);
    }

    @Transactional("db1TxManager")
    @Override
    public OrderBO paymentOrder(OrderPayBO orderPayBO, String type, HttpServletRequest request) {
        String orderNo = orderPayBO.getOrderNo();
        OrderBO orderBO = orderRoMapper.selectById(orderNo);
        //查询地址信息
        if(orderPayBO != null && orderPayBO.getAddressId() != null && !"".equals(orderPayBO.getAddressId())){
            UserAddressBO userAddress = userAddressRoMapper.selectById(orderPayBO.getAddressId());
            if(userAddress != null){
                StringBuffer address = new StringBuffer();
                address.append(userAddress.getProvinceName() + "-");
                address.append(userAddress.getCityName() + "-");
                address.append(userAddress.getAreaName() + "-");
                address.append(userAddress.getDetail());
                orderBO.setConsignee(userAddress.getName());
                orderBO.setContactNumber(userAddress.getPhone());
                orderBO.setShippingAddress(address.toString());
            }
        }
        if (orderBO != null) {
            OrderProductBO pBO = new OrderProductBO();
            pBO.setOrderNo(orderNo);
            List<OrderProductBO> orderProductBOs = orderProductRoMapper.selectByOrderNo(pBO);

            for (OrderProductBO orderProductBO : orderProductBOs) {
                String goodsId = orderProductBO.getGoodsId();
                GoodsBO goodsBO = goodsRoMapper.selectGoods(goodsId);
                String goodsType = goodsBO.getGoodsType();
                //订单状态，2：待付款，3：付款中，4：付款成功，5：已发货，6：已完成，7：已结束，8：付款失败，9：已退单
                //支付状态，1：支付中，2：支付成功，3：支付失败，
                int isPay = orderPayBO.getIsPay();
                Order order = new Order();
                /*order.setOrderNo(orderNo);
                order.setPayMethod(orderPayBO.getPayMethod());
                order.setAddressId(orderPayBO.getAddressId());
                order.setUserId(orderBO.getUserId());*/
                orderBO.setLastUpdate(new Date());
                BeanUtils.copyProperties(orderBO,order);
                //实物订单，必须要先填写地址
                if (goodsType.equals("1")){
                    if(orderPayBO.getAddressId() == null || "".equals(orderPayBO.getAddressId())){
                        throw new ServiceException(4999,"实物订单，必须要先填写地址");
                    }
                }
                if("RMB".equals(type)){
                    if (isPay == 1) {
                        order.setOrderStatus("3");
                        int update = orderMapper.update(order);
                        if (update != 1) {
                            LOGGER.warn("修改失败，参数：{}", order);
                            throw new ServiceException(4102);
                        }
                        insertOrderLog(orderBO.getUserId(), orderNo, "3", "用户付款中","0");
                    } else if (isPay == 2) {
                        updateStock(orderBO, orderProductBO);
                        setTodoTask(orderBO);
                        //查询商品类型，商品类型，1.实物 2.虚拟 3.服务，4.会员服务，5.会员充值，6.学堂服务
                        if (goodsType.equals("1") || goodsType.equals("2")) {
                            order.setOrderStatus("4");
                            orderMapper.update(order);

                            insertPoints(orderBO);
                            insertOrderLog(orderBO.getUserId(), orderNo, "4", "用户付款成功","0");
                        } else if (goodsType.equals("3") || goodsType.equals("4")) {
                            order.setOrderStatus("6");
                            orderMapper.update(order);

                            insertPoints(orderBO);
                            LOGGER.info("查询是否为会员服务订单，支付成功则更新会员状态: {}", orderNo);
                            userService.updateUserVipInfo(orderBO.getUserId(), goodsBO.getMemberLevel());

                            LOGGER.info("插入会员日志: {}", orderNo);
                            insertVipLog(orderNo, orderBO.getUserId(), goodsBO.getMemberLevel());

                            insertOrderLog(orderBO.getUserId(), orderNo, "6", "用户付款成功，完成订单", "0");
                            //发送消息
                            sendMemberMsg(orderProductBO, order);
                        } else if (goodsType.equals("5")) {
                            order.setOrderStatus("6");
                            orderMapper.update(order);

                            insertPoints(orderBO);
                            insertOrderLog(orderBO.getUserId(), orderNo, "6", "用户付款成功，完成订单","0");
                            //发送消息
                            sendPointsMsg(orderProductBO, order);
                        }else if (goodsType.equals("6")) {
                            order.setOrderStatus("6");
                            orderMapper.update(order);

                            insertPoints(orderBO);
                            insertOrderLog(orderBO.getUserId(), orderNo, "6", "用户付款成功，完成订单","0");
                        }
                    } else if (isPay == 3) {
                        order.setOrderStatus("2");
                        orderMapper.update(order);
                        insertOrderLog(orderBO.getUserId(), orderNo, "2", "等待用户付款", "0");
                    }
                }else if("POINTS".equals(type)){
                    updateStock(orderBO, orderProductBO);
                    //查询商品类型，商品类型，1.实物 2.虚拟 3.服务，4.会员服务，5.会员充值，6.学堂服务
                    if (goodsType.equals("1") || goodsType.equals("2")) {
                        order.setOrderStatus("4");
                        orderMapper.update(order);

                        insertDeductPoints(orderBO);
                        insertOrderLog(orderBO.getUserId(), orderNo, "4", "用户付款成功","0");
                    } else if (goodsType.equals("3") || goodsType.equals("4")) {
                        order.setOrderStatus("6");
                        orderMapper.update(order);

                        insertDeductPoints(orderBO);
                        LOGGER.info("查询是否为会员服务订单，支付成功则更新会员状态: {}", orderNo);
                        userService.updateUserVipInfo(orderBO.getUserId(), goodsBO.getMemberLevel());

                        LOGGER.info("插入会员日志: {}", orderNo);
                        insertVipLog(orderNo, orderBO.getUserId(), goodsBO.getMemberLevel());

                        insertOrderLog(orderBO.getUserId(), orderNo, "6", "用户付款成功，完成订单","0");
                        sendMemberMsg(orderProductBO, order);
                    }else if (goodsType.equals("6")) {
                        order.setOrderStatus("6");
                        orderMapper.update(order);

                        insertDeductPoints(orderBO);
                        insertOrderLog(orderBO.getUserId(), orderNo, "6", "用户付款成功，完成订单","0");
                    }
                    insertTradeLog(orderBO);
                }
            }
        }
        return orderBO;
    }

    /**
     * 加入消费赠送积分规则
     * @param order
     */
    private void setTodoTask(OrderBO order) {
        double amount = order.getTotalPrice();
        String userId = order.getUserId();
        todoTaskService.doTask(userId, UCConstant.SYS_TASK_FIRST_CONSUME_ID);
        if(amount >= 1000 && amount< 3000){
            todoTaskService.doTask(userId, UCConstant.SYS_TASK_FIRST_CONSUME_BEYOND_1000_ID);
        }

        if(amount >= 3000 && amount < 5000){
            todoTaskService.doTask(userId, UCConstant.SYS_TASK_FIRST_CONSUME_BEYOND_3000_ID);
        }
        if(amount >= 5000){
            todoTaskService.doTask(userId, UCConstant.SYS_TASK_FIRST_CONSUME_BEYOND_5000_ID);
        }
    }

    /**
     * 修改库存
     */
    private void updateStock(OrderBO orderBO, OrderProductBO orderProductBO) {
        //查询产品库存信息
        ProductBO prBO = productRoMapper.selectBOById(orderProductBO.getProductId());
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
        Date date = new Date();
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
    }

    /**
     * 购买会员，消息发送
     */
    private void sendMemberMsg(OrderProductBO orderProductBO, Order order) {
        Message message = new Message();
        message.setBusinessId(order.getOrderNo());
        message.setType(MessageConstant.SPDD);
        message.setContent(MessageConstant.BUYING_MEMBERS_PREFIX+orderProductBO.getName()+MessageConstant.BUYING_MEMBERS_SUFFIX+"。<a href=\""+ SpringCtxHolder.getProperty("abc12366.api.url.uc")+"/member/member_rights.html\">会员权益详情查看</a>");
        message.setUserId(order.getUserId());
        messageSendUtil.sendMessage(message);
    }

    /**
     * 积分充值，消息发送
     */
    private void sendPointsMsg(OrderProductBO orderProductBO, Order order) {
        Message message = new Message();
        message.setBusinessId(order.getOrderNo());
        message.setType(MessageConstant.SPDD);
        User user = userRoMapper.selectOne(order.getUserId());
        message.setContent(MessageConstant.INTEGRAL_RECHARGE+orderProductBO.getName()+user.getPoints()+"。<a href=\""+SpringCtxHolder.getProperty("abc12366.api.url.uc")+"/pointsExchange/points.php\">查看积分明细</a>");
        message.setUserId(order.getUserId());
        messageSendUtil.sendMessage(message);
    }

    /**
     * 插入交易流水记录
     */
    private void insertTradeLog(OrderBO orderBO) {
        //加入交易日志
        TradeLog tradeLog=new TradeLog();
        tradeLog.setId(Utils.uuid());
        tradeLog.setOrderNo(orderBO.getOrderNo());
        tradeLog.setAliTrandeNo(orderBO.getOrderNo());
        tradeLog.setTradeStatus("1");
        tradeLog.setTradeType("2");
        tradeLog.setAmount(Double.parseDouble("-"+orderBO.getTotalPrice()));
        Timestamp now = new Timestamp(new Date().getTime());
        tradeLog.setTradeTime(now);
        tradeLog.setCreateTime(now);
        tradeLog.setLastUpdate(now);
        tradeLogMapper.insertTradeLog(tradeLog);
    }

    public void insertVipLog(String orderNo, String userId, String memberLevel) {
        VipLogBO vipLogBO = new VipLogBO();
        vipLogBO.setLevelId(memberLevel);
        vipLogBO.setSource(orderNo);
        vipLogBO.setUserId(userId);
        vipLogService.insert(vipLogBO);
    }

    /**
     * 插入可获得的积分
     * @param orderBO
     */
    private void insertPoints(OrderBO orderBO) {
        PointsLogBO pointsLog = new PointsLogBO();
        pointsLog.setUserId(orderBO.getUserId());
        pointsLog.setRuleId(UCConstant.POINT_RULE_ORDER_ID);
        pointsLog.setId(Utils.uuid());
        pointsLog.setIncome(orderBO.getGiftPoints());
        pointsLog.setRemark("用户下单 - 订单号："+orderBO.getOrderNo());
        pointsLog.setLogType("ORDER_INCOME");
        pointsLogService.insert(pointsLog);
    }

    /**
     * 插入所扣去的积分
     * @param orderBO
     */
    private void insertDeductPoints(OrderBO orderBO) {
        PointsLogBO pointsLog = new PointsLogBO();
        pointsLog.setUserId(orderBO.getUserId());
        pointsLog.setRuleId(UCConstant.POINT_RULE_EXCHANGE_ID);
        pointsLog.setOutgo(orderBO.getTotalPrice().intValue());
        pointsLog.setLogType("POINTS_RECHARGE");
        pointsLog.setRemark("积分兑换 - 订单号："+orderBO.getOrderNo());
        pointsLogService.insert(pointsLog);
    }

    @Override
    public List<OrderListBO> selectExprotOrder(Order order) {
        List<OrderBO> orderBOList = orderRoMapper.selectExprotOrder(order);
        List<OrderBO> orderDataList = orderRoMapper.selectExprotOrder(order);
        List<OrderListBO> orderListBOList = new ArrayList<>();

        for(OrderBO bo:orderBOList){
            OrderListBO orderListBO = new OrderListBO();
            orderListBO.setOrderNo(bo.getOrderNo());
            //收货地址
            String address = bo.getShippingAddress();
            String phone = bo.getContactNumber();
//            if(bo.getUserAddressBO() != null){
//                UserAddressBO userAddress = setUserAddress(bo, address);
                orderListBO.setAddress(address);
//                phone = userAddress.getPhone();
                orderListBO.setPhone(phone);
                orderListBO.setFullName(bo.getConsignee());
//            }
            boolean isAlike = false;
            StringBuffer goodsName = new StringBuffer();
            int num = 0;
            if(address != null && !"".equals(address) && phone != null && !"".equals(phone)) {
                for(OrderBO data:orderDataList){
                    String addressBuffer = data.getShippingAddress();
                    String phoneTemp = data.getContactNumber();
                    //有地址和电话相同的
                    if(address.equals(addressBuffer) && phone.equals(phoneTemp)){
                        isAlike = true;
                        //寄托货物信息合并
                        if(data.getOrderProductBOList() != null){
                            List<OrderProductBO> orderProductBOList = data.getOrderProductBOList();
                            for(OrderProductBO orderProductBO:orderProductBOList){
                                goodsName.append(orderProductBO.getName());
                                goodsName.append(" "+orderProductBO.getSpecInfo());
                            }
                            goodsName.append(";");
                        }
                        num++;
                    }
                }
            }
            //没有地址和电话相同的
            if(!isAlike){
                //寄托货物
                if(bo.getOrderProductBOList() != null){
                    List<OrderProductBO> orderProductBOList = bo.getOrderProductBOList();
                    for(OrderProductBO orderProductBO:orderProductBOList){
                        goodsName.append(orderProductBO.getName());
                        goodsName.append(" "+orderProductBO.getSpecInfo());
                    }
                    goodsName.append(";");
                }
            }
            orderListBO.setGoodsName(goodsName.toString());
            //寄托数量
            orderListBO.setNum(num);
            //支付方式
            orderListBO.setPayMethod(bo.getPayMethod());

            orderListBOList.add(orderListBO);
        }
        return orderListBOList;
    }

    /*//拼接地址信息
    private UserAddressBO setUserAddress(OrderBO bo, StringBuffer address) {
        UserAddressBO userAddress = bo.getUserAddressBO();
        address.append(userAddress.getProvinceName() + "-");
        address.append(userAddress.getCityName() + "-");
        address.append(userAddress.getAreaName() + "-");
        address.append(userAddress.getDetail());
        return userAddress;
    }*/

    @Override
    public void selectImportOrder(List<OrderBO> orderBOList, String expressCompId, HttpServletRequest request) {
        for(OrderBO bo:orderBOList){
            Order data = orderRoMapper.selectByPrimaryKey(bo.getOrderNo());
            if(data == null){
                LOGGER.warn("订单数据不存在：{}",bo);
                throw new ServiceException(4102,"订单数据不存在");
            }
            if(data.getIsShipping() == 2){
                LOGGER.warn("该订单不需要寄送：{}",bo);
                throw new ServiceException(4102,bo.getOrderNo()+"该订单不需要寄送");
            }
            if(bo.getExpressNo() != null && CharUtil.isChinese(bo.getExpressNo())){
                LOGGER.warn("运单号不能存在中文：{}",bo);
                throw new ServiceException(4102,bo.getExpressNo() + "运单号不能存在中文");
            }
            Order order = new Order();
            BeanUtils.copyProperties(bo, order);
            order.setOrderStatus("5");
            order.setExpressCompId(expressCompId);
            order.setLastUpdate(new Date());
            int upd = orderMapper.update(order);
            if(upd != 1){
                LOGGER.warn("修改失败，参数：{}", order);
                throw new ServiceException(4102);
            }
            insertOrderLog(order.getUserId(), order.getOrderNo(), "5", "管理员已发货","0");

            //发送消息
            ExpressComp expressComp = expressCompRoMapper.selectByPrimaryKey(expressCompId);
            if(expressComp == null){
                LOGGER.warn("物流公司查询失败：{}", order.getExpressCompId());
                throw new ServiceException(4102,"物流公司查询失败");
            }
            Message message = new Message();
            message.setBusinessId(order.getOrderNo());
            message.setType(MessageConstant.SPDD);
            message.setContent(MessageConstant.DELIVER_GOODS_PREFIX+expressComp.getCompName()+"+"+order.getExpressNo()+MessageConstant.SUFFIX);
            message.setUserId(order.getUserId());
            messageSendUtil.sendMessage(message, request);
        }
    }

    @Transactional("db1TxManager")
    @Override
    public void sendOrder(OrderOperationBO orderOperationBO, HttpServletRequest request){
        //订单状态，2：待支付，3：支付中，4：待发货，5：待收货，6：已完成，7：已取消
        orderOperationBO.setOrderStatus("5");
        Order order = new Order();
        BeanUtils.copyProperties(orderOperationBO,order);
        int upd = orderMapper.update(order);
        if(upd != 1){
            LOGGER.warn("修改失败，参数：{}", order);
            throw new ServiceException(4102);
        }
        insertOrderLog(Utils.getAdminId(), order.getOrderNo(), "5", orderOperationBO.getRemark(),"0");
        //发送消息
        ExpressComp expressComp = null;
        if(order.getExpressCompId() != null && !"".equals(order.getExpressCompId())){
            expressComp = expressCompRoMapper.selectByPrimaryKey(order.getExpressCompId());
        }
        Message message = new Message();
        message.setBusinessId(order.getOrderNo());
        message.setType(MessageConstant.SPDD);
        if(expressComp != null){
            message.setContent(MessageConstant.DELIVER_GOODS_PREFIX+expressComp.getCompName()+"+"+order.getExpressNo()+MessageConstant.SUFFIX);
        }else{
            message.setContent(MessageConstant.DELIVER_GOODS_PREFIX+order.getExpressNo()+MessageConstant.SUFFIX);
            message.setContent(MessageConstant.DELIVER_GOODS_PREFIX_NO+"<a href=\""+SpringCtxHolder.getProperty("abc12366.api.url.uc")+"/orderDetail/"+order.getOrderNo()+"\">"+order.getOrderNo()+"</a>"+MessageConstant.SUFFIX);
        }

        message.setUserId(order.getUserId());
        messageSendUtil.sendMessage(message, request);
    }

    @Transactional("db1TxManager")
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

    @Transactional("db1TxManager")
    @Override
    public void automaticReceipt() {
        Date date = DataUtils.getAddDate(UCConstant.ORDER_RECEIPT_DAYS);
        //查询15天之前未确认的订单
        List<Order> orderList = orderRoMapper.selectReceiptOrderByDate(date);
        for(Order order:orderList){
            order.setOrderStatus("6");
            orderMapper.update(order);
            insertOrderLog("", order.getOrderNo(), "6", "系统自动确认收货","0");

            //发送消息
//            Message message = new Message();
//            message.setBusinessId(order.getOrderNo());
//            message.setType(MessageConstant.SPDD);
//            message.setContent(MessageConstant.AUTOMATIC_CONFIRMATION_RECEIPT+"<a href=\""+SpringCtxHolder.getProperty("abc12366.api.url.uc")+"/orderDetail/"+order.getOrderNo()+"\">"+order.getOrderNo()+"</a>");
//            message.setUserId(order.getUserId());
//            messageSendUtil.sendMessage(message);
        }
    }

    @Transactional("db1TxManager")
    @Override
    public void automaticCancel() {
        Date date = DataUtils.getAddTime(UCConstant.ORDER_CANCEL_TIME);
        //查询两个小时未支付的订单，自动取消
        List<Order> orderList = orderRoMapper.selectCancelOrderByDate(date);
        for(Order order:orderList){
            order.setOrderStatus("7");
            orderMapper.update(order);
            insertOrderLog("", order.getOrderNo(), "7" ,"系统自动取消订单","0");
        }
    }

    @Override
    public OrderBO selectOrderByGoodsIdAndUserId(Order order) {
        return orderRoMapper.selectOrderByGoodsIdAndUserId(order);
    }

    @Transactional("db1TxManager")
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

    @Transactional("db1TxManager")
    @Override
    public void confirmOrder(Order order) {
        order.setOrderStatus("6");
        int update = orderMapper.update(order);
        if(update != 1){
            LOGGER.warn("修改失败，参数：{}", order);
            throw new ServiceException(4102);
        }
        insertOrderLog(order.getUserId(), order.getOrderNo(), "6" ,"用户确认收货","0");
    }

    @Override
    public List<OrderBO> selectOrderListByInvoice(OrderBO order, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<OrderBO> oList = orderRoMapper.selectOrderList(order);
        return oList;
    }

    @Override
    public List<OrderBO> selectCurriculumOrderList(OrderBO orderBO, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        return orderRoMapper.selectCurriculumOrderList(orderBO);
    }

}
