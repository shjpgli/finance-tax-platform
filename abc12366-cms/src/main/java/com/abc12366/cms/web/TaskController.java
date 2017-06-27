package com.abc12366.cms.web;

import com.abc12366.cms.model.bo.IdsBo;
import com.abc12366.cms.model.bo.TaskBo;
import com.abc12366.cms.service.TaskService;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模型管理模块
 *
 * @author xieyanmao
 * @create 2017-06-27
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/task",headers = Constant.VERSION_HEAD + "=1")
public class TaskController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        Map<String, Object> dataMap = new HashMap<>();
        List<TaskBo> dataList = taskService.selectList(dataMap);
        LOGGER.info("{}", dataList);
        return ResponseEntity.ok(Utils.kv("dataList", (Page) dataList, "total", ((Page) dataList).getTotal()));
    }

    @PostMapping
    public ResponseEntity save(@RequestBody TaskBo taskBo) {
        LOGGER.info("{}", taskBo);
        //新增评论信息
        taskBo = taskService.save(taskBo);
        LOGGER.info("{}", taskBo);
        return ResponseEntity.ok(Utils.kv("data", taskBo));
    }

    @GetMapping(path = "/{taskId}")
    public ResponseEntity selectOne(@PathVariable String taskId) {
        LOGGER.info("{}", taskId);
        //查询评论信息
        TaskBo taskBo = taskService.selectTask(taskId);
        LOGGER.info("{}", taskBo);
        return ResponseEntity.ok(Utils.kv("data", taskBo));
    }

    @PutMapping(path = "/{taskId}")
    public ResponseEntity update(@PathVariable String taskId,
                                 @Valid @RequestBody TaskBo taskBo) {

        LOGGER.info("{}", taskBo);
        //更新评论信息
        taskBo = taskService.update(taskBo);
        LOGGER.info("{}", taskBo);
        return ResponseEntity.ok(Utils.kv("data", taskBo));
    }

    @DeleteMapping(path = "/{taskId}")
    public ResponseEntity delete(@PathVariable String taskId) {
        LOGGER.info("{}", taskId);
        //删除评论信息
        String rtn = taskService.delete(taskId);
        LOGGER.info("{}", rtn);
        return ResponseEntity.ok(Utils.kv("data", rtn));
    }

    @PostMapping(path = "/deleteList")
    public ResponseEntity deleteList(@RequestBody IdsBo idsBo) {
        LOGGER.info("{}", idsBo);
        //删除评论信息
        String rtn = taskService.deleteList(idsBo.getIds());
        LOGGER.info("{}", rtn);
        return ResponseEntity.ok(Utils.kv("data", idsBo));
    }




}
