package com.abc12366.gateway.service;

import com.abc12366.common.util.Utils;
import com.abc12366.gateway.mapper.db1.AppSettingMapper;
import com.abc12366.gateway.mapper.db2.AppSettingRoMapper;
import com.abc12366.gateway.model.AppSetting;
import com.abc12366.gateway.model.bo.AppSettingBO;
import org.springframework.beans.BeanUtils;
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
    public List<AppSettingBO> selectList(String appId) {
        AppSettingBO bo = new AppSettingBO();
        bo.setAppId(appId);
        return appSettingRoMapper.selectList(bo);
    }

    @Override
    public AppSetting update(AppSettingBO bo) {

        AppSetting updateBO = new AppSetting();
        updateBO.setId(bo.getId());

        updateBO = appSettingRoMapper.selectOne(updateBO);
        if (updateBO != null) {
            updateBO.setLastUpdate(new Date());
            updateBO.setStatus(bo.getStatus());
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
        bo.setId(Utils.uuid());
        Date now = new Date();
        bo.setCreateTime(now);
        bo.setLastUpdate(now);
        AppSetting appSetting = new AppSetting();
        BeanUtils.copyProperties(bo,appSetting);
        appSettingMapper.insert(appSetting);
        return appSetting;
    }

    @Override
    public void delete(String appId, String id) {
        appSettingMapper.delete(id);
    }

    @Override
    public AppSetting selectOne(String appId, String id) {
        AppSetting appSetting = new AppSetting();
        appSetting.setAppId(appId);
        appSetting.setId(id);

        return appSettingRoMapper.selectOne(appSetting);
    }
}
