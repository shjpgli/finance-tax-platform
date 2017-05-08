package com.abc12366.uc.web;

import com.abc12366.common.util.Constant;
import com.abc12366.uc.model.bo.UserExtendBO;
import com.abc12366.uc.service.UserExtendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 用户扩展信息控制器
 *
 * @author liuguiyao
 * @create 2017-05-05 10:08 AM
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "userextend", headers = Constant.VERSION_HEAD + "=1")
public class UserExtendController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserExtendController.class);

    @Autowired
    private UserExtendService userExtendService;

    @GetMapping(path = "/{userId}")
    public ResponseEntity selectOne(@PathVariable String userId) {
        LOGGER.info("{}", userId);
        userExtendService.selectOne(userId);
        return ResponseEntity.ok(null);
    }

    @PostMapping
    public ResponseEntity insert(@RequestBody UserExtendBO userExtendBO) {
        LOGGER.info("{}", userExtendBO);
        UserExtendBO userExtendBO1 = userExtendService.insert(userExtendBO);
        LOGGER.info("{}", userExtendBO1);
        return ResponseEntity.ok(userExtendBO1);
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseEntity delete(String userId) {
        LOGGER.info("{}", userId);
        UserExtendBO userExtendBO = userExtendService.delete(userId);
        LOGGER.info("{}", userExtendBO);
        return ResponseEntity.ok(userExtendBO);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody UserExtendBO userExtendBO) {
        LOGGER.info("{}", userExtendBO);
        UserExtendBO userExtendBO1 = userExtendService.update(userExtendBO);
        LOGGER.info("{}", userExtendBO1);
        return ResponseEntity.ok(userExtendBO1);
    }
}
