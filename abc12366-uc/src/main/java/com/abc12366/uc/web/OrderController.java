package com.abc12366.uc.web;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.Order;
import com.abc12366.uc.model.OrderBack;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.service.OrderService;
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
     * @param pageNum
     * @param pageSize
     * @param orderNo
     * @param username
     * @param startTime
     * @param endTime
     * @return
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
        User user = new User();
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
     * @param pageNum
     * @param pageSize
     * @param name
     * @param userId
     * @return
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
        User user = new User();
        user.setId(userId);
        order.setUser(user);

        GoodsBO goodsBO = new GoodsBO();
        goodsBO.setName(name);
        order.setGoodsBO(goodsBO);
        order.setOrderStatus(status);
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
     * @param pageNum
     * @param pageSize
     * @param name
     * @param userId
     * @return
     */
    @GetMapping(path = "/user/all")
    public ResponseEntity selectUserAllOrderList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                                 @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                                 @RequestParam(value = "name", required = false) String name,
                                                 @RequestParam(value = "userId", required = true) String userId) {
        LOGGER.info("{}:{}", pageNum, pageSize);
        OrderBO order = new OrderBO();
        User user = new User();
        user.setId(userId);
        order.setUser(user);

        GoodsBO goodsBO = new GoodsBO();
        goodsBO.setName(name);
        order.setGoodsBO(goodsBO);
        order.setIsInvoice(false);
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
     * @param pageNum
     * @param pageSize
     * @param name
     * @param userId
     * @return
     */
    @GetMapping(path = "/user/all/invoice")
    public ResponseEntity selectOrderListByInvoice(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                                 @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                                 @RequestParam(value = "name", required = false) String name,
                                                 @RequestParam(value = "userId", required = true) String userId) {
        LOGGER.info("{}:{}", pageNum, pageSize);
        OrderBO order = new OrderBO();
        User user = new User();
        user.setId(userId);
        order.setUser(user);

        GoodsBO goodsBO = new GoodsBO();
        goodsBO.setName(name);
        order.setGoodsBO(goodsBO);
        List<OrderBO> orderBOs = orderService.selectUserAllOrderList(order, pageNum, pageSize);
        PageInfo<OrderBO> pageInfo = new PageInfo<>(orderBOs);
        LOGGER.info("{}", orderBOs);
        return (orderBOs == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", JSON.toJSONString(pageInfo.getList()), "total", pageInfo.getTotal()));
    }


    /**
     * 查询订单详情
     *
     * @param orderNo
     * @return
     */
    @GetMapping(path = "/select/{orderNo}")
    public ResponseEntity selectByOrderNo(@PathVariable("orderNo") String orderNo) {
        LOGGER.info("{}", orderNo);
        OrderBO orderBO = orderService.selectByOrderNo(orderNo);
        LOGGER.info("{}", orderBO);
        return ResponseEntity.ok(Utils.kv("data", orderBO));
    }

    /**
     * 导出订单信息
     *
     * @return
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
     *
     * @return
     */
    @PostMapping(path = "/import")
    public ResponseEntity importOrder(@Valid @RequestBody List<OrderBO> orderBOList) {
        LOGGER.info("{}", orderBOList);
        orderService.selectImprotOrder(orderBOList);
        return ResponseEntity.ok(Utils.kv());
    }


    /**
     * 用户下单
     */
    @PostMapping(path = "/submit/{userId}")
    public ResponseEntity submitOrder(@Valid @RequestBody OrderBO orderBO, @PathVariable("userId") String userId) {
        LOGGER.info("{}", orderBO);
        orderBO.setUserId(userId);
        OrderBO bo = orderService.submitOrder(orderBO);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    /**
     * 用户修改订单
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
     * 用户将订单改为支付中，虚拟订单
     */
    @PostMapping(path = "/payment")
    public ResponseEntity paymentOrderFictitious(@Valid @RequestBody OrderPayBO orderPayBO) {
        LOGGER.info("{}{}", orderPayBO);
        OrderBO bo = orderService.paymentOrder(orderPayBO);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }


    /**
     * 用户将订单改为支付中，虚拟订单
     */
    @PostMapping(path = "/confirm/{orderNo}/{userId}")
    public ResponseEntity confirmOrder(@PathVariable("orderNo") String orderNo,@PathVariable("userId") String userId) {
        LOGGER.info("{}{}", orderNo,userId);
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(userId);
        orderService.confirmOrder(order);
        return ResponseEntity.ok(Utils.kv());
    }

    /**
     * 订单发货
     *
     * @return
     */
    @PostMapping(path = "/send")
    public ResponseEntity sendOrder(@Valid @RequestBody OrderOperationBO orderOperationBO) {
        LOGGER.info("{}", orderOperationBO);
        orderService.sendOrder(orderOperationBO);
        return ResponseEntity.ok(Utils.kv());
    }

    /**
     * 订单作废
     *
     * @return
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
     * @return
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
     * @param userId
     * @param id
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
     * 修改订单
     * @param orderBO
     * @param userId
     * @param id
     * @return
     */
    /*
    @PutMapping(path = "/{userId}/{id}")
    public ResponseEntity update(@Valid @RequestBody OrderBO orderBO, @PathVariable("userId") String userId,
    @PathVariable("id") String id) {
        LOGGER.info("{}", orderBO);
        orderBO.setId(id);
        orderBO.setUserId(userId);
        OrderBO bo = orderService.updateCart(orderBO);
        LOGGER.info("{}", bo);
        return new ResponseEntity<>(bo, HttpStatus.OK);
    }*/

    /**
     * 删除购物车订单
     *
     * @param userId
     * @param id
     * @return
     *//*
    @DeleteMapping(path = "/{userId}/{id}")
    public ResponseEntity deleteCart(@PathVariable("userId") String userId, @PathVariable("id") String id) {
        OrderBO orderBO = new OrderBO();
        orderBO.setOrderNo(id);
        orderBO.setUserId(userId);
        orderService.deleteCart(orderBO);
        return ResponseEntity.ok(Utils.kv("data", orderBO));
    }*/

    /**
     * 反馈虚拟产品订单信息
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
     * @return
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
     * @param userId
     * @return
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
     * @return
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
     * @return
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
     * @return
     */
    @GetMapping(path = "/goods")
    public ResponseEntity selectOrderByGoodsIdAndUserId(@RequestParam(value = "goodsId", required = true) String goodsId,
                                                        @RequestParam(value = "userId", required = true) String userId) {
        Order order = new Order();
        order.setGoodsId(goodsId);
        order.setUserId(userId);
        LOGGER.info("{}", order);
        OrderBO bo = orderService.selectOrderByGoodsIdAndUserId(order);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

}
