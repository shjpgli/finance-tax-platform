package com.abc12366.uc.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.model.Order;
import com.abc12366.uc.model.OrderBack;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.service.OrderService;
import com.abc12366.uc.util.DataUtils;
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
     * @param pageNum
     * @param pageSize
     * @param orderNo
     * @param username
     * @param phone
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
                                     @RequestParam(value ="startTime", required = false) String startTime,
                                     @RequestParam(value ="endTime", required = false) String endTime) {
        LOGGER.info("{}:{}", pageNum, pageSize);
        OrderBO order = new OrderBO();
        User user = new User();
        user.setUsername(username);
        user.setPhone(phone);
        order.setUser(user);
        order.setOrderNo(orderNo);
        order.setOrderStatus(orderStatus);
//        order.setStartTime(startTime);
//        order.setEndTime(endTime);
        if(startTime != null && !"".equals(startTime)){
            order.setStartTime(DataUtils.StrToDate(startTime));
        }
        if(endTime != null && !"".equals(endTime)){
            order.setEndTime(DataUtils.StrToDate(endTime));
        }

        List<OrderBO> orderList = orderService.selectList(order,pageNum,pageSize);
        PageInfo<OrderBO> pageInfo = new PageInfo<>(orderList);
        LOGGER.info("{}", orderList);
        return (orderList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(), "total", pageInfo.getTotal()));
    }

    /**
     * 已完成订单列表查询
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
                                     @RequestParam(value = "userId", required = true) String userId) {
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
        order.setIsInvoice(false);
        List<OrderBO> orderBOs = orderService.selectOrderList(order,pageNum,pageSize);
        PageInfo<OrderBO> pageInfo = new PageInfo<>(orderBOs);
        LOGGER.info("{}", orderBOs);
        return (orderBOs == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(), "total", pageInfo.getTotal()));
    }

    /**
     * 用户所有订单查询
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
        List<OrderBO> orderBOs = orderService.selectUserAllOrderList(order, pageNum,pageSize);
        PageInfo<OrderBO> pageInfo = new PageInfo<>(orderBOs);
        LOGGER.info("{}", orderBOs);
        return (orderBOs == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(), "total", pageInfo.getTotal()));
    }

    /**
     * 查询订单详情
     * @param orderNo
     * @return
     */
    @GetMapping(path = "/select/{orderNo}")
    public ResponseEntity<?> selectByOrderNo(@PathVariable("orderNo") String orderNo) {
        LOGGER.info("{}", orderNo);
        OrderBO orderBO = orderService.selectByOrderNo(orderNo);
        LOGGER.info("{}", orderBO);
        return ResponseEntity.ok(Utils.kv("data", orderBO));
    }

    /**
     * 用户下单
     * @param userId
     * @return
     */
    @PostMapping(path = "/submit/{userId}")
    public ResponseEntity submitOrder(@Valid @RequestBody OrderBO orderBO,@PathVariable("userId") String userId) {
        LOGGER.info("{}", orderBO);
        orderBO.setUserId(userId);
        OrderBO bo = orderService.submitOrder(orderBO);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    /**
     * 用户取消订单
     * @param userId
     * @return
     */
    @PostMapping(path = "/cancel/{userId}/{orderNo}")
    public ResponseEntity cancelOrder(@Valid @RequestBody Order order,@PathVariable("userId") String userId,@PathVariable("orderNo") String orderNo) {
        LOGGER.info("{}", order);
        order.setUserId(userId);
        order.setOrderNo(orderNo);
        OrderBO bo = orderService.cancelOrder(order);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    /**
     * 删除购物车订单
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
        return ResponseEntity.ok(Utils.kv("data", orderBO));
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
    public ResponseEntity update(@Valid @RequestBody OrderBO orderBO, @PathVariable("userId") String userId, @PathVariable("id") String id) {
        LOGGER.info("{}", orderBO);
        orderBO.setId(id);
        orderBO.setUserId(userId);
        OrderBO bo = orderService.updateCart(orderBO);
        LOGGER.info("{}", bo);
        return new ResponseEntity<>(bo, HttpStatus.OK);
    }*/

    /**
     * 删除购物车订单
     * @param userId
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{userId}/{id}")
    public ResponseEntity deleteCart(@PathVariable("userId") String userId, @PathVariable("id") String id) {
        OrderBO orderBO = new OrderBO();
        orderBO.setOrderNo(id);
        orderBO.setUserId(userId);
        orderService.deleteCart(orderBO);
        return ResponseEntity.ok(Utils.kv("data", orderBO));
    }

    /**
     * 反馈虚拟产品订单信息
     * @param orderBO
     * @param userId
     * @param id
     * @return
     */
    @PutMapping(path = "/feedback/{userId}/{id}")
    public ResponseEntity feedback(@Valid @RequestBody OrderBO orderBO, @PathVariable("userId") String userId, @PathVariable("id") String id) {
        LOGGER.info("{}", orderBO);
        orderBO.setOrderNo(id);
        orderBO.setUserId(userId);
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
                                         @RequestParam(value ="orderNo", required = false) String orderNo,
                                         @RequestParam(value ="username", required = false) String username) {
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
                ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(), "total", pageInfo.getTotal()));
    }

    /**
     * 用户申请退单
     * @param userId
     * @return
     */
    @PostMapping(path = "/back/apply/{userId}/{orderNo}")
    public ResponseEntity applyBackOrder(@Valid @RequestBody OrderBack orderBack,@PathVariable("userId") String userId,@PathVariable("orderNo") String orderNo) {
        LOGGER.info("{}", orderBack);
        orderBack.setUserId(userId);
        orderBack.setOrderNo(orderNo);
        OrderBack bo = orderService.applyBackOrder(orderBack);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    /**
     * 管理员审核退单申请
     * @param userId
     * @return
     */
    @PostMapping(path = "/back/check/{userId}/{orderNo}/{backId}")
    public ResponseEntity backCheckOrder(@Valid @RequestBody OrderBack orderBack,
                                          @PathVariable("userId") String userId ,
                                          @PathVariable("orderNo") String orderNo,
                                          @PathVariable("backId") String backId) {
        LOGGER.info("{}", orderBack);
        orderBack.setUserId(userId);
        orderBack.setOrderNo(orderNo);
        orderBack.setId(backId);
        OrderBack bo = orderService.backCheckOrder(orderBack);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    /**
     * 用户提交退单
     * @param userId
     * @return
     */
    @PostMapping(path = "/back/submit/{userId}/{orderNo}/{backId}")
    public ResponseEntity submitBackOrder(@Valid @RequestBody OrderBack orderBack,
                                          @PathVariable("userId") String userId ,
                                          @PathVariable("orderNo") String orderNo,
                                          @PathVariable("backId") String backId) {
        LOGGER.info("{}", orderBack);
        orderBack.setUserId(userId);
        orderBack.setOrderNo(orderNo);
        orderBack.setId(backId);
        OrderBack bo = orderService.submitBackOrder(orderBack);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }
}
