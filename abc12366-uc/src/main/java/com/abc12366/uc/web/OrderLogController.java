package com.abc12366.uc.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.model.OrderLog;
import com.abc12366.uc.service.OrderLogService;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 订单控制类
 *
 * @author lizhongwei
 * @create 2017-06-08
 * @since 2.0.0
 */
@RestController
@RequestMapping(path = "/orderlog", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class OrderLogController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderLogController.class);

    @Autowired
    private OrderLogService orderLogService;

    /**
     * 订单日志列表查询
     */
    @GetMapping(path = "/{orderNo}")
    public ResponseEntity selectList(@PathVariable("orderNo") String orderNo) {
        OrderLog orderLog = new OrderLog();

        orderLog.setOrderNo(orderNo);
        List<OrderLog> orderLogList = orderLogService.selectList(orderLog);
        LOGGER.info("{}", orderLogList);
        return (orderLogList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", orderLogList));
    }

}
