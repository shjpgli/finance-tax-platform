package com.abc12366.uc.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.model.Goods;
import com.abc12366.uc.model.GoodsCategory;
import com.abc12366.uc.model.bo.GoodsBO;
import com.abc12366.uc.model.bo.GoodsCategoryBO;
import com.abc12366.uc.model.bo.GoodsCheckBO;
import com.abc12366.uc.service.GoodsCategoryService;
import com.abc12366.uc.service.GoodsService;
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
 * @author lizhongwei
 * @since 2.0.0
 */
@RestController
@RequestMapping(path = "/goods", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class GoodsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                     @RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "categoryId", required = false) String categoryId,
                                     @RequestParam(value = "recommendType", required = false) String recommendType) {
        LOGGER.info("{}:{}", pageNum, pageSize);
        Goods goods = new Goods();
        goods.setName(name);
        goods.setCategoryId(categoryId);
        goods.setRecommendType(recommendType);
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<GoodsBO> goodsList = goodsService.selectList(goods);
        LOGGER.info("{}", goodsList);
        return (goodsList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) goodsList, "total", ((Page) goodsList).getTotal()));
    }

    @GetMapping(path = "/name")
    public ResponseEntity selectListByName(@RequestParam(value = "name", required = false) String name) {
        LOGGER.info("{}", name);
        Goods goods = new Goods();
        goods.setName(name);
        List<Goods> goodsList = goodsService.selectGoodsList(goods);
        LOGGER.info("{}", goodsList);
        return ResponseEntity.ok(Utils.kv("dataList", goodsList));
    }


    @GetMapping(path = "/user")
    public ResponseEntity selectGoodsList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                     @RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "categoryId", required = false) String categoryId,
                                     @RequestParam(value = "recommendType", required = false) String recommendType) {
        LOGGER.info("{}:{}", pageNum, pageSize);
        Goods goods = new Goods();
        goods.setName(name);
        goods.setCategoryId(categoryId);
        goods.setRecommendType(recommendType);
        //已发布状态
        goods.setStatus(true);
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<GoodsBO> goodsList = goodsService.selectList(goods);
        LOGGER.info("{}", goodsList);
        return (goodsList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) goodsList, "total", ((Page) goodsList).getTotal()));
    }
    /**
     * 新增商品
     * @param goodsBO
     * @return
     */
    @PostMapping
    public ResponseEntity addGoods(@Valid @RequestBody GoodsBO goodsBO) {
        LOGGER.info("{}", goodsBO);
        GoodsBO bo = goodsService.add(goodsBO);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    /**
     * 查询单个商品
     * @param id
     * @return
     */
    @PostMapping(path = "/{id}")
    public ResponseEntity selectGoods(@PathVariable("id") String id) {
        LOGGER.info("{}", id);
        GoodsBO goodsBO = goodsService.selectGoods(id);
        LOGGER.info("{}", goodsBO);
        return ResponseEntity.ok(Utils.kv("data", goodsBO));
    }

    /**
     * 修改商品信息
     * @param goodsBO
     * @param id
     * @return
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity updateGoods(@Valid @RequestBody GoodsBO goodsBO, @PathVariable("id") String id) {
        LOGGER.info("{}", goodsBO);
        goodsBO.setId(id);
        GoodsBO bo = goodsService.update(goodsBO);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    /**
     * 修改商品信息
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteGoods(@PathVariable("id") String id) {
        LOGGER.info("{}", id);
        goodsService.deleteGoods(id);
        return ResponseEntity.ok(Utils.kv());
    }

    /**
     * 审核商品信息
     * @return
     */
    @PutMapping(path = "/check/{id}/{status}")
    public ResponseEntity checkGoods(@Valid @RequestBody GoodsCheckBO goodsCheckBO) {
        LOGGER.info("{}", goodsCheckBO);
        goodsService.checkGoods(goodsCheckBO);
        return ResponseEntity.ok(Utils.kv());
    }


    /**
     * 产品分类列表
     * @return
     */
    @GetMapping(path = "/category")
    public ResponseEntity selectGategoryList(
                                     @RequestParam(value = "category", required = false) String category) {
        GoodsCategory goodsCategory = new GoodsCategory();
        goodsCategory.setCategory(category);
        GoodsCategoryBO categoryBO = goodsCategoryService.selectList(goodsCategory);
        LOGGER.info("{}", categoryBO);
        return (categoryBO == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST) :
                 ResponseEntity.ok(Utils.kv("data", categoryBO));
    }


    /**
     * 查询单个产品分类
     * @param id
     * @return
     */
    @PostMapping(path = "/category/{id}")
    public ResponseEntity selectGategory(@PathVariable("id") String id) {
        LOGGER.info("{}", id);
        GoodsCategory goodsCategory = goodsCategoryService.selectGategory(id);
        LOGGER.info("{}", goodsCategory);
        return ResponseEntity.ok(Utils.kv("data", goodsCategory));
    }

    /**
     * 新增产品分类
     * @param goodsCategoryBO
     * @return
     */
    @PostMapping(path = "/category")
    public ResponseEntity addGategory(@Valid @RequestBody GoodsCategoryBO goodsCategoryBO) {
        LOGGER.info("{}", goodsCategoryBO);
        GoodsCategory bo = goodsCategoryService.add(goodsCategoryBO);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    @PutMapping(path = "/category/{id}")
    public ResponseEntity updateGategory(@Valid @RequestBody GoodsCategoryBO goodsCategoryBO, @PathVariable("id") String id) {
        LOGGER.info("{}", goodsCategoryBO);
        goodsCategoryBO.setId(id);
        GoodsCategoryBO bo = goodsCategoryService.update(goodsCategoryBO);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    @DeleteMapping(path = "/category/{id}")
    public ResponseEntity deleteGategory(@PathVariable("id") String id) {
        LOGGER.info("{}", id);
        goodsCategoryService.delete(id);
        return ResponseEntity.ok(Utils.kv());
    }
}
