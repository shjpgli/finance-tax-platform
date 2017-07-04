package com.abc12366.uc.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.model.bo.ProductRepoBO;
import com.abc12366.uc.service.ProductRepoService;
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
 * 库存控制类
 *
 * @author lizhongwei
 * @create 2017-07-01
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/productRepo", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class ProductRepoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductRepoController.class);

    @Autowired
    private ProductRepoService productRepoService;

    /**
     * 库存列表管理
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                     @RequestParam(value = "type", required = false) String type,
                                     @RequestParam(value = "productName", required = false) String productName) {
        LOGGER.info("{}:{}", pageNum, pageSize);
        ProductRepoBO productRepo = new ProductRepoBO();
        productRepo.setProductName(productName);
        productRepo.setType(type);
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<ProductRepoBO> productRepoList = productRepoService.selectList(productRepo);
        LOGGER.info("{}", productRepoList);
        return (productRepoList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) productRepoList, "total", ((Page) productRepoList).getTotal()));
    }


    /**
     * 查询库存详情
     * @param id
     * @return
     */
    @GetMapping(path = "/selectOne/{id}")
    public ResponseEntity<?> selectOne(@PathVariable String id) {
        LOGGER.info("{}", id);
        ProductRepoBO productRepoBO = productRepoService.selectOne(id);
        LOGGER.info("{}", productRepoBO);
        return ResponseEntity.ok(Utils.kv("data", productRepoBO));
    }

    /**
     * 库存入库
     * @return
     */
    @PostMapping(path = "/income")
    public ResponseEntity income(@Valid @RequestBody ProductRepoBO productRepoBO) {
        LOGGER.info("{}", productRepoBO);
        ProductRepoBO bo = productRepoService.income(productRepoBO);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    /**
     * 库存入库
     * @return
     */
    @PostMapping(path = "/outcome")
    public ResponseEntity outcome(@Valid @RequestBody ProductRepoBO productRepoBO) {
        LOGGER.info("{}", productRepoBO);
        ProductRepoBO bo = productRepoService.outcome(productRepoBO);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

}
