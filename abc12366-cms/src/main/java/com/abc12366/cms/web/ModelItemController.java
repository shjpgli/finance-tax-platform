package com.abc12366.cms.web;

import com.abc12366.cms.model.bo.ModelBo;
import com.abc12366.cms.model.bo.ModelItemBo;
import com.abc12366.cms.model.bo.ModelItemListBo;
import com.abc12366.cms.service.ModelItemService;
import com.abc12366.cms.service.ModelService;
import com.abc12366.common.util.Constant;
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
    public ResponseEntity selectList(@RequestParam(value = "modelId", required = false) String modelId,
                                     @RequestParam(value = "isChannel", required = false) String isChannel) {
        //查询模型项
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("modelId",modelId);
        dataMap.put("isChannel",0);
        List<ModelItemBo> dataList = modelItemService.selectList(dataMap);
        LOGGER.info("{}", dataList);
        return ResponseEntity.ok(dataList);

    }

    @PostMapping
    public ResponseEntity save(@RequestBody ModelItemBo modelItemBo) {
        LOGGER.info("{}", modelItemBo);
        //新增评论信息
        modelItemBo = modelItemService.save(modelItemBo);
        LOGGER.info("{}", modelItemBo);
        return ResponseEntity.ok(modelItemBo);
    }

    @GetMapping(path = "/{modelItemId}")
    public ResponseEntity selectOne(@PathVariable String modelItemId) {
        LOGGER.info("{}", modelItemId);
        //查询评论信息
        ModelItemBo modelBo = modelItemService.selectModel(modelItemId);
        LOGGER.info("{}", modelBo);
        return ResponseEntity.ok(modelBo);
    }

    @PutMapping(path = "/{modelItemId}")
    public ResponseEntity update(@PathVariable String modelItemId,
                                 @Valid @RequestBody ModelItemBo modelItemBo) {

        LOGGER.info("{}", modelItemBo);
        //更新评论信息
        modelItemBo = modelItemService.update(modelItemBo);
        LOGGER.info("{}", modelItemBo);
        return ResponseEntity.ok(modelItemBo);
    }

    @PutMapping(path = "/updateList")
    public ResponseEntity updateList(@Valid @RequestBody ModelItemListBo modelItemListBo) {

        LOGGER.info("{}", modelItemListBo);
        //更新评论信息
        modelItemListBo = modelItemService.updateList(modelItemListBo);
        LOGGER.info("{}", modelItemListBo);
        return ResponseEntity.ok(modelItemListBo);
    }

    @DeleteMapping(path = "/{modelItemId}")
    public ResponseEntity delete(@PathVariable String modelItemId) {
        LOGGER.info("{}", modelItemId);
        //删除评论信息
        String rtn = modelItemService.delete(modelItemId);
        LOGGER.info("{}", rtn);
        return ResponseEntity.ok(rtn);
    }




}
