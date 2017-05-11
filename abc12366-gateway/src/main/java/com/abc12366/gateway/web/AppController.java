package com.abc12366.gateway.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.gateway.model.bo.AppBO;
import com.abc12366.gateway.model.bo.AppGeneralBO;
import com.abc12366.gateway.model.bo.AppRespBO;
import com.abc12366.gateway.model.bo.AppUpdateBO;
import com.abc12366.gateway.service.AppService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 应用控制器
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-05 12:59 PM
 * @since 1.0.0
 */
@Controller
@RequestMapping(path = "/app", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class AppController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private AppService appService;

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody AppBO appBO) throws Exception {
        LOGGER.info("{}", appBO);
        AppRespBO app = appService.register(appBO);
        ResponseEntity responseEntity = app != null ? ResponseEntity.ok(app)
                : new ResponseEntity<>(Utils.bodyStatus(4007), HttpStatus.CONFLICT);
        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody AppBO appBO) throws Exception {
        LOGGER.info("{}", appBO);
        String token = appService.login(appBO);
        return token != null ? ResponseEntity.ok(
                Utils.kv(Constant.APP_TOKEN_HEAD, token, "expires_in", Constant.APP_TOKEN_VALID_SECONDS))
                : new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize
    ) {
        LOGGER.info("{}:{}", pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<AppGeneralBO> appList = appService.selectList();
        LOGGER.info("{}", appList);
        return (appList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) appList, "total", ((Page) appList).getTotal()));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity selectOne(@PathVariable String id) {
        LOGGER.info("{}", id);
        AppGeneralBO app = appService.selectById(id);
        LOGGER.info("{}", app);
        return (app != null) ? ResponseEntity.ok(app) : new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody AppUpdateBO appUpdateBO) {
        LOGGER.info("{}", appUpdateBO);
        AppGeneralBO app = appService.update(appUpdateBO);
        LOGGER.info("{}", app);
        return (app != null) ? ResponseEntity.ok(app) : new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity enableOrDisable(@PathVariable String id, @RequestParam boolean status) {
        LOGGER.info("{}:{}", id, status);
        AppGeneralBO app = appService.enableOrDisable(id, status);
        LOGGER.info("{}");
        return (app != null) ? ResponseEntity.ok(app) : new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST);
    }
}
