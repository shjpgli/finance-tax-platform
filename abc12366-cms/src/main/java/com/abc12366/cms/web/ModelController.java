package com.abc12366.cms.web;

import com.abc12366.cms.model.bo.ModelBo;
import com.abc12366.cms.service.ModelService;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
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

    @GetMapping
    public ResponseEntity selectList() {
        List<ModelBo> dataList = modelService.selectList();
        LOGGER.info("{}", dataList);
        return ResponseEntity.ok(dataList);

    }

    @PostMapping
    public ResponseEntity save(@RequestBody ModelBo modelBo) {
        LOGGER.info("{}", modelBo);
        //新增评论信息
        modelBo = modelService.save(modelBo);
        LOGGER.info("{}", modelBo);
        return ResponseEntity.ok(modelBo);
    }

    @GetMapping(path = "/{modelId}")
    public ResponseEntity selectOne(@PathVariable String modelId) {
        LOGGER.info("{}", modelId);
        //查询评论信息
        ModelBo modelBo = modelService.selectModel(modelId);
        LOGGER.info("{}", modelBo);
        return ResponseEntity.ok(modelBo);
    }

    @PutMapping(path = "/{modelId}")
    public ResponseEntity update(@PathVariable String modelId,
                                 @Valid @RequestBody ModelBo modelBo) {

        LOGGER.info("{}", modelBo);
        //更新评论信息
        modelBo = modelService.update(modelBo);
        LOGGER.info("{}", modelBo);
        return ResponseEntity.ok(modelBo);
    }

    @DeleteMapping(path = "/{modelId}")
    public ResponseEntity delete(@PathVariable String modelId) {
        LOGGER.info("{}", modelId);
        //删除评论信息
        String rtn = modelService.delete(modelId);
        LOGGER.info("{}", rtn);
        return ResponseEntity.ok(rtn);
    }




}
