package com.abc12366.bangbang.web;

import com.abc12366.bangbang.model.bo.*;
import com.abc12366.bangbang.service.FollowLecturerService;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户关注讲师表控制类
 *
 * @author lizhongwei
 * @date 2017-03-12
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/followLecturer", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class FollowLecturerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FollowLecturerController.class);

    @Autowired
    private FollowLecturerService followLecturerService;

    /**
     * 新增-关注和取消关注
     */
    @PostMapping
    public ResponseEntity insert(@Valid @RequestBody FollowLecturerBO followLecturerBO) {
        LOGGER.info("{}:{}", followLecturerBO);
        FollowLecturerBO FollowLecturerBO = followLecturerService.insert(followLecturerBO);
        return ResponseEntity.ok(Utils.kv("data", FollowLecturerBO));
    }

    /**
     * 查询列表
     */
    @GetMapping
    public ResponseEntity selectList(
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "lecturerId", required = false) String lecturerId,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
            @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        LOGGER.info("{}:{}:{}:{}:{}:{}", userId, page, size);
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("lecturerId",lecturerId);
        map.put("status",status);
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<FollowLecturerBO> FollowLecturerList = followLecturerService.selectList(map);
        PageInfo<FollowLecturerBO> pageInfo = new PageInfo<>(FollowLecturerList);
        LOGGER.info("{}", FollowLecturerList);
        return (FollowLecturerList == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(), "total", pageInfo.getTotal()));
    }

    /**
     * 查询列表详解
     */
    @GetMapping(path = "/list")
    public ResponseEntity selectBOList(
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "lecturerId", required = false) String lecturerId,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
            @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        LOGGER.info("{}:{}:{}:{}:{}:{}", userId, page, size);
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("lecturerId",lecturerId);
        map.put("status",status);
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<FollowLecturerBO> FollowLecturerList = followLecturerService.selectBOList(map);
        PageInfo<FollowLecturerBO> pageInfo = new PageInfo<>(FollowLecturerList);
        LOGGER.info("{}", FollowLecturerList);
        return (FollowLecturerList == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(), "total", pageInfo.getTotal()));
    }

    /**
     * 单个查询
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity selectOne(@PathVariable String id) {
        LOGGER.info("{}", id);
        FollowLecturerBO FollowLecturerBO = followLecturerService.selectFollowLecturerBO(id);
        return ResponseEntity.ok(Utils.kv("data", FollowLecturerBO));
    }

    /**
     * 修改-关注和取消关注
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity update(@PathVariable String id, @Valid @RequestBody FollowLecturerBO followLecturerBO,
                                 HttpServletRequest request) {
        LOGGER.info("{}", id);
        followLecturerBO.setId(id);
        FollowLecturerBO FollowLecturerBO = followLecturerService.update(followLecturerBO);
        return ResponseEntity.ok(Utils.kv("data", FollowLecturerBO));
    }

    /**
     * 删除
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable("id") String id, HttpServletRequest request) {
        LOGGER.info("{}", id);
        String userId = request.getHeader(Constant.USER_TOKEN_HEAD);
        followLecturerService.delete(id, userId);
        return ResponseEntity.ok(Utils.kv());
    }
}
