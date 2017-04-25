package com.abc12366.gateway.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.gateway.model.bo.AppBO;
import com.abc12366.gateway.model.bo.AppRespBO;
import com.abc12366.gateway.service.AppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * 应用控制器
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-05 12:59 PM
 * @since 1.0.0
 */
@Controller
@RequestMapping("/app")
public class AppController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private AppService appService;

    @PostMapping("/register")
    public ResponseEntity register(@Valid AppBO appBO) {
        LOGGER.info("{}", appBO);
        AppRespBO app = appService.register(appBO);
        return app != null ? ResponseEntity.ok(app) : new ResponseEntity<>(Utils.bodyStatus(4007), HttpStatus.CONFLICT);
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid AppBO appBO) {
        LOGGER.info("{}", appBO);
        String token = appService.login(appBO);
        return token != null ? ResponseEntity.ok(Utils.kv(Constant.APP_TOKEN_HEAD, token))
                : new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST);
    }
}
