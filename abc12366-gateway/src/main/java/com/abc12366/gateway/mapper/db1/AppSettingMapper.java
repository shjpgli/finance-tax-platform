package com.abc12366.gateway.mapper.db1;

import com.abc12366.gateway.model.AppSetting;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-27 4:47 PM
 * @since 1.0.0
 */
public interface AppSettingMapper {
    void update(AppSetting appSetting);

    void insert(AppSetting appSetting);

    void delete(String id);
}
