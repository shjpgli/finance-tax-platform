package com.abc12366.uc.web;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.Dict;
import com.abc12366.uc.model.OrderExchange;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.service.OrderExchangeService;
import com.abc12366.uc.service.admin.DictService;
import com.abc12366.uc.util.DataUtils;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * 换货申请
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-05-16
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/exchange", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class OrderExchangeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderExchangeController.class);

    @Autowired
    private OrderExchangeService orderExchangeService;

    @Autowired
    private DictService dictService;

    /**
     * 退换货列表
     */
    @GetMapping()
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                     @RequestParam(value = "orderNo", required = false) String orderNo,
                                     @RequestParam(value = "username", required = false) String username,
                                     @RequestParam(value = "type", required = false) String type,
                                     @RequestParam(value = "status", required = false) String status) {
        OrderExchange oe = new OrderExchange.Builder()
                .orderNo(orderNo)
                .username(username)
                .type(type)
                .status(status)
                .build();
        LOGGER.info("{}", oe);

        List<OrderExchange> exchangeList = orderExchangeService.selectList(oe, pageNum, pageSize);
        PageInfo<OrderExchange> pageInfo = new PageInfo<>(exchangeList);

        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(), "total", pageInfo
                .getTotal()));
        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    /**
     * 退换货列表-财务
     */
    @GetMapping("/finance")
    public ResponseEntity selectListForFinance(
                                     @RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                     @RequestParam(value = "orderNo", required = false) String orderNo,
                                     @RequestParam(value = "username", required = false) String username,
                                     @RequestParam(value = "type", required = false) String type,
                                     @RequestParam(value = "status", required = false) String status) {
        OrderExchange oe = new OrderExchange.Builder()
                .orderNo(orderNo)
                .username(username)
                .type(type)
                .status(status)
                .build();
        LOGGER.info("{}", oe);

        List<OrderExchange> exchangeList = orderExchangeService.selectListForFinance(oe, pageNum, pageSize);
        PageInfo<OrderExchange> pageInfo = new PageInfo<>(exchangeList);

        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(), "total", pageInfo
                .getTotal()));
        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    /**
     * 退换货列表
     */
    @GetMapping("/record")
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                     @RequestParam(value = "orderNo", required = false) String orderNo) {
        OrderExchange oe = new OrderExchange.Builder()
                .orderNo(orderNo)
                .userId(Utils.getUserId())
                .build();
        LOGGER.info("{}", oe);

        List<OrderExchange> exchangeList = orderExchangeService.selectList(oe, pageNum, pageSize);
        PageInfo<OrderExchange> pageInfo = new PageInfo<>(exchangeList);

        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(), "total", pageInfo
                .getTotal()));
        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    /**
     * 查看
     */
    @GetMapping("/{id}")
    public ResponseEntity selectOne(@PathVariable("id") String id) {
        LOGGER.info("{}", id);
        OrderExchange oe = orderExchangeService.selectOne(id);
        List<ExchangeOrderInvoiceBO> dataList = null;
        if (oe != null) {
            dataList = orderExchangeService.selectInvoice(oe.getOrderNo());
        }
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", oe, "dataList", dataList));
        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    /**
     * 同意换货
     */
    @PutMapping(path = "/agree/{id}")
    public ResponseEntity agree(@PathVariable("id") String id, @Valid @RequestBody ExchangeAdminBO data) {

        data.setId(id);
        LOGGER.info("{}", data);
        OrderExchange oe = orderExchangeService.agree(data);
        List<Dict> dataList = dictService.selectList("receive_info");

        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", oe, "dataList", dataList));
        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    /**
     * 拒绝换货
     */
    @PutMapping(path = "/disagree/{id}")
    public ResponseEntity disagree(@PathVariable("id") String id, @Valid @RequestBody ExchangeAdminBO data) {

        data.setId(id);
        LOGGER.info("{}", data);
        OrderExchange oe = orderExchangeService.disagree(data);

        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", oe));
        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    /**
     * 操作员确认收货
     */
    @PutMapping(path = "/confirm/{id}")
    public ResponseEntity confirm(@PathVariable("id") String id, @Valid @RequestBody ExchangeConfirmBO data) {

        data.setId(id);
        LOGGER.info("{}", data);
        OrderExchange oe = orderExchangeService.confirm(data);

        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", oe));
        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    /**
     * 用户确认收货
     */
    @PutMapping(path = "/receive/{id}")
    public ResponseEntity receive(@PathVariable("id") String id) {

        LOGGER.info("{}", id);
        OrderExchange oe = orderExchangeService.receive(id);

        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", oe));
        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    /**
     * 确认退货
     */
    @PutMapping(path = "/back/{id}")
    public ResponseEntity back(@PathVariable("id") String id, @Valid @RequestBody ExchangeBackBO data) throws
            Exception {

        data.setId(id);
        LOGGER.info("{}", data);

        if (StringUtils.isEmpty(data.getExpressNo()) && StringUtils.isEmpty(data.getExpressComp())) {
            OrderExchange orderExchange = orderExchangeService.selectOne(id);
            if ("2".equals(orderExchange.getGoodsType())) {
                return ResponseEntity.ok(Utils.bodyStatus(4958));
            }
            List<ExchangeOrderInvoiceBO> dataList = orderExchangeService.selectInvoice(orderExchange.getOrderNo());
            if (dataList.size() > 0) {
                for (ExchangeOrderInvoiceBO eoi : dataList) {
                    if ("1".equals(eoi.getProperty())) {
                        return ResponseEntity.ok(Utils.bodyStatus(4959));
                    }
                }
            }
        }
        OrderExchange oe = orderExchangeService.back(data);

        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", oe));
        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    /**
     * 退款
     */
    @PutMapping(path = "/refund/{id}")
    public ResponseEntity refund(@PathVariable("id") String id, @Valid @RequestBody ExchangeRefundBO data) {

        data.setId(id);
        LOGGER.info("{}", data);
        ResponseEntity responseEntity = orderExchangeService.refund(data);

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    /**
     * 用户提交换货单
     */
    @PostMapping()
    public ResponseEntity insert(@Valid @RequestBody ExchangeApplicationBO data) {
        LOGGER.info("{}", data);
        OrderExchange oe = orderExchangeService.insert(data);

        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", oe));
        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    /**
     * 用户重新提交换货单
     */
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") String id, @Valid @RequestBody ExchangeApplicationBO data) {
        data.setId(id);
        LOGGER.info("{}", data);
        OrderExchange oe = orderExchangeService.update(data);

        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", oe));
        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    /**
     * 导出json
     */
    @GetMapping("/export")
    public ResponseEntity exportJson(@RequestParam(value = "startTime", required = false) String startTime,
                                     @RequestParam(value = "endTime", required = false) String endTime) {
        LOGGER.info("{},{}", startTime, endTime);

        if (StringUtils.isEmpty(startTime)) {
            startTime = "2017-01-01 00:00:01";
        }
        if (StringUtils.isEmpty(endTime)) {
            endTime = DataUtils.dateToStr(new Date());
        }
        OrderExchangeExportBO bo = new OrderExchangeExportBO();
        bo.setStartTime(startTime);
        bo.setEndTime(endTime);

        List<SfExportBO> dataList = orderExchangeService.export(bo);

        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("dataList", dataList));
        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    /**
     * 导入json
     */
    @PutMapping("/import")
    public ResponseEntity importJson(@Valid @RequestBody List<SfImportBO> dataList) {
        LOGGER.info("{}", dataList);
        orderExchangeService.importJson(dataList);

        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv());
        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }
}
