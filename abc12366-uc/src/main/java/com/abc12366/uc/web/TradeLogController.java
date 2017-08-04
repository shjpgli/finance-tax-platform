package com.abc12366.uc.web;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.TradeLog;
import com.abc12366.uc.service.TradeLogService;
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
@RequestMapping(path = "/tradelog", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class TradeLogController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TradeLogController.class);

    @Autowired
    private TradeLogService tradeLogService;

    /**
     * 订单日志列表查询
     */
    @GetMapping(path = "/{orderNo}")
    public ResponseEntity selectList(@PathVariable("orderNo") String orderNo) {
        TradeLog tradeLog = new TradeLog();

        tradeLog.setOrderNo(orderNo);
        List<TradeLog> orderLogList = tradeLogService.selectList(tradeLog);
        LOGGER.info("{}", orderLogList);
        return (orderLogList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("orderLogList", orderLogList));
    }

}
