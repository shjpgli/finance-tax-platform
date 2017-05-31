package com.abc12366.gateway.component;

import com.abc12366.gateway.mapper.util.DataUtils;
import com.abc12366.gateway.model.ApiLog;
import com.abc12366.gateway.model.IpSetting;
import com.abc12366.gateway.service.ApiLogService;
import com.abc12366.gateway.service.IpSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * IP拦截器
 *
 * @since 1.0.0
 */
public class IpInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpInterceptor.class);

    @Autowired
    private ApiLogService apiLogService;

    @Autowired
    private IpSettingService ipSettingService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 查询IP设定
        IpSetting ipSetting = ipSettingService.selectOne();

        //根据IP设定查询日志表
        ApiLog apiLog = new ApiLog();
        apiLog.setYyyyMMdd(DataUtils.getDataString());
        long time = System.currentTimeMillis();
        apiLog.setStartTime(time-3600*1000);
        apiLog.setEndTime(time+(ipSetting.getSetTime()*1000));

        int count = apiLogService.selectApiLogCount(apiLog);
        System.out.println(count);
       /* if (!ipSettingService.isAuthentication(userToken)) {
            BodyStatus bodyStatus = Utils.bodyStatus(4198);
            response.setStatus(401);
            response.getWriter().write(JSON.toJSONString(bodyStatus));
            response.getWriter().flush();
            response.getWriter().close();
            LOGGER.warn("URI:{}, IP:{}, {}", request.getRequestURI(), request.getRemoteAddr(), bodyStatus);
            return false;
        }*/

        return true;
    }
}
