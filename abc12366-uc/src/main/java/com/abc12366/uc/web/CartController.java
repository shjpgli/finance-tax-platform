package com.abc12366.uc.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.bo.OrderBO;
import com.abc12366.uc.service.OrderService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
@RequestMapping(path = "/cart", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class CartController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private OrderService orderService;


    /**
     * 订单列表查询
     * @param pageNum
     * @param pageSize
     * @param userId
     * @return
     */
    @GetMapping(path = "/{userId}")
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                     @PathVariable("userId") String userId) {
        LOGGER.info("{}:{}", pageNum, pageSize);
        OrderBO order = new OrderBO();
        order.setStatus("1");
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<OrderBO> orderBOs = orderService.selectCartList(order);
        LOGGER.info("{}", orderBOs);
        return (orderBOs == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("cartList", (Page) orderBOs, "total", ((Page) orderBOs).getTotal()));
    }

    /**
     * 加入购物车
     * @param orderBO
     * @param userId
     * @return
     */
    @PostMapping(path = "/{userId}")
    public ResponseEntity joinCart(@Valid @RequestBody OrderBO orderBO,@PathVariable("userId") String userId) {
        LOGGER.info("{}", orderBO);
        OrderBO bo = orderService.joinCart(orderBO);
        LOGGER.info("{}", bo);
        return new ResponseEntity<>(bo, HttpStatus.OK);
    }

    /**
     * 修改购物车
     * @param orderBO
     * @param userId
     * @param id
     * @return
     */
    @PutMapping(path = "/{userId}/{id}")
    public ResponseEntity updateCart(@Valid @RequestBody OrderBO orderBO, @PathVariable("userId") String userId, @PathVariable("id") String id) {
        LOGGER.info("{}", orderBO);
        orderBO.setId(id);
        orderBO.setUserId(userId);
        OrderBO bo = orderService.updateCart(orderBO);
        LOGGER.info("{}", bo);
        return new ResponseEntity<>(bo, HttpStatus.OK);
    }

    /**
     * 取消订单
     * @param userId
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{userId}/{id}")
    public ResponseEntity deleteCart(@PathVariable("userId") String userId, @PathVariable("id") String id) {
        OrderBO orderBO = new OrderBO();
        orderBO.setId(id);
        orderBO.setUserId(userId);
        int bo= orderService.deleteByIdAndUserId(orderBO);
        LOGGER.info("{}", bo);
        return new ResponseEntity<>(bo, HttpStatus.OK);
    }
}
