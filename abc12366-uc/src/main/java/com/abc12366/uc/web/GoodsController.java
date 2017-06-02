package com.abc12366.uc.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.model.Goods;
import com.abc12366.uc.model.GoodsCategory;
import com.abc12366.uc.model.bo.GoodsBO;
import com.abc12366.uc.model.bo.GoodsCategoryBO;
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
 * @create 2017-05-20 3:18 PM
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
        List<Goods> goodsList = goodsService.selectList(goods);
        LOGGER.info("{}", goodsList);
        return (goodsList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("goodsList", (Page) goodsList, "total", ((Page) goodsList).getTotal()));
    }

    /**
     * 产品分类列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(path = "/category")
    public ResponseEntity selectGategoryList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                     @RequestParam(value = "category", required = false) String category) {
        LOGGER.info("{}:{}", pageNum, pageSize);
        GoodsCategory goodsCategory = new GoodsCategory();
        goodsCategory.setCategory(category);
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<GoodsCategoryBO> categoryList = goodsCategoryService.selectList(goodsCategory);
        LOGGER.info("{}", categoryList);
        return (categoryList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("categoryList", (Page) categoryList, "total", ((Page) categoryList).getTotal()));
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
        return new ResponseEntity<>(goodsCategory, HttpStatus.OK);
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
        return new ResponseEntity<>(bo, HttpStatus.OK);
    }

    @PutMapping(path = "/category/{id}")
    public ResponseEntity updateGategory(@Valid @RequestBody GoodsCategoryBO goodsCategoryBO, @PathVariable("id") String id) {
        LOGGER.info("{}", goodsCategoryBO);
        goodsCategoryBO.setId(id);
        GoodsCategoryBO bo = goodsCategoryService.update(goodsCategoryBO);
        LOGGER.info("{}", bo);
        return new ResponseEntity<>(bo, HttpStatus.OK);
    }

    @DeleteMapping(path = "/category/{id}")
    public ResponseEntity deleteGategory(@PathVariable("id") String id) {
        LOGGER.info("{}", id);
        goodsCategoryService.delete(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }


/*
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> selectOne(@PathVariable String id) {
        LOGGER.info("{}", id);
        GoodsBO goodsBO = goodsService.selectOne(id);
        LOGGER.info("{}", goodsBO);
        return ResponseEntity.ok(goodsBO);
    }

    @PostMapping()
    public ResponseEntity add(@Valid @RequestBody GoodsBO goodsBO) {
        LOGGER.info("{}", goodsBO);
        GoodsBO bo = goodsService.add(goodsBO);
        LOGGER.info("{}", bo);
        return new ResponseEntity<>(bo, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity update(@Valid @RequestBody GoodsBO goodsBO, @PathVariable String id) {
        LOGGER.info("{}", goodsBO);
        goodsBO.setId(id);
        GoodsBO bo = goodsService.update(goodsBO);
        LOGGER.info("{}", bo);
        return new ResponseEntity<>(bo, HttpStatus.OK);
    }

    *//**
     * 删除产品信息，逻辑删除
     *
     * @param id
     * @return
     *//*
    @DeleteMapping(path = "/{id}")
    public ResponseEntity updateStatus(@PathVariable String id) {
        LOGGER.info("{}", id);
        goodsService.updateStatus(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }*/
}
