package com.abc12366.bangbang.web;

import com.abc12366.bangbang.service.FollowService;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-06
 * Time: 16:21
 */
@RestController
@RequestMapping(path = "/follow", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class FollowController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FollowController.class);

    @Autowired
    private FollowService followService;

    //关注用户
    @PostMapping(path = "/{followedUserId}")
    public ResponseEntity insert(@PathVariable String followedUserId, HttpServletRequest request){
        LOGGER.info("{}", followedUserId);
        followService.insert(followedUserId, request);
        return ResponseEntity.ok(Utils.kv());
    }

    //取消关注
    @DeleteMapping(path = "/{followedUserId}")
    public ResponseEntity delete(@PathVariable String followedUserId){
        LOGGER.info("{}", followedUserId);
        followService.delete(followedUserId);
        return ResponseEntity.ok(Utils.kv());
    }

    //查看已关注用户
    @GetMapping(path = "/{userId}")
    public ResponseEntity selectHaveFollowedList(@PathVariable String userId){
        LOGGER.info("{}", userId);
        followService.selectHaveFollowedList(userId);
        return ResponseEntity.ok(Utils.kv());
    }

    //查询谁关注了我
    @GetMapping(path = "/followed/{userId}")
    public ResponseEntity selectMyFollowerList(@PathVariable String userId){
        LOGGER.info("{}", userId);
        followService.selectMyFollowerList(userId);
        return ResponseEntity.ok(Utils.kv());
    }

    //查询用户被关注次数
    @GetMapping(path = "count/follow/{followedUserId}")
    public ResponseEntity selectFollowedCount(@PathVariable String followedUserId){
        LOGGER.info("{}", followedUserId);
        followService.selectFollowedCount(followedUserId);
        return ResponseEntity.ok(Utils.kv());
    }
}
