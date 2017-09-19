package com.abc12366.gateway.service;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.mapper.db1.AppMapper;
import com.abc12366.gateway.mapper.db2.ApiLogRoMapper;
import com.abc12366.gateway.mapper.db2.AppRoMapper;
import com.abc12366.gateway.mapper.db2.AppSettingRoMapper;
import com.abc12366.gateway.model.ApiLog;
import com.abc12366.gateway.model.App;
import com.abc12366.gateway.model.bo.AppBO;
import com.abc12366.gateway.model.bo.AppSettingBO;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.DateUtils;
import com.abc12366.gateway.util.TimeUtil;
import com.abc12366.gateway.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
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
    private ApiLogRoMapper apiLogRoMapper;

    @Autowired
    private AppSettingRoMapper appSettingRoMapper;

    /*
     * 将时间戳转换为时间
     */
    public static Date getLongToDate(long lt) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        return new Date(lt);
    }

    @Transactional("db1TxManager")
    @Override
    public AppBO register(AppBO bo) throws Exception {
        LOGGER.info("{}", bo);
        bo.setId(Utils.uuid());
        Date date = new Date();
        bo.setPassword(Utils.md5(bo.getPassword()));
        bo.setStartTime(date);
        bo.setCreateTime(date);
        bo.setLastUpdate(date);
        bo.setEndTime(DateUtils.addYears(date, Constant.APP_VALID_YEARS));
        App app = new App();
        BeanUtils.copyProperties(bo, app);
        //根据名称查询app
        App temp = appRoMapper.selectByName(app.getName());
        if (temp != null) {
            LOGGER.warn("APP应用名称已存在{}", app);
            throw new ServiceException(4095);
        }
        int insert = appMapper.insert(app);
        if (insert != 1) {
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
        app = appRoMapper.selectByName(bo.getName());
        if (app == null) {
            LOGGER.warn("APP用户名不存在：{}", app);
            throw new ServiceException(4094);
        }
        String password = Utils.md5(bo.getPassword());
        if (app != null && app.getPassword().equals(password)) {
            //判断app登录是否已过期
            long lastTime = TimeUtil.getDateStringToLong(app.getLastResetTokenTime());
            long currentTime = System.currentTimeMillis();
            String token = app.getAccessToken();
            Date now = new Date();
            if (currentTime > lastTime) {
                LOGGER.warn("APP登录已过期，返回新的token：{}", app);
                token = Utils.token();
                app.setAccessToken(token);
                //设置有效时间
            }
            app.setLastResetTokenTime(getLongToDate(System.currentTimeMillis() + Constant
                    .ADMIN_USER_TOKEN_VALID_SECONDS));
            app.setLastUpdate(now);
            int upd = appMapper.update(app);
            if (upd != 1) {
                LOGGER.warn("APP修改异常：{}", app);
                throw new ServiceException(4102);
            }
            LOGGER.info("{}", token);
            return token;
        } else {
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
        app = appRoMapper.selectOne(app);
        //判断app是否正常
        if (app == null) {
            LOGGER.warn("APP不存在或APP未启用：{}", app);
            throw new ServiceException(4035);
        }
        return true;
    }

    @Override
    public boolean isAuthentization(HttpServletRequest request) {
        // 1.获取最佳匹配地址
        // path: /uc
        String bestMatchingPattern = (String) request.getAttribute("org.springframework.web.servlet.HandlerMapping" +
                ".bestMatchingPattern");

        // 2.查询接口表中是否存在对应的接口
        String accessToken = request.getHeader(Constant.APP_TOKEN_HEAD);
        App app = new App();
        app.setAccessToken(accessToken);
        app.setStatus(true);
        app = appRoMapper.selectOne(app);
        //判断app是否正常
        if (app == null) {
            LOGGER.warn("APP不存在或APP未启用：{}", app);
            throw new ServiceException(4035);
        }
        //判断app登录是否已过期
        long lastTime = TimeUtil.getDateStringToLong(app.getLastResetTokenTime());
        long currentTime = System.currentTimeMillis();
        if (currentTime > lastTime) {
             LOGGER.warn("APP登录已过期，请重新登录：{}", app);
            throw new ServiceException(4025);
        }
        //判断app是否已过期
        long endTime = TimeUtil.getDateStringToLong(app.getEndTime());
        if (currentTime > endTime) {
            LOGGER.warn("APP已过期，请重新续费：{}", app);
            throw new ServiceException(4026);
        }
        String appId = app.getId();

        //TODO api校验暂时屏蔽
        // 设置appId，用于在业务中快速获取有效AppId，在AppInterceptor.postHandle中删除。

        request.setAttribute(Constant.APP_ID, appId);
        String method = request.getMethod();
        String version = request.getHeader(Constant.VERSION_HEAD);
        AppSettingBO appSettingBO = new AppSettingBO();
        appSettingBO.setAppId(appId);
        appSettingBO.setUri(bestMatchingPattern);

        //TODO 目前只对微信API拦截,微信appId=c1109d75-02b1-4c9b-83da-677f86182003
        if("c1109d75-02b1-4c9b-83da-677f86182003".equals(appId)){
            AppSettingBO bo = appSettingRoMapper.selectByAppId(appSettingBO);
            if(bo == null){
                LOGGER.warn("API不存在或未授权：{}", app);
                throw new ServiceException(4027);
            }
            if(bo.getIsValidate() == true){
                if(method != null && !method.equals(bo.getMethod())){
                    LOGGER.warn("API方法不正确：{}", app);
                    throw new ServiceException(4028);
                }
                if(version != null && !version.equals(bo.getVersion())){
                    LOGGER.warn("API版本不正确：{}", app);
                    throw new ServiceException(4029);
                }
                //查询每分钟访问的次数
                ApiLog apiLog = new ApiLog();
                currentTime = System.currentTimeMillis();
                apiLog.setUri(bestMatchingPattern);
                apiLog.setStartTime(currentTime - (60 * 1000));
                apiLog.setEndTime(currentTime);
                apiLog.setYyyyMMdd(DateUtils.getDataString());
                apiLog.setAppId(appId);
                apiLog.setMethod(method);
                int minuteCount = apiLogRoMapper.selectApiLogCount(apiLog);
                if(bo.getTimesPerMinute() != 0 && minuteCount > bo.getTimesPerMinute()){
                    LOGGER.warn("API接口每分钟访问次数已超出，请稍后访问：{}", app);
                    throw new ServiceException(4031);
                }
                //查询每小时访问的次数
                apiLog.setStartTime(currentTime - (60 * 1000 * 60));
                int hourCount = apiLogRoMapper.selectApiLogCount(apiLog);
                if(bo.getTimesPerHour() != 0 && hourCount > bo.getTimesPerHour()){
                    LOGGER.warn("API接口每小时访问次数已超出，请稍后访问：{}", app);
                    throw new ServiceException(4032);
                }
                //查询每天访问的次数
                apiLog.setStartTime(currentTime - (60 * 1000 * 60 * 24));
                int dayCount = apiLogRoMapper.selectApiLogCount(apiLog);
                if(bo.getTimesPerDay() != 0 && dayCount > bo.getTimesPerDay()){
                    LOGGER.warn("API接口每天访问次数已超出，请稍后访问：{}", app);
                    throw new ServiceException(4033);
                }
            }
        }
        return true;
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
        try {
            appBO.setPassword(Utils.md5(appBO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        App app = new App();
        BeanUtils.copyProperties(appBO, app);
        app.setLastUpdate(new Date());
        int update = appMapper.update(app);
        if (update != 1) {
            LOGGER.warn("修改异常：{}", app);
            throw new ServiceException(4102);
        }
        return appBO;
    }

    @Override
    public AppBO selectById(String id) {
        LOGGER.info("{}", id);
        App app = appRoMapper.selectById(id);
        if (app == null) {
            LOGGER.warn("APP用户名不存在：{}", app);
            throw new ServiceException(4094);
        }
        AppBO appBO = new AppBO();
        BeanUtils.copyProperties(app, appBO);
        return appBO;
    }

    @Override
    public List<AppBO> selectBySettingIdList(String id) {
        return appRoMapper.selectBySettingIdList(id);
    }
}
