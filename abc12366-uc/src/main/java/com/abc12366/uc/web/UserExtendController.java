package com.abc12366.uc.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.model.bo.UserExtendBO;
import com.abc12366.uc.model.bo.UserExtendUpdateBO;
import com.abc12366.uc.service.UserExtendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户扩展信息控制器
 *
 * @author liuguiyao
 * @create 2017-05-05 10:08 AM
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/user/extend", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class UserExtendController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserExtendController.class);

    @Autowired
    private UserExtendService userExtendService;

    @GetMapping(path = "/{id}")
    public ResponseEntity selectOne(@PathVariable String id) {
        LOGGER.info("{}", id);
        UserExtendBO user_extend = userExtendService.selectOne(id);
        LOGGER.info("{}", user_extend);
        return ResponseEntity.ok(Utils.kv("data", user_extend));
    }

    @PostMapping
    public ResponseEntity insert(@Valid @RequestBody UserExtendBO userExtendBO) {
        LOGGER.info("{}", userExtendBO);
        UserExtendBO user_extend = userExtendService.insert(userExtendBO);
        LOGGER.info("{}", user_extend);
        return ResponseEntity.ok(Utils.kv("data", user_extend));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        LOGGER.info("{}", id);
        userExtendService.delete(id);
        return ResponseEntity.ok(Utils.kv());
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity update(@Valid @RequestBody UserExtendUpdateBO userExtendUpdateBO, @PathVariable String id) {
        LOGGER.info("{}:{}", userExtendUpdateBO, id);
        userExtendUpdateBO.setUserId(id);
        UserExtendBO user_extend = userExtendService.update(userExtendUpdateBO);
        LOGGER.info("{}", user_extend);
        return ResponseEntity.ok(Utils.kv("data", user_extend));
    }
}
