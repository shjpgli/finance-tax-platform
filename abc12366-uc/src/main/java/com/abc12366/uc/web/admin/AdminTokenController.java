package com.abc12366.uc.web.admin;

import com.abc12366.gateway.util.Constant;
import com.abc12366.uc.service.admin.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lizhongwei
 * @create 2017-05-02 10:08 AM
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/admin/token", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class AdminTokenController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminTokenController.class);

    @Autowired
    private AdminService adminService;

    /**
     * 验证token
     *
     * @param token
     * @return
     */
    @PostMapping(value = "/check/{token}")
    public ResponseEntity checkToken(@PathVariable("token") String token) {
        LOGGER.info("验证token{}" + token);
        Boolean isToken = adminService.checkToken(token);
        return ResponseEntity.ok(isToken);
    }

    /**
     * 刷新token
     *
     * @param token
     * @return
     */
    @PostMapping(value = "/refresh/{token}")
    public ResponseEntity refreshToken(@PathVariable("token") String token) {
        LOGGER.info("刷新token{}" + token);
        Boolean isToken = adminService.refreshToken(token);
        return ResponseEntity.ok(isToken);
    }

}
