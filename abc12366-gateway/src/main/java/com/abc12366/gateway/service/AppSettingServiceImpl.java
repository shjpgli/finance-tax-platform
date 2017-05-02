package com.abc12366.gateway.service;

import com.abc12366.common.util.Utils;
import com.abc12366.gateway.mapper.db1.AppSettingMapper;
import com.abc12366.gateway.mapper.db2.AppSettingRoMapper;
import com.abc12366.gateway.model.AppSetting;
import com.abc12366.gateway.model.bo.AppSettingApiBO;
import com.abc12366.gateway.model.bo.AppSettingBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-27 4:06 PM
 * @since 1.0.0
 */
@Service
public class AppSettingServiceImpl implements AppSettingService {

    @Autowired
    private AppSettingRoMapper appSettingRoMapper;

    @Autowired
    private AppSettingMapper appSettingMapper;

    @Override
    public List<AppSettingApiBO> selectList(String appId) {
        return appSettingRoMapper.selectList(appId);
    }

    @Override
    public AppSetting update(AppSettingBO bo) {

        AppSetting updateBO = new AppSetting();
        updateBO.setId(bo.getId());

        updateBO = appSettingRoMapper.selectOne(updateBO);
        if (updateBO != null) {
            updateBO.setLastUpdate(new Date());
            updateBO.setStatus(bo.isStatus());
            updateBO.setTimesPerMinute(bo.getTimesPerMinute());
            updateBO.setTimesPerHour(bo.getTimesPerHour());
            updateBO.setTimesPerDay(bo.getTimesPerDay());
            appSettingMapper.update(updateBO);
            return updateBO;
        }
        return null;
    }

    @Override
    public AppSetting insert(AppSettingBO bo) {
        AppSetting appSetting = new AppSetting.Builder()
                .appId(bo.getAppId())
                .apiId(bo.getApiId())
                .build();
        if (appSettingRoMapper.selectOne(appSetting) == null) {
            appSetting.setId(Utils.uuid());
            appSetting.setTimesPerMinute(bo.getTimesPerMinute());
            appSetting.setTimesPerHour(bo.getTimesPerHour());
            appSetting.setTimesPerDay(bo.getTimesPerDay());
            appSetting.setStatus(bo.isStatus());
            Date now = new Date();
            appSetting.setCreateTime(now);
            appSetting.setLastUpdate(now);
            appSettingMapper.insert(appSetting);
            return appSetting;
        }
        return null;
    }

    @Override
    public void delete(String appId, String id) {
        AppSetting appSetting = new AppSetting.Builder()
                .id(id)
                .build();
        if (appSettingRoMapper.selectOne(appSetting) != null) {
            appSettingMapper.delete(id);
        }
    }

    @Override
    public AppSetting selectOne(String appId, String id) {
        AppSetting appSetting = new AppSetting.Builder()
                .id(id)
                .build();

        return appSettingRoMapper.selectOne(appSetting);
    }
}
