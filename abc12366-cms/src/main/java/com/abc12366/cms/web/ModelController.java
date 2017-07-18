package com.abc12366.cms.web;

import com.abc12366.cms.model.bo.IdsBo;
import com.abc12366.cms.model.bo.ModelBo;
import com.abc12366.cms.model.bo.ModelListBo;
import com.abc12366.cms.service.ModelService;
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
import java.util.List;

/**
 * 模型管理模块
 *
 * @author xieyanmao
 * @create 2017-05-08
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/model",headers = Constant.VERSION_HEAD + "=1")
public class ModelController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelController.class);

    @Autowired
    private ModelService modelService;

    /**
     * 模型列表查询
     */
    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<ModelBo> dataList = modelService.selectList();
        LOGGER.info("{}", dataList);
        return ResponseEntity.ok(Utils.kv("dataList", (Page) dataList, "total", ((Page) dataList).getTotal()));

    }

    /**
     * 模型新增
     */
    @PostMapping
    public ResponseEntity save(@RequestBody ModelBo modelBo) {
        LOGGER.info("{}", modelBo);
        //新增模型信息
        modelBo = modelService.save(modelBo);
        LOGGER.info("{}", modelBo);
        return ResponseEntity.ok(Utils.kv("data", modelBo));
    }

    /**
     * 查询单个模型信息
     */
    @GetMapping(path = "/{modelId}")
    public ResponseEntity selectOne(@PathVariable String modelId) {
        LOGGER.info("{}", modelId);
        //查询模型信息
        ModelBo modelBo = modelService.selectModel(modelId);
        LOGGER.info("{}", modelBo);
        return ResponseEntity.ok(Utils.kv("data", modelBo));
    }

    /**
     * 更新模型信息
     */
    @PutMapping(path = "/{modelId}")
    public ResponseEntity update(@PathVariable String modelId,
                                 @Valid @RequestBody ModelBo modelBo) {

        LOGGER.info("{}", modelBo);
        //更新模型信息
        modelBo = modelService.update(modelBo);
        LOGGER.info("{}", modelBo);
        return ResponseEntity.ok(Utils.kv("data", modelBo));
    }

    /**
     * 批量更新模型信息
     */
    @PutMapping(path = "/updateList")
    public ResponseEntity updateList(@Valid @RequestBody ModelListBo modelListBo) {

        LOGGER.info("{}", modelListBo);
        //更新模型信息
        modelListBo = modelService.updateList(modelListBo);
        LOGGER.info("{}", modelListBo);
        return ResponseEntity.ok(Utils.kv("data", modelListBo));
    }

    /**
     * 删除模型信息
     */
    @DeleteMapping(path = "/{modelId}")
    public ResponseEntity delete(@PathVariable String modelId) {
        LOGGER.info("{}", modelId);
        //删除模型信息
        String rtn = modelService.delete(modelId);
        LOGGER.info("{}", rtn);
        return ResponseEntity.ok(Utils.kv("data", rtn));
    }

    /**
     * 批量删除模型信息
     */
    @PostMapping(path = "/deleteList")
    public ResponseEntity deleteList(@RequestBody IdsBo idsBo) {
        LOGGER.info("{}", idsBo);
        //批量删除模型信息
        String rtn = modelService.deleteList(idsBo.getIds());
        LOGGER.info("{}", rtn);
        return ResponseEntity.ok(Utils.kv("data", idsBo));
    }




}
