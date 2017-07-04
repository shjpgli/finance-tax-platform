package com.abc12366.admin.web;

import com.abc12366.admin.model.Dict;
import com.abc12366.admin.model.bo.DictBO;
import com.abc12366.admin.model.bo.DictUpdateBO;
import com.abc12366.admin.service.DictService;
import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 字典控制器
 *
 * @create 2017-04-24 2:51 PM
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/dict", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class DictController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DictController.class);

    @Autowired
    private DictService dictService;

    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                     @RequestParam(value = "dictName", required = false) String dictName,
                                     @RequestParam(value = "status", required = false) Boolean status) {
        Dict dict = new Dict();
        dict.setDictName(dictName);
        dict.setStatus(status);
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<Dict> dictList = dictService.selectList(dict);
        LOGGER.info("{}",dictList);
        return (dictList == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) dictList, "total", ((Page) dictList).getTotal()));
    }

    @GetMapping(path="/{id}")
    public ResponseEntity selectById(@PathVariable("id") String id){
        Dict dict = dictService.selectById(id);
        LOGGER.info("{}",dict);
        return ResponseEntity.ok(Utils.kv("data",dict));
    }

    @GetMapping(path="/name")
    public ResponseEntity selectListByDictName(@RequestParam(value = "dictName", required = false) String dictName){
        Dict dict = new Dict();
        dict.setDictName(dictName);
        List<Dict> dictList = dictService.selectListByDictName(dict);
        LOGGER.info("{}",dictList);
        return ResponseEntity.ok(Utils.kv("dataList",dictList));
    }

    @GetMapping(path="/firstLevel")
    public ResponseEntity selectFirstLevel(){
        List<DictBO> dictBOs = dictService.selectFirstLevel();
        LOGGER.info("{}",dictBOs);
        return ResponseEntity.ok(Utils.kv("dataList",dictBOs));
    }

    @GetMapping(path="/kv/{dictId}")
    public ResponseEntity selectDictList(@PathVariable("dictId") String dictId){
        Dict dict = new Dict();
        dict.setDictId(dictId);
        List<DictBO> dictBOList = dictService.selectDictList(dict);
        LOGGER.info("{}",dictBOList);
        return ResponseEntity.ok(Utils.kv("dataList",dictBOList));
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity delete(@PathVariable("id") String id){
        LOGGER.info("{}",id);
        dictService.delete(id);
        return ResponseEntity.ok(Utils.kv());
    }

    @PutMapping(path="/{id}")
    public ResponseEntity update(@Valid @RequestBody DictUpdateBO dictUpdateBO, @PathVariable("id") String id){
        LOGGER.info("{}", dictUpdateBO, id);
        dictUpdateBO.setId(id);
        DictBO dictBO = dictService.update(dictUpdateBO);
        LOGGER.info("{}",dictBO);
        return ResponseEntity.ok(Utils.kv("data",dictBO));
    }

    @PostMapping
    public ResponseEntity insert(@Valid@RequestBody DictBO bo){
        LOGGER.info("{}",bo);
        DictBO dictBO = dictService.insert(bo);
        LOGGER.info("{}",dictBO);
        return ResponseEntity.ok(Utils.kv("data",dictBO));
    }

    @PostMapping(path = "/delete")
    public ResponseEntity batchDelete(@Valid@RequestBody Dict bo){
        LOGGER.info("{}",bo);
        dictService.batchDelete(bo);
        return ResponseEntity.ok(Utils.kv());
    }
}
