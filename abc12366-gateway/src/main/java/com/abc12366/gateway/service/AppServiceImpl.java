package com.abc12366.gateway.service;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.DateUtils;
import com.abc12366.common.util.Utils;
import com.abc12366.gateway.mapper.db1.AppMapper;
import com.abc12366.gateway.mapper.db2.AppRoMapper;
import com.abc12366.gateway.mapper.db2.AppSettingRoMapper;
import com.abc12366.gateway.model.App;
import com.abc12366.gateway.model.bo.*;
import com.alibaba.fastjson.JSON;
import com.mysql.jdbc.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-05 1:12 PM
 * @since 1.0.0
 */
@Service
public class AppServiceImpl implements AppService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppServiceImpl.class);

    @Autowired
    private AppMapper appMapper;

    @Autowired
    private AppRoMapper appRoMapper;

    @Autowired
    private AppSettingRoMapper appSettingRoMapper;

    @Override
    public AppRespBO register(AppBO bo) throws Exception {
        LOGGER.info("{}", bo);
        App app = new App.Builder()
                .name(bo.getName())
                .build();
        app = appRoMapper.selectOne(app);
        if (app == null) {
            String password = Utils.md5(bo.getPassword());
            Date now = new Date();
            App newApp = new App.Builder()
                    .id(Utils.uuid())
                    .name(bo.getName())
                    .password(password)
                    .remark(bo.getRemark())
                    .status(false)
                    .startTime(now)
                    .endTime(DateUtils.addYears(now, Constant.APP_VALID_YEARS))
                    .createTime(now)
                    .lastUpdate(now)
                    .build();

            appMapper.insert(newApp);
            AppRespBO appRespDTO = new AppRespBO();
            BeanUtils.copyProperties(newApp, appRespDTO);
            LOGGER.info("{}", appRespDTO);
            return appRespDTO;
        }

        return null;
    }

    @Override
    public String login(AppBO bo) throws Exception {
        LOGGER.info("{}", bo);
        App app = new App.Builder()
                .name(bo.getName())
                .status(true)
                .build();
        app = appRoMapper.selectOne(app);
        String password = Utils.md5(bo.getPassword());
        if (app != null && app.getPassword().equals(password)) {
            Date now = new Date();
            String token = Utils.token();
            app.setAccessToken(token);
            app.setLastResetTokenTime(now);
            app.setLastUpdate(now);
            appMapper.update(app);
            LOGGER.info("{}", token);
            return token;
        }
        return null;
    }

    @Override
    public boolean isAuthentication(String accessToken) {
        LOGGER.info("{}", accessToken);
        App app = new App.Builder()
                .accessToken(accessToken)
                .status(true)
                .build();
        boolean isAuthen = appRoMapper.selectOne(app) != null;
        LOGGER.info("{}", isAuthen);
        return isAuthen;
    }

    @Override
    public boolean isAuthentization(HttpServletRequest request) {
        // 1.获取最佳匹配地址
        // path: /uc
//        String servletPath = request.getServletPath();
        // path: /test
        String bestMatchingPattern = (String) request.getAttribute("org.springframework.web.servlet.HandlerMapping.bestMatchingPattern");
//        String path = servletPath + bestMatchingPattern;

        // 2.查询接口表中是否存在对应的接口
        String accessToken = request.getHeader(Constant.APP_TOKEN_HEAD);
        App app = new App.Builder()
                .accessToken(accessToken)
                .status(true)
                .build();
        app = appRoMapper.selectOne(app);
        String appId = app.getId();
        // 设置appId，用于在业务中快速获取有效AppId，在AppInterceptor.postHandle中删除。
        request.setAttribute(Constant.APP_ID, appId);
        String method = request.getMethod();
        String version = request.getHeader(Constant.VERSION_HEAD);
        return true;

      /*  AppSettingApiBO appSettingApiBO = new AppSettingApiBO.Builder()
                .appId(appId)
                .uri(bestMatchingPattern)
                .method(method)
                .version(version)
                .status(true)
                .build();
        // 查询接口
        appSettingApiBO = appSettingRoMapper.isAuthentization(appSettingApiBO);
        return appSettingApiBO != null && appSettingApiBO.isAuthentication()
                && !StringUtils.isNullOrEmpty(request.getHeader(Constant.USER_TOKEN_HEAD));*/
    }

    @Override
    public List<AppGeneralBO> selectList(AppGeneralBO appGeneralBO) {
        List<AppGeneralBO> apps = appRoMapper.selectList(appGeneralBO);
        if (apps.size() < 1) {
            return null;
        }
        LOGGER.info("{}", apps);
        return apps;
    }

    @Transactional("gw1TxManager")
    @Override
    public AppGeneralBO update(AppUpdateBO appUpdateBO) {
        LOGGER.info("{}", appUpdateBO);
        App app = appRoMapper.selectById(appUpdateBO.getId());
        if (app == null) {
            return null;
        }
        BeanUtils.copyProperties(appUpdateBO, app);
        app.setLastUpdate(new Date());
        appMapper.update(app);
        AppGeneralBO appGeneralBO = new AppGeneralBO();
        BeanUtils.copyProperties(app, appGeneralBO);
        return appGeneralBO;
    }

    @Override
    public AppGeneralBO selectById(String id) {
        LOGGER.info("{}", id);
        App app = appRoMapper.selectById(id);
        if (app == null) {
            return null;
        }
        AppGeneralBO appGeneralBO = new AppGeneralBO();
        BeanUtils.copyProperties(app, appGeneralBO);
        return appGeneralBO;
    }
}
