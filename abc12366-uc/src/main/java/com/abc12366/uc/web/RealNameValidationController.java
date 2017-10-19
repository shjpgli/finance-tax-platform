package com.abc12366.uc.web;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.bo.UserExtendBO;
import com.abc12366.uc.model.bo.UserExtendListBO;
import com.abc12366.uc.model.bo.UserExtendUpdateBO;
import com.abc12366.uc.service.RealNameValidationService;
import com.abc12366.uc.util.AdminUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实名认证接口控制器
 * Admin: liuguiyao<435720953@qq.com>
 * Date: 2017-07-04
 * Time: 10:29
 */
@RestController
@RequestMapping(path = "/realnamevalidation", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class RealNameValidationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RealNameValidationController.class);

    @Autowired
    private RealNameValidationService realNameValidationService;

    /**
     * 查询用户实名认证列表
     * @param username
     * @param status
     * @param realName
     * @param phone
     * @param validStatus
     * @param page
     * @param size
     * @return
     */
    @GetMapping
    public ResponseEntity selectList(@RequestParam(required = false) String username,
    		                         @RequestParam(required = false) Boolean status,
    		                         @RequestParam(required = false) String realName,
    		                         @RequestParam(required = false) String phone,
    		                         @RequestParam(required = false) String validStatus,
                                     @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        LOGGER.info("{}:{}:{}", username, page, size);
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        if (StringUtils.isEmpty(username)) {
            username = null;
        }
        if (StringUtils.isEmpty(realName)) {
        	realName = null;
        }
        if (StringUtils.isEmpty(phone)) {
        	phone = null;
        }
        if (StringUtils.isEmpty(validStatus)) {
        	validStatus = null;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("status", status);
        map.put("realName", realName);
        map.put("phone", phone);
        map.put("validStatus", validStatus);
        List<UserExtendListBO> userExtendBOList = realNameValidationService.selectList(map);
        return (userExtendBOList == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) userExtendBOList, "total", ((Page) userExtendBOList)
                        .getTotal()));
    }

    @PutMapping(path = "/{userId}/{validStatus}")
    public ResponseEntity realNameValidate(@PathVariable String userId, @PathVariable String validStatus, @Valid
    @RequestBody(required = false) UserExtendUpdateBO userExtendUpdateBO, HttpServletRequest request) throws ParseException {
        LOGGER.info("{}:{}:{}", userId, validStatus, userExtendUpdateBO);
        //必须admin用户登录才能操作
        AdminUtil.getAdminId(request);
        UserExtendBO userExtendBO = realNameValidationService.validate(userId.trim(), validStatus.trim(), userExtendUpdateBO);
        return ResponseEntity.ok(Utils.kv("data", userExtendBO));
    }

}
