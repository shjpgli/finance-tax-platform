package com.abc12366.uc.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.model.bo.UserDzsbBO;
import com.abc12366.uc.model.bo.UserHndsBO;
import com.abc12366.uc.model.bo.UserHngsBO;
import com.abc12366.uc.service.UserBindService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 用户绑定办税身份控制器类，以常规JSON形式返回数据
 *
 * @author liuguiyao<435720953@qq.com.com>
 * @create 2017-05-15 10:18 PM
 * @since 2.0.0
 */
@RestController
@RequestMapping(headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class UserBindController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserBindController.class);

    @Autowired
    private UserBindService userBindService;

    @PostMapping(path = "/bind/dzsb")
    public ResponseEntity userDzsbBind(@RequestBody UserDzsbBO userDzsbBO) {
        LOGGER.info("{}", userDzsbBO);
        UserDzsbBO user_dzsb = userBindService.dzsbBind(userDzsbBO);
        LOGGER.info("{}", user_dzsb);
        return (user_dzsb != null) ? ResponseEntity.ok(user_dzsb) : new ResponseEntity(Utils.bodyStatus(4101), HttpStatus.BAD_REQUEST);
    }

    @PutMapping(path = "/unbind/dzsb/{id}")
    public ResponseEntity userDzsbUnbind(@PathVariable String id) {
        LOGGER.info("{}", id);
        boolean result = userBindService.dzsbUnbind(id);
        return result ? ResponseEntity.ok(null) : new ResponseEntity(Utils.bodyStatus(4102), HttpStatus.BAD_REQUEST);
    }

    @PostMapping(path = "/bind/hngs")
    public ResponseEntity userHngsBind(@RequestBody UserHngsBO userHngsBO) {
        LOGGER.info("{}", userHngsBO);
        UserHngsBO user_hngs = userBindService.hngsBind(userHngsBO);
        return (user_hngs != null) ? ResponseEntity.ok(user_hngs) : new ResponseEntity(Utils.bodyStatus(4101), HttpStatus.BAD_REQUEST);
    }

    @PutMapping(path = "/unbind/hngs/{id}")
    public ResponseEntity userHngsUnbind(@PathVariable String id) {
        LOGGER.info("{}", id);
        boolean result = userBindService.hngsUnbind(id);
        return result ? ResponseEntity.ok(null) : new ResponseEntity(Utils.bodyStatus(4102), HttpStatus.BAD_REQUEST);
    }

    @PostMapping(path = "/bind/hnds")
    public ResponseEntity userHndsBind(@RequestBody UserHndsBO userHndsBO) {
        LOGGER.info("{}", userHndsBO);
        UserHndsBO user_hnds = userBindService.hndsBind(userHndsBO);
        return (user_hnds != null) ? ResponseEntity.ok(user_hnds) : new ResponseEntity(Utils.bodyStatus(4101), HttpStatus.BAD_REQUEST);
    }

    @PutMapping(path = "/unbind/hnds/{id}")
    public ResponseEntity userHndsUnbind(@PathVariable String id) {
        LOGGER.info("{}", id);
        boolean result = userBindService.hndsUnbind(id);
        return result ? ResponseEntity.ok(null) : new ResponseEntity(Utils.bodyStatus(4102), HttpStatus.BAD_REQUEST);
    }
}
