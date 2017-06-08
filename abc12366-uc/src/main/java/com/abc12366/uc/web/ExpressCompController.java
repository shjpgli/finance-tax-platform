package com.abc12366.uc.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.model.ExpressComp;
import com.abc12366.uc.model.bo.ExpressCompBO;
import com.abc12366.uc.service.ExpressCompService;
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
 * @since 2.0.0
 */
@RestController
@RequestMapping(path = "/express", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class ExpressCompController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpressCompController.class);

    @Autowired
    private ExpressCompService expressCompService;

    @GetMapping(path = "/comp")
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                     @RequestParam(value = "compName", required = false) String compName) {
        LOGGER.info("{}:{}", pageNum, pageSize);
        ExpressComp expressComp = new ExpressComp();
        expressComp.setCompName(compName);
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<ExpressComp> compList = expressCompService.selectList(expressComp);
        LOGGER.info("{}", compList);
        return (compList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("compList", (Page) compList, "total", ((Page) compList).getTotal()));
    }

    /**
     * 新增商品
     * @param expressCompBO
     * @return
     */
    @PostMapping(path = "/comp")
    public ResponseEntity addComp(@Valid @RequestBody ExpressCompBO expressCompBO) {
        LOGGER.info("{}", expressCompBO);
        ExpressCompBO bo = expressCompService.add(expressCompBO);
        LOGGER.info("{}", bo);
        return new ResponseEntity<>(bo, HttpStatus.OK);
    }

    /**
     * 查询单个商品
     * @param id
     * @return
     */
    @GetMapping(path = "/comp/{id}")
    public ResponseEntity selectExpressComp(@PathVariable("id") String id) {
        LOGGER.info("{}", id);
        ExpressComp expressComp = expressCompService.selectExpressComp(id);
        LOGGER.info("{}", expressComp);
        return new ResponseEntity<>(expressComp, HttpStatus.OK);
    }

    /**
     * 修改商品信息
     * @param expressCompBO
     * @param id
     * @return
     */
    @PutMapping(path = "/comp/{id}")
    public ResponseEntity updateExpressComp(@Valid @RequestBody ExpressCompBO expressCompBO, @PathVariable("id") String id) {
        LOGGER.info("{}", expressCompBO);
        expressCompBO.setId(id);
        ExpressCompBO bo = expressCompService.update(expressCompBO);
        LOGGER.info("{}", bo);
        return new ResponseEntity<>(bo, HttpStatus.OK);
    }

    /**
     * 审核商品信息
     * @return
     */
    @DeleteMapping(path = "/comp/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        LOGGER.info("{}", id);
        expressCompService.delete(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }


}
