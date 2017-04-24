package com.abc12366.gateway.service;

import com.abc12366.common.util.Utils;
import com.abc12366.gateway.mapper.db1.AppMapper;
import com.abc12366.gateway.mapper.db2.AppRoMapper;
import com.abc12366.gateway.model.App;
import com.abc12366.gateway.model.bo.AppBO;
import com.abc12366.gateway.model.bo.AppRespBO;
import com.abc12366.gateway.model.bo.TokenBO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-05 1:12 PM
 * @since 1.0.0
 */
@Service
public class AppServiceImpl implements AppService {

    @Autowired
    private AppMapper appMapper;

    @Autowired
    private AppRoMapper appRoMapper;

    @Override
    public AppRespBO register(AppBO appBO) {
        App app = appRoMapper.selectByName(appBO.getName());
        if (app == null) {
            App newApp = new App.Builder()
                    .id(Utils.uuid())
                    .name(appBO.getName())
                    .password(appBO.getPassword())
                    .mark(appBO.getMark())
                    .status(true)
                    .createDate(new Date())
                    .modifyDate(new Date())
                    .build();

            int rows = appMapper.insert(newApp);
            if (rows > 0) {
                AppRespBO appRespDTO = new AppRespBO();
                BeanUtils.copyProperties(newApp, appRespDTO);
                return appRespDTO;
            }
        }

        return null;
    }

    @Override
    public TokenBO login(AppBO appBO) {
        App app = appRoMapper.selectByName(appBO.getName());

        if (app != null && app.getPassword().equals(appBO.getPassword())) {
            return new TokenBO(app.getId());
        }
        return null;
    }

    @Override
    public boolean isAuthentication(String accessToken) {
        return appRoMapper.selectOne(accessToken) != null;
    }

    @Override
    public boolean isAuthentization(String accessToken, String requestURI) {
        return true;
    }
}
