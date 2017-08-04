package com.abc12366.gateway.web;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.model.IpSetting;
import com.abc12366.gateway.service.IpSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * IP控制器
 */
@Controller
@RequestMapping(path = "/ipsetting", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class IpSettingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpSettingController.class);

    @Autowired
    private IpSettingService ipSettingService;

    @GetMapping
    public ResponseEntity selectOne() {
        IpSetting ipSetting = ipSettingService.selectOne();
        LOGGER.info("{}", ipSetting);
        return ResponseEntity.ok(ipSetting);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity update(@RequestBody IpSetting ipSetting, @PathVariable String id) {
        LOGGER.info("{}", ipSetting);
        ipSetting.setId(id);
        IpSetting setting = ipSettingService.update(ipSetting);
        LOGGER.info("{}", setting);
        return ResponseEntity.ok(setting);
    }
}
