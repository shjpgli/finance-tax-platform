package com.abc12366.message.web.dzsj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.abc12366.gateway.util.Constant;
import com.abc12366.message.config.ApplicationConfig;
import com.abc12366.message.model.bo.WsbsLoginBo;
import com.alibaba.fastjson.JSONObject;
import com.hnds.security.HndsSecurityUtils;

/**
 * 国税地税网上办税
 * @author zhushuai 2017-11-6
 *
 */
@Controller
@RequestMapping(path = "/wsbs", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class WsbsController {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(WsbsController.class);
	
	@Autowired
    private RestTemplate restTemplate;
	
	@Autowired
    protected ApplicationConfig cfg;
    
	/**
	 * 地税单点登录
	 * @param loginBo
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/login")
	public @ResponseBody JSONObject wsbsLogin(@RequestBody WsbsLoginBo loginBo){
		String taxurl=loginBo.toLoginStr(cfg.getHndsUrl());
		LOGGER.info("请求地址:"+taxurl);
		HttpEntity httpEntity = new HttpEntity(new HttpHeaders());
		JSONObject jsonObject=null;
        try {
        	ResponseEntity<String> responseEntity = restTemplate.exchange(taxurl, HttpMethod.GET, httpEntity, String.class, new Object());
            jsonObject=JSONObject.parseObject(responseEntity.getBody());
            if("00".equals(jsonObject.getString("retcode"))){
            	jsonObject.put("sign", HndsSecurityUtils.encodeDES(cfg.getHndsKey(), loginBo.getMainuserid(), loginBo.getChangePwd()));
            }
        } catch (Exception e) {
            LOGGER.error("RestClient调用服务出现异常: " + e.getMessage(), e);
        }
        return jsonObject;
	}
	
	
}
