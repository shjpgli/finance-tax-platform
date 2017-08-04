package com.abc12366.uc.web;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.Invoice;
import com.abc12366.uc.model.InvoiceBack;
import com.abc12366.uc.model.InvoiceDetail;
import com.abc12366.uc.model.InvoiceRepo;
import com.abc12366.uc.model.bo.InvoiceBO;
import com.abc12366.uc.model.bo.InvoiceBackBO;
import com.abc12366.uc.model.bo.InvoiceExcel;
import com.abc12366.uc.model.bo.InvoiceRepoBO;
import com.abc12366.uc.service.InvoiceRepoService;
import com.abc12366.uc.service.InvoiceService;
import com.abc12366.uc.util.DataUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
                                     @RequestParam(value = "consignee", required = false) String consignee,
                                     @RequestParam(value = "userOrderNo", required = false) String userOrderNo,
                                     @RequestParam(value = "username", required = false) String username,
                                     @RequestParam(value = "invoiceNo", required = false) String invoiceNo,
                                     @RequestParam(value = "property", required = false) String property,
                                     @RequestParam(value = "startTime", required = false) String startTime,
                                     @RequestParam(value = "endTime", required = false) String endTime) {
        LOGGER.info("{}:{}", pageNum, pageSize);
        InvoiceBO invoice = new InvoiceBO();
        invoice.setConsignee(consignee);
        invoice.setUserOrderNo(userOrderNo);
        invoice.setUsername(username);
        invoice.setInvoiceNo(invoiceNo);
        invoice.setProperty(property);
        if (startTime != null && !"".equals(startTime)) {
            invoice.setStartTime(DataUtils.StrToDate(startTime));
        }
        if (endTime != null && !"".equals(endTime)) {
            invoice.setEndTime(DataUtils.StrToDate(endTime));
        }

        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<InvoiceBO> invoiceList = invoiceService.selectList(invoice);
        LOGGER.info("{}", invoiceList);
        return (invoiceList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) invoiceList, "total", ((Page) invoiceList).getTotal()));
    }

    /**
     * 前台，历史发票列表管理
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(path = "/history")
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                     @RequestParam(value = "userId", required = true) String userId,
                                     @RequestParam(value = "status", required = true) String status) {
        LOGGER.info("{}:{}", pageNum, pageSize);
        InvoiceBO invoice = new InvoiceBO();
        invoice.setStatus(status);
        invoice.setUserId(userId);
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<InvoiceBO> invoiceList = invoiceService.selectList(invoice);
        LOGGER.info("{}", invoiceList);
        return (invoiceList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) invoiceList, "total", ((Page) invoiceList).getTotal()));
    }

    /**
     * 前台，票详情查看
     *
     * @param invoiceId
     * @return
     */
    @GetMapping(path = "/user/{invoiceId}/{userId}")
    public ResponseEntity userInvoice(@PathVariable("invoiceId") String invoiceId, @PathVariable("userId") String
            userId) {
        LOGGER.info("{}", invoiceId);
        Invoice invoice = new Invoice();
        invoice.setUserId(userId);
        invoice.setId(invoiceId);
        InvoiceBO bo = invoiceService.selectUserInvoice(invoice);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    /**
     * 发票详情查看
     *
     * @param invoiceId
     * @return
     */
    @GetMapping(path = "/{invoiceId}")
    public ResponseEntity selectInvoice(@PathVariable("invoiceId") String invoiceId) {
        LOGGER.info("{}", invoiceId);
        InvoiceBO bo = invoiceService.selectOne(invoiceId);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
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
     * 管理员开票、拒绝开票
     *
     * @return
     */
    @PostMapping(path = "/billing")
    public ResponseEntity billing(@Valid @RequestBody InvoiceBO invoiceBO) {
        InvoiceBO bo = invoiceService.billing(invoiceBO);
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
     *
     * @param pageNum
     * @param pageSize
     * @param invoiceCode
     * @return
     */
    @GetMapping(path = "/repo")
    public ResponseEntity selectInvoiceRepoList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int
                                                            pageNum,
                                                @RequestParam(value = "size", defaultValue = Constant.pageSize) int
                                                        pageSize,
                                                @RequestParam(value = "invoiceCode", required = false) String
                                                            invoiceCode) {
        LOGGER.info("{}:{}", pageNum, pageSize);
        InvoiceRepo invoiceRepo = new InvoiceRepo();
        invoiceRepo.setInvoiceCode(invoiceCode);
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<InvoiceRepo> invoiceList = invoiceRepoService.selectInvoiceRepoList(invoiceRepo);
        LOGGER.info("{}", invoiceList);
        return (invoiceList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
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
    @DeleteMapping(path = "/repo/{id}")
    public ResponseEntity deleteInvoiceRepo(@PathVariable("id") String id) {
        LOGGER.info("{}", id);
        invoiceRepoService.deleteInvoiceRepo(id);
        return ResponseEntity.ok(Utils.kv());
    }


    /**
     * 发票导出列表查询
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
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", invoiceList));
    }

    /**
     * 退票管理列表
     *
     * @return
     */
    @GetMapping(path = "/back")
    public ResponseEntity selectBackList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                         @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                         @RequestParam(value = "expressNo", required = false) String expressNo,
                                         @RequestParam(value = "userOrderNo", required = false) String userOrderNo,
                                         @RequestParam(value = "invoiceNo", required = false) String invoiceNo,
                                         @RequestParam(value = "sendExpressNo", required = false) String
                                                     sendExpressNo) {
        InvoiceBackBO invoiceBackBO = new InvoiceBackBO();
        invoiceBackBO.setExpressNo(expressNo);

        InvoiceBO invoiceBO = new InvoiceBO();
        invoiceBO.setUserOrderNo(userOrderNo);
        invoiceBO.setInvoiceNo(invoiceNo);
        invoiceBackBO.setInvoiceBO(invoiceBO);

        invoiceBackBO.setSendExpressNo(sendExpressNo);

        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<InvoiceBackBO> invoiceList = invoiceService.selectBOList(invoiceBackBO);
        PageInfo<InvoiceBackBO> pageInfo = new PageInfo<>(invoiceList);
        LOGGER.info("{}", invoiceList);
        return (invoiceList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(), "total", pageInfo.getTotal()));
    }

    /**
     * 退票详情查看
     *
     * @return
     */
    @GetMapping(path = "/back/{id}")
    public ResponseEntity selectBackOne(@PathVariable("id") String id) {

        InvoiceBackBO invoiceBackBO = invoiceService.selectBackOne(id);
        LOGGER.info("{}", invoiceBackBO);
        return ResponseEntity.ok(Utils.kv("data", invoiceBackBO));
    }

    /**
     * 发票退票
     *
     * @param expressId
     * @return
     */
    @PostMapping(path = "/back/{expressId}")
    public ResponseEntity refund(@PathVariable("expressId") String expressId, @Valid @RequestBody InvoiceBackBO
            invoiceBackBO) {
        invoiceBackBO.setExpressId(expressId);
        InvoiceBackBO bo = invoiceService.refund(invoiceBackBO);

        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    /**
     * 发票退票审核
     *
     * @param expressId
     * @return
     */
    @PostMapping(path = "/back/check/{expressId}/{id}")
    public ResponseEntity refundCheck(@PathVariable("expressId") String expressId, @PathVariable("id") String id,
                                      @Valid @RequestBody InvoiceBack invoiceBack) {
        invoiceBack.setId(id);
        invoiceBack.setExpressId(expressId);
        InvoiceBackBO bo = invoiceService.refundCheck(invoiceBack);

        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    /**
     * 发票仓库详情列表
     *
     * @param pageNum
     * @param pageSize
     * @param invoiceNo
     * @param invoiceCode
     * @param status
     * @return
     */
    @GetMapping(path = "/detail")
    public ResponseEntity selectInvoiceDetailList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int
                                                              pageNum,
                                                  @RequestParam(value = "size", defaultValue = Constant.pageSize) int
                                                          pageSize,
                                                  @RequestParam(value = "invoiceNo", required = false) String invoiceNo,
                                                  @RequestParam(value = "invoiceCode", required = false) String
                                                              invoiceCode,
                                                  @RequestParam(value = "status", required = false) String status,
                                                  @RequestParam(value = "invoiceRepoId", required = true) String
                                                              invoiceRepoId) {
        LOGGER.info("{}:{}", pageNum, pageSize);
        InvoiceDetail invoiceDetail = new InvoiceDetail();
        invoiceDetail.setInvoiceNo(invoiceNo);
        invoiceDetail.setInvoiceCode(invoiceCode);
        invoiceDetail.setStatus(status);
        invoiceDetail.setInvoiceRepoId(invoiceRepoId);
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<InvoiceDetail> invoiceList = invoiceRepoService.selectInvoiceDetailList(invoiceDetail);
        PageInfo<InvoiceDetail> pageInfo = new PageInfo<>(invoiceList);
        LOGGER.info("{}", invoiceList);
        return (invoiceList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(), "total", pageInfo.getTotal()));
    }

    /**
     * 根据发票号码，获取发票仓库详情信息
     *
     * @param invoiceNo
     * @return
     */
    @GetMapping(path = "/detail/invoice")
    public ResponseEntity selectInvoiceDetailListByInvoice(@RequestParam(value = "page", defaultValue = Constant
            .pageNum) int pageNum,
                                                           @RequestParam(value = "size", defaultValue = Constant
                                                                   .pageSize) int pageSize,
                                                           @RequestParam(value = "invoiceNo", required = false)
                                                               String invoiceNo) {
        InvoiceDetail invoiceDetail = new InvoiceDetail();
        invoiceDetail.setInvoiceNo(invoiceNo);
        invoiceDetail.setStatus("0");
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<InvoiceDetail> invoiceList = invoiceRepoService.selectInvoiceDetailListByInvoice(invoiceDetail);
        PageInfo<InvoiceDetail> pageInfo = new PageInfo<>(invoiceList);
        LOGGER.info("{}", invoiceList);
        return ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(), "total", pageInfo.getTotal()));
    }

    /**
     * 发票详情信息删除
     *
     * @return
     */
    @DeleteMapping(path = "/detail/del/{id}")
    public ResponseEntity deleteInvoiceDetail(@PathVariable("id") String id) {
        LOGGER.info("{}", id);
        invoiceRepoService.deleteInvoiceDetail(id);
        return ResponseEntity.ok(Utils.kv());
    }


    /**
     * 获取一张发票
     *
     * @return
     */
    @GetMapping(path = "/detail/select")
    public ResponseEntity selectInvoiceDetail() {
        InvoiceDetail invoiceDetail = invoiceRepoService.selectInvoiceDetail();
        LOGGER.info("{}", invoiceDetail);
        return ResponseEntity.ok(Utils.kv("data", invoiceDetail));
    }

    /**
     * 发票详情信息作废
     *
     * @return
     */
    @PutMapping(path = "/detail/invalid/{id}")
    public ResponseEntity invalidInvoiceDetail(@PathVariable("id") String id) {
        LOGGER.info("{}", id);
        invoiceRepoService.invalidInvoiceDetail(id);
        return ResponseEntity.ok(Utils.kv());
    }
}
