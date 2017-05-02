package com.abc12366.gateway.web;

import com.abc12366.common.util.Constant;
import com.abc12366.gateway.model.App;
import com.abc12366.gateway.model.AppSetting;
import com.abc12366.gateway.model.bo.AppSettingApiBO;
import com.abc12366.gateway.model.bo.AppSettingBO;
import com.abc12366.gateway.service.AppSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-27 3:29 PM
 * @since 1.0.0
 */
@RestController()
@RequestMapping(name = "/app/setting", headers = Constant.VERSION_HEAD + "=1")
public class AppSettingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private AppSettingService appSettingService;

    @GetMapping("/{appId}")
    public ResponseEntity selectList(@PathVariable("appId") String appId) {
        LOGGER.info(appId);
        List<AppSettingApiBO> dataList = appSettingService.selectList(appId);
        LOGGER.info("{}", dataList);
        return ResponseEntity.ok(dataList);
    }

    @GetMapping("/{appId}/{id}")
    public ResponseEntity selectOne(@PathVariable("appId") String appId,
                                    @PathVariable("id") String id) {
        LOGGER.info("{}/{}", appId, id);
        AppSetting appSetting = appSettingService.selectOne(appId, id);
        LOGGER.info("{}", appSetting);
        return ResponseEntity.ok(appSetting);
    }

    @PostMapping("/{appId}")
    public ResponseEntity insert(@PathVariable("appId") String appId,
                                 @Valid @RequestBody AppSettingBO appSettingBO) {
        LOGGER.info("{}: {}", appId, appSettingBO);
        if (StringUtils.isEmpty(appSettingBO.getAppId())) {
            appSettingBO.setAppId(appId);
        }
        AppSetting appSetting = appSettingService.insert(appSettingBO);
        LOGGER.info("{}", appSetting);
        return ResponseEntity.ok(appSetting);
    }

    @PutMapping("/{appId}/{id}")
    public ResponseEntity update(@PathVariable("appId") String appId,
                                 @PathVariable("id") String id,
                                 @Valid @RequestBody AppSettingBO appSettingBO) {
        LOGGER.info("{}/{}", appId, id);
        if (StringUtils.isEmpty(appSettingBO.getAppId())) {
            appSettingBO.setAppId(appId);
        }
        if (StringUtils.isEmpty(appSettingBO.getId())) {
            appSettingBO.setId(id);
        }
        AppSetting appSetting = appSettingService.update(appSettingBO);
        LOGGER.info("{}", appSetting);
        return ResponseEntity.ok(appSetting);
    }

    @DeleteMapping("/{appId}/{id}")
    public ResponseEntity delete(@PathVariable("appId") String appId,
                                 @PathVariable("id") String id) {
        LOGGER.info("{}/{}", appId, id);
        appSettingService.delete(appId, id);
        return ResponseEntity.ok(null);
    }
}
