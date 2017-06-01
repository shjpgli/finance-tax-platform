package com.abc12366.message.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Properties;
import com.abc12366.common.util.Utils;
import com.abc12366.common.web.BaseController;
import com.abc12366.message.model.bo.QueryStatusParam;
import com.abc12366.message.model.bo.SendCodeParam;
import com.abc12366.message.model.bo.SendTemplateParam;
import com.abc12366.message.model.bo.VerifyCodeParam;
import com.abc12366.message.util.CheckSumBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-05-27
 * Time: 10:42
 */
@RestController
@RequestMapping(path = "/sms", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class SmsController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsController.class);

    Properties properties = new Properties("application.properties");

    public SmsController(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @PostMapping(path = "/sendcode")
    public ResponseEntity sendCode(@Valid @RequestBody SendCodeParam sendCodeParam) throws IOException {
        LOGGER.info("{}", sendCodeParam);

        //不变参数
        String url = properties.getValue("message.netease.url.sendcode");//"https://api.netease.im/sms/sendcode.action";
        String appKey = properties.getValue("message.netease.appKey");//"2dea65aed55012fd8e4686177392412e";
        String appSecret = properties.getValue("message.netease.appSecret");//"cf03fe4b439f";
        String contentType = properties.getValue("message.netease.contentType");//"application/x-www-form-urlencoded";
        String charset = properties.getValue("message.netease.charset");//"utf-8";
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

        ResponseEntity response = exchange(url, HttpMethod.POST, requestEntity, String.class, new HashMap());
        if (response == null) {
            return (ResponseEntity) ResponseEntity.badRequest();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/verifycode")
    ResponseEntity verifyCode(@Valid @RequestBody VerifyCodeParam verifyCodeParam) throws IOException {
        LOGGER.info("{}", verifyCodeParam);

        //不变参数
        String url = properties.getValue("message.netease.url.verifycode");
        String appKey = properties.getValue("message.netease.appKey");//"2dea65aed55012fd8e4686177392412e";
        String appSecret = properties.getValue("message.netease.appSecret");//"cf03fe4b439f";
        String contentType = properties.getValue("message.netease.contentType");//"application/x-www-form-urlencoded";
        String charset = properties.getValue("message.netease.charset");//"utf-8";
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

        ResponseEntity response = exchange(url, HttpMethod.POST, requestEntity, String.class, new HashMap());
        if (response == null) {
            return (ResponseEntity) ResponseEntity.badRequest();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/sendtemplate")
    ResponseEntity sendTemplate(@Valid @RequestBody SendTemplateParam sendTemplateParam) throws IOException {
        LOGGER.info("{}", sendTemplateParam);

        //不变参数
        String url = properties.getValue("message.netease.url.sendtemplate");
        String appKey = properties.getValue("message.netease.appKey");//"2dea65aed55012fd8e4686177392412e";
        String appSecret = properties.getValue("message.netease.appSecret");//"cf03fe4b439f";
        String contentType = properties.getValue("message.netease.contentType");//"application/x-www-form-urlencoded";
        String charset = properties.getValue("message.netease.charset");//"utf-8";
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
        requestBody.add("mobiles", mobiles.toString());
        if (params != null) {
            requestBody.add("params", params);
        }

        HttpEntity requestEntity = new HttpEntity(requestBody, httpHeaders);

        ResponseEntity response = exchange(url, HttpMethod.POST, requestEntity, String.class, new HashMap());
        if (response == null) {
            return (ResponseEntity) ResponseEntity.badRequest();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/querystatus")
    ResponseEntity queryStatus(@Valid @RequestBody QueryStatusParam queryStatusParam) throws IOException {
        LOGGER.info("{}", queryStatusParam);

        //不变参数
        String url = properties.getValue("message.netease.url.querystatus");
        String appKey = properties.getValue("message.netease.appKey");//"2dea65aed55012fd8e4686177392412e";
        String appSecret = properties.getValue("message.netease.appSecret");//"cf03fe4b439f";
        String contentType = properties.getValue("message.netease.contentType");//"application/x-www-form-urlencoded";
        String charset = properties.getValue("message.netease.charset");//"utf-8";
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
        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("sendid", sendid.toString());

        HttpEntity requestEntity = new HttpEntity(requestBody, httpHeaders);

        ResponseEntity response = exchange(url, HttpMethod.POST, requestEntity, String.class, new HashMap());
        if (response == null) {
            return (ResponseEntity) ResponseEntity.badRequest();
        }
        return ResponseEntity.ok(response);
    }
}
