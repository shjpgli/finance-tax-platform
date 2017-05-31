package com.abc12366.gateway.service;

import com.abc12366.gateway.model.IpSetting;

/**
 * Created by MY on 2017-05-26.
 */
public interface IpSettingService {
    IpSetting selectOne();

    IpSetting update(IpSetting ipSetting);
}
