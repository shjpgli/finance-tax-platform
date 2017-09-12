package com.abc12366.uc.web;

import com.abc12366.gateway.util.Constant;
import com.abc12366.uc.service.TodoTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-09-12
 * Time: 14:36
 */
@RestController
@RequestMapping(path = "/todo/task", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class TodoTaskController {
    private static Logger LOGGER = LoggerFactory.getLogger(TodoTaskController.class);

    @Autowired
    private TodoTaskService todoTaskService;

    @GetMapping(path = "/type/userId")
    public ResponseEntity selectList(@PathVariable("type") String type, @PathVariable("userId") String userId){
        LOGGER.info("{}:{}", type, userId);
        todoTaskService.selectList(type, userId);
        return ResponseEntity.ok(null);
    }
}
