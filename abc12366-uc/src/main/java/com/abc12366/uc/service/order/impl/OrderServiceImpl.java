package com.abc12366.uc.service.order.impl;

import com.abc12366.gateway.component.SpringCtxHolder;
import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.*;
import com.abc12366.uc.mapper.db1.*;
import com.abc12366.uc.mapper.db2.*;
import com.abc12366.uc.model.Dict;
import com.abc12366.uc.model.ExpressComp;
import com.abc12366.uc.model.Message;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.bo.PointsLogBO;
import com.abc12366.uc.model.bo.PointsRuleBO;
import com.abc12366.uc.model.bo.VipLogBO;
import com.abc12366.uc.model.bo.VipPrivilegeLevelBO;
import com.abc12366.uc.model.order.*;
import com.abc12366.uc.model.order.bo.*;
import com.abc12366.uc.service.*;
import com.abc12366.uc.service.order.OrderService;
import com.abc12366.uc.util.CharUtil;
import com.abc12366.uc.service.MessageSendUtil;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;

/**
 * @create 2017-10-19
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
    private TradeLogRoMapper tradeLogRoMapper;

    @Autowired
    private TradeMapper tradeMapper;

    @Autowired
    private TradeRoMapper traderoMapper;

    @Autowired
    private UserAddressRoMapper userAddressRoMapper;

    @Autowired
    private ExpressCompRoMapper expressCompRoMapper;

    @Autowired
    private MessageSendUtil messageSendUtil;

    @Autowired
    private TodoTaskService todoTaskService;

    @Autowired
    private PointsRuleService pointsRuleService;

    @Autowired
    private IWxTemplateService templateService;

    @Autowired
    private VipPrivilegeLevelRoMapper vipPrivilegeLevelRoMapper;

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
        order.setOrderNo(DateUtils.getDateToString());
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
    public OrderBO submitOrder(OrderSubmitBO orderSubmitBO) {
        Date date = new Date();
        //根据是否需要寄送来判断地址是否填写
        if (orderSubmitBO.getIsShipping() != null && orderSubmitBO.getIsShipping() == 1) {
            if (orderSubmitBO.getConsignee() == null || "".equals(orderSubmitBO.getConsignee())
                    || orderSubmitBO.getContactNumber() == null && "".equals(orderSubmitBO.getContactNumber())
                    || orderSubmitBO.getShippingAddress() == null && "".equals(orderSubmitBO.getShippingAddress())) {
                LOGGER.info("需要寄送的商品必须填写联系人、联系方式、寄送地址：{}", orderSubmitBO);
                throw new ServiceException(4922);
            }
        }
        Order order = new Order();
        //加入订单与产品关系信息
        List<OrderProductBO> orderProductBOs = orderSubmitBO.getOrderProductBOList();
        if (orderProductBOs == null) {
            LOGGER.info("产品信息错误：{}", orderSubmitBO);
            throw new ServiceException(4166);
        } else {
            for (OrderProductBO orderProductBO : orderProductBOs) {
                //判断是否需要查询产品库存信息
                if (orderProductBO.getProductId() != null && !"".equals(orderProductBO.getProductId())) {
                    ProductBO prBO = productRoMapper.selectBOById(orderProductBO.getProductId());
                    if (prBO == null) {
                        throw new ServiceException(4903, "商品信息错误");
                    }
                    if (prBO.getStock() == null) {
                        throw new ServiceException(4903, "商品库存信息异常");
                    }
                    if (prBO.getStock() < orderProductBO.getNum()) {
                        LOGGER.info("商品库存不足：{}", prBO);
                        throw new ServiceException(4903);
                    }

                }
                BeanUtils.copyProperties(orderSubmitBO, order);
                order.setOrderStatus("2");
                String orderNo = DateUtils.getDateToString();
                order.setOrderNo(orderNo);

                order.setCreateTime(date);
                order.setLastUpdate(date);
                order.setIsInvoice(false);

                int insert = orderMapper.insert(order);
                if (insert != 1) {
                    LOGGER.info("提交产品订单失败：{}", orderSubmitBO);
                    throw new ServiceException(4139);
                }

                orderProductBO.setOrderNo(order.getOrderNo());
                OrderProduct orderProduct = new OrderProduct();
                BeanUtils.copyProperties(orderProductBO, orderProduct);
                int opInsert = orderProductMapper.insert(orderProduct);
                if (opInsert != 1) {
                    LOGGER.info("提交订单与产品关系信息失败：{}", orderProduct);
                    throw new ServiceException(4167);
                }

                insertOrderLog(orderSubmitBO.getUserId(), order.getOrderNo(), "2", "用户新增订单", "0");
            }
        }
        //新增交易流水号
        String tradeNo = DateUtils.getJYLSH();
        TradeLog tradeLog = new TradeLog();
        tradeLog.setLastUpdate(date);
        tradeLog.setCreateTime(date);
        tradeLog.setTradeNo(tradeNo);
        LOGGER.info("新增交易日志记录：{}" + tradeNo);
        int logInsert = tradeLogMapper.insert(tradeLog);
        if (logInsert != 1) {
            LOGGER.info("新增交易日志记录失败：{}", tradeNo);
            throw new ServiceException(4919);
        }
        Trade trade = new Trade();
        trade.setTradeNo(tradeNo);
        trade.setOrderNo(order.getOrderNo());
        trade.setCreateTime(date);
        trade.setLastUpdate(date);
        int tradeInsert = tradeMapper.insert(trade);
        if (tradeInsert != 1) {
            LOGGER.info("新增交易日志与订单关系失败：{}", tradeNo);
            throw new ServiceException(4920);
        }

        OrderBO temp = new OrderBO();
        BeanUtils.copyProperties(order, temp);
        TradeBO tradeBO = new TradeBO();
        BeanUtils.copyProperties(trade, tradeBO);
        temp.setTradeBO(tradeBO);

        return temp;

    }

    /**
     * 处理人民币购买服务
     */
    @Transactional("db1TxManager")
    private void operationMoneyServiceOrder(OrderBO orderBO, Order order, OrderProductBO orderProductBO, String orderStatus) {
        orderProductBO.setOrderNo(orderBO.getOrderNo());
        orderBO.setOrderStatus(orderStatus);
        BeanUtils.copyProperties(orderBO, order);
        int insert = orderMapper.insert(order);
        if (insert != 1) {
            LOGGER.info("提交产品订单失败：{}", orderBO);
            throw new ServiceException(4139);
        }
        orderProductBO.setOrderNo(orderBO.getOrderNo());
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
    private void operationMoneyRechargeOrder(Order order, OrderProductBO orderProductBO, String orderStatus) {
        // 查询用户信息
        order.setOrderStatus(orderStatus);
        orderProductBO.setOrderNo(order.getOrderNo());
        int insert = orderMapper.insert(order);
        if (insert != 1) {
            LOGGER.info("提交产品订单失败：{}", order);
            throw new ServiceException(4139);
        }
        orderProductBO.setOrderNo(order.getOrderNo());
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
    private void operationPointsOrder(OrderBO orderBO, Order order, OrderProductBO orderProductBO, String orderStatus) {
        orderProductBO.setOrderNo(orderBO.getOrderNo());
        //加入订单信息,
        orderBO.setOrderStatus(orderStatus);

        BeanUtils.copyProperties(orderBO, order);
        int insert = orderMapper.insert(order);
        if (insert != 1) {
            LOGGER.info("提交产品订单失败：{}", orderBO);
            throw new ServiceException(4139);
        }

        orderProductBO.setOrderNo(orderBO.getOrderNo());
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
    private void insertOrderLog(String userId, String orderNo, String status, String remark, String logType) {
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
        //订单状态，2：待付款，3：付款中，4：付款成功，5：已发货，6：已完成，7：已结束，8：付款失败，9：已退单，11：已删除
        if ("7".equals(bo.getOrderStatus()) || "2".equals(bo.getOrderStatus())) {
            order.setOrderStatus("11");
            int del = orderMapper.update(order);
            if (del != 1) {
                LOGGER.info("删除失败：{}", orderBO);
                throw new ServiceException(4103);
            }
            insertOrderLog(orderBO.getUserId(), orderBO.getOrderNo(), "11", "用户删除订单", "0");
        } else {
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
        //查询订单和交易记录对应关系
        Trade trade = traderoMapper.selectOrderNo(bo.getOrderNo());
        if(trade != null){
            TradeLog tradeLog = new TradeLog();
            tradeLog.setTradeNo(trade.getTradeNo());
            tradeLog.setTradeStatus("4");
            //更新交易记录信息
            tradeLogMapper.update(tradeLog);
        }

        insertOrderLog(bo.getUserId(), bo.getOrderNo(), "7", "用户取消订单", "0");
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
        insertOrderLog(orderBack.getUserId(), orderBack.getOrderNo(), "1", "用户提交退单", "1");
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
        if (oUpdate != 1) {
            LOGGER.info("修改失败：{}", order);
            throw new ServiceException(4102);
        }
        insertOrderLog(orderBack.getUserId(), orderBack.getOrderNo(), "7", "用户填写快递号", "1");
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
            insertOrderLog(orderBack.getUserId(), orderNo, "6", "管理员允许退单", "1");

            //获取订单和产品关系信息
            OrderProductBO pBO = new OrderProductBO();
            pBO.setOrderNo(orderBack.getOrderNo());
            List<OrderProductBO> orderProductBOs = orderProductRoMapper.selectByOrderNo(pBO);
            for (OrderProductBO orderProductBO : orderProductBOs) {
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
            Order order = new Order();
            order.setOrderNo(orderBack.getOrderNo());
            order.setOrderStatus("6");
            orderMapper.update(order);
            insertOrderLog(orderBack.getUserId(), orderBack.getOrderNo(), "6", "管理员不允许退单", "1");
        }
        return orderBack;
    }

    @Override
    public List<OrderBackBO> selectOrderBackList(OrderBackBO orderBackBO) {
        return orderBackRoMap.selectOrderBackList(orderBackBO);
    }

    @Transactional("db1TxManager")
    @Override
    public void paymentOrder(OrderPayBO orderPayBO, String type, HttpServletRequest request) {
        String tradeNo = orderPayBO.getTradeNo();
        //根据交易流水号查询订单信息
        List<OrderBO> orderBOList = orderRoMapper.selectByTradeNo(tradeNo);

        for (OrderBO orderBO : orderBOList) {
            if (orderBO != null) {
                OrderProductBO pBO = new OrderProductBO();
                String orderNo = orderBO.getOrderNo();
                pBO.setOrderNo(orderNo);
                List<OrderProductBO> orderProductBOs = orderProductRoMapper.selectByOrderNo(pBO);
                for (OrderProductBO orderProductBO : orderProductBOs) {
                    int isPay = orderPayBO.getIsPay();
                    Order order = new Order();
                    orderBO.setLastUpdate(new Date());
                    BeanUtils.copyProperties(orderBO, order);
                    //订单状态，2：待付款，3：付款中，4：付款成功，5：已发货，6：已完成，7：已结束，8：付款失败，9：已退单
                    //UC商城:UCSC，财税课堂:CSKT，积分充值:JFCZ，会员充值:HYCZ
                    String trading = orderProductBO.getTradingChannels();
                    order.setPayMethod(orderPayBO.getPayMethod());
                    if ("RMB".equals(type)) {
                        //支付状态，1：支付中，2：支付成功，3：支付失败，
                        if (isPay == 1) {
                            order.setOrderStatus("3");
                            //修改订单状态
                            updOrder(order);
                            insertOrderLog(orderBO.getUserId(), orderNo, "3", "用户付款中", "0");
                        } else if (isPay == 2) {
                            if(!"2".equals(orderBO.getOrderStatus()) && !"3".equals(orderBO.getOrderStatus())){
                                LOGGER.warn("订单只有在待支付或支付中才能进行正常支付：{}",orderBO.getOrderStatus());
                                throw new ServiceException(4925);
                            }
                            //判断是否需要查询产品库存信息
                            setTodoTask(orderBO);
                            if (trading.equals("UCSC")) {
                                updateStock(orderBO, orderProductBO);
                                order.setOrderStatus("4");
                                //修改订单状态
                                updOrder(order);

                                insertPoints(orderBO);
                                insertOrderLog(orderBO.getUserId(), orderNo, order.getOrderStatus(), "用户付款成功","0");
                            } else if (trading.equals("HYCZ")) {
                                order.setOrderStatus("6");
                                //修改订单状态
                                updOrder(order);

                                insertPoints(orderBO);
                                LOGGER.info("查询是否为会员服务订单，支付成功则更新会员状态: {}", orderNo);
                                userService.updateUserVipInfo(orderBO.getUserId(), orderProductBO.getSpecInfo());

                                LOGGER.info("插入会员日志: {}", orderNo);
                                insertVipLog(orderNo, orderBO.getUserId(), orderProductBO.getSpecInfo());

                                insertOrderLog(orderBO.getUserId(), orderNo, order.getOrderStatus(), "用户付款成功，完成订单", "0");
                                //发送消息
                                sendMemberMsg(orderProductBO, order,request);
                            } else if (trading.equals("CSKT")) {
                                order.setOrderStatus("6");
                                //修改订单状态
                                updOrder(order);

                                insertPoints(orderBO);
                                insertOrderLog(orderBO.getUserId(), orderNo, order.getOrderStatus(), "用户付款成功，完成订单","0");
                                //发送消息
                                sendPointsMsg(orderProductBO, order,request);
                            }else if (trading.equals("JFCZ")) {
                                order.setOrderStatus("6");
                                //修改订单状态
                                updOrder(order);

                                insertPoints(orderBO);
                                insertOrderLog(orderBO.getUserId(), orderNo, order.getOrderStatus(), "用户付款成功，完成订单","0");
                            }
//                        }
                        } else if (isPay == 3) {
                            order.setOrderStatus("2");
                            //修改订单状态
                            updOrder(order);
                            insertOrderLog(orderBO.getUserId(), orderNo, "2", "等待用户付款", "0");
                        }
                    } else if ("POINTS".equals(type)) {

                        //UC商城:UCSC，财税课堂:CSKT，积分充值:JFCZ，会员充值:HYCZ
                        //判断是否需要查询产品库存信息
                        if (trading.equals("UCSC")) {
                            updateStock(orderBO, orderProductBO);
                            order.setOrderStatus("4");
                            //修改订单状态
                            updOrder(order);
                            insertDeductPoints(orderBO);
                            insertOrderLog(orderBO.getUserId(), orderNo, "4", "用户付款成功", "0");
                        } else if (trading.equals("HYCZ")) {
                            order.setOrderStatus("6");
                            //修改订单状态
                            updOrder(order);
                            insertDeductPoints(orderBO);
                            LOGGER.info("查询是否为会员服务订单，支付成功则更新会员状态: {}", orderNo);
                            userService.updateUserVipInfo(orderBO.getUserId(), orderProductBO.getSpecInfo());

                            LOGGER.info("插入会员日志: {}", orderNo);
                            insertVipLog(orderNo, orderBO.getUserId(), orderProductBO.getSpecInfo());

                            insertOrderLog(orderBO.getUserId(), orderNo, "6", "用户付款成功，完成订单","0");
                            sendMemberMsg(orderProductBO, order,request);
                        }else if (trading.equals("CSKT")) {
                            order.setOrderStatus("6");
                            //修改订单状态
                            updOrder(order);
                            insertDeductPoints(orderBO);
                            insertOrderLog(orderBO.getUserId(), orderNo, "6", "用户付款成功，完成订单","0");
                        }
                        insertTradeLog(order,tradeNo);
                    }
                }
            }
        }
    }

    private void updOrder(Order order) {
        int update = orderMapper.update(order);
        if(update != 1){
            LOGGER.warn("修改失败，参数：{}", order);
            throw new ServiceException(4102);
        }
    }

    /**
     * 加入消费赠送积分规则
     *
     * @param order
     */
    private void setTodoTask(OrderBO order) {
        double amount = order.getTotalPrice();
        String userId = order.getUserId();
        todoTaskService.doTask(userId, TaskConstant.SYS_TASK_FIRST_CONSUME_CODE);
        if (amount >= 1000 && amount < 3000) {
            todoTaskService.doTask(userId, TaskConstant.SYS_TASK_CONSUME_BEYOND_1000_CODE);
        }

        if (amount >= 3000 && amount < 5000) {
            todoTaskService.doTask(userId, TaskConstant.SYS_TASK_CONSUME_BEYOND_3000_CODE);
        }
        if (amount >= 5000) {
            todoTaskService.doTask(userId, TaskConstant.SYS_TASK_CONSUME_BEYOND_5000_CODE);
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
        if (stock < 0) {
            LOGGER.info("库存不足,请联系管理员：{}", stock);
            orderBO.setRemark(orderProductBO.getName()+"库存不足,请联系客服。");
            //throw new ServiceException(4905);
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

    @Override
    public void selectStock(String tradeNo) {
        LOGGER.info("根据交易流水号判断库存{}", tradeNo);
        List<OrderBO> orderBOList = orderRoMapper.selectByTradeNo(tradeNo);

        for (OrderBO orderBO : orderBOList) {
            OrderProductBO pBO = new OrderProductBO();
            String orderNo = orderBO.getOrderNo();
            pBO.setOrderNo(orderNo);
            List<OrderProductBO> orderProductBOs = orderProductRoMapper.selectByOrderNo(pBO);
            for (OrderProductBO orderProductBO : orderProductBOs) {
                //查询产品库存信息
                if(orderProductBO.getProductId() != null && !"".equals(orderProductBO.getProductId())){
                    ProductBO prBO = productRoMapper.selectBOById(orderProductBO.getProductId());
                    orderProductBO.setOrderNo(orderBO.getOrderNo());
                    //减去Product库存数量
                    int num = orderProductBO.getNum();
                    int stock = prBO.getStock() - num;
                    if (stock < 0) {
                        LOGGER.info("库存不足,请联系管理员：{}", stock);
                        orderBO.setRemark(orderProductBO.getName()+"库存不足,请联系客服。");
                        throw new ServiceException(4905);
                    }
                }
            }
        }
    }

    /**
     * 购买会员，消息发送
     */
    private void sendMemberMsg(OrderProductBO orderProductBO, Order order, HttpServletRequest request) {
        User user = userRoMapper.selectOne(order.getUserId());
        //查询会员特权-业务提醒
        VipPrivilegeLevelBO obj = new VipPrivilegeLevelBO();
        obj.setLevelId(user.getVipLevel());
        obj.setPrivilegeId(MessageConstant.YWTX_CODE);
        VipPrivilegeLevelBO findObj = vipPrivilegeLevelRoMapper.selectLevelIdPrivilegeId(obj);
        //查看业务提醒是否启用
        if(findObj != null && findObj.getStatus()){
            Message message = new Message();
            message.setBusinessId(order.getOrderNo());
            message.setBusiType(MessageConstant.SPDD);
            message.setType(MessageConstant.SYS_MESSAGE);
            String content = RemindConstant.BUYING_MEMBERS_PREFIX.replaceAll("\\{#DATA.VIP\\}", orderProductBO.getName());
            message.setContent(content);
            message.setUrl("<a href=\"" + SpringCtxHolder.getProperty("abc12366.api.url.uc") + "/member/member_rights.html\">" + MessageConstant.VIEW_DETAILS + "</a>");
            message.setUserId(order.getUserId());

            //web消息
            if(findObj.getVal1() != null && MessageConstant.YWTX_WEB.equals(findObj.getVal1())){
                messageSendUtil.sendMessage(message, request);
            }


            //微信消息
            if(findObj.getVal2() != null && MessageConstant.YWTX_WECHAT.equals(findObj.getVal2()) && StringUtils.isNotEmpty(user.getWxopenid())){
                Map<String, String> map = new HashMap<String, String>();
                map.put("userId", user.getId());
                map.put("openId", user.getWxopenid());
                map.put("first", "亲爱的" + user.getUsername() + "，恭喜您成功升级为 VIP 会员！");
                map.put("remark", "感恩您的参与和支持，谢谢！。");
                map.put("keyword1", user.getVipLevelName());
                map.put("keyword2", orderProductBO.getName());
                map.put("keyword3", DateUtils.dateToStr(new Date()));
                templateService.templateSend("GrA5UnnYg39Rhs2nyDzwoFiYfmZh5sFkNXTZWGGmrkY", map);
            }

            //短信消息
            if(findObj.getVal3() != null && MessageConstant.YWTX_MESSAGE.equals(findObj.getVal3()) && StringUtils.isNotEmpty(user.getPhone())){
                sendPhoneMessage(request, content, user);
            }
        }
    }

    /**
     * 积分充值，消息发送
     */
    private void sendPointsMsg(OrderProductBO orderProductBO, Order order, HttpServletRequest request) {
        User user = userRoMapper.selectOne(order.getUserId());
        //查询会员特权-业务提醒
        VipPrivilegeLevelBO obj = new VipPrivilegeLevelBO();
        obj.setLevelId(user.getVipLevel());
        obj.setPrivilegeId(MessageConstant.YWTX_CODE);
        VipPrivilegeLevelBO findObj = vipPrivilegeLevelRoMapper.selectLevelIdPrivilegeId(obj);
        //查看业务提醒是否启用
        if(findObj != null && findObj.getStatus()){
            Message message = new Message();
            message.setBusinessId(order.getOrderNo());
            message.setBusiType(MessageConstant.SPDD);
            message.setType(MessageConstant.SYS_MESSAGE);
            String content = RemindConstant.INTEGRAL_RECHARGE.replaceAll("\\{#DATA.POINT\\}", String.valueOf(user.getPoints()));
            message.setUrl("<a href=\"" + SpringCtxHolder.getProperty("abc12366.api.url.uc") + "/pointsExchange/points.php\">" + MessageConstant.VIEW_DETAILS + "</a>");
            message.setUserId(order.getUserId());
            //web消息
            if(findObj.getVal1() != null && MessageConstant.YWTX_WEB.equals(findObj.getVal1())){
                messageSendUtil.sendMessage(message, request);
            }

            //微信消息
            if(findObj.getVal2() != null && MessageConstant.YWTX_WECHAT.equals(findObj.getVal2()) && StringUtils.isNotEmpty(user.getWxopenid())){
                Map<String, String> map = new HashMap<String, String>();
                map.put("userId", user.getId());
                map.put("openId", user.getWxopenid());
                map.put("first", "亲爱的"+user.getUsername()+"，你已兑换成功。");
                map.put("remark", "感谢你的使用。");
                map.put("keyword1", orderProductBO.getName());
                map.put("keyword2", String.valueOf(order.getGiftPoints()));
                templateService.templateSend("mfSWjnagZEzWLYz1Xp8LfQXKLos2fBE7QFoShCwGJkU", map);
            }

            //短信消息
            if(findObj.getVal3() != null && MessageConstant.YWTX_MESSAGE.equals(findObj.getVal3()) && StringUtils.isNotEmpty(user.getPhone())){
                sendPhoneMessage(request, content, user);
            }
        }
    }

    /**
     * 插入交易流水记录
     */
    private void insertTradeLog(Order order,String tradeNo) {
        //加入交易日志
            TradeLog tradeLog = new TradeLog();
            tradeLog.setTradeNo(tradeNo);
            tradeLog.setAliTrandeNo(tradeNo);
            tradeLog.setTradeStatus("2");
            tradeLog.setTradeType("1");
            tradeLog.setAmount(order.getTotalPrice());
            Timestamp now = new Timestamp(System.currentTimeMillis());
            tradeLog.setTradeTime(now);
            tradeLog.setCreateTime(now);
            tradeLog.setLastUpdate(now);
            tradeLog.setPayMethod(order.getPayMethod());
            tradeLogMapper.update(tradeLog);
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
     *
     * @param orderBO
     */
    private void insertPoints(OrderBO orderBO) {
        if (orderBO != null && orderBO.getGiftPoints() != null && orderBO.getGiftPoints() > 0) {
            //如果积分规则为空则返回
            PointsRuleBO pointsRuleBO = pointsRuleService.selectValidOneByCode(TaskConstant.POINT_RULE_ORDER_CODE);
            if (pointsRuleBO == null) {
                return;
            }

            PointsLogBO pointsLog = new PointsLogBO();
            pointsLog.setUserId(orderBO.getUserId());
            pointsLog.setRuleId(pointsRuleBO.getId());
            pointsLog.setId(Utils.uuid());
            pointsLog.setIncome(orderBO.getGiftPoints());
            pointsLog.setRemark("用户下单 - 订单号：" + orderBO.getOrderNo());
            pointsLog.setLogType("ORDER_INCOME");
            pointsLogService.insertByConsume(pointsLog);
        }
    }

    /**
     * 插入所扣去的积分
     *
     * @param orderBO
     */
    private void insertDeductPoints(OrderBO orderBO) {
        //如果积分规则为空则返回
        PointsRuleBO pointsRuleBO = pointsRuleService.selectValidOneByCode(TaskConstant.POINT_RULE_EXCHANGE_CODE);
        if (pointsRuleBO == null) {
            return;
        }
        PointsLogBO pointsLog = new PointsLogBO();
        pointsLog.setUserId(orderBO.getUserId());
        pointsLog.setRuleId(pointsRuleBO.getId());
        pointsLog.setOutgo(orderBO.getTotalPrice().intValue());
        pointsLog.setLogType("POINTS_RECHARGE");
        pointsLog.setRemark("积分兑换 - 订单号：" + orderBO.getOrderNo());
        pointsLogService.insert(pointsLog);
    }

    @Override
    public List<OrderListBO> selectExprotOrder(Order order) {
        List<OrderBO> orderBOList = orderRoMapper.selectExprotOrder(order);
        List<OrderBO> orderDataList = new ArrayList<>();
        orderDataList.addAll(orderBOList);
        List<OrderListBO> orderListBOList = new ArrayList<>();

        for (OrderBO bo : orderBOList) {
            OrderListBO orderListBO = new OrderListBO();
            orderListBO.setOrderNo(bo.getOrderNo());
            //收货地址
            String address = bo.getShippingAddress();
            String phone = bo.getContactNumber();
            String consignee = bo.getConsignee();
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
            if (address != null && !"".equals(address) && phone != null && !"".equals(phone)) {
                for (OrderBO data : orderDataList) {
                    String addressBuffer = data.getShippingAddress();
                    String phoneTemp = data.getContactNumber();
                    //有地址和电话相同的
                    if (address.equals(addressBuffer) && phone.equals(phoneTemp)) {
                        isAlike = true;
                        //寄托货物信息合并
                        if (data.getOrderProductBOList() != null) {
                            List<OrderProductBO> orderProductBOList = data.getOrderProductBOList();
                            for (OrderProductBO orderProductBO : orderProductBOList) {
                                goodsName.append(orderProductBO.getName());
                                goodsName.append(" " + orderProductBO.getSpecInfo());
                            }
                            goodsName.append(";");
                        }
                        num++;
                    }
                }
            }
            //没有地址和电话相同的
            if (!isAlike) {
                //寄托货物
                if (bo.getOrderProductBOList() != null) {
                    List<OrderProductBO> orderProductBOList = bo.getOrderProductBOList();
                    for (OrderProductBO orderProductBO : orderProductBOList) {
                        goodsName.append(orderProductBO.getName());
                        goodsName.append(" " + orderProductBO.getSpecInfo());
                    }
                    goodsName.append(";");
                }
            }
            orderListBO.setFullName(consignee);
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
        for (OrderBO bo : orderBOList) {
            Order data = orderRoMapper.selectByPrimaryKey(bo.getOrderNo());
            if (data == null) {
                LOGGER.warn("订单数据不存在：{}", bo);
                throw new ServiceException(4102, "订单数据不存在");
            }
            if (data.getIsShipping() == 2) {
                LOGGER.warn("该订单不需要寄送：{}", bo);
                throw new ServiceException(4102, bo.getOrderNo() + "该订单不需要寄送");
            }
            if (bo.getExpressNo() != null && CharUtil.isChinese(bo.getExpressNo())) {
                LOGGER.warn("运单号不能存在中文：{}", bo);
                throw new ServiceException(4102, bo.getExpressNo() + "运单号不能存在中文");
            }
            Order order = new Order();
            BeanUtils.copyProperties(bo, order);
            order.setOrderStatus("5");
            order.setExpressCompId(expressCompId);
            order.setLastUpdate(new Date());
            //修改订单状态
            updOrder(order);
            insertOrderLog(data.getUserId(), order.getOrderNo(), "5", "管理员已发货", "0");



            //查询用户信息
            User user = userRoMapper.selectOne(data.getUserId());
            //查询会员特权
            VipPrivilegeLevelBO obj = new VipPrivilegeLevelBO();
            obj.setLevelId(user.getVipLevel());
            obj.setPrivilegeId("A_YWTX");
            VipPrivilegeLevelBO findObj = vipPrivilegeLevelRoMapper.selectLevelIdPrivilegeId(obj);
            if(findObj != null && findObj.getStatus()){
                //发送消息
                ExpressComp expressComp = expressCompRoMapper.selectByPrimaryKey(expressCompId);
                if (expressComp == null) {
                    LOGGER.warn("物流公司查询失败：{}", order.getExpressCompId());
                    throw new ServiceException(4102, "物流公司查询失败");
                }
                Message message = new Message();
                String content = RemindConstant.DELIVER_GOODS_PREFIX+order.getOrderNo()+ RemindConstant.DELIVER_GOODS_YDH + expressComp.getCompName() + "+" + order.getExpressNo() + RemindConstant.SUFFIX;

                //web消息
                if(findObj.getVal1() != null && MessageConstant.YWTX_WEB.equals(findObj.getVal1())) {
                    message.setBusinessId(data.getOrderNo());
                    message.setBusiType(MessageConstant.SPDD);
                    message.setType(MessageConstant.SYS_MESSAGE);
                    message.setContent(content);
                    message.setUserId(data.getUserId());
                    messageSendUtil.sendMessage(message, request);
                }
                if(findObj.getVal2() != null && MessageConstant.YWTX_WECHAT.equals(findObj.getVal2()) && StringUtils.isNotEmpty(user.getWxopenid())){
                    //发送微信消息
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("userId", user.getId());
                    map.put("openId", user.getWxopenid());
                    map.put("first", "你有新的订单提醒：");
                    map.put("remark", "详情请登录财税平台查看。");
                    map.put("keyword1", data.getOrderNo());
                    map.put("keyword2", Utils.getAdminInfo().getNickname());
                    map.put("keyword3", DateUtils.dateToStr(new Date()));
                    templateService.templateSend("mfSWjnagZEzWLYz1Xp8LfQXKLos2fBE7QFoShCwGJkU", map);
                }
                //发送短信
                if(findObj.getVal3() != null && MessageConstant.YWTX_WECHAT.equals(findObj.getVal3()) && StringUtils.isNotEmpty(user.getPhone())){
                    sendPhoneMessage(request, content, user);

                }
            }
        }
    }

    /**
     * 发送短信公告方法
     * @param request
     * @param content
     * @param user
     */
    private void sendPhoneMessage(HttpServletRequest request, String content, User user) {
        //发送短信
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("var", content);
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        list.add(maps);

        String accessToken = request.getHeader(Constant.APP_TOKEN_HEAD);
        messageSendUtil.sendPhoneMessage(user.getPhone(),"625", list, accessToken);
    }

    @Transactional("db1TxManager")
    @Override
    public void sendOrder(OrderOperationBO orderOperationBO, HttpServletRequest request) {
        //订单状态，2：待支付，3：支付中，4：待发货，5：待收货，6：已完成，7：已取消
        orderOperationBO.setOrderStatus("5");
        Order order = new Order();
        BeanUtils.copyProperties(orderOperationBO, order);
        //修改订单状态
        updOrder(order);
        insertOrderLog(Utils.getAdminId(), order.getOrderNo(), "5", orderOperationBO.getRemark(), "0");


        User user = userRoMapper.selectOne(order.getUserId());
        //查询会员特权-业务提醒
        VipPrivilegeLevelBO obj = new VipPrivilegeLevelBO();
        obj.setLevelId(user.getVipLevel());
        obj.setPrivilegeId(MessageConstant.YWTX_CODE);
        VipPrivilegeLevelBO findObj = vipPrivilegeLevelRoMapper.selectLevelIdPrivilegeId(obj);
        //查看业务提醒是否启用
        if(findObj != null && findObj.getStatus()){
            //发送普通业务消息
            ExpressComp expressComp = null;
            if (order.getExpressCompId() != null && !"".equals(order.getExpressCompId())) {
                expressComp = expressCompRoMapper.selectByPrimaryKey(order.getExpressCompId());
            }
            String content;
            Message message = new Message();
            message.setBusinessId(order.getOrderNo());
            message.setBusiType(MessageConstant.SPDD);
            message.setType(MessageConstant.SYS_MESSAGE);
            if (expressComp != null) {
                content = RemindConstant.DELIVER_GOODS_PREFIX + expressComp.getCompName() + "+" + order.getExpressNo() + RemindConstant.SUFFIX;
                message.setContent(content);
            } else {
                content = RemindConstant.DELIVER_GOODS_PREFIX_NO + order.getOrderNo() + RemindConstant.SUFFIX;
                message.setContent(content);
                message.setUrl("<a href=\"" + SpringCtxHolder.getProperty("abc12366.api.url.uc") + "/orderDetail/" + order.getOrderNo() + "\">" + MessageConstant.VIEW_DETAILS + "</a>");
            }
            //web消息
            if(findObj.getVal1() != null && MessageConstant.YWTX_WEB.equals(findObj.getVal1())) {
                message.setUserId(order.getUserId());
                messageSendUtil.sendMessage(message, request);
            }
            //微信消息
            if(findObj.getVal2() != null && MessageConstant.YWTX_WECHAT.equals(findObj.getVal2()) && StringUtils.isNotEmpty(user.getWxopenid())){
                Map<String, String> map = new HashMap<String, String>();
                map.put("userId", user.getId());
                map.put("openId", user.getWxopenid());
                map.put("first", "你有新的订单提醒：");
                map.put("remark", "详情请登录财税平台查看。");
                map.put("keyword1", order.getOrderNo());
                map.put("keyword2", Utils.getAdminInfo().getNickname());
                map.put("keyword3", DateUtils.dateToStr(new Date()));
                templateService.templateSend("mfSWjnagZEzWLYz1Xp8LfQXKLos2fBE7QFoShCwGJkU", map);
            }

            //短信消息
            if(findObj.getVal3() != null && MessageConstant.YWTX_MESSAGE.equals(findObj.getVal3()) && StringUtils.isNotEmpty(user.getPhone())){
                sendPhoneMessage(request, content, user);
            }
        }

    }

    @Transactional("db1TxManager")
    @Override
    public void invalidOrder(OrderOperationBO orderOperationBO) {
        orderOperationBO.setOrderStatus("6");
        Order order = new Order();
        BeanUtils.copyProperties(orderOperationBO, order);
        //修改订单状态
        updOrder(order);
    }

    @Transactional("db1TxManager")
    @Override
    public void automaticReceipt() {
        Date date = DateUtils.getAddDate(Constant.ORDER_RECEIPT_DAYS);
        //查询15天之前未确认的订单
        List<Order> orderList = orderRoMapper.selectReceiptOrderByDate(date);
        for (Order order : orderList) {
            order.setOrderStatus("6");
            orderMapper.update(order);
            insertOrderLog("", order.getOrderNo(), "6", "系统自动确认收货", "0");
        }
    }

    @Transactional("db1TxManager")
    @Override
    public void automaticCancel() {
        Date date = DateUtils.getAddTime(Constant.ORDER_CANCEL_TIME);
        //查询两个小时未支付的订单，自动取消
        List<Order> orderList = orderRoMapper.selectCancelOrderByDate(date);
        for (Order order : orderList) {
            order.setOrderStatus("7");
            orderMapper.update(order);
            insertOrderLog("", order.getOrderNo(), "7", "系统自动取消订单", "0");
        }
    }

    @Override
    public OrderBO selectOrderByGoodsIdAndUserId(Map<String, Object> map) {
        return orderRoMapper.selectOrderByGoodsIdAndUserId(map);
    }

    @Transactional("db1TxManager")
    @Override
    public OrderUpdateBO updateOrder(OrderUpdateBO orderUpdateBO) {
        Order order = new Order();
        order.setOrderNo(orderUpdateBO.getOrderNo());
        order.setUserId(orderUpdateBO.getUserId());
        order.setRecommendUser(orderUpdateBO.getRecommendUser());
        //修改订单状态
        updOrder(order);
        return orderUpdateBO;
    }

    @Transactional("db1TxManager")
    @Override
    public void confirmOrder(Order order) {
        order.setOrderStatus("6");
        //修改订单状态
        updOrder(order);
        insertOrderLog(order.getUserId(), order.getOrderNo(), "6", "用户确认收货", "0");
    }

    @Override
    public List<OrderBO> selectOrderListByInvoice(OrderBO order, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<OrderBO> oList = orderRoMapper.selectOrderListByInvoice(order);
        return oList;
    }

    @Override
    public List<OrderBO> selectCurriculumOrderList(OrderBO orderBO, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        return orderRoMapper.selectCurriculumOrderList(orderBO);
    }

    @Override
    public Integer selectTodoListCount() {
        return orderRoMapper.selectTodoListCount();
    }

    @Override
    public OrderStatBO orderStat() {
        return orderRoMapper.orderStat();
    }

    @Override
    public OrderBO selectByOrderNoAdmin(String orderNo) {
        return orderRoMapper.selectByOrderNoAdmin(orderNo);
    }

    @Override
    public OrderBO openVip(OrderVipBO orderVipBO, HttpServletRequest request) {
        String orderNo = orderVipBO.getOrderNo();
        OrderBO orderBO = orderRoMapper.selectByOrderNoAdmin(orderNo);
        if (orderBO == null) {
            LOGGER.warn("订单信息不存在：{}", orderBO);
            throw new ServiceException(4921);
        }
        if (orderBO.getStatus() != null && !"4".equals(orderBO.getStatus())) {
            LOGGER.warn("订单还未支付完成，不能开通会员服务：{}", orderBO);
            throw new ServiceException(4921);
        }

        String levelCode = orderVipBO.getLevelCode();
        LOGGER.info("查询是否为会员服务订单，支付成功则更新会员状态: {}", orderNo);
        userService.updateUserVipInfo(orderBO.getUserId(), levelCode);

        LOGGER.info("插入会员日志: {}", orderNo);
        insertVipLog(orderNo, orderBO.getUserId(), levelCode);

        Order order = new Order();
        orderBO.setOrderStatus("6");
        BeanUtils.copyProperties(orderBO, order);
        orderMapper.update(order);

        insertOrderLog(orderBO.getUserId(), orderNo, "6", "完成订单", "0");

        List<OrderProductBO> orderProductBOs = orderBO.getOrderProductBOList();
        for (OrderProductBO orderProductBO : orderProductBOs) {
            sendMemberMsg(orderProductBO, order, request);
        }
        return orderBO;
    }

    @Override
    public OrderTradeBO selectOrderTrade(String tradeNo) {

        return orderRoMapper.selectOrderTrade(tradeNo);
    }

    @Override
    public void adminDeleteOrder(OrderBO orderBO) {
        Order order = new Order();
        BeanUtils.copyProperties(orderBO, order);
        //查询订单状态
        OrderBO bo = orderRoMapper.selectOrderByAdmin(order);
        if (bo == null) {
            LOGGER.info("订单信息不存在：{}", orderBO);
            throw new ServiceException(4134);
        }
        //订单状态，2：待付款，3：付款中，4：付款成功，5：已发货，6：已完成，7：已结束，8：付款失败，9：已退单，11：已删除
        if ("11".equals(bo.getOrderStatus())) {
            //order.setOrderStatus("9");
            int del = orderMapper.delete(order.getOrderNo());
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
            insertOrderLog(orderBO.getUserId(), orderBO.getOrderNo(), "10", "管理员删除用户订单", "0");
        } else {
            LOGGER.info("订单只有在前台用户删除后，管理员才可以进行删除：{}", orderBO);
            throw new ServiceException(4140);
        }
    }

}
