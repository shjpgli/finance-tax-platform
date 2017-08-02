package com.abc12366.gateway.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.gateway.model.AppSetting;
import com.abc12366.gateway.model.bo.AppSettingBO;
import com.abc12366.gateway.service.AppSettingService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
@RestController
@RequestMapping(headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class AppSettingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private AppSettingService appSettingService;

    /**
     * APP设置列表查询
     * @param appId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(path = "/appsetting")
    public ResponseEntity selectList(@RequestParam(value = "appId", required = true) String appId,
                                     @RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize) {
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        LOGGER.info(appId);
        List<AppSettingBO> dataList = appSettingService.selectList(appId);
        PageInfo<AppSettingBO> pageInfo = new PageInfo<>(dataList);
        LOGGER.info("{}", dataList);
        return ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(), "total", pageInfo.getTotal()));
    }

    /**
     * APP设置详细查询
     * @param appId
     * @param id
     * @return
     */
    @GetMapping(path = "/appsetting/{appId}/{id}")
    public ResponseEntity selectOne(@PathVariable("appId") String appId,
                                    @PathVariable("id") String id) {
        LOGGER.info("{}/{}", appId, id);
        AppSetting appSetting = appSettingService.selectOne(appId, id);
        LOGGER.info("{}", appSetting);
        return ResponseEntity.ok(Utils.kv("data", appSetting));
    }

    /**
     * APP设置批量授权
     * @param appId
     * @param appSettingBOList
     * @return
     */
    @PostMapping(path = "/appsetting/list/{appId}")
    public ResponseEntity insertList(@PathVariable("appId") String appId,
                                     @Valid @RequestBody List<AppSettingBO> appSettingBOList) {
        LOGGER.info("{}: {}", appId, appSettingBOList);
        List<AppSetting> appSettingList = appSettingService.insertList(appId,appSettingBOList);
        LOGGER.info("{}", appSettingList);
        return ResponseEntity.ok(Utils.kv("data", appSettingList));
    }

    /**
     * APP设置单个查询
     * @param appId
     * @param appSettingBO
     * @return
     */
    @PostMapping(path = "/appsetting/{appId}")
    public ResponseEntity insert(@PathVariable("appId") String appId,
                                 @Valid @RequestBody AppSettingBO appSettingBO) {
        LOGGER.info("{}: {}", appId, appSettingBO);
        if (StringUtils.isEmpty(appSettingBO.getAppId())) {
            appSettingBO.setAppId(appId);
        }
        AppSetting appSetting = appSettingService.insert(appSettingBO);
        LOGGER.info("{}", appSetting);
        return ResponseEntity.ok(Utils.kv("data", appSetting));
    }

    /**
     * APP设置修改
     * @param appId
     * @param id
     * @param appSettingBO
     * @return
     */
    @PutMapping(path = "/appsetting/{appId}/{id}")
    public ResponseEntity update(@PathVariable("appId") String appId,
                                 @PathVariable("id") String id,
                                 @Valid @RequestBody AppSettingBO appSettingBO) {
        LOGGER.info("{}/{}", appId, id);
        appSettingBO.setAppId(appId);
        appSettingBO.setId(id);
        AppSetting appSetting = appSettingService.update(appSettingBO);
        LOGGER.info("{}", appSetting);
        return ResponseEntity.ok(Utils.kv("data", appSetting));
    }

    /**
     * APP设置删除
     * @param appId
     * @param id
     * @return
     */
    @DeleteMapping(path = "/appsetting/{appId}/{id}")
    public ResponseEntity delete(@PathVariable("appId") String appId,
                                 @PathVariable("id") String id) {
        LOGGER.info("{}/{}", appId, id);
        appSettingService.delete(appId, id);
        return ResponseEntity.ok(Utils.kv());
    }
}
