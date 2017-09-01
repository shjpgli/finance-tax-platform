package com.abc12366.uc.web;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.bo.PasswordUpdateBO;
import com.abc12366.uc.model.bo.UserBO;
import com.abc12366.uc.model.bo.UserUpdateBO;
import com.abc12366.uc.service.AuthService;
import com.abc12366.uc.service.IWxGzhService;
import com.abc12366.uc.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User测试控制器类，包含CRUD接口；以常规JSON形式返回数据
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-20 3:18 PM
 * @since 2.0.0
 */
@RestController
@RequestMapping(path = "/user", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;
    
    @Autowired
    private IWxGzhService iWxGzhService;
    
    @SuppressWarnings("rawtypes")
	@PutMapping("/changeheadbywx/{userid}/{mediaid}")
    public ResponseEntity changeHeadByWx(@PathVariable("userid")String userid,@PathVariable("mediaid")String mediaid){
    	String filePath=iWxGzhService.getWxDownFilePath(userid, mediaid);
    	
    	UserUpdateBO userUpdateDTO=new UserUpdateBO();
    	userUpdateDTO.setId(userid);
    	userUpdateDTO.setUserPicturePath(filePath);
    	UserBO user = userService.update(userUpdateDTO);
        LOGGER.info("{}", user);
        return ResponseEntity.ok(Utils.kv("data", user));
    }
    

    //查询用户列表，支持多标签查询
    @GetMapping
    public ResponseEntity selectList(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String nickname,
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) String tagName,
            @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
            @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        LOGGER.info("{}:{}:{}:{}:{}:{}:{}", username, phone, nickname, status, tagName, page, size);
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        Map<String, Object> map = new HashMap<>();
        if (username != null && StringUtils.isEmpty(username)) {
            username = null;
        }
        if (phone != null && StringUtils.isEmpty(phone)) {
            phone = null;
        }
        if (nickname != null && StringUtils.isEmpty(nickname)) {
            nickname = null;
        }
        if (status != null && StringUtils.isEmpty(status)) {
            status = null;
        }
        if (tagName != null && StringUtils.isEmpty(tagName)) {
            tagName = null;
        }
        map.put("username", username);
        map.put("phone", phone);
        map.put("nickname", nickname);
        map.put("status", status);
        map.put("tagName", tagName);
        List<UserBO> userList = userService.selectList(map);
        LOGGER.info("{}", userList);
        return (userList == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) userList, "total", ((Page) userList).getTotal()));
    }

    //根据用户ID查询用户
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> selectOne(@PathVariable String id) {
        LOGGER.info("{}", id);
        Map map = userService.selectOne(id);
        LOGGER.info("{}", map);
        return (map == null) ?
                ResponseEntity.ok(Utils.kv("user", null, "user_extend", null)) :
                ResponseEntity.ok(Utils.kv("user", map.get("user"), "user_extend", map.get("user_extend")));
    }

    //根据用户名或者电话查询用户
    @GetMapping(path = "/u/{usernameOrPhone}")
    public ResponseEntity selectByUsernameOrPhone(@PathVariable String usernameOrPhone) {
        LOGGER.info("{}", usernameOrPhone);
        UserBO user = userService.selectByUsernameOrPhone(usernameOrPhone);
        if (user == null) {
            throw new ServiceException(4018);
        }
        LOGGER.info("{}", user);
        return ResponseEntity.ok(Utils.kv("data", user));
    }

    //更新用户信息
    @PutMapping(path = "/{id}")
    public ResponseEntity update(@Valid @RequestBody UserUpdateBO userUpdateDTO, @PathVariable String id) {
        LOGGER.info("{}", userUpdateDTO);
        userUpdateDTO.setId(id);
        UserBO user = userService.update(userUpdateDTO);
        LOGGER.info("{}", user);
        return ResponseEntity.ok(Utils.kv("data", user));
    }

    //删除用户
    @DeleteMapping(path = "/{userId}")
    public ResponseEntity delete(@PathVariable String userId) {
        LOGGER.info("{}", userId);
        userService.delete(userId);
        return ResponseEntity.ok(Utils.kv());
    }

    // 用户token校验,根据用户token获取用户并刷新token
    @GetMapping(path = "/token/{token}")
    public ResponseEntity authAndRefreshToken(@PathVariable String token, HttpServletRequest request) {
        LOGGER.info("{}", token);

        // token校验
        boolean isAuth = authService.isAuthentication(token, request);
        if (!isAuth) {
            return ResponseEntity.ok().build();
        }
        // 根据用户token获取用户
        UserBO userBO = userService.authAndRefreshToken(token);
        LOGGER.info("{}", userBO);
        return ResponseEntity.ok(Utils.kv("data", userBO));
    }

    //用户修改密码
    @PutMapping(path = "/password")
    public ResponseEntity updatePassword(@Valid @RequestBody PasswordUpdateBO passwordUpdateBO, HttpServletRequest
            request) {
        LOGGER.info("{}:{}", passwordUpdateBO, request);
        Boolean message = userService.updatePassword(passwordUpdateBO, request);
        LOGGER.info("{}", message);
        return ResponseEntity.ok(Utils.kv("data", message));
    }
    /**
     * 启用、禁用
     */
    @PutMapping(path = "/{id}/{status}")
    public ResponseEntity enableOrDisable(@PathVariable String id, @PathVariable String status) {
        LOGGER.info("{}:{}", id, status);
        userService.enableOrDisable(id, status);
        return ResponseEntity.ok(Utils.kv());
    }

    @PostMapping(path = "/test")
    public ResponseEntity test(){
        return ResponseEntity.ok(Utils.kv());
    }

//    @PutMapping(path = "/updatevip")
//    public ResponseEntity updateVip(){
//        userService.updateUserVipInfo("7f6c2464-5d6b-4863-bc52-c1bafc4e503a", "LV2");
//        return ResponseEntity.ok(Utils.kv());
//    }
}
