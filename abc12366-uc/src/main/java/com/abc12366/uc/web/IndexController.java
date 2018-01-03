package com.abc12366.uc.web;

import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.job.DzsbJob;
import com.abc12366.uc.webservice.AcceptClient;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-27 3:58 PM
 * @since 1.0.0
 */
@RestController
public class IndexController {
	
	@Autowired
	private AcceptClient client;

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @GetMapping("/")
    public ResponseEntity index() {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/test")
    public ResponseEntity test() {
    	 Map<String, String> map = new HashMap<>();
         map.put("serviceid", "GY01");
         map.put("ywid", "NOTIFY_CJXX");
         map.put("lrrq", "2017-11-02 15:26:24");
         map.put("maxcount", "20");
         DzsbJob job=client.processYw(map);
         System.out.println(JSONObject.toJSONString(job));
         return ResponseEntity.ok(Utils.kv("message", "OK"));
    }
}
