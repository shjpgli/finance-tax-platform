package com.abc12366.uc.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.model.bo.UserTaskBO;
import com.abc12366.uc.model.bo.UserTaskInsertBO;
import com.abc12366.uc.model.bo.UserTaskUpdateBO;
import com.abc12366.uc.service.UserTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * User: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-24
 * Time: 11:39
 */
@RestController
@RequestMapping(path = "/task", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class UserTaskController {
    private static final Logger LOGGER = LoggerFactory.getLogger(VipPrivilegeController.class);

    @Autowired
    private UserTaskService userTaskService;

//    @GetMapping(path = "/{userId}")
//    public ResponseEntity select(@PathVariable String userId) {
//        LOGGER.info("{}", userId);
//        UserTaskBO userTaskBO = userTaskService.insert(userId);
//        LOGGER.info("{}", userTaskBO);
//        return ResponseEntity.ok(Utils.kv("data",userTaskBO));
//    }

    @PostMapping(path = "/{userId}")
    public ResponseEntity insert(@Valid @RequestBody UserTaskInsertBO userTaskInsertBO, @PathVariable String userId) {
        LOGGER.info("{}:{}", userTaskInsertBO, userId);
        UserTaskBO userTaskBO = userTaskService.insert(userTaskInsertBO, userId);
        LOGGER.info("{}", userTaskBO);
        return ResponseEntity.ok(Utils.kv("data",userTaskBO));
    }

    @PutMapping(path = "/{userId}/{id}")
    public ResponseEntity update(@RequestBody UserTaskUpdateBO userTaskUpdateBO, @PathVariable String userId, @PathVariable String id) {
        LOGGER.info("{}:{}:{}", userTaskUpdateBO, userId, id);
        UserTaskBO userTaskBO = userTaskService.update(userTaskUpdateBO, userId, id);
        LOGGER.info("{}", userTaskBO);
        return ResponseEntity.ok(Utils.kv("data", userTaskBO));
    }

    @DeleteMapping(path = "/{userId}/{id}")
    public ResponseEntity delete(@PathVariable String userId, @PathVariable String id) {
        LOGGER.info("{}:{}", userId, id);
        Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("id", id);
        userTaskService.delete(map);
        return ResponseEntity.ok(Utils.kv());
    }
}
