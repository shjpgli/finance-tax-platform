package com.abc12366.uc.service.impl;

import com.abc12366.gateway.component.SpringCtxHolder;
import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.MessageConstant;
import com.abc12366.gateway.util.RemindConstant;
import com.abc12366.gateway.util.RestTemplateUtil;
import com.abc12366.uc.mapper.db2.VipPrivilegeLevelRoMapper;
import com.abc12366.uc.model.Message;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.bo.MessageBO;
import com.abc12366.uc.model.bo.VipPrivilegeLevelBO;
import com.abc12366.uc.service.AutoSendUtil;
import com.abc12366.uc.service.IWxTemplateService;
import com.abc12366.uc.service.MessageSendUtil;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时任务请求，工具实现类
 * @author lizhongwei 2017-12-15
 */
@Service("autoSendUtil")
public class AutoSendUtilImpl implements AutoSendUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutoSendUtilImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void autoRecordCompany(Map<String, Object> map) {
        sendMessage("/record/statis/auto");
    }

    @Override
    public void autoRecordStatis(Map<String, Object> map) {
        sendMessage("/record/statis/company/auto");
    }

    /**
     * 不需要验证token的发送
     */
    public void sendMessage(String uri) {
        LOGGER.info("{}:{}");
        String url = SpringCtxHolder.getProperty("abc12366.sns.url") + uri;

        Map<String, Object> map = new HashMap<>();

        String responseStr;
        try {
            responseStr = sendMsg(url, HttpMethod.GET, map);
        } catch (Exception e) {
            throw new ServiceException(4821);
        }
        LOGGER.info("responseStr = {}",responseStr);
    }

    public String sendMsg(String url, HttpMethod method, Map<String, Object> map) {
        //请求头设置
        HttpHeaders httpHeaders = new HttpHeaders();

        HttpEntity requestEntity = new HttpEntity(map, httpHeaders);
        LOGGER.info("Request: {}, {}", url, requestEntity);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(url, method, requestEntity, String.class);
        } catch (RestClientException e) {
            throw new ServiceException("0000", "调用接口异常，地址：" + url);
        }
        LOGGER.info("Response: {}, {}", url, responseEntity);
        return responseEntity != null ? responseEntity.getBody() : null;
    }
}
