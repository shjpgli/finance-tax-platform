package com.abc12366.uc.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.model.bo.VipLevelBO;
import com.abc12366.uc.model.bo.VipLevelUpdateBO;
import com.abc12366.uc.service.VipLevelService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户等级接口
 *
 * @author liuguiyao<435720953@qq.com.com>
 * @create 2017-05-19 10:18 PM
 * @since 2.0.0
 */
@RestController
@RequestMapping(path = "/uvip/level", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class VipLevelController {
    private static final Logger LOGGER = LoggerFactory.getLogger(VipLevelController.class);

    @Autowired
    private VipLevelService vipLevelService;

    @GetMapping
    public ResponseEntity selectList(@RequestParam(required = false) String level,
                                     @RequestParam(required = false) Boolean status,
                                     @RequestParam(required = false, defaultValue = Constant.pageNum) int page,
                                     @RequestParam(required = false, defaultValue = Constant.pageSize) int size) {
        LOGGER.info("{}:{}:{}:{}", level, status, page, size);
        PageHelper.offsetPage(page, size, true).pageSizeZero(true).reasonable(true);
        Map<String, Object> map = new HashMap<>();
        if (level != null && StringUtils.isEmpty(level)) {
            level = null;
        }
        if (status != null && StringUtils.isEmpty(status)) {
            status = null;
        }
        map.put("level", level);
        map.put("status", status);
        List<VipLevelBO> vipLevelBOList = vipLevelService.selectList(map);
        return ResponseEntity.ok(Utils.kv("levelList", (Page) vipLevelBOList, "total", ((Page) vipLevelBOList).getTotal()));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity selectOne(@PathVariable String id) {
        LOGGER.info("{}", id);
        VipLevelBO vipLevelBO = vipLevelService.selectOne(id);
        return ResponseEntity.ok(vipLevelBO);
    }

    @PostMapping
    public ResponseEntity insert(@Valid @RequestBody VipLevelBO vipLevelBO) {
        LOGGER.info("{}", vipLevelBO);
        VipLevelBO vipLevelBOReturn = vipLevelService.insert(vipLevelBO);
        LOGGER.info("{}", vipLevelBOReturn);
        return (vipLevelBOReturn != null) ? ResponseEntity.ok(vipLevelBOReturn) : new ResponseEntity(Utils.bodyStatus(4101), HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity update(@Valid @RequestBody VipLevelUpdateBO vipLevelUpdateBO) {
        LOGGER.info("{}", vipLevelUpdateBO);
        VipLevelBO vipLevelBOReturn = vipLevelService.update(vipLevelUpdateBO);
        LOGGER.info("{}", vipLevelBOReturn);
        return (vipLevelBOReturn != null) ? ResponseEntity.ok(vipLevelBOReturn) : new ResponseEntity(Utils.bodyStatus(4102), HttpStatus.BAD_REQUEST);
    }
}


