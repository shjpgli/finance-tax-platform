package com.abc12366.uc.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.model.Product;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.bo.ProductBO;
import com.abc12366.uc.service.ProductService;
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
 * User测试控制器类，包含CRUD接口；以常规JSON形式返回数据
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-20 3:18 PM
 * @since 2.0.0
 */
@RestController
@RequestMapping(path = "/product", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                     @RequestParam String name, @RequestParam String category, @RequestParam String vipLevel) {
        LOGGER.info("{}:{}", pageNum, pageSize);
        Product product = new Product();
        product.setName(name);
        product.setCategory(category);
        product.setVipLevel(vipLevel);
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<Product> productList = productService.selectList(product);
        LOGGER.info("{}", productList);
        return (productList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("productList", (Page) productList, "total", ((Page) productList).getTotal()));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> selectOne(@PathVariable String id) {
        LOGGER.info("{}", id);
        ProductBO productBO = productService.selectOne(id);
        LOGGER.info("{}", productBO);
        return ResponseEntity.ok(productBO);
    }

    @PostMapping()
    public ResponseEntity add(@Valid @RequestBody ProductBO productBO) {
        LOGGER.info("{}", productBO);
        ProductBO bo = productService.add(productBO);
        LOGGER.info("{}", bo);
        return new ResponseEntity<>(bo, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity update(@Valid @RequestBody ProductBO productBO, @PathVariable String id) {
        LOGGER.info("{}", productBO);
        productBO.setId(id);
        ProductBO bo = productService.update(productBO);
        LOGGER.info("{}", bo);
        return new ResponseEntity<>(bo, HttpStatus.OK);
    }
}
