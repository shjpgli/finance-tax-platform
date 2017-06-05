package com.abc12366.message.service;

import com.abc12366.common.util.Properties;
import com.abc12366.common.util.Utils;
import com.abc12366.message.model.bo.*;
import com.abc12366.message.util.CheckSumBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-06-01
 * Time: 16:46
 */
@Service
public class SmsServiceImpl implements SmsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    private static Properties properties = new Properties("application.properties");
    private static String appKey;
    private static String appSecret;
    private static String contentType;
    private static String charset;

    static {
        try {
            appKey = properties.getValue("message.netease.appKey");//"2dea65aed55012fd8e4686177392412e";
            appSecret = properties.getValue("message.netease.appSecret");//"cf03fe4b439f";
            contentType = properties.getValue("message.netease.contentType");//"application/x-www-form-urlencoded";
            charset = properties.getValue("message.netease.charset");//"utf-8";
        } catch (IOException e) {
            LOGGER.warn("网易短信服务接口参数加载异常！");
            e.printStackTrace();
        }
    }

    @Override
    public ResponseEntity sendCode(SendCodeParam sendCodeParam) throws IOException {
//不变参数
        String url = properties.getValue("message.netease.url.sendcode");//"https://api.netease.im/sms/sendcode.action";
        /*String appKey = properties.getValue("message.netease.appKey");//"2dea65aed55012fd8e4686177392412e";
        String appSecret = properties.getValue("message.netease.appSecret");//"cf03fe4b439f";
        String contentType = properties.getValue("message.netease.contentType");//"application/x-www-form-urlencoded";
        String charset = properties.getValue("message.netease.charset");//"utf-8";*/
        //可变参数
        String nonce = Utils.uuid();
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);
        //请求头设置
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("appKey", appKey);
        httpHeaders.add("appSecret", appSecret);
        httpHeaders.add("Content-Type", contentType);
        httpHeaders.add("charset", charset);
        httpHeaders.add("Nonce", nonce);
        httpHeaders.add("CurTime", curTime);
        httpHeaders.add("CheckSum", checkSum);

        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("mobile", sendCodeParam.getMobile());
        if (sendCodeParam.getDeviceId() != null) {
            requestBody.add("deviceId", sendCodeParam.getDeviceId());
        }
        if (sendCodeParam.getTemplateid() != null) {
            requestBody.add("templateid", sendCodeParam.getTemplateid().toString());
        }
        if (sendCodeParam.getCodeLen() != null) {
            requestBody.add("codeLen", sendCodeParam.getCodeLen().toString());
        }

        HttpEntity requestEntity = new HttpEntity(requestBody, httpHeaders);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class, new HashMap());
    }

    @Override
    public ResponseEntity verify(VerifyCodeParam verifyCodeParam) throws IOException {
        //不变参数
        String url = properties.getValue("message.netease.url.verifycode");
        //可变参数
        String nonce = Utils.uuid();
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);
        //请求头设置
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("appKey", appKey);
        httpHeaders.add("appSecret", appSecret);
        httpHeaders.add("Content-Type", contentType);
        httpHeaders.add("charset", charset);
        httpHeaders.add("Nonce", nonce);
        httpHeaders.add("CurTime", curTime);
        httpHeaders.add("CheckSum", checkSum);

        String mobile = verifyCodeParam.getMobile();
        String code = verifyCodeParam.getCode();
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("mobile", mobile);
        requestBody.add("code", code);

        HttpEntity requestEntity = new HttpEntity(requestBody, httpHeaders);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
    }

    @Override
    public ResponseEntity sendTemplate(SendTemplateParam sendTemplateParam) throws IOException {
        //不变参数
        String url = properties.getValue("message.netease.url.sendtemplate");
        //可变参数
        String nonce = Utils.uuid();
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);
        //请求头设置
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("appKey", appKey);
        httpHeaders.add("appSecret", appSecret);
        httpHeaders.add("Content-Type", contentType);
        httpHeaders.add("charset", charset);
        httpHeaders.add("Nonce", nonce);
        httpHeaders.add("CurTime", curTime);
        httpHeaders.add("CheckSum", checkSum);

        Integer templateid = sendTemplateParam.getTemplateid();
        String mobiles = sendTemplateParam.getMobiles();
        String params = sendTemplateParam.getParams();
        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("templateid", templateid.toString());
        requestBody.add("mobiles", mobiles);
        if (params != null) {
            requestBody.add("params", params);
        }

        HttpEntity requestEntity = new HttpEntity(requestBody, httpHeaders);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
    }

    @Override
    public ResponseEntity queryStatus(QueryStatusParam queryStatusParam) throws IOException {
        //不变参数
        String url = properties.getValue("message.netease.url.querystatus");
        //可变参数
        String nonce = Utils.uuid();
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);
        //请求头设置
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("appKey", appKey);
        httpHeaders.add("appSecret", appSecret);
        httpHeaders.add("Content-Type", contentType);
        httpHeaders.add("charset", charset);
        httpHeaders.add("Nonce", nonce);
        httpHeaders.add("CurTime", curTime);
        httpHeaders.add("CheckSum", checkSum);

        Long sendid = queryStatusParam.getSendid();
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("sendid", sendid.toString());

        HttpEntity requestEntity = new HttpEntity(requestBody, httpHeaders);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
    }
}
