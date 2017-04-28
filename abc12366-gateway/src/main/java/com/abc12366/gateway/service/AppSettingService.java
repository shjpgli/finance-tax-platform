package com.abc12366.gateway.service;

import com.abc12366.gateway.model.AppSetting;
import com.abc12366.gateway.model.bo.AppSettingApiBO;
import com.abc12366.gateway.model.bo.AppSettingBO;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-27 4:04 PM
 * @since 1.0.0
 */
public interface AppSettingService {
    List<AppSettingApiBO> selectList(String appId);

    AppSetting update(AppSettingBO appSettingBO);

    AppSetting insert(AppSettingBO appSettingBO);

    void delete(String appId, String id);

    AppSetting selectOne(String appId, String id);
}
