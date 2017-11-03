package com.abc12366.message.web.dzsb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.abc12366.gateway.util.Constant;
import com.abc12366.message.config.ApplicationConfig;
import com.abc12366.message.model.bo.DzsjResult;
import com.abc12366.message.model.bo.Result;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Controller()
@RequestMapping(path = "/dzsb", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class DzsbController {
	
	// App缓存信息
    protected static Map<String, String> appCache = new ConcurrentHashMap<>();
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(DzsbController.class);
	
	@Autowired
    protected ApplicationConfig cfg;
	
	@Autowired
    private RestTemplate restTemplate;
	
	/**
     * 提前3分钟判断App的token是否过期
     *
     * @return 过期:true, 否则:false
     */
    private boolean isExpires(Long expiresTime) {
        Date now = new Date();
        LOGGER.info("过期时间：" + dateToStr(new Date(expiresTime), "yyyy-MM-dd HH:mm:ss") +
                ", 系统时间：" + dateToStr(now, "yyyy-MM-dd HH:mm:ss"));
        return now.getTime() > expiresTime - 3 * 60 * 1000;
    }
    
    /**
     * 日期转字符串
     *
     * @param date 日期
     * @param format 格式
     * @return string
     */
    public static String dateToStr(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
	
	/**
     * 字符串转换成日期
     *
     * @param str 日期字符串
     * @param format 格式
     * @return date
     */
    public static Date strToDate(String str, String format) {

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
	
	
	/**
     * 对请求参数为rest型变量的exchange方法的封装，可以参考对应的方法RestTemplate.exchange
     *
     * @param url           相对url地址
     * @param method        HttpMethod方法类型
     * @param requestEntity 包含请求头(HttpHeaders)或(和)请求体(Object)的实体
     * @param uriVariables  uri中的参数占位符
     * @return String
     */
    public String exchange(String url,
                           HttpMethod method,
                           HttpEntity<?> requestEntity,
                           Object... uriVariables) {
        LOGGER.info("Request: {}, {}", url, requestEntity);

        ResponseEntity<String> responseEntity = null;
        Result result = null;
        try {
            responseEntity = restTemplate.exchange(url, method, requestEntity, String.class, uriVariables);
        } catch (RestClientException e) {
            LOGGER.error("RestClient调用服务出现异常: " + e.getMessage(), e);
            result = new Result.Builder().code("5003").message(e.getMessage()).build();
        }
        LOGGER.info("Response: {}, {}", url, responseEntity);

        return responseEntity != null ? responseEntity.getBody() : result != null ? JSON.toJSONString(result) : null;
    }
	
	 /**
     * 电子税局登录
     */
    private DzsjResult getDzsjLogin() {
        Map<String, String> map = new HashMap<>();
        map.put("appId", cfg.getAppId());
        map.put("secret", cfg.getSecret());

        String url = cfg.getDzsjUrl() + "/app/login";
        String str = exchange(url, HttpMethod.POST, new HttpEntity<Object>(map));
        return JSON.parseObject(str, DzsjResult.class);
    }

    /**
     * 获取电子税局请求头
     */
    protected HttpHeaders getHttpHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("platform", "CSZJ");
        headers.add("accessToken", getAccessToken());
        return headers;
    }

    /**
     * 获取电子税局token
     */
    protected String getAccessToken() {
        if (appCache.containsKey("accessToken") &&
                !StringUtils.isEmpty(appCache.get("accessToken")) &&
                appCache.containsKey("expiresIn") &&
                !StringUtils.isEmpty(appCache.get("expiresIn")) &&
                !isExpires(strToDate(appCache.get("expiresIn"), "yyyy-MM-dd HH:mm:ss").getTime())) {

            return appCache.get("accessToken");
        } else {
            DzsjResult result = getDzsjLogin();
            if (result == null) {
                return null;
            }
            appCache.put("accessToken", result.getAccessToken());
            appCache.put("expiresIn", result.getExpiresTime());
            return result.getAccessToken();
        }
    }
    
    /**
     * 获取电子税局公钥
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getpublickey")
	public @ResponseBody JSONObject getPublicKey() {
        HttpEntity httpEntity = new HttpEntity(getHttpHeader());
        String pkStr = exchange(cfg.getDzsjUrl() + "/pk", HttpMethod.GET, httpEntity);
        return JSONObject.parseObject(pkStr);
    }
    
    /**
     * 电子税局post请求
     * @param api
     * @param body
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/post")
    public @ResponseBody JSONObject sbPost(@RequestParam("api") String api,
    		@RequestBody Map<String, String> body) {
        HttpEntity httpEntity = new HttpEntity(body, getHttpHeader());
        return JSONObject.parseObject(exchange(cfg.getDzsjUrl() +api, HttpMethod.POST, httpEntity));
    }
    
    /**
     * 调用电子税局的get方法
     *
     * @param api 接口名
     * @return String
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/get")
    @ResponseBody
    public JSONObject taxGet(@RequestParam("api") String api) {
        HttpEntity httpEntity = new HttpEntity(getHttpHeader());
        return JSONObject.parseObject(exchange(cfg.getDzsjUrl() + new String(Base64.getDecoder().decode(api)), HttpMethod.GET, httpEntity));
    }
}
