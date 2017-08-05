package com.abc12366.uc.web;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.bo.UserExtendBO;
import com.abc12366.uc.model.bo.UserExtendListBO;
import com.abc12366.uc.model.bo.UserExtendUpdateBO;
import com.abc12366.uc.service.RealNameValidationService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
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

    @GetMapping
    public ResponseEntity selectList(@RequestParam(required = false) String username,
                                     @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        LOGGER.info("{}:{}:{}", username, page, size);
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        if (username.equals("")) {
            username = null;
        }
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        List<UserExtendListBO> userExtendBOList = realNameValidationService.selectList(map);
        return (userExtendBOList == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) userExtendBOList, "total", ((Page) userExtendBOList)
                        .getTotal()));
    }

    @PutMapping(path = "/{userId}/{validStatus}")
    public ResponseEntity realNameValidate(@PathVariable String userId, @PathVariable String validStatus, @Valid
    @RequestBody UserExtendUpdateBO userExtendUpdateBO) throws ParseException {
        LOGGER.info("{}:{}:{}", userId, validStatus, userExtendUpdateBO);
        UserExtendBO userExtendBO = realNameValidationService.validate(userId, validStatus, userExtendUpdateBO);
        return ResponseEntity.ok(Utils.kv("data", userExtendBO));
    }

}
