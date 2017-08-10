package com.abc12366.uc.web.invoice;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.invoice.InvoiceRepo;
import com.abc12366.uc.model.invoice.InvoiceRepo;
import com.abc12366.uc.model.invoice.bo.InvoiceRepoBO;
import com.abc12366.uc.model.invoice.bo.InvoiceRepoBO;
import com.abc12366.uc.model.invoice.bo.InvoiceUseCheckBO;
import com.abc12366.uc.service.invoice.InvoiceRepoService;
import com.abc12366.uc.util.DataUtils;
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
 * 发票领用申请控制类
 *
 * @author lizhongwei
 * @create 2017-08-09
 * @since 2.0.0
 */
@RestController
@RequestMapping(path = "/invoice/repo", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class InvoiceRepoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceRepoController.class);

    @Autowired
    private InvoiceRepoService invoiceRepoService;

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
        InvoiceRepoBO invoiceRepo = new InvoiceRepoBO();
        invoiceRepo.setInvoiceCode(invoiceCode);
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<InvoiceRepoBO> invoiceList = invoiceRepoService.selectList(invoiceRepo);
        LOGGER.info("{}", invoiceList);
        return (invoiceList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) invoiceList, "total", ((Page) invoiceList).getTotal()));
    }

    /**
     * 发票仓库列表
     *
     * @return
     */
    @GetMapping(path = "/repo/{id}")
    public ResponseEntity selectInvoiceRepo(@PathVariable("id") String id) {
        InvoiceRepo invoiceRepo = new InvoiceRepo();
        invoiceRepo.setId(id);
        InvoiceRepoBO bo = invoiceRepoService.selectInvoiceRepo(invoiceRepo);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    /**
     * 发票仓库新增
     *
     * @return
     */
    @PostMapping(path = "/repo")
    public ResponseEntity addInvoiceRepo(@Valid @RequestBody InvoiceRepoBO invoiceRepoBO) {
        LOGGER.info("{}", invoiceRepoBO);
        InvoiceRepoBO bo = invoiceRepoService.add(invoiceRepoBO);
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
        invoiceRepoService.delete(id);
        return ResponseEntity.ok(Utils.kv());
    }

}
