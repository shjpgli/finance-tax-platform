package com.abc12366.uc.web.order;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.order.Order;
import com.abc12366.uc.model.order.bo.*;
import com.abc12366.uc.model.OrderBack;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.service.order.OrderService;
import com.abc12366.uc.util.DataUtils;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 订单控制类
 *
 * @author lizhongwei
 * @create 2017-05-16
 * @since 2.0.0
 */
@RestController
@RequestMapping(path = "/order", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    /**
     * 订单列表管理
     *
     * @param pageNum     页数
     * @param pageSize    条数
     * @param orderNo     订单号
     * @param username    用户名
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @param orderStatus 订单状态
     * @param phone       电话号码
     * @return 订单列表
     */
    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                     @RequestParam(value = "orderNo", required = false) String orderNo,
                                     @RequestParam(value = "orderStatus", required = false) String orderStatus,
                                     @RequestParam(value = "username", required = false) String username,
                                     @RequestParam(value = "phone", required = false) String phone,
                                     @RequestParam(value = "startTime", required = false) String startTime,
                                     @RequestParam(value = "endTime", required = false) String endTime) {
        LOGGER.info("{}:{}", pageNum, pageSize);
        OrderBO orderBO = new OrderBO();
        UserBO user = new UserBO();
        user.setUsername(username);
        user.setPhone(phone);
        orderBO.setUser(user);
        orderBO.setOrderNo(orderNo);
        orderBO.setOrderStatus(orderStatus);
        if (startTime != null && !"".equals(startTime)) {
            orderBO.setStartTime(DataUtils.StrToDate(startTime));
        }
        if (endTime != null && !"".equals(endTime)) {
            orderBO.setEndTime(DataUtils.StrToDate(endTime));
        }

        List<OrderBO> orderList = orderService.selectList(orderBO, pageNum, pageSize);
        PageInfo<OrderBO> pageInfo = new PageInfo<>(orderList);
        LOGGER.info("{}", orderList);
        return (orderList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", JSON.toJSONString(pageInfo.getList()), "total", pageInfo.getTotal()));
    }

    /**
     * 已完成订单列表查询
     *
     * @param pageNum     页数
     * @param pageSize    条数
     * @param name        商品名称
     * @param tradeMethod 交易方式
     * @param status      状态
     * @param userId      用户ID
     * @param isInvoice   是否开发票
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 订单列表
     */
    @GetMapping(path = "/user")
    public ResponseEntity selectUserOrderList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                              @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                              @RequestParam(value = "name", required = false) String name,
                                              @RequestParam(value = "tradeMethod", required = false) String tradeMethod,
                                              @RequestParam(value = "status", required = true) String status,
                                              @RequestParam(value = "userId", required = true) String userId,
                                              @RequestParam(value = "isInvoice", required = false) Boolean isInvoice,
                                              @RequestParam(value = "startTime", required = false) String startTime,
                                              @RequestParam(value = "endTime", required = false) String endTime) {
        LOGGER.info("{}:{}", pageNum, pageSize);
        OrderBO order = new OrderBO();
        UserBO user = new UserBO();
        user.setId(userId);
        order.setUser(user);

        GoodsBO goodsBO = new GoodsBO();
        goodsBO.setName(name);
        String data[] = status.split(",");
        order.setStatus(data);
        order.setTradeMethod(tradeMethod);
        order.setIsInvoice(isInvoice);

        if (startTime != null && !"".equals(startTime)) {
            order.setStartTime(DataUtils.StrToDate(startTime));
        }
        if (endTime != null && !"".equals(endTime)) {
            order.setEndTime(DataUtils.StrToDate(endTime));
        }
        List<OrderBO> orderBOs = orderService.selectOrderList(order, pageNum, pageSize);
        PageInfo<OrderBO> pageInfo = new PageInfo<>(orderBOs);
        LOGGER.info("{}", orderBOs);
        return (orderBOs == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", JSON.toJSONString(pageInfo.getList()), "total", pageInfo.getTotal()));
    }

    /**
     * 用户所有订单查询，未开票的订单
     *
     * @param pageNum   页数
     * @param pageSize  条数
     * @param name      商品名称
     * @param userId    用户ID
     * @param goodsType 商品类型
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 订单列表
     */
    @GetMapping(path = "/user/all")
    public ResponseEntity selectUserAllOrderList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                                 @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                                 @RequestParam(value = "name", required = false) String name,
                                                 @RequestParam(value = "userId", required = true) String userId,
                                                 @RequestParam(value = "goodsType", required = false) String goodsType,
                                                 @RequestParam(value = "startTime", required = false) String startTime,
                                                 @RequestParam(value = "endTime", required = false) String endTime) {
        LOGGER.info("{}:{}", pageNum, pageSize);
        OrderBO order = new OrderBO();
        UserBO user = new UserBO();
        user.setId(userId);
        order.setUser(user);

        GoodsBO goodsBO = new GoodsBO();
        goodsBO.setName(name);
//        order.setGoodsBO(goodsBO);
        order.setIsInvoice(false);
        order.setGoodsType(goodsType);
        if (startTime != null && !"".equals(startTime)) {
            order.setStartTime(DataUtils.StrToDate(startTime));
        }
        if (endTime != null && !"".equals(endTime)) {
            order.setEndTime(DataUtils.StrToDate(endTime));
        }
        List<OrderBO> orderBOs = orderService.selectUserAllOrderList(order, pageNum, pageSize);
        PageInfo<OrderBO> pageInfo = new PageInfo<>(orderBOs);
        LOGGER.info("{}", orderBOs);
        return (orderBOs == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", JSON.toJSONString(pageInfo.getList()), "total", pageInfo.getTotal()));
    }

    /**
     * 用户所有订单查询,未开票和已开票的订单
     *
     * @param pageNum   页数
     * @param pageSize  条数
     * @param name      商品名称
     * @param userId    用户ID
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 订单列表
     */
    @GetMapping(path = "/user/all/invoice")
    public ResponseEntity selectOrderListByInvoice(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                                   @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                                   @RequestParam(value = "name", required = false) String name,
                                                   @RequestParam(value = "userId", required = true) String userId,
                                                   @RequestParam(value = "startTime", required = false) String startTime,
                                                   @RequestParam(value = "endTime", required = false) String endTime) {
        LOGGER.info("{}:{}", pageNum, pageSize);
        OrderBO order = new OrderBO();
        UserBO user = new UserBO();
        user.setId(userId);
        order.setUser(user);

        GoodsBO goodsBO = new GoodsBO();
        goodsBO.setName(name);
//        order.setGoodsBO(goodsBO);
        if (startTime != null && !"".equals(startTime)) {
            order.setStartTime(DataUtils.StrToDate(startTime));
        }
        if (endTime != null && !"".equals(endTime)) {
            order.setEndTime(DataUtils.StrToDate(endTime));
        }

        List<OrderBO> orderBOs = orderService.selectOrderListByInvoice(order, pageNum, pageSize);
        PageInfo<OrderBO> pageInfo = new PageInfo<>(orderBOs);
        LOGGER.info("{}", orderBOs);
        return (orderBOs == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", JSON.toJSONString(pageInfo.getList()), "total", pageInfo.getTotal()));
    }


    /**
     * 课程订单查询
     * @param pageNum   页数
     * @param pageSize  条数
     * @param goodsId   商品ID
     * @param nickname  用户昵称
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 订单列表
     *//*
    @GetMapping(path = "/curriculum")
    public ResponseEntity selectCurriculumOrderList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                                    @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                                    @RequestParam(value = "goodsId", required = true) String goodsId,
                                                    @RequestParam(value = "nickname", required = false) String nickname,
                                                    @RequestParam(value = "startTime", required = false) String startTime,
                                                    @RequestParam(value = "endTime", required = false) String endTime) {
        LOGGER.info("{}:{}", pageNum, pageSize);
        OrderBO orderBO = new OrderBO();
        UserBO user = new UserBO();
        user.setNickname(nickname);
        orderBO.setUser(user);
//        orderBO.setGoodsId(goodsId);
        if (startTime != null && !"".equals(startTime)) {
            orderBO.setStartTime(DataUtils.StrToDate(startTime));
        }
        if (endTime != null && !"".equals(endTime)) {
            orderBO.setEndTime(DataUtils.StrToDate(endTime));
        }

        List<OrderBO> orderList = orderService.selectCurriculumOrderList(orderBO, pageNum, pageSize);
        PageInfo<OrderBO> pageInfo = new PageInfo<>(orderList);
        LOGGER.info("{}", orderList);
        return (orderList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(), "total", pageInfo.getTotal()));
    }*/

    /**
     * 查询订单详情-后台
     *
     * @param orderNo 订单号
     * @return 订单详情
     */
    @GetMapping(path = "/admin/select/{orderNo}")
    public ResponseEntity selectByOrderNoAdmin(@PathVariable("orderNo") String orderNo) {
        LOGGER.info("{}", orderNo);
        OrderBO orderBO = orderService.selectByOrderNoAdmin(orderNo);
        LOGGER.info("{}", orderBO);
        return ResponseEntity.ok(Utils.kv("data", orderBO));
    }

    /**
     * 查询订单详情
     *
     * @param orderNo 订单号
     * @return 订单详情
     */
    @GetMapping(path = "/select/{orderNo}")
    public ResponseEntity selectByOrderNo(@PathVariable("orderNo") String orderNo) {
        LOGGER.info("{}", orderNo);
        OrderBO orderBO = orderService.selectByOrderNo(orderNo);
        LOGGER.info("{}", orderBO);
        return ResponseEntity.ok(Utils.kv("data", orderBO));
    }

    /**
     * 根据交易流水号查询列表
     *
     * @param tradeNo 订单号
     * @return 订单详情
     */
    /*@GetMapping(path = "/select/trade/list/{tradeNo}")
    public ResponseEntity selectListByTradeNo(@PathVariable("tradeNo") String tradeNo) {
        LOGGER.info("{}", tradeNo);
        List<OrderTradeBO> boList = orderService.selectListByTradeNo(tradeNo);
        PageInfo<OrderTradeBO> pageInfo = new PageInfo<>(boList);
        LOGGER.info("{}", boList);
        return (boList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(), "total", pageInfo.getTotal()));
    }*/

    /**
     * 根据交易流水号查询订单合并内容
     *
     * @param tradeNo 订单号
     * @return 订单详情
     */
    @GetMapping(path = "/select/trade/{tradeNo}")
    public ResponseEntity selectByTradeNo(@PathVariable("tradeNo") String tradeNo) {
        LOGGER.info("{}", tradeNo);
        OrderTradeBO orderTradeBO = orderService.selectOrderTrade(tradeNo);
        LOGGER.info("{}", orderTradeBO);
        return ResponseEntity.ok(Utils.kv("data", orderTradeBO));
    }

    /**
     * 导出订单信息
     *
     * @return 订单列表
     */
    @GetMapping(path = "/export")
    public ResponseEntity exportOrder() {
        Order order = new Order();
        order.setOrderStatus("4");
        List<OrderListBO> orderListBOList = orderService.selectExprotOrder(order);
        LOGGER.info("{}", orderListBOList);
        return ResponseEntity.ok(Utils.kv("dataList", orderListBOList));
    }

    /**
     * 导入订单信息
     */
    @PostMapping(path = "/import/{expressCompId}")
    public ResponseEntity importOrder(@Valid @RequestBody List<OrderBO> orderBOList,
                                      @PathVariable("expressCompId") String expressCompId,
                                      HttpServletRequest request) {
        LOGGER.info("{}", orderBOList);
        orderService.selectImportOrder(orderBOList, expressCompId, request);
        return ResponseEntity.ok(Utils.kv());
    }


    /**
     * 用户下单
     *
     * @param orderSubmitBO 订单信息
     * @param userId  用户 ID
     * @return 订单信息
     */
    @PostMapping(path = "/submit/{userId}")
    public ResponseEntity submitOrder(@Valid @RequestBody OrderSubmitBO orderSubmitBO, @PathVariable("userId") String userId) {
        LOGGER.info("{}", orderSubmitBO);
        orderSubmitBO.setUserId(userId);
        OrderBO bo = orderService.submitOrder(orderSubmitBO);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    /**
     * 用户开通会员
     *
     * @param orderVipBO 开通VIP
     * @return  订单信息
     */
    @PutMapping(path = "/open")
    public ResponseEntity openVip(@Valid @RequestBody OrderVipBO orderVipBO,HttpServletRequest request) {
        LOGGER.info("{}", orderVipBO);
        OrderBO bo = orderService.openVip(orderVipBO,request);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    /**
     * 用户修改订单
     *
     * @param orderUpdateBO 订单信息
     * @param userId        用户ID
     * @return 订单信息
     */
    @PostMapping(path = "/update/{userId}")
    public ResponseEntity updateOrder(@Valid @RequestBody OrderUpdateBO orderUpdateBO, @PathVariable("userId") String userId) {
        LOGGER.info("{}", orderUpdateBO);
        orderUpdateBO.setUserId(userId);
        OrderUpdateBO bo = orderService.updateOrder(orderUpdateBO);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    /**
     * 用户将订单改为支付中
     *
     * @param orderPayBO 支付信息
     * @param request
     * @return 订单信息
     */
    @PostMapping(path = "/payment")
    public ResponseEntity paymentOrderFictitious(@Valid @RequestBody OrderPayBO orderPayBO, HttpServletRequest request) {
        LOGGER.info("{}{}", orderPayBO);
        orderService.paymentOrder(orderPayBO, "RMB", request);
        return ResponseEntity.ok(Utils.kv());
    }

    /**
     * 用户交易积分订单
     *
     * @param orderPayBO 支付信息
     * @param request
     * @return 订单信息
     */
    @PostMapping(path = "/paypoints")
    public ResponseEntity paymentOrder(@Valid @RequestBody OrderPayBO orderPayBO, HttpServletRequest request) {
        LOGGER.info("{}{}", orderPayBO);
        orderPayBO.setPayMethod("POINTS");
        orderService.paymentOrder(orderPayBO, "POINTS", request);

        return ResponseEntity.ok(Utils.kv());
    }


    /**
     * 用户确认收货
     *
     * @param orderNo 订单号
     * @param userId  用户ID
     */
    @PostMapping(path = "/confirm/{orderNo}/{userId}")
    public ResponseEntity confirmOrder(@PathVariable("orderNo") String orderNo, @PathVariable("userId") String userId) {
        LOGGER.info("{}{}", orderNo, userId);
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(userId);
        orderService.confirmOrder(order);
        return ResponseEntity.ok(Utils.kv());
    }

    /**
     * 订单发货
     *
     * @param orderOperationBO 订单操作信息
     * @param request
     */
    @PostMapping(path = "/send")
    public ResponseEntity sendOrder(@Valid @RequestBody OrderOperationBO orderOperationBO, HttpServletRequest request) {
        LOGGER.info("{}", orderOperationBO);
        orderService.sendOrder(orderOperationBO, request);
        return ResponseEntity.ok(Utils.kv());
    }

    /**
     * 订单作废
     *
     * @param orderOperationBO 订单操作信息
     */
    @PostMapping(path = "/invalid")
    public ResponseEntity invalidOrder(@Valid @RequestBody OrderOperationBO orderOperationBO) {
        LOGGER.info("{}", orderOperationBO);
        orderService.invalidOrder(orderOperationBO);
        return ResponseEntity.ok(Utils.kv());
    }

    /**
     * 用户取消订单
     *
     * @param orderCancelBO 订单取消信息
     * @return 订单信息
     */
    @PostMapping(path = "/cancel")
    public ResponseEntity cancelOrder(@Valid @RequestBody OrderCancelBO orderCancelBO) {
        LOGGER.info("{}", orderCancelBO);
        OrderBO bo = orderService.cancelOrder(orderCancelBO);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    /**
     * 删除购物车订单
     *
     * @param userId 用户ID
     * @param id     订单号
     * @return
     */
    @DeleteMapping(path = "/delete/{userId}/{id}")
    public ResponseEntity deleteOrder(@PathVariable("userId") String userId, @PathVariable("id") String id) {
        OrderBO orderBO = new OrderBO();
        orderBO.setOrderNo(id);
        orderBO.setUserId(userId);
        orderService.deleteOrder(orderBO);
        return ResponseEntity.ok(Utils.kv());
    }

    /**
     * 反馈虚拟产品订单信息
     *
     * @param orderBO 订单信息
     * @return
     */
    @PutMapping(path = "/feedback")
    public ResponseEntity feedback(@Valid @RequestBody OrderBO orderBO) {
        LOGGER.info("{}", orderBO);
        OrderBO bo = orderService.feedback(orderBO);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }


    /**
     * 退单管理列表
     *
     * @param pageNum  页数
     * @param pageSize 条数
     * @param orderNo  订单号
     * @param username 用户名
     * @return 退单列表
     */
    @GetMapping(path = "/back")
    public ResponseEntity selectBackList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                         @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                         @RequestParam(value = "orderNo", required = false) String orderNo,
                                         @RequestParam(value = "username", required = false) String username) {
        OrderBackBO orderBackBO = new OrderBackBO();
        orderBackBO.setOrderNo(orderNo);

        User user = new User();
        user.setUsername(username);
        orderBackBO.setUser(user);

        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<OrderBackBO> orderBackBOs = orderService.selectOrderBackList(orderBackBO);
        PageInfo<OrderBackBO> pageInfo = new PageInfo<>(orderBackBOs);
        LOGGER.info("{}", orderBackBOs);
        return (orderBackBOs == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", JSON.toJSONString(pageInfo.getList()), "total", pageInfo.getTotal()));
    }

    /**
     * 用户申请退单
     *
     * @param orderBack 退单信息
     * @param userId    用户ID
     * @param orderNo   订单号
     * @return 退单信息
     */
    @PostMapping(path = "/back/apply/{userId}/{orderNo}")
    public ResponseEntity applyBackOrder(@Valid @RequestBody OrderBack orderBack, @PathVariable("userId") String
            userId, @PathVariable("orderNo") String orderNo) {
        LOGGER.info("{}", orderBack);
        orderBack.setUserId(userId);
        orderBack.setOrderNo(orderNo);
        OrderBack bo = orderService.applyBackOrder(orderBack);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    /**
     * 管理员审核退单申请
     *
     * @param orderBack
     * @return 退单信息
     */
    @PostMapping(path = "/back/check")
    public ResponseEntity backCheckOrder(@Valid @RequestBody OrderBack orderBack) {
        LOGGER.info("{}", orderBack);
        OrderBack bo = orderService.backCheckOrder(orderBack);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    /**
     * 用户提交退单
     *
     * @param orderBack 退单信息
     * @return 退单信息
     */
    @PostMapping(path = "/back/submit")
    public ResponseEntity submitBackOrder(@Valid @RequestBody OrderBack orderBack) {
        LOGGER.info("{}", orderBack);
        OrderBack bo = orderService.submitBackOrder(orderBack);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    /**
     * 根据GoodsId和UserId查询订单信息
     *
     * @param goodsId 商品 ID
     * @param userId  用户ID
     * @return
     */
    @GetMapping(path = "/goods")
    public ResponseEntity selectOrderByGoodsIdAndUserId(@RequestParam(value = "goodsId", required = true) String goodsId,
                                                        @RequestParam(value = "userId", required = true) String userId) {
        Order order = new Order();
//        order.setGoodsId(goodsId);
        order.setUserId(userId);
        LOGGER.info("{}", order);
        OrderBO bo = orderService.selectOrderByGoodsIdAndUserId(order);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

}
