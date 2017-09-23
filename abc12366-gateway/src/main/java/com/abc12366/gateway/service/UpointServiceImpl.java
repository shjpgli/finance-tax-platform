package com.abc12366.gateway.service;

import com.abc12366.gateway.component.SpringCtxHolder;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.RestTemplateUtil;
import com.abc12366.gateway.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-08-30
 * Time: 20:16
 */
@Service
public class UpointServiceImpl implements UpointService {

    @Autowired
    private RestTemplateUtil restTemplate;

    @Override
    public void compute(HttpServletRequest request) throws IOException {
        String userId = Utils.getUserId(request);
        String clientType = request.getHeader(Constant.CLIENT_TYPE);
        String uri = (String) request.getAttribute("org.springframework.web.servlet.HandlerMapping" +
                ".bestMatchingPattern");

        String url = SpringCtxHolder.getProperty("abc12366.uc.url") + "/points/compute";

        Map<String, String> map = new HashMap<>();
        map.put("userId", userId == null ? userId : userId.trim());
        map.put("clientType", clientType == null ? clientType : clientType.trim());
        //map.put("clientType", "PCWEB");
        map.put("uri", uri == null ? uri : uri.trim());

        restTemplate.exchange(url, HttpMethod.POST, map, request);
    }
}
