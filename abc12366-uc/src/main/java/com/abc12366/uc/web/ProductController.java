package com.abc12366.uc.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.model.bo.GoodsBO;
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
 * 产品参数控制类
 *
 * @author lizhongwei
 * @create 2017-07-04
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/product", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    /**
     * 产品参数列表管理
     * @return
     */
    @GetMapping(path = "/select/{goodsId}")
    public ResponseEntity selectList(@PathVariable("goodsId") String goodsId) {
        LOGGER.info("{}:{}", goodsId);
        ProductBO product = new ProductBO();
        product.setGoodsId(goodsId);
        List<ProductBO> productList = productService.selectByGoodsId(product);
        LOGGER.info("{}", productList);
        return ResponseEntity.ok(Utils.kv("dataList",  productList));
    }

    /**
     * 查询商品库存列表
     * @param pageNum
     * @param pageSize
     * @param goodsName
     * @return
     */
    @GetMapping(path = "/productRepo")
    public ResponseEntity selectBOList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                            @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                            @RequestParam(value = "goodsName", required = false) String goodsName,
                                            @RequestParam(value = "startRepo", required = false) Integer startRepo,
                                            @RequestParam(value = "endRepo", required = false) Integer endRepo) {
        LOGGER.info("{}:{}", pageNum, pageSize);
        ProductBO productBO = new ProductBO();
        productBO.setGoodsName(goodsName);
        if(startRepo == null){
            startRepo = 0;
        }
        if(endRepo == null){
            endRepo = 100000000;
        }
        productBO.setStartRepo(startRepo);
        productBO.setEndRepo(endRepo);
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<GoodsBO> goodsList = productService.selectBOList(productBO);
        LOGGER.info("{}", goodsList);
        return (goodsList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) goodsList, "total", ((Page) goodsList).getTotal()));
    }

}
