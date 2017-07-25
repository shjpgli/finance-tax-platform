package com.abc12366.gateway.service;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.DateUtils;
import com.abc12366.common.util.Utils;
import com.abc12366.gateway.mapper.db1.AppMapper;
import com.abc12366.gateway.mapper.db2.AppRoMapper;
import com.abc12366.gateway.mapper.db2.AppSettingRoMapper;
import com.abc12366.gateway.model.App;
import com.abc12366.gateway.model.bo.AppBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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

    @Transactional("db1TxManager")
    @Override
    public AppBO register(AppBO bo) throws Exception {
        LOGGER.info("{}", bo);
        bo.setId(Utils.uuid());
        bo.setStatus(false);
        Date date = new Date();
        bo.setPassword(Utils.md5(bo.getPassword()));
        bo.setStartTime(date);
        bo.setCreateTime(date);
        bo.setLastUpdate(date);
        bo.setEndTime(DateUtils.addYears(date, Constant.APP_VALID_YEARS));
        App app = new App();
        BeanUtils.copyProperties(bo,app);
        //根据名称查询app
        App temp = appRoMapper.selectByName(app.getName());
        if(temp != null){
            LOGGER.warn("APP应用名称已存在", app);
            throw new ServiceException(4095);
        }
        int insert = appMapper.insert(app);
        if(insert != 1){
            LOGGER.warn("插入失败，参数：{}", app);
            throw new ServiceException(4101);
        }
        return bo;
    }

    @Transactional("db1TxManager")
    @Override
    public String login(AppBO bo) throws Exception {
        LOGGER.info("{}", bo);
        App app = new App();
        app.setName(bo.getName());
        app.setStatus(true);
        app = appRoMapper.selectOne(app);
        if(app == null){
            LOGGER.warn("APP用户名不存在：{}", app);
            throw new ServiceException(4094);
        }
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
        }else{
            LOGGER.warn("APP密码错误：{}", app);
            throw new ServiceException(4093);
        }
    }

    @Override
    public boolean isAuthentication(String accessToken) {
        LOGGER.info("{}", accessToken);
        App app = new App();
        app.setAccessToken(accessToken);
        app.setStatus(true);
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
        App app = new App();
        app.setAccessToken(accessToken);
        app.setStatus(true);
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
    public List<AppBO> selectList(AppBO appBO) {
        List<AppBO> apps = appRoMapper.selectList(appBO);
        LOGGER.info("{}", apps);
        return apps;
    }

    @Transactional("gw1TxManager")
    @Override
    public AppBO update(AppBO appBO) {
        LOGGER.info("{}", appBO);
        App app = appRoMapper.selectById(appBO.getId());
        if (app == null) {
            return null;
        }
        BeanUtils.copyProperties(appBO, app);
        app.setLastUpdate(new Date());
        appMapper.update(app);
        AppBO appGeneralBO = new AppBO();
        BeanUtils.copyProperties(app, appGeneralBO);
        return appGeneralBO;
    }

    @Override
    public AppBO selectById(String id) {
        LOGGER.info("{}", id);
        App app = appRoMapper.selectById(id);
        if (app == null) {
            return null;
        }
        AppBO appGeneralBO = new AppBO();
        BeanUtils.copyProperties(app, appGeneralBO);
        return appGeneralBO;
    }

    @Override
    public List<AppBO> selectBySettingIdList(String id) {
        return appRoMapper.selectBySettingIdList(id);
    }
}
