package com.abc12366.uc.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.model.ProductRepo;
import com.abc12366.uc.model.bo.GoodsBO;
import com.abc12366.uc.model.bo.OrderBO;
import com.abc12366.uc.model.bo.ProductBO;
import com.abc12366.uc.model.bo.ProductRepoBO;
import com.abc12366.uc.service.ProductRepoService;
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

    public final static String startStock = "0";

    public final static String endStock = "10000";

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepoService productRepoService;


    /**
     * 产品参数列表查询
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
                                            @RequestParam(value = "startRepo", defaultValue = startStock) int startRepo,
                                            @RequestParam(value = "endRepo", defaultValue = endStock) int endRepo) {
        LOGGER.info("{}:{}", pageNum, pageSize);
        ProductBO productBO = new ProductBO();
        productBO.setGoodsName(goodsName);
        productBO.setStartRepo(startRepo);
        productBO.setEndRepo(endRepo);
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<GoodsBO> goodsList = productService.selectBOList(productBO);
        LOGGER.info("{}", goodsList);
        return (goodsList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) goodsList, "total", ((Page) goodsList).getTotal()));
    }

    /**
     * 查询库存详情
     * @return
     */
    @GetMapping(path = "/productRepo/selectOne")
    public ResponseEntity selectProductRepoDetail(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                                  @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                                  @RequestParam(value = "goodsId", required = true) String goodsId,
                                                  @RequestParam(value = "productId", required = true) String productId) {
        LOGGER.info("{}:{}", pageNum, pageSize);
        ProductRepo productRepo = new ProductRepo();
        productRepo.setGoodsId(goodsId);
        productRepo.setProductId(productId);
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<ProductRepoBO> goodsList = productRepoService.selectProductRepoDetail(productRepo);
        LOGGER.info("{}", goodsList);
        return (goodsList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) goodsList, "total", ((Page) goodsList).getTotal()));
    }

}
