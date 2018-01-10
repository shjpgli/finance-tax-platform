package com.abc12366.uc.web;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.service.UserBindServiceNew;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 用户绑定办税身份控制器类，以常规JSON形式返回数据
 *
 * @author liuguiyao 435720953@qq.com
 * @date 2017-07-25
 */
@RestController
@RequestMapping(headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class UserBindControllerNew {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserBindControllerNew.class);

    @Autowired
    private UserBindServiceNew userBindService;

    /**
     * 用户绑定纳税人（电子申报）
     *
     * @param userDzsbInsertBO 纳税人信息
     * @param request          HttpServletRequest
     * @return 纳税人信息
     * @throws Exception 访问网络、解包异常
     */
    @SuppressWarnings("rawtypes")
	@PostMapping(path = "/bind/dzsbnew")
    public ResponseEntity userDzsbBind(@Valid @RequestBody UserDzsbInsertBO userDzsbInsertBO, HttpServletRequest
            request) throws Exception {
        LOGGER.info("{}:{}", userDzsbInsertBO, request);
        UserDzsbBO userDzsb = userBindService.dzsbBind(userDzsbInsertBO, request);
        LOGGER.info("{}", userDzsb);

        return ResponseEntity.ok(Utils.kv("data", userDzsb));
    }

   
    /**
     * 绑定湖南国税用户
     *
     * @param userHngsInsertBO 湖南国税用户信息
     * @param request          HttpServletRequest
     * @return 国税用户登陆信息
     * @throws Exception 网络异常
     */
    @SuppressWarnings("rawtypes")
	@PostMapping(path = "/bind/hngsnew")
    public ResponseEntity userHngsBind(@Valid @RequestBody UserHngsInsertBO userHngsInsertBO, HttpServletRequest
            request) throws Exception {
        LOGGER.info("{}:{}", userHngsInsertBO, request);
        UserHngsBO userHngs = userBindService.hngsBind(userHngsInsertBO, request);

        return ResponseEntity.ok(Utils.kv("data", userHngs));
    }

   
}
