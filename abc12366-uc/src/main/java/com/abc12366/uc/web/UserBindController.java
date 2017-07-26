package com.abc12366.uc.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.service.UserBindService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 用户绑定办税身份控制器类，以常规JSON形式返回数据
 *
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-25
 * Time: 16:22
 */
@RestController
@RequestMapping(headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class UserBindController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserBindController.class);

    @Autowired
    private UserBindService userBindService;

    @PostMapping(path = "/bind/dzsb")
    public ResponseEntity userDzsbBind(@Valid @RequestBody UserDzsbBO userDzsbBO) {
        LOGGER.info("{}", userDzsbBO);
        UserDzsbBO user_dzsb = userBindService.dzsbBind(userDzsbBO);
        LOGGER.info("{}", user_dzsb);
        return ResponseEntity.ok(Utils.kv("data", user_dzsb));
    }

    @PutMapping(path = "/unbind/dzsb/{id}")
    public ResponseEntity userDzsbUnbind(@PathVariable String id) {
        LOGGER.info("{}", id);
        userBindService.dzsbUnbind(id);
        return ResponseEntity.ok(Utils.kv());
    }

    @GetMapping(path = "/bind/dzsb/{userId}")
    public ResponseEntity getUserDzsbBind(@PathVariable String userId,
                                          @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                          @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        LOGGER.info("{}", userId);
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<UserDzsbListBO> userDzsbBOList = userBindService.getUserDzsbBind(userId);
        LOGGER.info("{}", userDzsbBOList);
        return (userDzsbBOList == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) userDzsbBOList, "total", ((Page) userDzsbBOList).getTotal()));
    }

    @GetMapping(path = "/bind/hngs/{userId}")
    public ResponseEntity getUserhngsBind(@PathVariable String userId,
                                          @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                          @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        LOGGER.info("{}", userId);
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<UserHngsListBO> userHngsBOList = userBindService.getUserhngsBind(userId);
        LOGGER.info("{}", userHngsBOList);
        return (userHngsBOList == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) userHngsBOList, "total", ((Page) userHngsBOList).getTotal()));
    }

    @GetMapping(path = "/bind/hnds/{userId}")
    public ResponseEntity getUserhndsBind(@PathVariable String userId,
                                          @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                          @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        LOGGER.info("{}", userId);
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<UserHndsBO> userHngsBOList = userBindService.getUserhndsBind(userId);
        LOGGER.info("{}", userHngsBOList);
        return (userHngsBOList == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) userHngsBOList, "total", ((Page) userHngsBOList).getTotal()));
    }

    @PostMapping(path = "/bind/hngs")
    public ResponseEntity userHngsBind(@Valid @RequestBody UserHngsBO userHngsBO) {
        LOGGER.info("{}", userHngsBO);
        UserHngsBO user_hngs = userBindService.hngsBind(userHngsBO);
        return ResponseEntity.ok(Utils.kv("data", user_hngs));
    }

    @PutMapping(path = "/unbind/hngs/{id}")
    public ResponseEntity userHngsUnbind(@PathVariable String id) {
        LOGGER.info("{}", id);
        userBindService.hngsUnbind(id);
        return ResponseEntity.ok(Utils.kv());
    }

    @PostMapping(path = "/bind/hnds")
    public ResponseEntity userHndsBind(@Valid @RequestBody UserHndsBO userHndsBO) {
        LOGGER.info("{}", userHndsBO);
        UserHndsBO user_hnds = userBindService.hndsBind(userHndsBO);
        return ResponseEntity.ok(Utils.kv("data", user_hnds));
    }

    @PutMapping(path = "/unbind/hnds/{id}")
    public ResponseEntity userHndsUnbind(@PathVariable String id) {
        LOGGER.info("{}", id);
        userBindService.hndsUnbind(id);
        return ResponseEntity.ok(Utils.kv());
    }
}
