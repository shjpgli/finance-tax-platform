package com.abc12366.gateway.mapper.db2;

import com.abc12366.gateway.model.AppSetting;
import com.abc12366.gateway.model.bo.AppSettingApiBO;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-27 2:56 PM
 * @since 1.0.0
 */
public interface AppSettingRoMapper {
    AppSettingApiBO isAuthentization(AppSettingApiBO appSettingApiBO);

    List<AppSettingApiBO> selectList(String appId);

    AppSetting selectOne(AppSetting appSetting);
}
