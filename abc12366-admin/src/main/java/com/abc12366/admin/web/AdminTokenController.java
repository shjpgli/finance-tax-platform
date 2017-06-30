package com.abc12366.admin.web;

import com.abc12366.admin.service.UserService;
import com.abc12366.common.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author lizhongwei
 * @create 2017-05-02 10:08 AM
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/admintoken", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class AdminTokenController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminTokenController.class);

    @Autowired
    private UserService userService;

    /**
     * 验证token
     * @param token
     * @return
     */
    @PostMapping(value = "/check/{token}")
    public ResponseEntity checkToken(@PathVariable("token") String token) {
        LOGGER.info("验证token"+token);
        Boolean isToken = userService.checkToken(token);
        return ResponseEntity.ok(isToken);
    }

    /**
     * 刷新token
     * @param token
     * @return
     */
    @PostMapping(value = "/refresh/{token}")
    public ResponseEntity refreshToken(@PathVariable("token") String token) {
        LOGGER.info("刷新token"+token);
        Boolean isToken = userService.refreshToken(token);
        return ResponseEntity.ok(isToken);
    }

}
