package com.abc12366.uc.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.model.bo.InvoiceBO;
import com.abc12366.uc.service.InvoiceService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * 发票控制类
 *
 * @author lizhongwei
 * @create 2017-05-16
 * @since 2.0.0
 */
@RestController
@RequestMapping(path = "/invoice", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class InvoiceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    private InvoiceService invoiceService;

    /**
     * 订单列表管理
     *
     * @param pageNum
     * @param pageSize
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                     @RequestParam(value = "startTime", defaultValue = "") String startTime,
                                     @RequestParam(value = "endTime", defaultValue = "") String endTime) {
        LOGGER.info("{}:{}", pageNum, pageSize);
        InvoiceBO invoice = new InvoiceBO();

        Date date = new Date();
        if (startTime == null || "".equals(startTime)) {
            invoice.setStartTime(Constant.getToday(date));
        }
        if (endTime == null || "".equals(endTime)) {
            invoice.setEndTime(Constant.getToday(date));
        }
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<InvoiceBO> invoiceList = invoiceService.selectList(invoice);
        LOGGER.info("{}", invoiceList);
        return (invoiceList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("invoiceList", (Page) invoiceList, "total", ((Page) invoiceList).getTotal()));
    }

    /**
     * 索要发票
     *
     * @param userId
     * @return
     */
    @PostMapping(path = "/{userId}")
    public ResponseEntity addInvoice(@PathVariable("userId") String userId, @Valid @RequestBody InvoiceBO invoiceBO) {
        invoiceBO.setUserId(userId);
        InvoiceBO bo = invoiceService.addInvoice(invoiceBO);

        LOGGER.info("{}", bo);
        return new ResponseEntity<>(bo, HttpStatus.OK);
    }

    /**
     * 发票信息修改
     *
     * @param userId
     * @return
     */
    @PutMapping(path = "/{userId}/{id}")
    public ResponseEntity updateInvoice(@PathVariable("userId") String userId, @PathVariable("id") String id,
                                        @Valid @RequestBody InvoiceBO invoiceBO) {
        invoiceBO.setId(id);
        invoiceBO.setUserId(userId);
        InvoiceBO bo = invoiceService.updateInvoice(invoiceBO);

        LOGGER.info("{}", bo);
        return new ResponseEntity<>(bo, HttpStatus.OK);
    }

    /**
     * 发票信息删除
     *
     * @param userId
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{userId}/{id}")
    public ResponseEntity update(@PathVariable("userId") String userId, @PathVariable("id") String id) {
        InvoiceBO invoiceBO = new InvoiceBO();
        invoiceBO.setId(id);
        invoiceBO.setUserId(userId);
        int bo = invoiceService.deleteByIdAndUserId(invoiceBO);
        LOGGER.info("{}", bo);
        return new ResponseEntity<>(bo, HttpStatus.OK);
    }

}
