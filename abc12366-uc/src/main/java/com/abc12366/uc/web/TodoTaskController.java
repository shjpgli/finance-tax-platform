package com.abc12366.uc.web;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.TodoTask;
import com.abc12366.uc.service.TodoTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(path = "/{type}/{userId}")
    public ResponseEntity selectList(@PathVariable("type") String type, @PathVariable("userId") String userId){
        LOGGER.info("{}:{}", type, userId);
        List<TodoTask> taskList = todoTaskService.selectList(type, userId);
        return ResponseEntity.ok(Utils.kv("dataList",taskList));
    }

    /**
     * 查询用户日常任务列表
     * @param userId
     * @return
     */
    @GetMapping(path = "/normal/{userId}")
    public ResponseEntity selectNormalTaskList(@PathVariable("userId") String userId){
        List<TodoTask> taskList = todoTaskService.selectNormalTaskList(userId);
        return ResponseEntity.ok(Utils.kv("dataList",taskList));
    }

    /**
     * 查询用户一次性（多为成长任务）任务列表
     * @param userId
     * @return
     */
    @GetMapping(path = "/onetime/{userId}")
    public ResponseEntity selectOnetimeTaskList(@PathVariable("userId") String userId){
        List<TodoTask> taskList = todoTaskService.selectOnetimeTaskList(userId);
        return ResponseEntity.ok(Utils.kv("dataList",taskList));
    }

    /**
     * 查询用户日常任务列表
     * @param userId
     * @return
     */
    @GetMapping(path = "/special/{userId}")
    public ResponseEntity selectSpecialTaskList(@PathVariable("userId") String userId){
        List<TodoTask> taskList = todoTaskService.selectSpecialTaskList(userId);
        return ResponseEntity.ok(Utils.kv("dataList",taskList));
    }

    @PostMapping
    public ResponseEntity generateTodoTaskList(){
        String userId = Utils.getUserId();
        todoTaskService.generateTodoTaskList(userId, "2");
        return ResponseEntity.ok(Utils.kv());
    }
}
