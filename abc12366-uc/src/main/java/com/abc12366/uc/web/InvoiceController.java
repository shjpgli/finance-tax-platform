package com.abc12366.uc.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.model.InvoiceBack;
import com.abc12366.uc.model.InvoiceRepo;
import com.abc12366.uc.model.bo.InvoiceBO;
import com.abc12366.uc.model.bo.InvoiceBackBO;
import com.abc12366.uc.model.bo.InvoiceExcel;
import com.abc12366.uc.model.bo.InvoiceRepoBO;
import com.abc12366.uc.service.InvoiceRepoService;
import com.abc12366.uc.service.InvoiceService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
    private InvoiceRepoService invoiceRepoService;

    @Autowired
    private InvoiceService invoiceService;
    /**
     * 发票列表管理
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
                                     @RequestParam(value ="startTime") @DateTimeFormat(pattern="yyyy-MM-dd") Date startTime,
                                     @RequestParam(value ="endTime") @DateTimeFormat(pattern="yyyy-MM-dd") Date endTime){
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
                ResponseEntity.ok(Utils.kv("dataList", (Page) invoiceList, "total", ((Page) invoiceList).getTotal()));
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
        return ResponseEntity.ok(Utils.kv("data", bo));
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
        return ResponseEntity.ok(Utils.kv("data", bo));
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
        return ResponseEntity.ok(Utils.kv("data", bo));
    }


    /**
     * 发票仓库列表
     * @param pageNum
     * @param pageSize
     * @param invoiceNo
     * @param invoiceCode
     * @param property
     * @param status
     * @return
     */
    @GetMapping(path = "/repo")
    public ResponseEntity selectInvoiceRepoList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                                @RequestParam(value = "invoiceNo", required = false) String invoiceNo,
                                                @RequestParam(value = "invoiceCode", required = false) String invoiceCode,
                                                @RequestParam(value = "property", required = false) String property,
                                                @RequestParam(value = "status", required = false) String status) {
        LOGGER.info("{}:{}", pageNum, pageSize);
        InvoiceRepo invoiceRepo = new InvoiceRepo();
        invoiceRepo.setInvoiceNo(invoiceNo);
        invoiceRepo.setInvoiceCode(invoiceCode);
        invoiceRepo.setProperty(property);
        invoiceRepo.setStatus(status);
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<InvoiceRepo> invoiceList = invoiceRepoService.selectInvoiceRepoList(invoiceRepo);
        LOGGER.info("{}", invoiceList);
        return (invoiceList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) invoiceList, "total", ((Page) invoiceList).getTotal()));
    }

    /**
     * 发票仓库新增
     *
     * @return
     */
    @PostMapping(path = "/repo")
    public ResponseEntity addInvoiceRepo(@Valid @RequestBody InvoiceRepoBO invoiceRepoBO) {
        LOGGER.info("{}", invoiceRepoBO);
        InvoiceRepoBO bo = invoiceRepoService.addInvoiceRepo(invoiceRepoBO);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    /**
     * 发票仓库删除
     *
     * @return
     */
    @PostMapping(path = "/repo/{id}")
    public ResponseEntity deleteInvoiceRepo( @PathVariable("id") String id) {
        LOGGER.info("{}", id);
        invoiceRepoService.deleteInvoiceRepo(id);
        return ResponseEntity.ok(Utils.kv());
    }


    /**
     * 发票列表管理
     *
     * @return
     */
    @GetMapping(path = "/export")
    public ResponseEntity exportInvoice(@RequestParam(value = "startTime", defaultValue = "") String startTime,
                                        @RequestParam(value = "endTime", defaultValue = "") String endTime,
                                        @RequestParam(value = "status", required = false) String status) {
        InvoiceBO invoice = new InvoiceBO();
        invoice.setStatus(status);
        Date date = new Date();
        if (startTime == null || "".equals(startTime)) {
            invoice.setStartTime(Constant.getToday(date));
        }
        if (endTime == null || "".equals(endTime)) {
            invoice.setEndTime(Constant.getToday(date));
        }
        List<InvoiceExcel> invoiceList = invoiceService.selectInvoiceExcelList(invoice);
        LOGGER.info("{}", invoiceList);
        return (invoiceList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST) :
          ResponseEntity.ok(Utils.kv("dataList", invoiceList));
    }

    /**
     * 退票
     *
     * @param expressId
     * @return
     */
    @PostMapping(path = "/back/{expressId}")
    public ResponseEntity refund(@PathVariable("expressId") String expressId, @Valid @RequestBody InvoiceBackBO invoiceBackBO) {
        invoiceBackBO.setExpressId(expressId);
        InvoiceBackBO bo = invoiceService.refund(invoiceBackBO);

        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    /**
     * 退票
     *
     * @param expressId
     * @return
     */
    @PutMapping(path = "/back/{expressId}/{id}")
    public ResponseEntity refundCheck(@PathVariable("expressId") String expressId,@PathVariable("id") String id, @Valid @RequestBody InvoiceBackBO invoiceBackBO) {
        invoiceBackBO.setId(id);
        invoiceBackBO.setExpressId(expressId);
        InvoiceBackBO bo = invoiceService.refundCheck(invoiceBackBO);

        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }
}
