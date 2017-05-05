package com.abc12366.gateway.service;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.DateUtils;
import com.abc12366.common.util.Utils;
import com.abc12366.gateway.mapper.db1.AppMapper;
import com.abc12366.gateway.mapper.db2.AppRoMapper;
import com.abc12366.gateway.mapper.db2.AppSettingRoMapper;
import com.abc12366.gateway.model.App;
import com.abc12366.gateway.model.bo.AppBO;
import com.abc12366.gateway.model.bo.AppRespBO;
import com.abc12366.gateway.model.bo.AppSettingApiBO;
import com.abc12366.gateway.model.bo.TokenBO;
import com.alibaba.fastjson.JSON;
import com.mysql.jdbc.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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
    public AppRespBO register(AppBO appBO) {
        App app = appRoMapper.selectByName(appBO.getName());
        if (app == null) {
            String password = null;
            try {
                password = Utils.md5(appBO.getPassword());
            } catch (Exception e) {
                LOGGER.error(e.getMessage() + e);
            }
            Date now = new Date();
            App newApp = new App.Builder()
                    .id(Utils.uuid())
                    .name(appBO.getName())
                    .password(password)
                    .remark(appBO.getRemark())
                    .status(true)
                    .startTime(now)
                    .endTime(DateUtils.addYears(now, Constant.APP_VALID_YEARS))
                    .createTime(now)
                    .lastUpdate(now)
                    .build();

            appMapper.insert(newApp);
            AppRespBO appRespDTO = new AppRespBO();
            BeanUtils.copyProperties(newApp, appRespDTO);
            return appRespDTO;
        }

        return null;
    }

    @Override
    public String login(AppBO appBO) throws Exception {
        App app = appRoMapper.selectByName(appBO.getName());
        String password = null;
        try {
            password = Utils.md5(appBO.getPassword());
        } catch (Exception e) {
            LOGGER.error(e.getMessage() + e);
        }
        if (app != null && app.getPassword().equals(password)) {
            Date now = new Date();
            String token = Utils.token();
            app.setAccessToken(token);
            app.setLastResetTokenTime(now);
            app.setLastUpdate(now);
            appMapper.update(app);
            return token;
        }
        return null;
    }

    @Override
    public boolean isAuthentication(String accessToken) {
        App app = new App.Builder()
                .accessToken(accessToken)
                .build();
        return appRoMapper.selectOne(app) != null;
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
        // 解码AppId
        String accessToken = request.getHeader(Constant.APP_TOKEN_HEAD);
        TokenBO tokenBO = JSON.parseObject(Utils.decode(accessToken), TokenBO.class);
        String appId = tokenBO.getId();
        String method = request.getMethod();
        String version = request.getHeader(Constant.VERSION_HEAD);

        AppSettingApiBO appSettingApiBO = new AppSettingApiBO.Builder()
                .appId(appId)
                .uri(bestMatchingPattern)
                .method(method)
                .version(version)
                .status(true)
                .build();
        // 查询接口
        appSettingApiBO = appSettingRoMapper.isAuthentization(appSettingApiBO);
        return appSettingApiBO != null && appSettingApiBO.isAuthentication()
                && !StringUtils.isNullOrEmpty(request.getHeader(Constant.USER_TOKEN_HEAD));
    }
}
