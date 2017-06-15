package com.abc12366.cms.web;

import com.abc12366.cms.model.bo.ModelItemBo;
import com.abc12366.cms.model.bo.ModelItemListBo;
import com.abc12366.cms.service.ModelItemService;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模型项管理模块
 *
 * @author xieyanmao
 * @create 2017-05-08
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/modelItem",headers = Constant.VERSION_HEAD + "=1")
public class ModelItemController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelItemController.class);

    @Autowired
    private ModelItemService modelItemService;

    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size,
                                     @RequestParam(value = "modelId", required = false) String modelId,
                                     @RequestParam(value = "isChannel", required = false) String isChannel) {
        //查询模型项
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("modelId",modelId);
        dataMap.put("isChannel",isChannel);
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<ModelItemBo> dataList = modelItemService.selectList(dataMap);
        LOGGER.info("{}", dataList);
        return ResponseEntity.ok(Utils.kv("dataList", (Page) dataList, "total", ((Page) dataList).getTotal()));

    }

    @PostMapping
    public ResponseEntity save(@RequestBody ModelItemBo modelItemBo) {
        LOGGER.info("{}", modelItemBo);
        //新增评论信息
        modelItemBo = modelItemService.save(modelItemBo);
        LOGGER.info("{}", modelItemBo);
        return ResponseEntity.ok(Utils.kv("data", modelItemBo));
    }

    @GetMapping(path = "/{modelItemId}")
    public ResponseEntity selectOne(@PathVariable String modelItemId) {
        LOGGER.info("{}", modelItemId);
        //查询评论信息
        ModelItemBo modelBo = modelItemService.selectModel(modelItemId);
        LOGGER.info("{}", modelBo);
        return ResponseEntity.ok(Utils.kv("data", modelBo));
    }

    @PutMapping(path = "/{modelItemId}")
    public ResponseEntity update(@PathVariable String modelItemId,
                                 @Valid @RequestBody ModelItemBo modelItemBo) {

        LOGGER.info("{}", modelItemBo);
        //更新评论信息
        modelItemBo = modelItemService.update(modelItemBo);
        LOGGER.info("{}", modelItemBo);
        return ResponseEntity.ok(Utils.kv("data", modelItemBo));
    }

    @PostMapping(path = "/saveList")
    public ResponseEntity saveList(@Valid @RequestBody ModelItemListBo modelItemListBo) {

        LOGGER.info("{}", modelItemListBo);
        //更新评论信息
        modelItemListBo = modelItemService.saveList(modelItemListBo);
        LOGGER.info("{}", modelItemListBo);
        return ResponseEntity.ok(Utils.kv("data", modelItemListBo));
    }

    @PutMapping(path = "/updateList")
    public ResponseEntity updateList(@Valid @RequestBody ModelItemListBo modelItemListBo) {

        LOGGER.info("{}", modelItemListBo);
        //更新评论信息
        modelItemListBo = modelItemService.updateList(modelItemListBo);
        LOGGER.info("{}", modelItemListBo);
        return ResponseEntity.ok(Utils.kv("data", modelItemListBo));
    }

    @DeleteMapping(path = "/{modelItemId}")
     public ResponseEntity delete(@PathVariable String modelItemId) {
        LOGGER.info("{}", modelItemId);
        //删除评论信息
        String rtn = modelItemService.delete(modelItemId);
        LOGGER.info("{}", rtn);
        return ResponseEntity.ok(Utils.kv("data", rtn));
    }

    @DeleteMapping(path = "/deleteList")
    public ResponseEntity deleteList(@RequestParam(value = "modelItemIds", required = true) String[] modelItemIds) {
        LOGGER.info("{}", modelItemIds);
        //删除评论信息
        String rtn = modelItemService.deleteList(modelItemIds);
        LOGGER.info("{}", rtn);
        return ResponseEntity.ok(Utils.kv("data", rtn));
    }




}
