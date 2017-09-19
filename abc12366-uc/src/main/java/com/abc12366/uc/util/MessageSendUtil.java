package com.abc12366.uc.util;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Constant;
import com.abc12366.uc.model.Message;
import com.abc12366.uc.model.bo.MessageBO;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/18.
 */
public interface MessageSendUtil {


    /**
     * 需要验证token的发送
     */
    MessageBO sendMessage(Message message, HttpServletRequest request);

    String send(String url, HttpMethod method, Map<String, Object> map, HttpServletRequest request);
    /**
     * 不需要验证token的发送
     */
    MessageBO sendMessage(Message message);

    String sendMsg(String url, HttpMethod method, Map<String, Object> map);

}
