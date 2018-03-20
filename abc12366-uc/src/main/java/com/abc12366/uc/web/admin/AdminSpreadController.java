package com.abc12366.uc.web.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.service.admin.IAdminSpreadService;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;

/**
 * 销售推广相关控制器
 * @author Administrator
 *
 */
@SuppressWarnings("rawtypes")
@RestController
@RequestMapping(path = "/admin/spread", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class AdminSpreadController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminSpreadController.class);

	@Autowired
	private IAdminSpreadService adminSpreadService;
	
	@GetMapping("/myconstomers")
	public ResponseEntity myConstomers(@RequestParam String recommend,
			                           @RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                       @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                       @RequestParam(required=false) String username,
                                       @RequestParam(required=false) String realname,
                                       @RequestParam(required=false) String phone,
                                       @RequestParam(required=false) String nickname,
                                       @RequestParam(required=false) String createTime){
		LOGGER.info("查询我的客户信息:{},{},{},{},{},{}",recommend,username,realname,phone,nickname,createTime);
		Map<String, Object> map = new HashMap<>(16);
		map.put("recommend",recommend);
		map.put("username",username);
		map.put("realname",realname);
		map.put("phone",phone);
		map.put("nickname",nickname);
		map.put("createTime",createTime);
		List<Map<String, Object>> dataList = adminSpreadService.myConstomers(map,pageNum,pageSize);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(dataList);
        LOGGER.info("{}", JSONArray.toJSONString(pageInfo));
        return ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(), "total", pageInfo.getTotal()));
	}
	
}
