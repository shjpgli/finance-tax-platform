package com.abc12366.uc.web;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.bo.PointComputeBO;
import com.abc12366.uc.model.bo.PointsBO;
import com.abc12366.uc.service.PointsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Admin: liuguiyao<435720953@qq.com>
 * Date: 2017-07-20
 * Time: 21:42
 */
@RestController
@RequestMapping(path = "/points", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class PointsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PointsController.class);

    @Autowired
    private PointsService pointsService;

    //根据用户ID查询用户
    @GetMapping(path = "/{userId}")
    public ResponseEntity selectOne(@PathVariable String userId) {
        LOGGER.info("{}", userId);
        PointsBO pointsBO = pointsService.selectOne(userId);
        LOGGER.info("{}", pointsBO);
        return ResponseEntity.ok(Utils.kv("data", pointsBO));
    }

    @PostMapping(path = "/compute")
    public ResponseEntity compute(@Valid @RequestBody PointComputeBO pointComputeBO){
        pointsService.compute(pointComputeBO);
        return ResponseEntity.ok(Utils.kv());
    }
}
