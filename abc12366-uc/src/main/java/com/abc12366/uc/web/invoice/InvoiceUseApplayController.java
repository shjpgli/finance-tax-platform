package com.abc12366.uc.web.invoice;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.Invoice;
import com.abc12366.uc.model.InvoiceBack;
import com.abc12366.uc.model.bo.InvoiceBO;
import com.abc12366.uc.model.bo.InvoiceBackBO;
import com.abc12366.uc.model.bo.InvoiceExcel;
import com.abc12366.uc.model.invoice.InvoiceDetail;
import com.abc12366.uc.model.invoice.InvoiceRepo;
import com.abc12366.uc.model.invoice.InvoiceUseApply;
import com.abc12366.uc.model.invoice.InvoiceUseDetail;
import com.abc12366.uc.model.invoice.bo.*;
import com.abc12366.uc.service.InvoiceRepoService;
import com.abc12366.uc.service.InvoiceService;
import com.abc12366.uc.service.invoice.InvoiceUseApplyService;
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
 * 发票领用申请控制类
 *
 * @author lizhongwei
 * @create 2017-08-09
 * @since 2.0.0
 */
@RestController
@RequestMapping(headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class InvoiceUseApplayController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceUseApplayController.class);

    @Autowired
    private InvoiceUseApplyService invoiceUseApplyService;

    /**
     * 发票领用申请列表管理
     *
     * @param pageNum
     * @param pageSize
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping(path = "/invoice/use")
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                     @RequestParam(value = "issueStatus", required = false) String issueStatus,
                                     @RequestParam(value = "examineStatus", required = false) String examineStatus,
                                     @RequestParam(value = "applyUser", required = false) String applyUser,
                                     @RequestParam(value = "startTime", required = false) String startTime,
                                     @RequestParam(value = "endTime", required = false) String endTime) {
        LOGGER.info("{}:{}", pageNum, pageSize);
        InvoiceUseApplyBO applyBO = new InvoiceUseApplyBO();
        applyBO.setIssueStatus(issueStatus);
        applyBO.setExamineStatus(examineStatus);
        applyBO.setApplyUser(applyUser);
        if (startTime != null && !"".equals(startTime)) {
            applyBO.setStartTime(DataUtils.StrToDate(startTime));
        }
        if (endTime != null && !"".equals(endTime)) {
            applyBO.setEndTime(DataUtils.StrToDate(endTime));
        }

        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<InvoiceUseApplyBO> applyBOs = invoiceUseApplyService.selectList(applyBO);
        LOGGER.info("{}", applyBOs);
        return (applyBOs == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) applyBOs, "total", ((Page) applyBOs).getTotal()));
    }

    /**
     * 根据发票种类代码，获取发票仓库库存数，可用份数
     *
     * @return
     */
    @GetMapping(path = "/invoice/use/num/{code}")
    public ResponseEntity selectInvoiceRepoNum(@PathVariable("code") String code) {
        InvoiceUseDetailBO bo = invoiceUseApplyService.selectInvoiceRepoNum(code);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    /**
     * 发票领用申请详情查看
     *
     * @return
     */
    @GetMapping(path = "/invoice/use/{id}")
    public ResponseEntity selectInvoiceUseApply(@PathVariable("id") String id) {
        LOGGER.info("{}", id);
        InvoiceUseApply invoiceUseApply = new InvoiceUseApply();
        invoiceUseApply.setId(id);
        InvoiceUseApplyBO bo = invoiceUseApplyService.selectInvoiceUseApply(invoiceUseApply);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    /**
     * 发票领用申请新增
     *
     * @return
     */
    @PostMapping(path = "/invoice/use")
    public ResponseEntity add(@Valid @RequestBody InvoiceUseApplyBO invoiceUseApplyBO) {
        LOGGER.info("{}", invoiceUseApplyBO);
        InvoiceUseApplyBO bo = invoiceUseApplyService.add(invoiceUseApplyBO);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    /**
     * 发票领用申请新增
     *
     * @return
     */
    @PutMapping(path = "/invoice/use")
    public ResponseEntity update(@Valid @RequestBody InvoiceUseApplyBO invoiceUseApplyBO) {
        LOGGER.info("{}", invoiceUseApplyBO);
        InvoiceUseApplyBO bo = invoiceUseApplyService.update(invoiceUseApplyBO);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    /**
     * 发票领用申请删除
     *
     * @return
     */
    @DeleteMapping(path = "/invoice/use/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        LOGGER.info("{}", id);
        invoiceUseApplyService.delete(id);
        return ResponseEntity.ok(Utils.kv());
    }

    /**
     * 发票领用申请审核
     *
     * @return
     */
    @PostMapping(path = "/invoice/use/check/{id}")
    public ResponseEntity checkUseApplay(@Valid @RequestBody InvoiceUseCheckBO invoiceUseCheckBO) {
        LOGGER.info("{}", invoiceUseCheckBO);
        invoiceUseApplyService.checkUseApplay(invoiceUseCheckBO);
        return ResponseEntity.ok(Utils.kv());
    }

    /**
     * 发票领用申请分发
     *
     * @return
     */
    @PostMapping(path = "/invoice/dist/send")
    public ResponseEntity distributeUseApply(@Valid @RequestBody InvoiceDistributeBO invoiceDistributeBO) {
        LOGGER.info("{}", invoiceDistributeBO);
        invoiceUseApplyService.distributeUseApply(invoiceDistributeBO);
        return ResponseEntity.ok(Utils.kv());
    }

    /**
     * 发票领用申请分发后，签收
     *
     * @return
     */
    @PostMapping(path = "/invoice/dist/sign")
    public ResponseEntity signUseApply(@Valid @RequestBody InvoiceDistributeBO invoiceDistributeBO) {
        LOGGER.info("{}", invoiceDistributeBO);
        invoiceUseApplyService.signUseApply(invoiceDistributeBO);
        return ResponseEntity.ok(Utils.kv());
    }

}
