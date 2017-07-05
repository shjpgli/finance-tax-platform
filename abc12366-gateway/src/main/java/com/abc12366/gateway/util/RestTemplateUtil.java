package com.abc12366.gateway.util;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-03
 * Time: 14:49
 */
@Component
public class RestTemplateUtil {
    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity send(String url, HttpMethod method, HttpServletRequest request){
        //请求头设置
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(Constant.APP_TOKEN_HEAD, request.getHeader(Constant.APP_TOKEN_HEAD));
        httpHeaders.add(Constant.ADMIN_TOKEN_HEAD, request.getHeader(Constant.ADMIN_TOKEN_HEAD));
        httpHeaders.add(Constant.USER_TOKEN_HEAD, request.getHeader(Constant.USER_TOKEN_HEAD));
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add(Constant.VERSION_HEAD, Constant.VERSION_1);

        //MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        HttpEntity requestEntity = new HttpEntity(null, httpHeaders);

        return restTemplate.exchange(url, method, requestEntity, String.class);
    }
}
