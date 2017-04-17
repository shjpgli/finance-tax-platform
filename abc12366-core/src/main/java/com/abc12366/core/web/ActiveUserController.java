package com.abc12366.core.web;

import com.abc12366.core.model.bo.ActiveUser;
import com.abc12366.core.service.ActiveUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User测试控制器类，包含CRUD接口；以常规JSON形式返回数据
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-20 3:18 PM
 * @since 2.0.0
 */
@RestController
@RequestMapping(path = "/activeuser", headers = "version=1")
public class ActiveUserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActiveUserController.class);

    @Autowired
    private ActiveUserService activeUserService;

    @GetMapping
    public ResponseEntity selectList() {
        List<ActiveUser> users = activeUserService.selectList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity selectOne(@PathVariable String id) {
        ActiveUser user = activeUserService.selectOne(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping()
    public ResponseEntity insert(@RequestBody ActiveUser user) {
        LOGGER.info("{}", user);
        ActiveUser activeUser = activeUserService.insert(user);
        return ResponseEntity.ok(activeUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        int rows = activeUserService.delete(id);
        return ResponseEntity.ok(rows);
    }
}
